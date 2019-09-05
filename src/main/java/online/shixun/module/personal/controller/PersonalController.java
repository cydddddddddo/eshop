package online.shixun.module.personal.controller;

//import java.util.LinkedList;
import java.util.List;

import online.shixun.dto.EvaluateDTO;
import online.shixun.dto.MemberDTO;
import online.shixun.dto.OrderItemDTO;
import online.shixun.dto.ProductDTO;
import online.shixun.module.order.service.OrderItemServiceImpl;
import online.shixun.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import online.shixun.module.evaluate.service.EvaluateServiceImpl;
import online.shixun.module.personal.service.PersonalService;
import online.shixun.module.receiver.service.ReceiverServiceImpl;

@Controller
@RequestMapping(value="/front/personal")
public class PersonalController extends BaseController {
	
	@Autowired
    private ReceiverServiceImpl receiverService;
	
	@Autowired
    private OrderItemServiceImpl orderItemService;
	
	@Autowired
	private PersonalService personalService;
	
	@Autowired
	private EvaluateServiceImpl evaluateService;

	
	@RequestMapping(value="")
	public String getMenu() {
		
		return "/personal/menu";
		
	}
	
	@RequestMapping(value="/address")
	public String getAddress(Model model,@RequestParam(required = false, defaultValue = "") String params) {
		MemberDTO member = this.getCurrentUser();
		
		List<OrderItemDTO> orderItems = orderItemService.getSelectCart(params, getSession());
		
		model.addAttribute("receivers", receiverService.getReceiversByMember(member.getId()));
		model.addAttribute("orderItems", orderItems);
		model.addAttribute("member", member);
		model.addAttribute("params", params);
		
		return "/personal/address";
	}
	

	@RequestMapping(value="/history")
	public String getHistory(Model model) {
		final MemberDTO member = this.getCurrentUser();
		Long memberId = member.getId();
		
		List<ProductDTO> products = personalService.getProducts(memberId);
		
//		List<ProductDTO> products = new LinkedList<>();
//		
//		for(int i = 0;i<allproduct.size();i++) {
//			
//			ProductDTO product = allproduct.get(i);
//			products.add(0,product);
//		}
	
		model.addAttribute("products", products);

		
		return "/personal/history";
	}
	
	@RequestMapping(value="/delete")
	public String deleteHistory(Model model,@RequestParam String createTime) {
		personalService.deleteHistory(createTime);
		
		final MemberDTO member = this.getCurrentUser();
		Long memberId = member.getId();
		
		List<ProductDTO> products = personalService.getProducts(memberId);
		model.addAttribute("products", products);
		
		return "/personal/history";
	}
	
	@RequestMapping(value="/evaluate")
	public String getEvaluates(Model model) {
		final MemberDTO member = this.getCurrentUser();
		Long memberId = member.getId();
		List<EvaluateDTO> evaluates = evaluateService.getEvaluateByMember(memberId);
		
		model.addAttribute("evaluates", evaluates);
		
		return "product/evaluate/list";
	}
}
