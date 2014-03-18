package com.wanzhu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.ArticleVo;
import com.wanzhu.jsonvo.LabelVO;
import com.wanzhu.jsonvo.RecommendFriendVO;
import com.wanzhu.jsonvo.VideoVo;
import com.wanzhu.service.EventService;
import com.wanzhu.service.HotService;
import com.wanzhu.service.LabelService;
import com.wanzhu.service.MessageService;
import com.wanzhu.service.NotificationService;
import com.wanzhu.service.RecommendFriendService;
import com.wanzhu.utils.ErrorHelper;
import com.wanzhu.utils.RecommendResult;
import com.wanzhu.utils.StringUtils;

/**
 * 主页
 * @author Keran
 *
 */
@Controller
@RequestMapping
public class IndexController extends BaseController{
	
	private static final int MAX_HOT_LABEL_COUNT = 5;
	
	@Autowired
	private EventService eventService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	MessageService messageService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private RecommendFriendService recommendFriendService;
	@Autowired
	HotService hotService;
	
	/**
	 * 查询近、往期热门活动列表
	 * @param model
	 * @return
	 * @Date:2013-4-24  
	 * @Author:Guibin Zhang  
	 * 1.增加轮播排序（pass）
	 * 2.增加文章接口（pass）
	 * 3.增加视频接口（pass）
	 * @Description:
	 */
	@RequestMapping("/index.html")
	public String index(Model model) {
		List<Event> comingEvents = eventService.queryRecommendComingEvents();
		model.addAttribute("comingEvents", comingEvents);
		List<Event> pastEvents = eventService.queryRecommendPastEvents();
		model.addAttribute("pastEvents", pastEvents);
		//文章列表
		List<ArticleVo> articles = eventService.queryRecommendArticle();
		model.addAttribute("articles", articles);
		//视频列表
		List<VideoVo> videos = eventService.queryRecommendVideo();
		model.addAttribute("videos", videos);
		
		List<LabelVO> labels = labelService.queryRecommendLables();
		if(labels == null){
			labels = new ArrayList<LabelVO>();
		}else if(labels.size() > 5){
			labels = labels.subList(0, 5);
		}
		model.addAttribute("result", labels);
		//热门标签
		//List<LabelVO> labels = hotService.queryHotLabels(MAX_HOT_LABEL_COUNT);
		//model.addAttribute("hotLabels", labels);
		
		return "index";
	}
	
	/**
	 * 查询未读通知与私信条数
	 * @return json
	 */
	@RequestMapping("/qumc.json")
	public String queryUnreadMessageCount(Model model) {
		Result rs = new Result();
		try {
			User user = this.getSessionUser();
			int unc = this.notificationService.getUnreadNotificationCount(user.getUserid());
			int umc =  this.messageService.getUnreadMessageCount(user.getUserid());
			int uc = unc+umc;
			rs.setSuccess(true);
			Map<String,String> data = new HashMap<String,String>();
			data.put("unc", unc+"");
			data.put("umc", umc+"");
			data.put("uc", uc+"");
			rs.setContent(data);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	
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
	
	public static void main(String[] args) {
		
	}
	
}
