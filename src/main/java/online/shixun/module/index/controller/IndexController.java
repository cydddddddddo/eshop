package online.shixun.module.index.controller;

import online.shixun.dto.MemberDTO;
import online.shixun.dto.ProductDTO;
import online.shixun.dto.QuestionDTO;
import online.shixun.module.advertisement.service.AdvertisementServiceImpl;
import online.shixun.module.answer.service.AnswerServiceImpl;
import online.shixun.module.cart.service.CartItemServiceImpl;
import online.shixun.module.index.service.IndexServiceImpl;
import online.shixun.module.product.service.ProductCategoryServiceImpl;
import online.shixun.module.product.service.ProductServiceImpl;
import online.shixun.module.question.service.QuestionServiceImpl;
import online.shixun.web.BaseController;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Autowired
    private AdvertisementServiceImpl advertisementService;

    @Autowired
    private CartItemServiceImpl cartItemService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private IndexServiceImpl indexService;

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    AnswerServiceImpl answerService;

    @Autowired
    AmqpTemplate amqpTemplate;


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

    /**
     * 常见问题
     * @param model
     * @return
     */
    @RequestMapping("/front/question")
    public String questions(Model model) {
        List<QuestionDTO> questions = questionService.getQuestion();
        model.addAttribute("questions",questions);
        return "/question/question";
    }

    /**
     * 利用消息队列向另一项目传递信息，
     * 但是没有解决跳转所以。。
     * @param userName
     * @return
     */
    @RequestMapping("/front/seckill")
    public String toSeckill(@RequestParam String userName){
        amqpTemplate.convertAndSend("seckill",userName);
        return "redirect:localhost:8080/login/to_login";
//        return "/question/question";
    }


}
