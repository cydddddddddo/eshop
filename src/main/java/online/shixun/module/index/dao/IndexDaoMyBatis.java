package online.shixun.module.index.dao;

import online.shixun.dto.ProductDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface IndexDaoMyBatis {
    //获取商品列表
    List<ProductDTO> getProducts(@Param("name") String name);
}
