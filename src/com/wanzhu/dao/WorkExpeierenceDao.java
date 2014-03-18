package com.wanzhu.dao;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.User;
import com.wanzhu.entity.WorkExperience;

@Repository
public class WorkExpeierenceDao extends BaseDao<WorkExperience> {

    /**
     * 检索某个用户当前WorkExperience
     * @param user
     *                      目标用户
     * @return
     */
    public WorkExperience queryCurrentWorkExperience(User user)
    {
        String hql = "FROM WorkExperience AS o WHERE o.user=? AND current=1";
        return findEntity(hql, new Object[]{user});
    }
}
