package com.wanzhu.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanzhu.dao.article.ArticleDao;
import com.wanzhu.dao.article.ArticleDetailDao;

@Service
public class ArticleDetailService{
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleDetailDao articleDetailDao;
	
}
