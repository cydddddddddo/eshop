package online.shixun.module.answer.service;

import java.util.List;

import online.shixun.dto.AnswerDTO;
import online.shixun.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.shixun.module.answer.dao.AnswerDaoMyBatis;

@Service
public class AnswerServiceImpl extends BaseService {
	@Autowired AnswerDaoMyBatis answerDao;
	public List<AnswerDTO> getAnswer(Long questionId) {
        return answerDao.getAnswerByQuestionId(questionId);
    }

}
