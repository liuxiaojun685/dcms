/**
 * 
 */
package cn.bestsec.dcms.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bestsec.dcms.platform.dao.StorageLocationDao;
import cn.bestsec.dcms.platform.service.LocationService;
import cn.bestsec.dcms.platform.web.Location;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time 2017年2月27日 下午2:28:31
 */
@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private StorageLocationDao storageLocationDao;
    /* (non-Javadoc)
     * @see cn.bestsec.dcms.platform.service.LocationService#updateLocationParams()
     */
    @Override
    public void updateLocationParams(int id) {
       Location.setSl(storageLocationDao.findById(id));
       
    }

}
