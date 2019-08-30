package com.qingshixun.project.eshop.module.question.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qingshixun.project.eshop.dto.QuestionDTO;
import com.qingshixun.project.eshop.module.answer.service.AnswerServiceImpl;
import com.qingshixun.project.eshop.module.question.service.QuestionServiceImpl;
import com.qingshixun.project.eshop.web.BaseController;

@Controller
@RequestMapping("/front/question")
public class QuestionController extends BaseController{
	@Autowired QuestionServiceImpl questionService;
	@Autowired AnswerServiceImpl answerService;
	@RequestMapping("")
	public String questions(Model model) {
		List<QuestionDTO> questions = questionService.getQuestion();
		model.addAttribute("questions",questions);
		return "/question/question";
	}
}
