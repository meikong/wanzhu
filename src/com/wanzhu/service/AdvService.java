package com.wanzhu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanzhu.dao.admin.AdminAdvDao;
import com.wanzhu.entity.Adv;

@Service("adv.service")
public class AdvService
{

    @Autowired
    private AdminAdvDao adminAdvDao;
    
    public Adv queryByParking(String parking)
    {
        return adminAdvDao.queryByParking(Integer.parseInt(parking));
    }
}
