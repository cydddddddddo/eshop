package online.shixun.module.advertisement.service;

import online.shixun.dto.AdvertisementDTO;
import online.shixun.module.advertisement.dao.AdvertisementDaoMyBatis;
import online.shixun.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementServiceImpl extends BaseService {

    @Autowired
    private AdvertisementDaoMyBatis advertisementDao;

    /**
     * 获取已启动的广告列表
     */
    public List<AdvertisementDTO> getAdvertisements() {
        return advertisementDao.getAdvertisements();
    }

}
