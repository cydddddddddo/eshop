package com.qingshixun.project.eshop.module.index.dao;

import com.qingshixun.project.eshop.dto.ProductDTO;
import com.qingshixun.project.eshop.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface IndexDaoMyBatis {
    //获取商品列表
    List<ProductDTO> getProducts(@Param("name") String name);
}
