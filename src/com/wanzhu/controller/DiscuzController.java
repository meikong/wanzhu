package com.wanzhu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wanzhu.base.BaseController;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.FriendVO;
import com.wanzhu.jsonvo.RemarkHotVo;
import com.wanzhu.jsonvo.TopicVO;
import com.wanzhu.service.DiscuzService;
import com.wanzhu.service.EventService;
import com.wanzhu.service.FriendsService;
import com.wanzhu.service.HotService;
import com.wanzhu.service.UserService;
import com.wanzhu.utils.ErrorHelper;
import com.wanzhu.utils.EscapeHtml;

/**
 * 讨论
 * @author zhanglei
 *
 */
@Controller
@RequestMapping("/discuz")
public class DiscuzController extends BaseController {
	
	@Autowired
	private DiscuzService discuzService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private EventService eventService;
	@Autowired
	private HotService hotService;
	
	
	
	/**
	 * @param model
	 * @return
	 * @Date:2013-5-8  
	 * @Author:xuguangyun  
	 * @Description:查询热门话题
	 */
	@RequestMapping("/t_{topicId}.html")
	public String queryHotTopics(Model model, @PathVariable("topicId") String topicId) {
		TopicVO topicVO=null;
		try {
			System.out.println("topic:yyyyyyyyyyy::::::::::::::::"+topicId);
			topicVO=discuzService.getTopicById(topicId).get(0);
			Event event = this.eventService.queryEventDetail(topicVO.getEventid());
			model.addAttribute("event", event);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.addAttribute("topicVo", topicVO);
		return "/topic/topic_detail";
	}
	
	/**
	 * @param model
	 * @param type
	 * @param summaryId
	 * @return
	 * @Date:2013-4-28  
	 * @Author:xuguangyun  
	 * @Description:跳转到话题列表
	 */
	
	@RequestMapping("/s_{eventId}.html")
	public String redirectTopHtml(Model model, @PathVariable("eventId") String eventId){
		model.addAttribute("eventId", eventId);
		Event event = this.eventService.queryEventDetail(eventId);
		model.addAttribute("event", event);
		return "/topic/topic_list";
	}
	/**
	 * 查询话题
	 * @return json
	 */
	@RequestMapping("/queryTopics.json")
	public String queryTopics(String eventId, int start, int size, Model model) {
		Result rs = new Result();
		try {
			rs.setSuccess(true);
			rs.setContent(this.discuzService.queryTopics(eventId, start, size));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}

	/**
	 * @param model
	 * @return
	 * @Date:2013-5-8  
	 * @Author:xuguangyun  
	 * @Description:查询热门话题
	 */
	@RequestMapping("/queryHotTopics.json")
	public String queryHotTopics(Model model) {
		Result rs = new Result();
		try {
			rs.setSuccess(true);
			List<TopicVO> topicVoList = new ArrayList<TopicVO>();
			List<RemarkHotVo> remarkHotVOList = hotService.queryByParking();
			for (RemarkHotVo remarkHotVo : remarkHotVOList) {
				topicVoList.add(discuzService.getTopicById(
						remarkHotVo.getHotId()).get(0));
			}
			System.out.println("queryHotTopics.jsonaaaaaaaaaaa:::::"+topicVoList.size());
			rs.setContent(topicVoList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	/**
	 * 查询评论
	 * @return json
	 */
	@RequestMapping("/queryRemarks.json")
	public String queryRemarks(String topicId, int start, int size, Model model) {
		Result rs = new Result();
		try {
			rs.setSuccess(true);
			rs.setContent(this.discuzService.queryRemarks(topicId, start, size));
			System.out.println("queryRemarks.json.jsonbbbbbbbbbbbb:::::"+this.discuzService.queryRemarks(topicId, start, size).size());
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		System.out.println(rs.getContent());
		return "jsonview";
	}
	
	/**
	 * 赞踩
	 * @return
	 */
	@RequestMapping("/agreeRemark.json")
	public String agree(String remarkId, Integer agreecount,Integer disagreecount, Model model) {
		Result rs = new Result();
		try { 
			
			discuzService.updateAgreecount(remarkId, agreecount,disagreecount);
			rs.setSuccess(true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	/**
	 * 赞一个
	 * @return
	 */
	@RequestMapping("/agree.json")
	public String agree(String topicId, Model model) {
		Result rs = new Result();
		try { 
			User user = this.getSessionUser();
			rs.setContent(discuzService.agree(topicId, user));
			rs.setSuccess(true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 踩一脚
	 * @return
	 */
	@RequestMapping("/disagree.json")
	public String disagree(String topicId, Model model) {
		Result rs = new Result();
		try { 
			User user = this.getSessionUser();
			rs.setContent(discuzService.disagree(topicId, user.getUserid()));
			rs.setSuccess(true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 发表话题1、评论话题2、回复评论3
	 * @return
	 */
	@RequestMapping("/publish.json")
	public String publish(int operateType, String eventId, String topicId, String remarkId, String content, Model model) {
		Result rs = new Result();
		try {
			//对内容转义
			content = EscapeHtml.Html2Text(content);
			content = content.replaceAll("\n", "<br/>");
			User user = this.getSessionUser();
			rs.setContent(this.discuzService.publish(operateType, user, eventId, topicId, remarkId, content));
			rs.setSuccess(true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 悬浮框显示用户基本信息
	 *   friendVO
	 */
	@RequestMapping("/aboutUser.json")
	public String aboutUser(Model model,String userId,String friendId)
	{
	    Result result  = new Result();
        try
        {
        	User user = this.getSessionUser();
            FriendVO friendVO = friendsService.abountUser(userId, friendId);
            result.setSuccess(true);
            result.setContent(friendVO);
        }
        catch(Exception e)
        {
            log.error(e.getMessage(), e);
            result.setSuccess(false);
        }
        model.addAttribute("result", result);
        return "jsonview";
	}
}
