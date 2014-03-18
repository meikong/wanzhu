package com.wanzhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wanzhu.base.BaseController;
import com.wanzhu.entity.Adv;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.service.ActivityService;
import com.wanzhu.service.AdvService;
import com.wanzhu.service.UserService;
import com.wanzhu.utils.ErrorHelper;

/**
 * 动态模块
 * 
 * @author ZOUSY
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController  extends BaseController
{
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdvService advService;
    
    /**
     * 查询好友动态列表 支持更多方式的分页 每次加载显示15个
     * 
     * type:html/json
     */
    @RequestMapping(value = "/queryActivitiesOfFriend.{type}")
    public String queryActivitiesOfFriend(@PathVariable String type,Model model,Integer currentPage,Integer pageSize)
    {
        Result result = new Result();
        try
        {
            User userByFriend = this.getSessionUser();
            currentPage = null==currentPage?1:currentPage;
            pageSize = null==pageSize?15:pageSize;
            result.setSuccess(true);
            result.setContent(activityService.friendGridData(userByFriend, currentPage, pageSize));
            model.addAttribute("adv", advService.queryByParking("1"));
        }
        catch(Exception e)
        {
            log.error(e.getMessage(), e);
            result.setCode(Integer.parseInt(e.getMessage()));
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
        }
        model.addAttribute("result", result);
        return null==type||"json".equals(type)?"jsonview":"activity/activityOfFriend_list";
    }

    /**
     * 查询我的个人动态列表,每页5条
     */
    @RequestMapping(value = "/queryActivitiesOfMine.json")
    public String queryActivitiesOfMine(Model model,Integer currentPage,Integer pageSize)
    {
        Result result = new Result();
        try
        {
            User user =  this.getSessionUser();
            currentPage = null==currentPage?1:currentPage;
            pageSize = null==pageSize?15:pageSize;
            result.setSuccess(true);
            result.setContent(activityService.personGridData(user,currentPage,pageSize));
        }
        catch(Exception e)
        {
            log.error(e.getMessage(), e);
            result.setCode(Integer.parseInt(e.getMessage()));
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
        }
        model.addAttribute("result", result);
        return "jsonview";
    }
    
    /**
     * 查询别人的个人动态 每页5条
     */
    @RequestMapping(value = "/act_{userId}.json")
    public String queryActivityOfFriend(@PathVariable String userId,Model model,Integer currentPage,Integer pageSize)
    {
        Result result = new Result();
        try
        {
            User user = userService.getUser(userId);
            currentPage = null==currentPage?1:currentPage;
            pageSize = null==pageSize?15:pageSize;
            result.setSuccess(true);
            result.setContent(activityService.personGridData(user,currentPage,pageSize));
        }
        catch(Exception e)
        {
            log.error(e.getMessage(), e);
            result.setCode(Integer.parseInt(e.getMessage()));
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
        }
        model.addAttribute("result", result);
        return "jsonview";
    }

}
