package online.shixun.module.product.dao;

import online.shixun.dto.ProductTypeAttributeDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface ProductTypeAttributeDaoMyBatis {

    /**
     * 获取指定商品类型的属性列表
     */
    List<ProductTypeAttributeDTO> getProductTypeAttributesByProductType(@Param("productTypeId") Long productTypeId);

}
