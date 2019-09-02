package com.qingshixun.project.eshop.module.answer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qingshixun.project.eshop.dto.AnswerDTO;
import com.qingshixun.project.eshop.module.answer.service.AnswerServiceImpl;
import com.qingshixun.project.eshop.web.BaseController;

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
