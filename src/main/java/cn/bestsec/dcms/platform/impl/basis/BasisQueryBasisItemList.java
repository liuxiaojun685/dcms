package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_QueryBasisItemListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.BasisItemItem;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisItemListRequest;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisItemListResponse;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;

@Component
public class BasisQueryBasisItemList implements Basis_QueryBasisItemListApi {
    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_QueryBasisItemListResponse execute(Basis_QueryBasisItemListRequest request) throws ApiException {
        FileLevelDecideBasis basis = fileLevelDecideBasisDao.findById(request.getBasisId());
        if (basis == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<BasisItemItem> basisItemList = fileLevelDecideBasisDao.findBasisItemList(basis.getBasisType(), basis.getBasisClass(), basis.getBasisName());;
        return new Basis_QueryBasisItemListResponse(basisItemList);
    }

}
