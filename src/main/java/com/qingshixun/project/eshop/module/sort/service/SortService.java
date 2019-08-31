package com.qingshixun.project.eshop.module.sort.service;

import com.qingshixun.project.eshop.dto.ProductDTO;
import com.qingshixun.project.eshop.module.sort.dao.SortDaoMybatis;
import com.qingshixun.project.eshop.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortService extends BaseService {
    @Autowired
    private SortDaoMybatis sortDao;

    public List<ProductDTO> getAllProducts(){
        return sortDao.getAllProducts();
    }

    public List<ProductDTO> getDllProducts(){return sortDao.getDllProducts();}
}
