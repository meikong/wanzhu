package com.wanzhu.controller.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Label;
import com.wanzhu.service.admin.AdminLabelsService;
import com.wanzhu.utils.EscapeHtml;

@Controller
@RequestMapping("/adminlabel")
public class AdminLabelsController extends BaseController {
	
	private static final String RECOMMEND_LABEL = "1";
	
	@Autowired
	private AdminLabelsService adminLabelsService;
	
	/**
	 * 返回所有标签
	 * @param model
	 * @param content
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Date:2013-4-28  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping(value="listlabel")
	public String queryUserList(Model model, String content, Integer pageNo, Integer pageSize) {
		if(this.getSessionAdmin()==null) {
			return "redirect:/admin/index?target=_top";
		}
		try {
			if(null == pageNo) {
				pageNo = 1;
			}
			if(null == pageSize) {
				pageSize = Page.DEFAULT_PAGE_SIZE;
			}
			
			Page<Label> page = adminLabelsService.listLabelPage(pageNo, pageSize, null, content);
			model.addAttribute("page", page);
			model.addAttribute("content", content);
		} catch (Exception e) {
			log.error(e);
		}
		return "admin/labelList";
	}
	
	@RequestMapping(value="listRecommendLabel")
	public String queryRecommendLabel(Model model, String content, Integer pageNo, Integer pageSize) {
		if(this.getSessionAdmin()==null) {
			return "redirect:/admin/index?target=_top";
		}
		try {
			if(null == pageNo) {
				pageNo = 1;
			}
			if(null == pageSize) {
				pageSize = Page.DEFAULT_PAGE_SIZE;
			}
			Page<Label> page = adminLabelsService.listLabelPage(pageNo, pageSize, RECOMMEND_LABEL, content);
			model.addAttribute("page", page);
			model.addAttribute("content", content);
		} catch (Exception e) {
			log.error(e);
		}
		return "admin/labelList";
	}
	
	@RequestMapping(value = "savelable")
	@ResponseBody
	public String saveLable(String label, String memo, String userid) {
		try {
			label = EscapeHtml.Html2Text(label);
			Label entity = new Label();
			entity.setLabel(label);
			entity.setMemo(memo);
			entity.setCreatetime(new Date());//创建时间
			entity.setShoworder(System.currentTimeMillis());//排序
			entity.setUserid(userid);//创建人id
			entity.setType(0);//系统标签
			entity.setRecommendation(0);//不推荐
			this.adminLabelsService.saveLabel(entity);
			return "0";
		} catch (Exception e) {
			return "1";
		}
	}
	
	@RequestMapping(value = "updatelable")
	@ResponseBody
	public String updateLable(String labelid, String label, String memo) {
		try {
			label = EscapeHtml.Html2Text(label);
			Label entity = this.adminLabelsService.getLabel(labelid);
			entity.setLabel(label);
			entity.setMemo(memo);
			entity.setType(0);//0--系统标签  1--用户标签
			this.adminLabelsService.updateLabel(entity);
			return "0";
		} catch (Exception e) {
			return "1";
		}
	}
	
	@RequestMapping(value = "getlabeleventcount")
	@ResponseBody
	public String getLabelEventCount(String labelId) {
		return this.adminLabelsService.getLabelEventCount(labelId);
	}
	
	@RequestMapping(value = "deletelable")
	@ResponseBody
	public String deleteLable(String labelId) {
		try {
			this.adminLabelsService.deleteLabel(labelId);
			return "0";
		} catch (Exception e) {
			return "1";
		}
	}
	
	/**
	 * 升级为系统标签 
	 * @param labelId
	 * @return
	 * @Date:2013-5-20  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping(value = "upgrade")
	@ResponseBody
	public String upgradeLable(String labelId) {
		try {
			this.adminLabelsService.upgradeLabel(labelId);
			return "0";
		} catch (Exception e) {
			return "1";
		}
	}
}
