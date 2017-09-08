package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_QueryBasisListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.BasisItem;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisListRequest;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisListResponse;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;

@Component
public class BasisQueryBasisList implements Basis_QueryBasisListApi {
    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_QueryBasisListResponse execute(Basis_QueryBasisListRequest request) throws ApiException {
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<BasisItem> basisList = fileLevelDecideBasisDao.findBasisList(basis.getBasisType(), basis.getBasisClass());
        return new Basis_QueryBasisListResponse(basisList);
    }

}
