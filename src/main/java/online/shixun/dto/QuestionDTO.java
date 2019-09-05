package online.shixun.dto;

import java.util.List;

public class QuestionDTO extends BaseDTO{
	
	private String question;

	private List<AnswerDTO> answers;
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}
	
}
