/*****************************************************************************
 * Copyright (c) 2017, www.qingshixun.com
 *
 * All rights reserved
 *
 *****************************************************************************/
package online.shixun.dto;

/**
 * 购物车实体类
 * 
 * @author QingShiXun
 * 
 * @version 1.0
 */

public class CartItemDTO extends BaseDTO {

    private static final long serialVersionUID = -4241469437632553865L;

    // 总价格
    private Double totalPrice;

    // 数量
    private Integer quantity;

    // 商品
    private ProductDTO product;

    // 会员
    private MemberDTO member;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        refreshPrice();
    }

    public void setQuantity() {
        setQuantity(quantity + 1);
    }

    public ProductDTO getProduct() {
        return product;
    }
    
    public void setProduct(ProductDTO product , Integer quantity) {
        this.product = product;
        //设置商品数量，新建为1，已有商品设置为数据库中的数量（优化）
        setQuantity(quantity);
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void refreshPrice() {
        if (product != null) {
            totalPrice = product.getPrice() * quantity;
        }
    }

}
