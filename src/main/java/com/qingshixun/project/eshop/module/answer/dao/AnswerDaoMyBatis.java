package com.qingshixun.project.eshop.module.answer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qingshixun.project.eshop.dto.AnswerDTO;
import com.qingshixun.project.eshop.web.MyBatisRepository;

@MyBatisRepository
public interface AnswerDaoMyBatis {
	 List<AnswerDTO> getAnswerByQuestionId(@Param("quetionId") Long quetionId);
}
