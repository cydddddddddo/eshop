package online.shixun.module.sort.dao;



import online.shixun.dto.ProductDTO;
import online.shixun.web.MyBatisRepository;

import java.util.List;

@MyBatisRepository
public interface SortDaoMybatis {
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getDllProducts();
}
