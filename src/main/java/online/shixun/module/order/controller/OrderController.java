package online.shixun.module.order.controller;

import online.shixun.dto.MemberDTO;
import online.shixun.dto.OrderDTO;
import online.shixun.dto.OrderItemDTO;
import online.shixun.dto.ProductDTO;
import online.shixun.dto.ReceiverDTO;
import online.shixun.module.cart.dao.CartItemDaoMyBatis;
import online.shixun.module.cart.service.CartItemServiceImpl;
import online.shixun.module.member.service.MemberServiceImpl;
import online.shixun.module.order.dao.OrderDaoMyBatis;
import online.shixun.module.order.service.OrderItemServiceImpl;
import online.shixun.module.order.service.OrderServiceImpl;
import online.shixun.module.product.dao.ProductDaoMyBatis;
import online.shixun.module.product.service.ProductCategoryServiceImpl;
import online.shixun.module.product.service.ProductServiceImpl;
import online.shixun.module.receiver.service.ReceiverServiceImpl;
import online.shixun.web.BaseController;
import online.shixun.web.ResponseData;
import online.shixun.web.SimpleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/front/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @Autowired
    private ReceiverServiceImpl receiverService;

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private CartItemServiceImpl cartItemService;

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;
    
    @Autowired
    private ProductServiceImpl productService;
    
    @Autowired
    private ProductDaoMyBatis productDao;
    
    @Autowired
    private CartItemDaoMyBatis cartItemDao;
    
    @Autowired
    private OrderDaoMyBatis orderDao;
    
    /**
     * 进入我的订单页面
     *
     * @param model
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String orderList(Model model) {
    	MemberDTO member = this.getCurrentUser();
        // 传递商品分类数据
        model.addAttribute("productCategories", productCategoryService.getProductCategories());
        // 传递我的订单数据
        model.addAttribute("orders", orderService.getOrdersByMember(member.getId()));
        // 传递登录会员数据
        model.addAttribute("member", member);
        return "/order/list";
    }

    /**
     * 进入订单详情页面
     *
     * @param model
     * @param orderId
     *            订单ID
     * @return
     */
    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
    public String orderDetailt(Model model, @PathVariable Long orderId) {
        MemberDTO member = this.getCurrentUser();

        model.addAttribute("productCategories", productCategoryService.getProductCategories());
        model.addAttribute("orderItems", orderItemService.getOrderItemsByOrder(orderId));
        model.addAttribute("orderId", orderId);
        // 传递登录会员数据
        model.addAttribute("member", member);
        return "/order/detail";
    }

    @RequestMapping("/settlement")
    public String save(Model model, @RequestParam(required = false, defaultValue = "") String params) {
        MemberDTO member = this.getCurrentUser();
        if (member == null) {

          //return "redirect:/front/index";
            //跳转到登陆界面

            return "redirect:/front/login";
        }

        List<OrderItemDTO> orderItems = orderItemService.getSelectCart(params, getSession());
        Double productTotalPrice = 0.0;
        for (OrderItemDTO orderItem : orderItems) {
            productTotalPrice += orderItem.getTotalPrice();
        }
        Double totalAmount = productTotalPrice;
        if (member.getMemberLevel() != null) {
            totalAmount = productTotalPrice * member.getMemberLevel().getDiscount();
        }
        BigDecimal bg = new BigDecimal(totalAmount);
        totalAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        model.addAttribute("receivers", receiverService.getReceiversByMember(member.getId()));
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("productTotalPrice", productTotalPrice);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("params", params);
        model.addAttribute("member", member);

        return "/order/main";
    }

    /**
     * 进入订单页面
     *
     * @param model
     * @param orderId 试题ID
     *
     * @return
     */
    @RequestMapping(value = "/main/{orderId}", method = RequestMethod.GET)
    public String orderForm(Model model, @PathVariable Long orderId) {
        // 获取当前的登录用户
        MemberDTO member = this.getCurrentUser();
        OrderDTO order = orderService.saveOrder(orderId, member);
        model.addAttribute("receivers", receiverService.getReceiversByMember(member.getId()));
        model.addAttribute("products", orderItemService.getOrderItemsByOrder(orderId));
        model.addAttribute("productTotalPrice", order.getProductTotalPrice());
        model.addAttribute("totalAmount", order.getTotalAmount());
        model.addAttribute("orderId", orderId);
        model.addAttribute("totalCartCount", cartItemService.getTotalCartCount(member, getSession()));
        model.addAttribute("member", member);
        //(修改部分)在付款页面显示购买的商品信息
        List<OrderItemDTO> orderItems = orderItemService.getOrderItemsByOrder(orderId);
        model.addAttribute("orderItems", orderItems);
        
        return "/order/main";
    }

    @RequestMapping("/receiver/form/{receiverId}")
    public String receiver(Model model, @PathVariable Long receiverId) throws Exception {
        ReceiverDTO receiver = new ReceiverDTO();

        // 编辑
        if (receiverId != 0) {
            receiver = receiverService.getReceiver(receiverId);
        }
        model.addAttribute("receiver", receiver);
        return "/order/receiver/form";
    }

    /**
     * 提交订单
     *
     * @param params jsp页面拼接的参数
     *
     * @return
     */
    @RequestMapping(value = "/commit/{params}/{receiverId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData orderSave(@PathVariable String params, @PathVariable Long receiverId) {
        MemberDTO member = this.getCurrentUser();

        return new SimpleHandler(request) {
            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
            	
                responseData.setData(orderService.commitOrder(params, member, receiverId, getSession()));
            }
        }.handle();
    }    

    /**
     * 模拟支付订单
     *
     * @param orderId 订单id
     *
     * @return
     */
    @RequestMapping(value = "/pay/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData orderPay(final @PathVariable Long orderId) {
        return new SimpleHandler(request) {
            @Override
            protected void doHandle(ResponseData responseData) throws Exception {

            	orderService.updateOrderStatus(orderId, "ORDS_Pay");
            	
            	//购买成功后库存减少相应的数量（新增）
                decreaseStore(orderId);
            }
        }.handle();
    }
    
    /**
     * 购买成功后减少库存（新增）
     * @param orderId
     */
    public void decreaseStore(Long orderId) {
    	int store = 0;
    	
    	//遍历订单项
    	List<OrderItemDTO> orderItemList = orderItemService.getOrderItemsByOrder(orderId);
    	
    	//每个订单项的对应的商品库存减去购买的商品数目
    	for (OrderItemDTO orderItem : orderItemList) {
    		//获取订单项目的商品Id
    		Long productId = orderItem.getProduct().getId();
    		//减少库存，并保存
    		ProductDTO product = productService.getProduct(productId);
    		store = product.getStore() - orderItem.getProductQuantity();  //计算库存
    		productDao.saveProduct(productId , store);              //更新库存
    		
    		//获取会员的Id
    		OrderDTO order = orderService.getOrder(orderId);
    		Long memberId = order.getMember().getId();
    		//购买成功后，删除购物车中的购买相应的商品
    		cartItemDao.deletePurchasedGood(memberId , productId);
    	}
    }
    
    /**
     * 保存收货人
     *
     * @param receiver 收货人实体
     *
     * @return
     */
    @RequestMapping(value = "/receiver/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData receiverSave(final @ModelAttribute ReceiverDTO receiver) {
        final MemberDTO member = this.getCurrentUser();
        SimpleHandler handler = new SimpleHandler(request) {

            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
                receiverService.saveOrUpdateReceiver(receiver, member);
            }
        };
        return handler.handle();
    }

    /**
     * 删除联系人
     *
     * @param receiverId 联系人id
     *
     * @return
     */
    @RequestMapping(value = "/receiver/delete/{receiverId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseData cartsDelete(final @PathVariable Long receiverId) {
        final MemberDTO member = this.getCurrentUser();

        return new SimpleHandler(request) {
            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
                receiverService.deleteReceiver(member, receiverId);
            }
        }.handle();
    }

    /**
     * 设置默认收货人
     *
     * @param model
     * @param receiverId 收货人Id
     *
     * @return
     */
    @RequestMapping(value = "/set/default/receiver/{receiverId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData setDefaultReceiverSave(Model model, final @PathVariable Long receiverId) {
        final MemberDTO member = this.getCurrentUser();
        return new SimpleHandler(request) {

            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
                memberService.updateMemberDefaultReceiverId(member.getId(), receiverId);
            }
        }.handle();
    }
    
    /**
     * 取消（删除）待付款的订单（新增）
     * @param model
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/delete/{orderId}", method = RequestMethod.GET)
    public String deleteOrder(Model model ,@PathVariable Long orderId) {
    	orderService.deleteOrder(orderId);
    	return "/order/delete";
    }
    
    /**
     * 七天无理由退货（新增）
     * @param model
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/after/{orderId}", method = RequestMethod.GET)
    public String afterSale(Model model , @PathVariable Long orderId){
    	
    	//获取购买商品到现在的天数
    	int day = orderDao.checkTime(orderId);
    	
    	//判断是否在七日之内
    	if (day <= 7) {
    		//七日内可成功退货
    		orderDao.updateOrderStatus(orderId, "ORDS_Return");
    		return "/order/after";
    	}else {
    		//超过七日，退货失败
    		return "/order/fail";
    	}
    }
}
