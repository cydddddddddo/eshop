package online.shixun.module.receiver.dao;

import online.shixun.dto.ReceiverDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface ReceiverDaoMyBatis {

    /**
     * 获取用户收货人列表
     */
    List<ReceiverDTO> getReceiversByMember(@Param("memberId") Long memberId);

    /**
     * 获取收货人信息
     */
    ReceiverDTO getReceiver(@Param("receiverId") Long receiverId);

    /**
     * 保存收货人信息
     */
    void saveOrUpdateReceiver(ReceiverDTO receiver);

    /**
     * 删除收货人信息
     */
    void deleteReceiver(@Param("receiverId") Long receiverId);

}
