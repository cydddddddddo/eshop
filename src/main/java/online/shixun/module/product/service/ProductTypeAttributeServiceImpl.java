package online.shixun.module.product.service;

import online.shixun.dto.ProductDTO;
import online.shixun.dto.ProductTypeAttributeDTO;
import online.shixun.module.product.dao.ProductTypeAttributeDaoMyBatis;
import online.shixun.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeAttributeServiceImpl extends BaseService {

    @Autowired
    private ProductTypeAttributeDaoMyBatis productTypeAttributeDao;

    /**
     * 获取指定商品类型的属性列表
     */
    public List<ProductTypeAttributeDTO> getProductTypeAttributesByProductType(Long productTypeId) {
        return productTypeAttributeDao.getProductTypeAttributesByProductType(productTypeId);
    }
    /**
     * 获取指定商品类型的属性列表
     * @param products
     * @return
     */
    public List<ProductTypeAttributeDTO> getProductTypeAttributesByCategoryId(List<ProductDTO> products ) {
    	List<ProductTypeAttributeDTO> productTypeAttributes=new ArrayList();
    	List<Long> typeIds=new ArrayList();
    	getProductTypeAttributes(products,typeIds);
    	for(Long typeId:typeIds) {
    		List<ProductTypeAttributeDTO> newAttributes=productTypeAttributeDao.getProductTypeAttributesByProductType(typeId);
			 if(newAttributes!=null) {
				productTypeAttributes.addAll(newAttributes);
			 }
    	}
        return productTypeAttributes;
    }

	/**
	 *
	 * @param products
	 * @param typeIds
	 */
    public void getProductTypeAttributes(List<ProductDTO> products,List<Long> typeIds) { 	
    	if(products.size()>0) {
    		for(ProductDTO product:products) {
    			Long newtypeId = product.getProductType().getId();
    			if(newtypeId!=null) {
    				if(!typeIds.contains(newtypeId)){
    					typeIds.add(newtypeId);
    				}
    			}
    		}
    	}
    }  
}
