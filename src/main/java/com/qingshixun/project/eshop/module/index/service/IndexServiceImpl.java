package com.qingshixun.project.eshop.module.index.service;

import com.qingshixun.project.eshop.dto.ProductDTO;
import com.qingshixun.project.eshop.module.index.dao.IndexDaoMyBatis;
import com.qingshixun.project.eshop.module.product.dao.ProductDaoMyBatis;
import com.qingshixun.project.eshop.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl extends BaseService {

    @Autowired
    private IndexDaoMyBatis indexDao;
    //获取所有商品
    public List<ProductDTO> getProducts(String name) {
        return indexDao.getProducts(name);
    }
}
