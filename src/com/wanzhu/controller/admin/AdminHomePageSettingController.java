package com.wanzhu.controller.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.base.SystemBuffer;
import com.wanzhu.entity.Adv;
import com.wanzhu.entity.Code;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Label;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.Summary;
import com.wanzhu.service.admin.AdminAdvService;
import com.wanzhu.service.admin.AdminEventService;
import com.wanzhu.service.admin.AdminLabelsService;
import com.wanzhu.service.admin.AdminUserService;
import com.wanzhu.utils.StringUtils;
import com.wanzhu.utils.showorder.Order;
import com.wanzhu.utils.showorder.ShowOrderUtil;

@Controller
@RequestMapping("/adminHomePageSetting")
public class AdminHomePageSettingController extends BaseController {

	public static int RecommendationCount = 0;
	private static final String EVENT_VISIBLE = "1"; // 活动可见
	private static final String EVENT_STATE_NOT_BEGIN = "1"; // 活动未开始
	private static final int EVENT_STATE_OVER = 2; // 活动已结束
	private static final String RECOMMEND_STATE = "0"; // 没有推荐
	@Autowired
	private AdminAdvService adminAdvService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AdminEventService adminEventService;
	@Autowired
	private AdminLabelsService adminLabelsService;
	
	/**
	 * 推荐/收起推荐活动
	 * @param model
	 * @param event
	 * @param kind 0--收起推荐  1--推荐
	 * @return
	 * @Date:2013-4-16  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/recommendEvent")
	public String recommend(Model model, String eventId, int kind) {
		try{
			adminEventService.recommendById(eventId, kind);
			if(kind == 1)
				RecommendationCount ++;
			else
				RecommendationCount --;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "redirect:/adminHomePageSetting/queryRecommendEvent";
	}
	
	/**
	 * 推荐/收起推荐
	 * @param model
	 * @param event
	 * @param kind 0--收起推荐  1--推荐
	 * @return
	 * @Date:2013-4-16  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/recommendSummary")
	public String recommend(Model model, String summaryid,int type, int kind) {
		try{
			adminEventService.recommendSummaryById(summaryid, kind);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "redirect:/adminHomePageSetting/queryRecommendSummary?type=" + type;
	}
	
	/**
	 * 推荐/收起推荐
	 * @param model
	 * @param labelid
	 * @param kind 0--收起推荐  1--推荐
	 * @return
	 * @Date:2013-4-16  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/recommendLabel")
	public String recommendLabel(Model model, String labelid, int kind) {
		try{
			adminLabelsService.recommendLabelById(labelid, kind);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "redirect:/adminHomePageSetting/queryLabels";
	}
	
	/**
	 * 跨页计算排序
	 * @param model
	 * @param id 要更改的记录的id
	 * @param type 1--活动 2--文章 0--视频 3--标签
	 * @param kind 1--向上跨页 2--向下跨页
	 * @return
	 * @Date:2013-4-21  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/crossPageSaveOrder")
	public String crossPageSaveOrder(Model model, Integer currentPageNo, Integer currentpageSize, String id, Integer type, Integer kind) {
		try{
			if(kind == 1){
				currentPageNo--;
			}else if(kind == 2){
				currentPageNo++;
			}
			if(type == 1){//活动跨页
				adminEventService.crossPageSaveOrder(id, type, kind);
				String rs = queryEvent(model, currentPageNo, currentpageSize, null);
				return rs;
			}else if(type == 2 || type == 0){//视频或文字跨页
				//调用视频或文字方法
				adminEventService.crossPageSaveOrder(id, type, kind);
				String rs = querySummary(model, type, currentPageNo, currentpageSize, null);
				return rs;
			}else if(type == 3){//标签排序
				adminLabelsService.crossPageSaveOrder(id, kind);
				String rs = queryLabels(model, currentPageNo, currentpageSize, null);
				return rs;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("/query")
	public String query(Model model, Integer pageNo, Integer pageSize, String content) {
		List<Code> listCode = adminEventService.findCode();
		model.addAttribute("listCode", listCode);
		if (RecommendationCount == 0)
			RecommendationCount = adminEventService.queryRecommendationEventsCount();
		try {
			if (this.getSessionAdmin() == null) {
				return "redirect:/admin/index?target=_top";
			}
			if (null == pageNo) {
				pageNo = 1;
			}
			if (null == pageSize) {
				pageSize = Page.ADMIN_EVENT_SIZE;
			}
			Page<Event> page = null;
			if (pageNo == 1) {// 如果是第一页 需要加载推荐的条数
				page = adminEventService.queryEventsPage(pageNo, pageSize, null, EVENT_STATE_NOT_BEGIN, EVENT_VISIBLE, content);
				List<Event> events = page.getResult();
				model.addAttribute("vos", events.subList(0, RecommendationCount));
				pageSize = Page.ADMIN_EVENT_SIZE - RecommendationCount;
				page.setPageSize(pageSize);
				page.setResult(events.subList(RecommendationCount, events.size()));
				page.setTotalCount(page.getTotalCount() - RecommendationCount);
			} else {// 不是第一页 需要计算
				pageSize = Page.ADMIN_EVENT_SIZE - RecommendationCount;
				page = adminEventService.queryEventsPage(pageNo, pageSize, RECOMMEND_STATE, EVENT_STATE_NOT_BEGIN, EVENT_VISIBLE, content);
			}
			model.addAttribute("page", page);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "admin/homePageSetting";
	}
	
	/**
	 * 保存当页排序
	 * @param model
	 * @param currentPage
	 * @param pageSize
	 * @param type 1--活动 2、3 视频文字
	 * @param updateArray
	 * @return
	 * @Date:2013-4-20  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/saveOrdered")
	public String saveOrdered(Model model, Integer currentPage, Integer pageSize,int type, String updateArray) {
		Result result = new Result();
		try{//保存变更的序号
			//System.out.println("currentPage: " + currentPage);
			//System.out.println("pageSize: " + pageSize);
			List<Order> orders = ShowOrderUtil.formatToList(updateArray);
			boolean r = false;
			if(type == 1)//活动
				r = adminEventService.saveEventOrder(orders);
			else if(type == 2 || type == 0)//文章或视频
				r = adminEventService.saveOrder(orders);
			else if(type == 3){//标签
				r = adminLabelsService.saveOrder(orders);
			}
            result.setSuccess(r);
		} catch (Exception e) { 
			result.setSuccess(false);
			log.error(e);
			e.printStackTrace();
		}
		model.addAttribute("result", result);
		return "jsonview"; 
	}
	
	/**
	 * 首页管理tab页 活动管理查询数据
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @param content
	 * @return
	 * @Date:2013-4-19  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/queryRecommendEvent")
	public String queryEvent(Model model, Integer pageNo, Integer pageSize, String content) {
		List<Code> listCode = adminEventService.findCode();
		model.addAttribute("listCode", listCode);
		RecommendationCount = adminEventService.queryRecommendationEventsCount();
		try {
			if (this.getSessionAdmin() == null) {
				return "redirect:/admin/index?target=_top";
			}
			if (null == pageNo) {
				pageNo = 1;
			}
			if (null == pageSize) {
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
			}
			Page<Event> page = null;
			
			if (pageNo == 1 && (content==null || content.length()==0)) {// 如果是第一页,且不是搜索 需要加载推荐的条数
				page = adminEventService.queryEventsPage(pageNo, pageSize, null, EVENT_STATE_NOT_BEGIN, EVENT_VISIBLE, content);
				List<Event> events = page.getResult();
				model.addAttribute("vos", events.subList(0, RecommendationCount));
				page.setResult(new ArrayList<Event>(events.subList(RecommendationCount, events.size())));
			} else if(!(content==null || content.length()==0)){// 是搜索，需要排除已推荐的
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
				page = adminEventService.queryEventsPage(pageNo, pageSize, RECOMMEND_STATE, EVENT_STATE_NOT_BEGIN, EVENT_VISIBLE, content);			
			} else { //不是第一页 且 不是搜索
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
				page = adminEventService.queryEventsPage(pageNo, pageSize, null, EVENT_STATE_NOT_BEGIN, EVENT_VISIBLE, content);
			}
			model.addAttribute("page", page);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "admin/recommendEvent";
	}
	
	/**
	 * 查询视频或文字
	 * @param model
	 * @param type 0--视频 2 文字
	 * @param pageNo
	 * @param pageSize
	 * @param content
	 * @return
	 * @Date:2013-4-20  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/queryRecommendSummary")
	public String querySummary(Model model, int type, Integer pageNo, Integer pageSize, String content) {
		List<Event> allEvents = adminEventService.findEvent(EVENT_STATE_OVER);
		model.addAttribute("allEvents", allEvents);
		int recommend = 0;
		try {
			recommend = adminEventService.queryRecommendationSummaryCount(type);
			if (this.getSessionAdmin() == null) {
				return "redirect:/admin/index?target=_top";
			}
			if (null == pageNo) {
				pageNo = 1;
			}
			if (null == pageSize) {
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
			}
			Page<Summary> page = null; 
			
			if (pageNo == 1 && (content==null || content.length()==0)) {// 如果是第一页,且不是搜索 需要加载推荐的条数
				page = adminEventService.querySummaryPage(type, pageNo, pageSize, null, content);
				List<Summary> summarys = page.getResult();
				model.addAttribute("vos", summarys.subList(0, recommend));
				page.setResult(summarys.subList(recommend, summarys.size()));
			} else if(!(content==null || content.length()==0)){// 是搜索，需要排除已推荐的
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
				page = adminEventService.querySummaryPage(type, pageNo, pageSize, RECOMMEND_STATE, content);			
			} else { //不是第一页 且 不是搜索
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
				page = adminEventService.querySummaryPage(type, pageNo, pageSize, null, content);
			}
			model.addAttribute("page", page);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return (CommonConstant.SUMMARY_OF_VIDEO == type)?"admin/recommendVideo":"admin/recommendArticle"; 
	}
	

	@RequestMapping("/modify")
	public String modify(Model model, Adv adv, @RequestParam(value = "file", required = false) MultipartFile[] file, String parkings) {
		try {
			Adv vo = adminAdvService.queryById(adv.getAdvid());
			vo = null == vo ? new Adv() : vo;
			vo.setMemo(adv.getMemo());
			vo.setLink(adv.getLink());
			vo.setValid(adv.getValid());
			vo.setParking(adv.getParking());
			if (null != file && file.length > 0 && !file[0].isEmpty()) {
				String filePath = CommonConstant.upload_file_system_path + "/" + CommonConstant.adv_dir_name + "/";
				String fileName = SystemBuffer.uuidHexGenerator.generateUUID() + "." + StringUtils.getFileExtension(file[0].getOriginalFilename());
				File f = new File(filePath, fileName);
				FileUtils.copyInputStreamToFile(file[0].getInputStream(), f);
				String newPath = CommonConstant.upload_http_path + "/" + CommonConstant.adv_dir_name + "/" + fileName;
				vo.setUrl(newPath);
			}
			adminAdvService.saveOrUpdate(vo, parkings);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "redirect:/adminAdv/query";
	}

	@RequestMapping("del")
	public String del(Model model, Adv adv) {
		try {
			if (null != adv && adv.getAdvid().length() > 0)
				adminAdvService.del(adv);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "redirect:/adminAdv/query";
	}

	@RequestMapping("/detail")
	public String detail(Model model, Adv adv) {
		Result result = new Result();
		try {
			result.setContent(adminAdvService.queryById(adv.getAdvid()));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		model.addAttribute("result", result);
		return "jsonview";
	}

	@RequestMapping("/valid")
	public String valid(Model model, Adv adv) {
		try {
			adminAdvService.valid(adv);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "redirect:/adminAdv/query";
	}
	
	/**
	 * 查询标签
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @param content
	 * @return
	 * @Date:2013-4-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/queryLabels")
	public String queryLabels(Model model, Integer pageNo, Integer pageSize, String content) {
		int recommend = 0;
		try {
			recommend = adminLabelsService.queryRecommendationLabelCount();
			if (this.getSessionAdmin() == null) {
				return "redirect:/admin/index?target=_top";
			}
			if (null == pageNo) {
				pageNo = 1;
			}
			if (null == pageSize) { 
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
			}
			Page<Label> page = null; 
			
			if (pageNo == 1 && (content==null || content.length()==0)) {// 如果是第一页,且不是搜索 需要加载推荐的条数
				page = adminLabelsService.listLabelPage(pageNo, pageSize, null,content);
				List<Label> labels = page.getResult();
				model.addAttribute("vos", labels.subList(0, recommend));
				page.setResult(labels.subList(recommend, labels.size()));
			} else if(!(content==null || content.length()==0)){// 是搜索，需要排除已推荐的
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
				page = adminLabelsService.listLabelPage(pageNo, pageSize, RECOMMEND_STATE, content);			
			} else { //不是第一页 且 不是搜索
				pageSize = Page.ADMIN_MAX_PAGE_SIZE;
				page = adminLabelsService.listLabelPage(pageNo, pageSize, null, content);
			}
			model.addAttribute("page", page);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "admin/recommendLabel";
	}

}
