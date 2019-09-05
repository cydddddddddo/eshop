package online.shixun.module.question.service;

import java.util.List;

import online.shixun.dto.QuestionDTO;
import online.shixun.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import online.shixun.module.question.dao.QuestionDaoMyBatis;

@Service
public class QuestionServiceImpl extends BaseService {
	@Autowired QuestionDaoMyBatis questionDao;
	public List<QuestionDTO> getQuestion() {
        return questionDao.getQuestion();
    }
}
