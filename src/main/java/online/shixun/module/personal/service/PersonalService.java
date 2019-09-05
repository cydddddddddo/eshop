package online.shixun.module.personal.service;

import java.util.List;

import online.shixun.dto.ProductDTO;
import online.shixun.module.personal.dao.PersonalDaoMyBatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
