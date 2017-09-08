package cn.bestsec.dcms.platform.impl.basis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_QueryBasisClassListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.BasisClassItem;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisClassListRequest;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisClassListResponse;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;

@Component
public class BasisQueryBasisClassList implements Basis_QueryBasisClassListApi {
    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_QueryBasisClassListResponse execute(Basis_QueryBasisClassListRequest request) throws ApiException {
        List<BasisClassItem> basisClassList = fileLevelDecideBasisDao.findBasisClassList(request.getBasisType());
        return new Basis_QueryBasisClassListResponse(basisClassList);
    }

}
