package online.shixun.module.index.service;

import online.shixun.dto.ProductDTO;
import online.shixun.module.index.dao.IndexDaoMyBatis;
import online.shixun.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class IndexServiceImpl extends BaseService {

    @Autowired
    private IndexDaoMyBatis indexDao;
    //获取所有商品
    public List<ProductDTO> getProducts(String name) {
        return indexDao.getProducts(name);
    }
}
