package com.qingshixun.project.eshop.module.question.dao;

import java.util.List;

import com.qingshixun.project.eshop.dto.QuestionDTO;
import com.qingshixun.project.eshop.web.MyBatisRepository;

@MyBatisRepository
public interface QuestionDaoMyBatis {
	List<QuestionDTO> getQuestion();
}
