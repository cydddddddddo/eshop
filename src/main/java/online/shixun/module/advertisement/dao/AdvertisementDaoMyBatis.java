package online.shixun.module.advertisement.dao;

import online.shixun.dto.AdvertisementDTO;
import online.shixun.web.MyBatisRepository;

import java.util.List;

@MyBatisRepository
public interface AdvertisementDaoMyBatis {

    
    List<AdvertisementDTO> getAdvertisements();

}
