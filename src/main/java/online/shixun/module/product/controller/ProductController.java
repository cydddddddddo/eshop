package online.shixun.module.product.controller;

import online.shixun.core.Constants;
import online.shixun.dto.CartItemDTO;
import online.shixun.dto.EvaluateDTO;
import online.shixun.dto.MemberDTO;
import online.shixun.dto.ProductDTO;
import online.shixun.module.brand.service.BrandServiceImpl;
import online.shixun.module.cart.service.CartItemServiceImpl;
import online.shixun.module.evaluate.service.EvaluateServiceImpl;
import online.shixun.module.order.service.OrderServiceImpl;
import online.shixun.module.personal.service.PersonalService;
import online.shixun.module.product.service.ProductCategoryServiceImpl;
import online.shixun.module.product.service.ProductServiceImpl;
import online.shixun.module.product.service.ProductTypeAttributeServiceImpl;
import online.shixun.util.DateUtils;
import online.shixun.web.BaseController;
import online.shixun.web.ResponseData;
import online.shixun.web.SimpleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/front/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Autowired
    private ProductTypeAttributeServiceImpl productTypeAttributeService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private BrandServiceImpl brandService;

    @Autowired
    private EvaluateServiceImpl evaluateService;

    @Autowired
    private CartItemServiceImpl cartItemService;

    @Autowired
    private OrderServiceImpl orderService;
    
    @Autowired
    private PersonalService personalService;

    @Autowired
    private ProductCategoryServiceImpl productCategoryServiceImpl;

    /**
     * 商品列表页
     */
    @RequestMapping("/list")
    public String list(Model model, @RequestParam Long categoryId) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);

        MemberDTO member = getCurrentUser();

        // 非空验证
        if (!products.isEmpty()) {

            // 获取第一个商品的类型id
            Long typeId = products.get(0).getProductType().getId();
            //品牌
            model.addAttribute("brands", brandService.getBrandsByCategory(categoryId));
            //可选择的筛选条件
            model.addAttribute("productTypeAttributes", productTypeAttributeService.getProductTypeAttributesByProductType(typeId));

        }
        //左侧分类
        model.addAttribute("productCategories", productCategoryService.getProductCategories());
        model.addAttribute("totalCartCount", cartItemService.getTotalCartCount(member, getSession()));
        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);

        return "/product/list";
    }


    /**
     * 产品详情中的评论列表中除评论外的数据
     * @param model
     * @param productId
     * @return
     */
    @RequestMapping("/main")
    public String main(Model model, @RequestParam Long productId) {

        ProductDTO product = productService.getProduct(productId);
        final MemberDTO member = this.getCurrentUser();
        
        if(member != null) {
        	String createTime = DateUtils.timeToString(new Date());
        	Long memberId = member.getId();
            personalService.saveIds(productId,memberId,createTime);}
        
        //MemberDTO member = getCurrentUser();
        
        int quantity = 1;

        model.addAttribute("product", product);
        model.addAttribute("cart", new CartItemDTO());
        model.addAttribute("imagePath", Constants.PRODUCT_IMAGE_PATH);
        model.addAttribute("products", productService.getProductsByPrice(product.getPrice()));
        model.addAttribute("score", evaluateService.getAverageEvaluateScoreByProduct(productId));
        model.addAttribute("totalEvaluateCount", evaluateService.getEvaluateCountByProduct(productId));
        model.addAttribute("satisfiedEvaluateCount", evaluateService.getEvaluateCountByStatusAndProduct("满意", product.getId()));
        model.addAttribute("commonlyEvaluateCount", evaluateService.getEvaluateCountByStatusAndProduct("一般", product.getId()));
        model.addAttribute("disSatisfiedEvaluateCount", evaluateService.getEvaluateCountByStatusAndProduct("不满意", product.getId()));
        model.addAttribute("totalCartCount", cartItemService.getTotalCartCount(member, getSession()));
        model.addAttribute("quantity",quantity);
        //model.addAttribute("productId",productId);
        
        return "/product/main";
    }

    /**
     * 获得评论数据
     * @param model
     * @param status
     * @param productId
     * @return
     */
    @RequestMapping("/evaluate/list")
    public String evaluates(Model model, @RequestParam(required = false, defaultValue = "") String status, @RequestParam Long productId) {
        model.addAttribute("evaluates", evaluateService.getEvaluatesByStatusAndProduct(status, productId));

        return "/product/evaluate/list";
    }

    /**
     * 商品评论页面
     *
     * @param productId 商品ID
     *
     * @throws Exception
     */
    @RequestMapping("/evaluate/{productId}/{orderId}")
    public String productEvaluate(Model model, @PathVariable Long productId, @PathVariable Long orderId) throws Exception {
    	
        // 获取当前的登录用户
        MemberDTO dbMember = this.getCurrentUser();
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("productId", productId);
        model.addAttribute("totalCartCount", cartItemService.getTotalCartCount(dbMember, getSession()));
        model.addAttribute("time", orderService.getOrder(orderId).getUpdateTime());
        model.addAttribute("orderId", orderId);
        model.addAttribute("member", dbMember);
        model.addAttribute("evaluate", evaluateService.getEvaluateByMemberAndProductAndOrder(dbMember.getId(), productId, orderId));
        return "/product/evaluate";
    }

    /**
     * 保存商品评论
     *
     * @param model
     * @param evaluate 评论实体
     */
    @RequestMapping(value = "/evaluate/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData receiverSave(Model model, EvaluateDTO evaluate) {
        MemberDTO member = this.getCurrentUser();
        //根据评论评分设置满意度
        evaluate.setEvaluateStatus(evaluate.getScore());

        return new SimpleHandler(request) {
            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
                evaluateService.saveOrUpdateEvaluate(evaluate, member);
            }
        }.handle();
    }


    @RequestMapping("/search/list")
    public String getSelectProduct(Model model, @RequestParam(required = false) String searchPargam, @RequestParam(required = false) int brandId, @RequestParam Long categoryId){
        List<ProductDTO> tempProducts = null;
        List<ProductDTO> products = null;
        Long[] num_1 = null;
        Long[] num_2 = {};
        Long[] tempNum = null;
        if (productCategoryServiceImpl.getProductCategoriesStatus(categoryId)!=null){
            num_2 = productCategoryServiceImpl.getProductCategoriesId(categoryId);
        }else {
            num_1 = productCategoryServiceImpl.getProductCategoriesId(categoryId);
            for (int i = 0; i < num_1.length; i++) {
                tempNum = productCategoryServiceImpl.getProductCategoriesId(num_1[i]);
                num_2 = mergeLongArray(tempNum,num_2);
            }
        }

        String selectAttribute = null;
        if (!("".equals(searchPargam))){
            searchPargam = searchPargam.replace(",","%");
             selectAttribute = searchPargam.substring(0,searchPargam.length()-1);
        }

        products = productService.getProductBySelect(selectAttribute,brandId,categoryId);

        if(num_2!=null){
            for (int i = 0; i < num_2.length; i++) {
                tempProducts = productService.getProductBySelect(selectAttribute,brandId,num_2[i]);
                products = mergeList(tempProducts,products);
            }
        }
        model.addAttribute("products", products);
        return "/product/select";
    }

    public List<ProductDTO> mergeList(List<ProductDTO> tempProducts, List<ProductDTO> products){
        for (int i = 0; i < tempProducts.size(); i++) {
            products.add(tempProducts.get(i));
        }
        return products;
    }

    public Long[] mergeLongArray(Long[] tempNum,Long[] num_2){
        Long[] num = new Long[tempNum.length+num_2.length];
        for (int i = 0; i < tempNum.length; i++) {
            num[i] = tempNum[i];
        }
        for (int j = 0; j < num_2.length; j++) {
            num[tempNum.length+j] = num_2[j];
        }
        return num;
    }

}
