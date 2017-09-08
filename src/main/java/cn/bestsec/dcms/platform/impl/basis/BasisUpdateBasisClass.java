package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_UpdateBasisClassApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_UpdateBasisClassRequest;
import cn.bestsec.dcms.platform.api.model.Basis_UpdateBasisClassResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BasisConsts;
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
public class BasisUpdateBasisClass implements Basis_UpdateBasisClassApi {

    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_UpdateBasisClassResponse execute(Basis_UpdateBasisClassRequest request) throws ApiException {
        // 需要修改的定密依据
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null || basis.getBasisClass() == null || basis.getBasisClass().isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        List<FileLevelDecideBasis> list = fileLevelDecideBasisDao.findByBasisClass(basis.getBasisClass());
        for (FileLevelDecideBasis b : list) {
            if (request.getBasisClass() != null && !request.getBasisClass().isEmpty()) {
                b.setBasisClass(request.getBasisClass());
            }
        }
        fileLevelDecideBasisDao.save(list);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_updateClass)
                .admin(request.tokenWrapper().getAdmin()).operateDescription(
                        BasisConsts.Type.parse(basis.getBasisType()).getDescription(), basis.getBasisClass());
        return new Basis_UpdateBasisClassResponse();
    }

}
