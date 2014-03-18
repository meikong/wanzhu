package com.wanzhu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.entity.FriendLog;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.FriendVO;
import com.wanzhu.jsonvo.RecommendFriendVO;
import com.wanzhu.service.AdvService;
import com.wanzhu.service.FriendsService;
import com.wanzhu.service.RecommendFriendService;
import com.wanzhu.service.UserService;
import com.wanzhu.utils.ErrorHelper;
import com.wanzhu.utils.RecommendResult;
import com.wanzhu.utils.StringUtils;

/**
 * 好友模块
 * 
 * @author ZOUSY
 */
@Controller
@RequestMapping(value = "/relationship")
public class RelationshipController
    extends BaseController
{
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdvService advService;
    @Autowired
	private RecommendFriendService recommendFriendService;
    
    /**
     * 查询我的好友列表 显示10条记录
     * 
     * @return json
     * @throws Exception 
     */
    @RequestMapping(value="/friends.json")
    public String queryFriends(Model model,Integer currentPage,Integer pageSize)
    {
        Result result = new Result();
        try
        {
            currentPage = null==currentPage?1:currentPage;
            pageSize = null==pageSize?10:pageSize;
            result.setSuccess(true);
            result.setContent(friendsService.queryFrindsVo(this.getSessionUser(),currentPage, pageSize));
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
     *  查询某个用户的好友列表 显示10条记录
     */
    @RequestMapping(value="/u_{userId}.json")
    public String queryFriends(@PathVariable("userId") String userId,Model model,Integer currentPage,Integer pageSize)
    {
        Result result = new Result();
        try
        {
            User user = userService.getUser(userId);
            Page<FriendVO> page = friendsService.queryFrindsVo(user,currentPage, pageSize);
            List<FriendVO> list = page.getResult();
            User i = this.getSessionUser();
            if(i == null){//没有登录
            	for (FriendVO friendVO : list) {
            		friendVO.setIsFriend(true);//没有登录，无法判断是否是好友
				}
            }else{
            	String myId = i.getUserid();
            	for (FriendVO friendVO : list) {
            		if(friendsService.isFriend(myId, friendVO.getUserId())){
            			friendVO.setIsFriend(true);
                        continue;
            		}else if(myId.equals(friendVO.getUserId())){
            			friendVO.setIsFriend(true);
            		}
				}
            }
            page.setResult(list);
            currentPage = null==currentPage?1:currentPage;
            pageSize = null==pageSize?10:pageSize;
            result.setSuccess(true);
            result.setContent(page);
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
     * 查询好友列表（包含自已及好友的拥有的好友个数） (分页形式，一页20个)
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/friendsInDetail.html")
    public String queryFriendsInDetail(Model model, Integer pageNo, Integer pageSize,String condition) throws Exception
    {
        try
        {
            pageNo = null==pageNo?1:pageNo;
            pageSize=null==pageSize?Page.DEFAULT_PAGE_SIZE:pageSize;
            User user = this.getSessionUser();
            //我的好友个数从SystemBuffer中拿
           model.addAttribute("friendsCountOfMine", friendsService.friendsCount(user));
            // 我的好友、好友的拥有的好友个数(好友个数从SystemBuffer中拿)
           model.addAttribute("page", friendsService.queryFriendsByName(user, condition, pageNo, pageSize));
           model.addAttribute("condition", condition);
           model.addAttribute("adv",advService.queryByParking("4"));
        }
        catch(Exception e)
        {
            log.error(e.getMessage(), e);
        }
        return "relationship/friendsInDetail";
    }
    
    
    /**
     * 查询别人的好友列表 (分页形式，一页20个)
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/friends_{userId}.html")
    public String queryUserFriends(@PathVariable("userId") String userId,Model model, Integer pageNo, Integer pageSize,String condition) 
    {
        try {
            pageNo = null==pageNo?1:pageNo;
            pageSize=null==pageSize?Page.DEFAULT_PAGE_SIZE:pageSize;
            User user = userService.getUser(userId);
            //他的好友个数从SystemBuffer中拿
           model.addAttribute("friendsCountOfMine", friendsService.friendsCount(user));
            // 他的好友、好友的拥有的好友个数(好友个数从SystemBuffer中拿)
           Page<FriendVO> page = friendsService.queryFriendsByName(user, condition, pageNo, pageSize);
           List<FriendVO> list = page.getResult();
           User i = this.getSessionUser();
           if(i == null){//没有登录
        	   for (FriendVO friendVO : list) {
        		   friendVO.setIsFriend(true);//没有登录，无法判断是否是好友
        	   }
           }else{
        	   String myId = i.getUserid();
           	   for (FriendVO friendVO : list) {
           		   if(friendsService.isFriend(myId, friendVO.getUserId())){
           			   friendVO.setIsFriend(true);
                       continue;
           		   }else if(myId.equals(friendVO.getUserId())){
           			   friendVO.setIsFriend(true); 
           		   }
           	   }
           }
           page.setResult(list);
           
           model.addAttribute("page", page);
           model.addAttribute("condition", condition);
           model.addAttribute("adv",advService.queryByParking("4"));
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return "relationship/userFriends";
    }

    /**
     * 添加好友（单个）
     * 
     * 产生通知
     * 
     * @return json
     */
    @RequestMapping(value = "/addFriend.json")
    public String addFriend(Model model,FriendLog friendLog)
    {
        Result result = new Result();
        try
        {
            // TODO
            // 1、操作添加好友日志表
            // 2、产生通知
            if(null == friendLog || friendLog.getUserByFriendid().getUserid().length()==0)
            {
                result.setSuccess(false);
                result.setMsg("操作失败！");
            }
            else
            {
                friendsService.addFriend(this.getSessionUser(),friendLog);
                result.setSuccess(true);
                result.setMsg("发送成功，等待对方接受你的好友请求。");
            }
        }
        catch(Exception e)
        {
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
            e.printStackTrace();
        }
        model.addAttribute("result", result);
        return "jsonview";
    }

    /**
     * * 添加好友（一键添加好友）
     * 
     * 产生通知
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/add_friends_{eventId}.json")
    public String addFriends(@PathVariable("eventId") String eventId, Model model)
    {
        Result result = new Result();
        try
        {
            // TODO
            // 1、操作添加好友日志表
            // 2、产生通知
            friendsService.addFriends(getSessionUser(), userService.querySignedInUsers(eventId, 0, Integer.MAX_VALUE).getResult());
            result.setSuccess(true);
            result.setMsg("一键添加好友成功!");
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
     * 删除好友(双向的)
     * 
     */
    @RequestMapping(value = "/del_{friendId}.{type}")
    public String deleteFriend(@PathVariable("friendId") String friendId,@PathVariable("type") String type, Model model)
    {
        Result result = new Result();
        // TODO
        // 1、操作好友表
        // 删除一条好友关系
        try
        {
            friendsService.deleteFriend(getSessionUser(),friendId);
            result.setSuccess(true);
            result.setMsg("操作成功。");
        }
        catch(Exception e)
        {
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
            log.error(e.getMessage(), e);
        }
        model.addAttribute("result", result);
        return null== type || "json".equals(type)?"jsonview":"redirect:/relationship/friendsInDetail.html";
    }

    /**
     * 同意添加好友
     * 
     * 产生通知 
     * 产生个人动态
     * 
     * @return json
     * @throws Exception 
     */
    @RequestMapping(value = "/agree_{friendId}_{notificationId}.json")
    public String agreeToAdd(@PathVariable("friendId") String friendId, @PathVariable("notificationId") String notificationId,  Model model)
    {
    	Result result = new Result();
        // TODO
        // 1、更新  加好友日志表
        // 2、操作好友表添加一条好友记录
        // 3、操作个人动态表，添加一条动态 
        // 4、产生通知
        try
        {
            User user = this.getSessionUser();
            User userByFriendId = userService.getUser(friendId);
            
            friendsService.agreeToAdd(user, userByFriendId, notificationId);
            result.setSuccess(true);
            result.setMsg("操作成功。");
        }
        catch(ConstraintViolationException e) {
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg("30003"));
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
            log.error(e.getMessage(), e);
        }
        model.addAttribute("result", result);
        return "jsonview";
       
    }

    /**
     * 拒绝添加好友 
     * 
     * 产生通知
     * 
     * @return json
     */
    @RequestMapping(value = "/disagree_{friendId}_{notificationId}.json")
    public String disagreeToAdd(@PathVariable("friendId") String friendId, @PathVariable("notificationId") String notificationId,  Model model)
    {
        Result result = new Result();
        // TODO
        // 1、更新添加好友日志表
        // 2、产生通知，被拒绝
        try
        {
            friendsService.disagreeToAdd(getSessionUser(), friendId, notificationId);
            result.setSuccess(true);
            result.setMsg("操作成功。");
        }
        catch(Exception e)
        {
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
            log.error(e.getMessage(), e);
        }
        model.addAttribute("result", result);
        return "jsonview";
    }
    
    /**
     * 推荐好友
     * @param model
     * @return
     * @Date:2013-5-29  
     * @Author:Guibin Zhang  
     * @Description:
     */
    @RequestMapping("/recommendFriend.json")
	@ResponseBody
	public Object queryRecommendFriends(Model model) {
		Page<RecommendFriendVO> page = null;
		RecommendResult<RecommendFriendVO> rResult = null;
		try {
			int max = 5;
			User u = this.getSessionUser();
			if(u == null || u.getUserid() ==null ||u.getUserid().length() == 0){
				rResult = new RecommendResult<RecommendFriendVO>(false, new ArrayList<RecommendFriendVO>(0));
			}else{
				page = recommendFriendService.queryRecommendFriendsPage(1, 100, u.getUserid());
				List<RecommendFriendVO> list = page.getResult();
				int total = list.size();
				Set<Integer> set = generateRandomNumber(total, max);
				ArrayList<RecommendFriendVO> result = new ArrayList<RecommendFriendVO>();
				for (Integer index : set) {
					RecommendFriendVO vo = list.get(index);
					String str = vo.getReason();
					String[] s = str.split(",");
					Random r = new Random();
					int strIndex = r.nextInt(s.length);
					vo.setReason(s[strIndex]);
					//加入默认头像
					if(StringUtils.isEmpty(vo.getPortrait())){
						String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
						vo.setPortrait(defaultpath);
					}
					result.add(list.get(index));
				}
				rResult = new RecommendResult<RecommendFriendVO>((total > max),result);
			}
		} catch (Exception e) {
			System.out.println("===================查询推荐好友出现异常=====================");
			rResult = new RecommendResult<RecommendFriendVO>(false, new ArrayList<RecommendFriendVO>(0));
			e.printStackTrace();
		}
		return rResult;
	}
	
	/**
	 * 生成5个不同的随机数
	 * @param total 列表总数
	 * @param max 最多产生几个
	 * @return
	 * @Date:2013-5-28  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	private Set<Integer> generateRandomNumber(int total, int max){
		Set<Integer> result = new TreeSet<Integer>();
		if(total <= max){//总数小于每页最多数5个
			for (int i = 0; i < total; i++) {
				result.add(i);
			}
		}else{
			Random r = new Random();
			while (result.size() < max) {
				result.add(r.nextInt(total));
			}
		}
		return result;
	}
    
}
