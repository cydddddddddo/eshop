package online.shixun.module.personal.dao;

import java.util.List;

import online.shixun.dto.ProductDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface PersonalDaoMyBatis {
	
	List<Long> getProductId();
	
	void saveIds(@Param("productId") Long productId,@Param("memberId") Long memberId,@Param("createTime") String createTime);
	
	List<ProductDTO> getProducts(@Param("memberId") Long memberId);
	
	void deleteHistory(@Param("createTime") String createTime);
}
