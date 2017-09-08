package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_DeleteBasisClassApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_DeleteBasisClassRequest;
import cn.bestsec.dcms.platform.api.model.Basis_DeleteBasisClassResponse;
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
public class BasisDeleteBasisClass implements Basis_DeleteBasisClassApi {
    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_DeleteBasisClassResponse execute(Basis_DeleteBasisClassRequest request) throws ApiException {
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null || basis.getBasisClass() == null || basis.getBasisClass().isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<FileLevelDecideBasis> list = fileLevelDecideBasisDao.findByBasisClass(basis.getBasisClass());
        fileLevelDecideBasisDao.delete(list);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_deleteClass)
                .admin(request.tokenWrapper().getAdmin()).operateDescription(
                        BasisConsts.Type.parse(basis.getBasisType()).getDescription(), basis.getBasisClass());
        return new Basis_DeleteBasisClassResponse();
    }

}
