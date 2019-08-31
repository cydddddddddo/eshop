package com.qingshixun.project.eshop.module.personal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingshixun.project.eshop.dto.ProductDTO;
import com.qingshixun.project.eshop.module.personal.dao.PersonalDaoMyBatis;

@Service
public class PersonalService {
	
	@Autowired
    private PersonalDaoMyBatis personalDao;
	
	public List<Long> getProductId() {
		
		return personalDao.getProductId();
	}
	
	public void saveIds(Long productId,Long memberId,String createTime) {
    	personalDao.saveIds(productId,memberId,createTime);
    }
	
	public List<ProductDTO> getProducts(Long memberId) {
        return personalDao.getProducts(memberId);
    }
	
	public void deleteHistory(String createTime) {
		personalDao.deleteHistory(createTime);
	}
	
}
