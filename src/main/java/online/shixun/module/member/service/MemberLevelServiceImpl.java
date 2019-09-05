package online.shixun.module.member.service;

import online.shixun.dto.MemberLevelDTO;
import online.shixun.module.member.dao.MemberLevelDaoMyBatis;
import online.shixun.web.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberLevelServiceImpl extends BaseService {

    @Autowired
    private MemberLevelDaoMyBatis memberLevelDao;

    /**
     * 获取对应名称的用户级别
     */
    public MemberLevelDTO getMemberLevelByName(String levelName) {
        return memberLevelDao.getMemberLevelByName(levelName);
    }

}
