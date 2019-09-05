package online.shixun.dto;

public class AnswerDTO extends BaseDTO{

	private String answer;
	private Long questionId;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
}
