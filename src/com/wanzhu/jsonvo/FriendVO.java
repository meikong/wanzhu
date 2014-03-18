package com.wanzhu.jsonvo;

public class FriendVO
{

    private String userId;
    private String username;
    private String portrait;
    private int sex;//性别
    private String summary;//一句话介绍
    private String company;//在职公司
    private String workPosition;//在职职位
    private long friendsCount;//好友个数
    private long eventsCount;//报名的活动个数
    private boolean isFriend;//跟当前登录用户是否为好友关系
    
    
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPortrait()
    {
        return portrait;
    }
    public void setPortrait(String portrait)
    {
        this.portrait = portrait;
    }
    public String getCompany()
    {
        return company;
    }
    public void setCompany(String company)
    {
        this.company = company;
    }
    public String getWorkPosition()
    {
        return workPosition;
    }
    public void setWorkPosition(String workPosition)
    {
        this.workPosition = workPosition;
    }
    public long getFriendsCount()
    {
        return friendsCount;
    }
    public void setFriendsCount(long friendsCount)
    {
        this.friendsCount = friendsCount;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public int getSex()
    {
        return sex;
    }
    public void setSex(int sex)
    {
        this.sex = sex;
    }
    public long getEventsCount()
    {
        return eventsCount;
    }
    public void setEventsCount(long eventsCount)
    {
        this.eventsCount = eventsCount;
    }
    public String getSummary()
    {
        return summary;
    }
    public void setSummary(String summary)
    {
        this.summary = summary;
    }
    public boolean getIsFriend()
    {
        return isFriend;
    }
    public void setIsFriend(boolean isFriend)
    {
        this.isFriend = isFriend;
    }
}
