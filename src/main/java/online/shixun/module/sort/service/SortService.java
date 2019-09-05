package online.shixun.module.sort.service;

import online.shixun.dto.ProductDTO;
import online.shixun.module.sort.dao.SortDaoMybatis;
import online.shixun.web.BaseService;
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
