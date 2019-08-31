package com.qingshixun.project.eshop.module.personal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qingshixun.project.eshop.dto.ProductDTO;
import com.qingshixun.project.eshop.web.MyBatisRepository;

@MyBatisRepository
public interface PersonalDaoMyBatis {
	
	List<Long> getProductId();
	
	void saveIds(@Param("productId") Long productId,@Param("memberId") Long memberId,@Param("createTime") String createTime);
	
	List<ProductDTO> getProducts(@Param("memberId") Long memberId);
	
	void deleteHistory(@Param("createTime") String createTime);
}
