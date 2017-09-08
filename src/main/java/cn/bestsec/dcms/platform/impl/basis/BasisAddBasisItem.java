package cn.bestsec.dcms.platform.impl.basis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Basis_AddBasisItemApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Basis_AddBasisItemRequest;
import cn.bestsec.dcms.platform.api.model.Basis_AddBasisItemResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BasisConsts;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

@Component
public class BasisAddBasisItem implements Basis_AddBasisItemApi {

    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_AddBasisItemResponse execute(Basis_AddBasisItemRequest request) throws ApiException {
        Integer basisId = request.getBasisId();
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(basisId);
        if (basis == null || basis.getBasisName() == null || basis.getBasisName().isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        if (basis.getBasisItem() != null && !basis.getBasisItem().isEmpty()) {
            basis = new FileLevelDecideBasis(basis.getBasisType(), basis.getBasisLevel(), basis.getBasisItem(),
                    basis.getBasisClass(), basis.getBasisName());
        }
        basis.setBasisLevel(request.getBasisLevel());
        basis.setBasisItem(request.getBasisItem());
        fileLevelDecideBasisDao.save(basis);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.basis_addItem)
                .admin(request.tokenWrapper().getAdmin())
                .operateDescription(BasisConsts.Type.parse(basis.getBasisType()).getDescription(),
                        FileConsts.Level.parse(request.getBasisLevel()).getDescription(),
                        basis.getBasisName() + " " + basis.getBasisItem());
        return new Basis_AddBasisItemResponse(basis.getId());
    }

}
