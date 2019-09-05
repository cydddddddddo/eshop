package online.shixun.module.order.dao;

import online.shixun.dto.OrderDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface OrderDaoMyBatis {

    /**
     * 获取用户订单列表
     */
    List<OrderDTO> getOrdersByMember(@Param("memberId") Long memberId);

    /**
     * 获取订单
     */
    OrderDTO getOrder(@Param("orderId") Long orderId);

    /**
     * 保存订单
     */
    void saveOrUpdateOrder(OrderDTO order);

    /**
     * 更新支付状态
     */
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("statusCode") String statusCode);
    
    /**
     * 删除订单
     * @return 
     */
    void deleteOrder(@Param("orderId") Long orderId);
    
    /**
     * 判断退货时间是否超过7天（新增）
     * @param orderId
     * @return
     */
    int checkTime(@Param("orderId") Long orderId);
}
