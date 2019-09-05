package online.shixun.module.product.dao;

import online.shixun.dto.ProductTypeValueDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface ProductTypeValueDaoMyBatis {

    /**
     * 获取指定商品的属性列表
     */
    List<ProductTypeValueDTO> getProductTypeValuesByProduct(@Param("productId") Long productId);

}
