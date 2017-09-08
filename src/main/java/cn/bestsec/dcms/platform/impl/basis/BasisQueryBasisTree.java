package cn.bestsec.dcms.platform.impl.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Basis_QueryBasisTreeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.BasisClassNode;
import cn.bestsec.dcms.platform.api.model.BasisItemNode;
import cn.bestsec.dcms.platform.api.model.BasisNode;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisTreeRequest;
import cn.bestsec.dcms.platform.api.model.Basis_QueryBasisTreeResponse;
import cn.bestsec.dcms.platform.dao.FileLevelDecideBasisDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideBasis;

@Component
public class BasisQueryBasisTree implements Basis_QueryBasisTreeApi {
    @Autowired
    private FileLevelDecideBasisDao fileLevelDecideBasisDao;

    @Override
    @Transactional
    public Basis_QueryBasisTreeResponse execute(Basis_QueryBasisTreeRequest request) throws ApiException {
        List<BasisClassNode> classList = new ArrayList<>();
        List<FileLevelDecideBasis> basiss = fileLevelDecideBasisDao
                .findByBasisLevelAndBasisType(request.getBasisLevel(), request.getBasisType());
        Map<String, Map<String, Map<Integer, BasisItemNode>>> classMap = new HashMap<>();
        for (FileLevelDecideBasis basis : basiss) {
            String className = basis.getBasisClass();
            String basisName = basis.getBasisName();
            String item = basis.getBasisItem();
            Integer basisId = basis.getId();
            if (!classMap.containsKey(className)) {
                classMap.put(className, new HashMap<>());
            }
            Map<String, Map<Integer, BasisItemNode>> basisMap = classMap.get(className);
            if (!basisMap.containsKey(basisName)) {
                basisMap.put(basisName, new HashMap<>());
            }
            Map<Integer, BasisItemNode> itemMap = basisMap.get(basisName);
            if (!itemMap.containsKey(basisId) && !item.isEmpty()) {
                itemMap.put(basisId, new BasisItemNode(basisId, basis.getBasisLevel(), item));
            }
        }

        Iterator<String> itClass = classMap.keySet().iterator();
        while (itClass.hasNext()) {
            String className = itClass.next();
            Map<String, Map<Integer, BasisItemNode>> basisMap = classMap.get(className);
            List<BasisNode> basisList = new ArrayList<>();
            Iterator<String> itBasis = basisMap.keySet().iterator();
            while (itBasis.hasNext()) {
                String basisName = itBasis.next();
                Map<Integer, BasisItemNode> itemMap = basisMap.get(basisName);
                List<BasisItemNode> itemList = new ArrayList<>(itemMap.values());
                basisList.add(new BasisNode(basisName, itemList));
            }
            classList.add(new BasisClassNode(className, basisList));
        }

        return new Basis_QueryBasisTreeResponse(classList);
    }

}
