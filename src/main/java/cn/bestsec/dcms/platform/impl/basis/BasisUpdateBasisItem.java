package cn.bestsec.dcms.platform.impl.basis;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_UpdateBasisItemApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_UpdateBasisItemRequest;
import cn.bestsec.dcms.platform.api.model.Basis_UpdateBasisItemResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BasisConsts;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改定密依据
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月5日 下午3:24:33
 */
@Component
public class BasisUpdateBasisItem implements Basis_UpdateBasisItemApi {

    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_UpdateBasisItemResponse execute(Basis_UpdateBasisItemRequest request) throws ApiException {
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        if (request.getBasisLevel() != null) {
            basis.setBasisLevel(request.getBasisLevel());
        }
        if (request.getBasisItem() != null && !request.getBasisItem().isEmpty()) {
            basis.setBasisItem(request.getBasisItem());
        }
        fileLevelDecideBasisDao.save(basis);
        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_updateItem)
                .admin(request.tokenWrapper().getAdmin())
                .operateDescription(BasisConsts.Type.parse(basis.getBasisType()).getDescription(),
                        FileConsts.Level.parse(basis.getBasisLevel()).getDescription(),
                        basis.getBasisName() + " " + basis.getBasisItem());
        return new Basis_UpdateBasisItemResponse();
    }

}
