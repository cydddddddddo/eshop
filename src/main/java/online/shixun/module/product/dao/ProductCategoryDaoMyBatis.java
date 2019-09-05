package online.shixun.module.product.dao;

import online.shixun.dto.ProductCategoryDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface ProductCategoryDaoMyBatis {

    /**
     * 根据父亲节点ID获取商品分类列表
     *
     * @param parentId 试题ID集合
     *
     * @return 商品分类列表
     */
    List<ProductCategoryDTO> getProductCategoriesByParent(@Param("parentId") Long parentId);

    Long[] getProductCategoriesId(@Param("categoryId")Long categoryId);

    Long getProductCategoriesStatus(@Param("categoryId")Long categoryId);
}
