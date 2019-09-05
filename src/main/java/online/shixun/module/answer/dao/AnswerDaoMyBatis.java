package online.shixun.module.answer.dao;

import java.util.List;

import online.shixun.dto.AnswerDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface AnswerDaoMyBatis {
	 List<AnswerDTO> getAnswerByQuestionId(@Param("quetionId") Long quetionId);
}
