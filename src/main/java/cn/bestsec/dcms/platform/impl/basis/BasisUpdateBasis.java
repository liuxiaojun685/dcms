package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_UpdateBasisApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_UpdateBasisRequest;
import cn.bestsec.dcms.platform.api.model.Basis_UpdateBasisResponse;
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
public class BasisUpdateBasis implements Basis_UpdateBasisApi {

    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_UpdateBasisResponse execute(Basis_UpdateBasisRequest request) throws ApiException {
        // 需要修改的定密依据
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null || basis.getBasisName() == null || basis.getBasisName().isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        List<FileLevelDecideBasis> list = fileLevelDecideBasisDao.findByBasisName(basis.getBasisName());
        for (FileLevelDecideBasis b : list) {
            if (request.getBasisName() != null && !request.getBasisName().isEmpty()) {
                b.setBasisName(request.getBasisName());
            }
        }
        fileLevelDecideBasisDao.save(list);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_updateBasis)
                .admin(request.tokenWrapper().getAdmin()).operateDescription(
                        BasisConsts.Type.parse(basis.getBasisType()).getDescription(), basis.getBasisName());

        return new Basis_UpdateBasisResponse();
    }

}
