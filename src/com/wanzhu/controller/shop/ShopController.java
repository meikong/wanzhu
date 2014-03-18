package com.wanzhu.controller.shop;

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
import com.wanzhu.service.article.ArticleDetailService;
import com.wanzhu.service.article.ArticleService;
import com.wanzhu.service.shop.ShopService;
import com.wanzhu.utils.ErrorHelper;

@Controller
@RequestMapping(value = "/shop")
public class ShopController extends BaseController{
    @Autowired
    private ShopService shopService;
    
    /**
     * 好店详情
     * @param id
     */
    @RequestMapping("/detail/{id}.html")
    public String shopIndex(Model model, @PathVariable("id") Integer id){
    	
    	//model.addAttribute(arg0, arg1);
    	return "";
    }
    
    /**
     * 好店列表
     * @param id
     */
    @RequestMapping("/list")
    public String shopIndex(Model model,  Integer pageNo, Integer pageSize){
    	
    	//model.addAttribute(arg0, arg1);
    	return "";
    }

}
