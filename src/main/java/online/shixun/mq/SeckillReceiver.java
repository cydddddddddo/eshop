package online.shixun.mq;

import online.shixun.dto.*;
import online.shixun.module.member.dao.MemberDaoMyBatis;
import online.shixun.module.order.dao.OrderDaoMyBatis;
import online.shixun.module.order.service.OrderItemServiceImpl;
import online.shixun.module.order.service.OrderServiceImpl;
import online.shixun.module.product.service.ProductServiceImpl;
import online.shixun.module.receiver.service.ReceiverServiceImpl;
import online.shixun.util.DateUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Cy
 * @data 2019/9/3 - 14:34
 */
@Component
public class SeckillReceiver {

    @Autowired
    private MemberDaoMyBatis memberDaoMyBatis;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ReceiverServiceImpl receiverService;

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @Autowired
    private OrderDaoMyBatis orderDao;

    @Autowired
    private ProductServiceImpl productService;

    @RabbitListener(queues = "transmit")
    public void receiver(byte[] bytes) {
        System.out.println(bytes);
        //字节码转对象
        TransmitOrder transmitOrder =(TransmitOrder)getObjectFromBytes(bytes);
        System.out.println(transmitOrder.getGoodsId());

        OrderDTO order = new OrderDTO();

        //秒杀订单状态设置
        if (transmitOrder.getStatus()==1){
            order.setStatus(new OrderStatus("ORDS_Pay"));
        }else {
            order.setStatus(new OrderStatus("ORDS_UnPay"));
        }

        //秒杀订单商品数量
        order.setProductTotalQuantity(transmitOrder.getGoodsCount());

        //秒杀订单商品价格
        order.setProductTotalPrice(transmitOrder.getGoodsPrice());

        //秒杀订单整体价格1元
        order.setTotalAmount(transmitOrder.getGoodsPrice());

        //获得用户
        MemberDTO member = memberDaoMyBatis.getMember(transmitOrder.getUserName());

        //设置用户
        order.setMember(member);

        //秒杀订单编号
        order.setOrderNum(orderService.getOrderNum(member.getId()));

        //秒杀订单来源
        order.setOrderChannel(new OrderChannel("ORDC_Web"));

        //秒杀订单创建时间
        order.setCreateTime(DateUtils.timeToString(transmitOrder.getCreateDate()));

        //秒杀订单修改时间
        order.setUpdateTime(DateUtils.timeToString(transmitOrder.getPayDate()));

        order.setReceiver(receiverService.getReceiver(39L));

        orderDao.saveOrUpdateOrder(order);

        OrderItemDTO orderItem = new OrderItemDTO();
        orderItem.setProduct(productService.getProduct(transmitOrder.getGoodsId()));
        orderItem.setProductQuantity(1);
        orderItem.setTotalPrice(transmitOrder.getGoodsPrice());
        orderItem.setOrder(orderDao.getOrder(order.getId()));
        orderItem.setStatus(new OrderItemStatus("ORIS_UnGrant"));
        orderItemService.saveOrderItem(orderItem);

        orderService.saveOrder(order.getId(),member);
    }

    private Object getObjectFromBytes(byte[] objBytes) {
        if (objBytes == null ||objBytes.length ==0 ){
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = null;

        try {
            oi = new ObjectInputStream(bi);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
