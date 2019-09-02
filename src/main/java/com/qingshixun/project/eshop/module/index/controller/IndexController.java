package com.qingshixun.project.eshop.module.index.controller;

import com.qingshixun.project.eshop.dto.MemberDTO;
import com.qingshixun.project.eshop.dto.ProductDTO;
import com.qingshixun.project.eshop.dto.QuestionDTO;
import com.qingshixun.project.eshop.module.advertisement.service.AdvertisementServiceImpl;
import com.qingshixun.project.eshop.module.answer.service.AnswerServiceImpl;
import com.qingshixun.project.eshop.module.cart.service.CartItemServiceImpl;
import com.qingshixun.project.eshop.module.index.service.IndexServiceImpl;
import com.qingshixun.project.eshop.module.product.service.ProductCategoryServiceImpl;
import com.qingshixun.project.eshop.module.product.service.ProductServiceImpl;
import com.qingshixun.project.eshop.module.question.service.QuestionServiceImpl;
import com.qingshixun.project.eshop.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Autowired
    private AdvertisementServiceImpl advertisementService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CartItemServiceImpl cartItemService;

    @Autowired
    private IndexServiceImpl indexService;

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    AnswerServiceImpl answerService;

    @RequestMapping(value = {"/front/index", ""})
    public String index(Model model) {
        MemberDTO member = getCurrentUser();
        model.addAttribute("productCategories", productCategoryService.getProductCategories());
        model.addAttribute("advertisements", advertisementService.getAdvertisements());
        model.addAttribute("hotProducts", productService.getHotProducts());
        model.addAttribute("newProducts", productService.getNewProducts());
        model.addAttribute("bestProducts", productService.getBestProducts());
        model.addAttribute("totalCartCount", cartItemService.getTotalCartCount(member, getSession()));
        // 当前登录用户数据保存到session中
        setSessionAttribute("member", member);

        return "/index";
    }

    /**
     * 搜索所有商品数据
     */
    @RequestMapping(value = "/front/search")
    public String search(Model model,@RequestParam(value="name",required=false) String name){
        //查询商品
        List<ProductDTO> productList=indexService.getProducts(name);
        model.addAttribute("productList",productList);

        return "/search";
    }


    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/front/register")
    public String register(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "/register";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/front/login")
    public String login() {
        return "/login";
    }

    /**
     * 帮助中心
     */
    @RequestMapping("front/help")
    public String help(){
        return "/help";
    }

    @RequestMapping("/front/question")
    public String questions(Model model) {
        List<QuestionDTO> questions = questionService.getQuestion();
        model.addAttribute("questions",questions);
        return "/question/question";
    }
}
