package com.qingshixun.project.eshop.module.answer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingshixun.project.eshop.dto.AnswerDTO;

import com.qingshixun.project.eshop.module.answer.dao.AnswerDaoMyBatis;
import com.qingshixun.project.eshop.web.BaseService;
@Service
public class AnswerServiceImpl extends BaseService{
	@Autowired AnswerDaoMyBatis answerDao;
	public List<AnswerDTO> getAnswer(Long questionId) {
        return answerDao.getAnswerByQuestionId(questionId);
    }

}
