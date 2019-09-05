package online.shixun.module.member.dao;

import online.shixun.dto.MemberLevelDTO;
import online.shixun.web.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface MemberLevelDaoMyBatis {

    /**
     * 获取对应名称的用户级别
     */
    MemberLevelDTO getMemberLevelByName(@Param("levelName") String levelName);

}
