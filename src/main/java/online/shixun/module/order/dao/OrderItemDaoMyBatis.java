package online.shixun.module.order.dao;

import online.shixun.dto.OrderItemDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface OrderItemDaoMyBatis {

    /**
     * 获取订单所有项
     */
    List<OrderItemDTO> getOrderItemsByOrder(@Param("orderId") Long orderId);

    /**
     * 保存订单项
     */
    void saveOrderItem(OrderItemDTO orderItem);
    
    /**
     * 删除订单项目
     */
    void deleteOrderItem(@Param("orderId") Long orderId);
}
