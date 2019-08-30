package com.qingshixun.project.eshop.module.question.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qingshixun.project.eshop.dto.QuestionDTO;
import com.qingshixun.project.eshop.module.question.dao.QuestionDaoMyBatis;
import com.qingshixun.project.eshop.web.BaseService;

@Service
public class QuestionServiceImpl extends BaseService{
	@Autowired QuestionDaoMyBatis questionDao;
	public List<QuestionDTO> getQuestion() {
        return questionDao.getQuestion();
    }
}
