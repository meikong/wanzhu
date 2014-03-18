package com.wanzhu.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanzhu.dao.shop.ShopDao;

@Service
public class ShopService{
	@Autowired
	private ShopDao shopDao;
	
}
