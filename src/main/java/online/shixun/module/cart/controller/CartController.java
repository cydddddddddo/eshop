package online.shixun.module.cart.controller;

import online.shixun.dto.CartItemDTO;
import online.shixun.dto.MemberDTO;
import online.shixun.module.cart.service.CartItemServiceImpl;
import online.shixun.web.BaseController;
import online.shixun.web.ResponseData;
import online.shixun.web.SimpleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/front/cart")
public class CartController extends BaseController {

    @Autowired
    private CartItemServiceImpl cartItemService;
    
    /**
     * 进入购物车列表
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String carts(Model model) {
        MemberDTO member = this.getCurrentUser();

        // 获取购物车中的购物项
        List<CartItemDTO> cartItems = cartItemService.getCartItems(member, getSession());

        // 获取当前购物车购物项数据
        model.addAttribute("carts", cartItems);
        // 获取当前购物车的商品数量
        model.addAttribute("totalCartCount", cartItemService.getTotalCartCount(cartItems));
        
        setSessionAttribute("member", member);

        return "/cart/list";
    }
    
    /**
     * 加入购物车
     * @param productId
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save(@RequestParam Long productId) {
        final MemberDTO member = this.getCurrentUser();

        return new SimpleHandler(request) {
            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
                cartItemService.saveCartItem(getSession(), productId, member);
            }
        }.handle();
    }
    
    /**
     * 设置购物车商品的数量
     * @param productId
     * @param quantity
     * @return
     */
    @RequestMapping(value = "/set/quantity/{productId}/{quantity}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData setCartCount(@PathVariable Long productId, @PathVariable int quantity) {
        final MemberDTO member = this.getCurrentUser();

        return new SimpleHandler(request) {
            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
                cartItemService.setCareItemCount(productId, member, getSession(), quantity);
            }
        }.handle();
    }

    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseData cartsDelete(final @PathVariable String ids) {
        final MemberDTO member = this.getCurrentUser();

        return new SimpleHandler(request) {
            @Override
            protected void doHandle(ResponseData responseData) throws Exception {
                cartItemService.deleteCartItem(ids, getSession(), member);
            }
        }.handle();
    }

}
