package com.qingshixun.project.eshop.module.sort.dao;



import com.qingshixun.project.eshop.dto.ProductDTO;
import com.qingshixun.project.eshop.web.MyBatisRepository;

import java.util.List;

@MyBatisRepository
public interface SortDaoMybatis {
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getDllProducts();
}
