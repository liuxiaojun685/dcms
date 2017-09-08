package cn.bestsec.dcms.platform.impl.basis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Basis_AddBasisClassApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Basis_AddBasisClassRequest;
import cn.bestsec.dcms.platform.api.model.Basis_AddBasisClassResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BasisConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

@Component
public class BasisAddBasisClass implements Basis_AddBasisClassApi {

    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_AddBasisClassResponse execute(Basis_AddBasisClassRequest request) throws ApiException {
        Integer basisType = request.getBasisType();
        String basisClass = request.getBasisClass();
        FileLevelDecideBasis basis = new FileLevelDecideBasis(basisType, null, null, basisClass, null);
        fileLevelDecideBasisDao.save(basis);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_addClass)
                .admin(request.tokenWrapper().getAdmin())
                .operateDescription(BasisConsts.Type.parse(request.getBasisType()).getDescription(), basisClass);
        return new Basis_AddBasisClassResponse(basis.getId());
    }

}
