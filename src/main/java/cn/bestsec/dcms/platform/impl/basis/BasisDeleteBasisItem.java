package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_DeleteBasisItemApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_DeleteBasisItemRequest;
import cn.bestsec.dcms.platform.api.model.Basis_DeleteBasisItemResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BasisConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除定密依据
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午3:40:03
 */
@Component
public class BasisDeleteBasisItem implements Basis_DeleteBasisItemApi {
    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_DeleteBasisItemResponse execute(Basis_DeleteBasisItemRequest request) throws ApiException {
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        fileLevelDecideBasisDao.delete(basis);
        List<FileLevelDecideBasis> list = fileLevelDecideBasisDao.findByBasisName(basis.getBasisName());
        if (list.isEmpty()) {
            fileLevelDecideBasisDao.save(new FileLevelDecideBasis(basis.getBasisType(), basis.getBasisLevel(), null,
                    basis.getBasisClass(), basis.getBasisName()));
        }

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_deleteItem)
                .admin(request.tokenWrapper().getAdmin())
                .operateDescription(BasisConsts.Type.parse(basis.getBasisType()).getDescription(),
                        basis.getBasisName() + " " + basis.getBasisItem());
        return new Basis_DeleteBasisItemResponse();
    }

}
