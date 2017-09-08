package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_DeleteBasisApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_DeleteBasisRequest;
import cn.bestsec.dcms.platform.api.model.Basis_DeleteBasisResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BasisConsts;
import cn.bestsec.dcms.platform.consts.FileConsts;
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
public class BasisDeleteBasis implements Basis_DeleteBasisApi {
    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_DeleteBasisResponse execute(Basis_DeleteBasisRequest request) throws ApiException {
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null || basis.getBasisName() == null || basis.getBasisName().isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<FileLevelDecideBasis> list = fileLevelDecideBasisDao.findByBasisName(basis.getBasisName());
        fileLevelDecideBasisDao.delete(list);

        list = fileLevelDecideBasisDao.findByBasisClass(basis.getBasisClass());
        if (list.isEmpty()) {
            fileLevelDecideBasisDao
                    .save(new FileLevelDecideBasis(basis.getBasisType(), null, null, basis.getBasisClass(), null));
        }

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_deleteBasis)
                .admin(request.tokenWrapper().getAdmin())
                .operateDescription(BasisConsts.Type.parse(basis.getBasisType()).getDescription(),
                        FileConsts.Level.parse(basis.getBasisLevel()).getDescription(), basis.getBasisName());
        return new Basis_DeleteBasisResponse();
    }

}
