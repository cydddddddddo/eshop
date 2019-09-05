package online.shixun.module.cart.dao;

import online.shixun.dto.CartItemDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface CartItemDaoMyBatis {

    /**
     * 通过会员Id获取购物项列表数据
     */
    List<CartItemDTO> getCartItemsByMember(@Param("memberId") Long memberId);

    /**
     * 通过会员Id商品id获取购物项数据
     */
    CartItemDTO getCartItem(@Param("memberId") Long memberId, @Param("productId") Long productId);

    /**
     * 保存购物车
     */
    void saveCartItem(CartItemDTO cartItem);
    
    void updateCartItem(CartItemDTO cartItem);
    
    /**
     * 更新购物车商品数量
     */
    void updateCartItemQuantity(CartItemDTO cartItem);

    /**
     * 删除购物车
     */
    void deleteCartItem(@Param("cartItemId") Long cartItemId);
    
    /**
     * 在购买相应商品后，删除购物车中的相应商品
     * @param memberId
     * @param productId
     */
    void deletePurchasedGood(@Param("memberId") Long memberId, @Param("productId") Long productId);
}
