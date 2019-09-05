package online.shixun.module.brand.service;

import online.shixun.dto.BrandDTO;
import online.shixun.dto.ProductCategoryDTO;
import online.shixun.module.brand.dao.BrandDaoMyBatis;
import online.shixun.module.product.service.ProductCategoryServiceImpl;
import online.shixun.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl extends BaseService {

    @Autowired
    private BrandDaoMyBatis brandDao;
    @Autowired
    private ProductCategoryServiceImpl productCategoryService;
    /**
     * 获取指定类型的品牌列表
     */
    public List<BrandDTO> getBrandsByCategory(Long categoryId) {

    	List<BrandDTO> brands=new ArrayList();
    	getBrands(categoryId,brands);
        return brands;
    }

    public void getBrands(Long categoryId,List<BrandDTO> brands) { 	
    	List<ProductCategoryDTO> categoryDTOs=productCategoryService.getProductCategoriesByParent(categoryId);
    	while(categoryDTOs.size()!=0){
    		for(ProductCategoryDTO categoryDTO:categoryDTOs) {
    			getBrands(categoryDTO.getId(),brands);
    		}
    		return;
    	}
    	List<BrandDTO> newbrands=brandDao.getBrandsByCategory(categoryId);
        if(newbrands.size()>0) {
        	for(BrandDTO newbrand:newbrands) {
        		brands.removeIf( 
       				 elementData-> elementData.getId().equals(newbrand.getId()));
        	}
        	brands.addAll(newbrands);
        }
    }

}
