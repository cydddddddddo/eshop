package online.shixun.module.brand.dao;

import online.shixun.dto.BrandDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface BrandDaoMyBatis {

    /**
     * 获取指定类型的品牌列表
     */
    List<BrandDTO> getBrandsByCategory(@Param("categoryId") Long categoryId);

}
