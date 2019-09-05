package online.shixun.module.alipay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;

import online.shixun.alipay.AlipayConfig;
import online.shixun.dto.OrderDTO;
import online.shixun.module.order.controller.OrderController;
import online.shixun.module.order.dao.OrderDaoMyBatis;
import online.shixun.module.order.service.OrderServiceImpl;

import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping(value = "/front/alipay")
public class PayController {
	
	//private static final Logger logger = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private OrderController orderController;

	@Autowired
	private OrderDaoMyBatis orderDao;
	
	/**
	 * 支付宝支付功能
	 * @param request
	 * @param response
	 * @param orderId
	 * @throws IOException
	 */
	@RequestMapping(value = "/pay/{orderId}")
    public void payController(HttpServletRequest request, HttpServletResponse response,@PathVariable Long orderId) throws IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        
        OrderDTO order = orderService.getOrder(orderId);
        
        //商户订单号，商户网站订单系统中唯一订单号，必填
        Long out_trade_no = orderId;
        //付款金额，必填
        Double total_amount = order.getTotalAmount();
        //订单名称，必填
        String subject = "购买商品";
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + AlipayConfig.charset);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
        //修改订单状态
        orderService.updateOrderStatus(orderId, "ORDS_Pay");
		
		//修改商品库存和销量
		orderController.decreaseStore(orderId);
    }
	
	/**
	 * 支付宝退货功能
	 * @param request
	 * @param response
	 * @param orderId
	 * @throws IOException
	 * @throws AlipayApiException
	 */
	@RequestMapping(value = "/after/{orderId}")
    public String afterController(HttpServletRequest request, HttpServletResponse response,@PathVariable Long orderId) throws IOException, AlipayApiException {
		//获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        
        OrderDTO order = orderService.getOrder(orderId);
        
      //商户订单号，必填
        Long out_trade_no = orderId;
        //需要退款的金额，该金额不能大于订单金额，必填
        Double refund_amount = order.getTotalAmount();
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String(UUID.randomUUID().toString());
     
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");
      
        //获取购买商品到现在的天数
    	int day = orderDao.checkTime(orderId);
    	//判断是否在七日之内
    	if (day <= 7) {
    		//七日内可调用支付宝退货功能
    		AlipayTradeRefundResponse alipayResponse = alipayClient.execute(alipayRequest);
    		//判断退款是否调用成功
            if (alipayResponse.isSuccess()){
            	System.out.println("调用成功");
            	orderDao.updateOrderStatus(orderId, "ORDS_Return");
            	return "/alipay/after";
            } else {
            	System.out.println("调用失败");
            	return "/alipay/fail";
            }
    	} else {
    		//超过七日，退货失败
    		System.out.println("超过七日，退货失败");
    		return "/alipay/fail";
    	}
	}
	/**
	 * @Description: 支付宝同步通知页面"
	 */
	/*@RequestMapping(value = "/alipayReturnNotice")
	public String alipayReturnNotice(HttpServletRequest request) throws Exception {
 
		logger.info("支付成功, 进入同步通知接口...");
 
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
 
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
		} catch (Exception e) {
			logger.error("SDK验证签名出现异常");
		}
 
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
 
			//修改订单状态为已支付
			orderService.updateOrderStatus(Long.parseLong(out_trade_no), "ORDS_Pay");
			
			//修改商品库存和销量
			orderController.decreaseStore(Long.parseLong(out_trade_no));
			
			String goods_names = "";
			
			
			logger.info("********************** 支付成功(支付宝同步通知) **********************");
			logger.info("* 订单号: {}", out_trade_no);
			logger.info("* 支付宝交易号: {}", trade_no);
			logger.info("* 实付金额: {}", total_amount);
			logger.info("* 购买产品: {}");
			logger.info("***************************************************************");
 
			return "/front/order/list";
		}else {
			logger.error("支付, 验签失败...");
		}
		return "/front/order/list";
	}*/
 
	/**
	 * @Description: 支付宝异步 通知页面
	 */
//	@RequestMapping(value = "/alipayNotifyNotice")
//	public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
//		logger.info("支付成功, 进入异步通知接口...");
// 
//		//获取支付宝POST过来反馈信息
//		Map<String,String> params = new HashMap<String,String>();
//		Map<String,String[]> requestParams = request.getParameterMap();
//		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i]
//						: valueStr + values[i] + ",";
//			}
//			//乱码解决，这段代码在出现乱码时使用
////			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			params.put(name, valueStr);
//		}
// 
//		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
// 
//		//——请在这里编写您的程序（以下代码仅作参考）——
//		
//		/* 实际验证过程建议商户务必添加以下校验：
//		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
//		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
//		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
//		4、验证app_id是否为该商户本身。
//		*/
//		if(signVerified) {//验证成功
//			//商户订单号
//			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
// 
//			//支付宝交易号
//			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
// 
//			//交易状态
//			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
// 
//			//付款金额
//			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
// 
//			if(trade_status.equals("TRADE_FINISHED")) {
//				//判断该笔订单是否在商户网站中已经做过处理
//				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//				//如果有做过处理，不执行商户的业务程序
// 
//				//注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
//				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//			} else if (trade_status.equals("TRADE_SUCCESS")) {
//				//判断该笔订单是否在商户网站中已经做过处理
//				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//				//如果有做过处理，不执行商户的业务程序
// 
//				//注意：
//				//付款完成后，支付宝系统发送该交易状态通知
// 
//				// 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
//				/*orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
// 
//				Orders order = orderService.getOrderById(out_trade_no);
//				Product product = productService.getProductById(order.getProductId());*/
// 
//				logger.info("********************** 支付成功(支付宝异步通知) **********************");
//				logger.info("* 订单号: {}", out_trade_no);
//				logger.info("* 支付宝交易号: {}", trade_no);
//				logger.info("* 实付金额: {}", total_amount);
//				logger.info("* 购买产品: {}", "佳士科技啊");
//				logger.info("***************************************************************");
//			}
//			logger.info("支付成功...");
// 
//		} else {//验证失败
//			logger.error("支付, 验签失败...");
//		}
// 
//		return "/front/order/list";
//	}
}