package com.wanzhu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.Page;
import com.wanzhu.dao.FriendActivityDao;
import com.wanzhu.dao.PersonalActivityDao;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.ActivityVO;

/**
 * 动态
 * @author ZOUSY
 */
@Service("activity.service")
@Transactional(readOnly = true)
public class ActivityService
{
    
    @Autowired
    private FriendActivityDao friendActivityDao;
    @Autowired
    private PersonalActivityDao personalActivityDao;
    
    /**
     * 检索个人动态信息
     * @param user
     *                      目标用户
     * @param pageNo
     *                      当前页码
     * @param pageSize
     *                      每页显示条数
     * @return     个人动态VO
     */
    public Page<ActivityVO> personGridData(User user,int pageNo,int pageSize)
    {
        return personalActivityDao.pagedPersionActivityVos(user, (pageNo-1)*pageSize, pageSize);
    }
    
    /**
     * 生成一条个人动态
     * 
     * @param userId
     *                      目标用户
     * @param contentTxt
     *                      动态内容
     * @param type
     *                      个人动态类型
     *  @param targetId
     *                      某个活动/用户的id，依type而异。注意话题类型，targetId传活动id！
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addPersonActivity(String userId,String contentTxt,int type,String targetId)
    {
        personalActivityDao.addPersonActivity(userId, contentTxt, type, targetId, null);
    }
    
    
    /**
     * 检索好友动态信息
     * @param user
     *                      目标用户
     * @param pageNo
     *                      当前页码
     * @param pageSize
     *                       页面显示条数
     * @return      好友动态信息集
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Page<ActivityVO> friendGridData(User user,int pageNo,int pageSize)
    {
        return friendActivityDao.pagedFriendActivityVos(user, (pageNo-1)*pageSize, pageSize);
    }
    
    /**
     * 生成一条好友动态
     * 
     * @param userId
     *                      目标用户
     * @param contentTxt
     *                      动态内容
     * @param type
     *                      好友动态类型
     * @param targetId
     *                      某个活动/用户的id，依type而异。注意话题类型，targetId传活动id！
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addFriendActivity(String userId,String contentTxt,int type,String targetId)
    {
        friendActivityDao.addFriendActivity(userId, contentTxt, type, targetId);
    }
}
