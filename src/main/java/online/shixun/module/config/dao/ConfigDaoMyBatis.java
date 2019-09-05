/*****************************************************************************
 * Copyright (c) 2015, www.qingshixun.com
 *
 * All rights reserved
 *
 *****************************************************************************/

package online.shixun.module.config.dao;

import online.shixun.dto.ConfigDTO;
import online.shixun.web.MyBatisRepository;

import java.util.List;

@MyBatisRepository
public interface ConfigDaoMyBatis {

    /**
     * 获取所有配置
     */
    List<ConfigDTO> getConfigs();

}