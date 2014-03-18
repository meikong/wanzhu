package com.wanzhu.controller.promotion;

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
import com.wanzhu.service.promotion.PromotionService;
import com.wanzhu.utils.ErrorHelper;

@Controller
@RequestMapping(value = "/p")
public class PromotionController extends BaseController{
    @Autowired
    private PromotionService promotionService;
    
}
