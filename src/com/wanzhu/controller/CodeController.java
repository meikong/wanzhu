package com.wanzhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.service.CodeService;
import com.wanzhu.utils.JsonUtil;

/**
 * 代码
 * @author zhanglei
 *
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/getProvinceCodeList")
	public String getProvinceCodeList(Model model) {
		//return JsonUtil.getJsonString4JavaPOJO(this.codeService.getProvinceCodeList());
		model.addAttribute("result", this.codeService.getProvinceCodeList());
		return "jsonview";
	}
	
	@RequestMapping("/getCityCodeList")
	public String getCityCodeList(String provinceCode, Model model) {
		model.addAttribute("result", this.codeService.getCityCodeList(provinceCode));
		return "jsonview";
	}
	
	
}
