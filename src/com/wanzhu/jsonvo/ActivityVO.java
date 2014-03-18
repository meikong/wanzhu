package com.wanzhu.jsonvo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wanzhu.utils.JsonDateSerializer;

/**
 * 动态vo
 * 
 * @author ZOUSY
 */
public class ActivityVO
{
    private String userId;
    private String username;//用户姓名
    private String portrait;//头像
    private String activityId;//动态id
    private String content;//动态内容
    private Date time;//时间
    
    
    public String getActivityId()
    {
        return activityId;
    }
    public void setActivityId(String activityId)
    {
        this.activityId = activityId;
    }
    
    
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
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
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getTime()
    {
        return time;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
}
