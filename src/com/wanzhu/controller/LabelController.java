package com.wanzhu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.jsonvo.LabelVO;
import com.wanzhu.service.HotService;
import com.wanzhu.service.LabelService;

/**
 * 活动标签
 * @author Keran
 */
@Controller
@RequestMapping("/label")
public class LabelController extends BaseController{

	private static final int MAX_HOT_LABEL_COUNT = 5;
	@Autowired
	private LabelService labelService;
	@Autowired
	HotService hotService;
	/**
	 * 查询标签及所包含的活动数
	 * @param model
	 * @param recent 0-往期,1-近期，不传-不限
	 * @param cityCode 城市代码，不传-不限
	 * @return
	 */
	@RequestMapping("/lc.json")
	public String queryLabelsWithEventCount(Model model, Integer recent, String cityCode) {
		if(recent == null) {
			recent = -1;
		}
		if(cityCode == null||cityCode=="") {
			cityCode = "0";
		}
		List<LabelVO> labels = labelService.queryLabelsWithEventCount(recent, cityCode);
		model.addAttribute("result", labels);
		return "jsonview";
	}
	
	/**
	 * 查询推荐标签
	 * @param model
	 * @return
	 */
	@RequestMapping("/recommendLables.json")
	public String recommendLables(Model model) {
		List<LabelVO> labels = labelService.queryRecommendLables();
		model.addAttribute("result", labels);
//		for (LabelVO labelVO : labels) {
//			System.out.println("Label ---> " + labelVO.getLabelId() + " --- " + labelVO.getLabel());
//		}
		return "jsonview";
	}
	
	/**
	 * 首页查询5个热门标签(临时版)
	 * @param model
	 * @return
	 * @Date:2013-5-15  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/hotLables.json")
	public String hotLables(Model model) {
		List<LabelVO> labels = labelService.queryRecommendLables();
		if(labels == null){
			labels = new ArrayList<LabelVO>();
		}else if(labels.size() > 5){
			labels = labels.subList(0, 5);
		}
		model.addAttribute("result", labels);
		return "jsonview";
	}
	
	/**
	 * 新版查询热门标签(未完成)
	 * @param model
	 * @return
	 * @Date:2013-5-15  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/newHotLables.json")
	public String newHotLables(Model model) {
//		List<LabelVO> labels = hotService.queryHotLabels(MAX_HOT_LABEL_COUNT);
//		for (LabelVO labelVO : labels) {
//			System.out.println(labelVO.getLabelId() + "--->" + labelVO.getLabel());
//		}
//		model.addAttribute("result", labels);
		return "jsonview";
	}
	
	
	/**
	 * 为活动增加标签
	 * 如果标签名
	 * @param model
	 * @param labelid废弃，传null 或不传
	 * @param label标签名
	 * @param eventid
	 * @return
	 * @throws Exception 
	 * @Date:2013-4-28  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/saveUserLabel")
	@ResponseBody
	public LabelVO saveUserLabel(Model model,String labelid, String labelName,String eventid) throws Exception {
		String userid = "";
		try {
			 userid = this.getSessionUser().getUserid();
		} catch (Exception e) {
		}
		String regex = "[^0-9a-zA-Z\u4e00-\u9fa5]+";
		labelName = labelName.replaceAll(regex, "");
		if(labelName != null && labelName.length() > 16){
			labelName = labelName.substring(0,17);//截取16个字符
		}
		return labelService.saveUserLabelWithOutId(labelName, userid, eventid);
	}
	
	/**
	 * 删除某活动的已有标签
	 * 如果该标签是系统标签，则直接删除关联关系
	 * 如果该用户是用户标签，则要检查一下该标签是否还被其他活动引用，若没被引用，直接删除该标签
	 * @param model
	 * @param labelid
	 * @param eventid
	 * @return
	 * @Date:2013-4-28  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@RequestMapping("/delLabel")
	@ResponseBody
	public int updateLable(Model model, String eventid, String labelid) {
		try {
			labelService.delLabel(eventid, labelid);
			return CommonConstant.SUCCESS;
		} catch (Exception e) {
			return CommonConstant.FAILURE;
		}
	}
	
	
	
	
	
	
	
}
