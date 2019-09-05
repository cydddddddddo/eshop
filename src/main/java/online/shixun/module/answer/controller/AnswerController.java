package online.shixun.module.answer.controller;

import java.util.List;

import online.shixun.dto.AnswerDTO;
import online.shixun.module.answer.service.AnswerServiceImpl;
import online.shixun.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/front/answer/{questionId}")
public class AnswerController extends BaseController {
	@Autowired
    AnswerServiceImpl answerService;

	@RequestMapping("")
	public String questions(Model model, @RequestParam Long questionId) {
		List<AnswerDTO> answer = answerService.getAnswer(questionId);
		model.addAttribute("answers", answer);
		return "/question/question";
	}
}
