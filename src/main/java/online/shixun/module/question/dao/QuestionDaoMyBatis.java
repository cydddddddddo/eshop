package online.shixun.module.question.dao;

import java.util.List;

import online.shixun.dto.QuestionDTO;
import online.shixun.web.MyBatisRepository;

@MyBatisRepository
public interface QuestionDaoMyBatis {
	List<QuestionDTO> getQuestion();
}
