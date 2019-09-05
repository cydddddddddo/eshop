/*****************************************************************************
 * Copyright (c) 2015, www.qingshixun.com
 *
 * All rights reserved
 *
 *****************************************************************************/

package online.shixun.module.member.dao;

import online.shixun.dto.MemberDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

/**
 * 会员处理 Dao 类
 *
 * @author QingShiXun
 * @version 1.0
 */
@MyBatisRepository
public interface MemberDaoMyBatis {

    /**
     * 根据会员名称获取会员信息
     *
     * @param userName 会员名称
     *
     * @return 会员实体
     */
    MemberDTO getMember(@Param("userName") String userName);

    /**
     * 根据会员Id获取会员信息
     *
     * @return 会员实体
     */
    MemberDTO getMemberById(@Param("memberId") Long memberId);

    /**
     * 根据会员邮箱获取会员信息
     *
     * @return 会员实体
     */
    MemberDTO getMemberByEmail(@Param("email") String email);

    /**
     * 保存用户
     */
    void saveMember(MemberDTO member);

    /**
     * 更新用户默认地址
     */
    void updateMemberDefaultReceiverId(@Param("memberId") Long memberId, @Param("defaultReceiverId") Long defaultReceiverId);

    /**
     * 更新用户状态
     */
    void updateMemberStatus(@Param("memberId") Long memberId, @Param("statusCode") String statusCode);

    /**
     * 删除用户
     */
    void deleteMember(@Param("memberId") Long memberId);
}
