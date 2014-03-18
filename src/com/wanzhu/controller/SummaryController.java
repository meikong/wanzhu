package com.wanzhu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.entity.Summary;
import com.wanzhu.service.SummaryService;

/**
 * 代码
 * @author Tu Chuanbin
 *
 */
@Controller
@RequestMapping("/summary")
public class SummaryController extends BaseController
{
	@Autowired
	private SummaryService summaryService;
	
	@RequestMapping("/downloadCount/{summaryId}")
	@ResponseBody
	public String downloadCount(@RequestParam("summaryId") String summaryId)
	{
		try
		{
			this.summaryService.downloadCount(summaryId);
			return "{result:\"success\"}";
		}
		catch(Exception e )
		{
			Logger.getLogger(SummaryController.class).error("下载计数失败，ID为："+summaryId, e);
			return "{result:\"failure\"}";
		}
	}
	/**
	 * @param model
	 * @return
	 * @Date:2013-4-12  
	 * @Author:xuguangyun  
	 * @Description:获得素材文章的标题列表
	 */
	@RequestMapping("/summaryTitleList")
	@ResponseBody
	public String getSummaryList(Model model)
	{
		try
		{
			List<Summary> summaryList=this.summaryService.getSummaryList();
			model.addAttribute("summaryList", summaryList);
			for(Summary summary : summaryList){
				System.out.println(summary.getSummaryTitle()+":CESHI");
			}
		    return "jsonview";
		}
		catch(Exception e )
		{
			Logger.getLogger(SummaryController.class).error("获取文章标题报错！", e);
			return "{result:\"failure\"}";
		}
	}
	/**
	 * 
	 * @param model
	 * @param type  素材类型 0视频，1PPT,2文章。
	 * @param summaryId 素材ID
	 * @return
	 * @Date:2013-4-26  
	 * @Author:xuguangyun  
	 * @Description:
	 */
	@RequestMapping("/sxu_{summaryId}.html")
	public String querySummary(Model model,  @PathVariable("summaryId") String summaryId){
		System.out.println(this.getClass().getName() + "----->  id : " + summaryId);
		Summary summary=this.summaryService.getSummaryById(summaryId);
		System.out.println(summary.getSummaryTitle()+"66666content:"+summary.getWords());
		model.addAttribute("summary",summary);
		return "/event/past_event_excerpt";
	}

}
