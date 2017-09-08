package cn.bestsec.dcms.platform.impl.basis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Basis_AddBasisApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_AddBasisRequest;
import cn.bestsec.dcms.platform.api.model.Basis_AddBasisResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BasisConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

@Component
public class BasisAddBasis implements Basis_AddBasisApi {

    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_AddBasisResponse execute(Basis_AddBasisRequest request) throws ApiException {
        Integer basisId = request.getBasisId();
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(basisId);
        if (basis == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        if (basis.getBasisName() != null && !basis.getBasisName().isEmpty()) {
            basis = new FileLevelDecideBasis(basis.getBasisType(), null, null, basis.getBasisClass(), null);
        }
        basis.setBasisName(request.getBasisName());
        fileLevelDecideBasisDao.save(basis);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_addBasis)
                .admin(request.tokenWrapper().getAdmin()).operateDescription(
                        BasisConsts.Type.parse(basis.getBasisType()).getDescription(), request.getBasisName());
        return new Basis_AddBasisResponse(basis.getId());
    }

}
