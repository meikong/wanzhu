package com.wanzhu.service.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.dao.admin.AdminAdvDao;
import com.wanzhu.entity.Adv;
import com.wanzhu.utils.DateUtil;
import com.wanzhu.utils.StringUtils;

@Service("admin.adv.service")
@Transactional(readOnly=true)
public class AdminAdvService
{
    
    @Autowired
    private AdminAdvDao adminAdvDao;
    
    @Value("${admin.adv.types}")
    private   String advTypes;
    private static Map<String, String> types = null;
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveOrUpdate(Adv adv,String _parkings)
    {
        if(null == _parkings || _parkings.length()==0)
            return ;
        adv.setLastuploadtime(new Date());
        String[] parkings = _parkings.split(",");
        if(null != adv.getAdvid() && adv.getAdvid().length()>0)
        {
            adminAdvDao.remove(adv);
        }
        for(String parking : parkings)
        {
            adv.setParking(Integer.parseInt(parking.trim()));
            adminAdvDao.delByParking(Integer.parseInt(parking.trim()));
            adminAdvDao.update(adv);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void del(Adv adv)
    {
        adminAdvDao.remove(adminAdvDao.get(adv.getAdvid()));
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void valid(Adv adv)
    {
        adv = adminAdvDao.get(adv.getAdvid());
        if(adv.getValid()==1)
            adminAdvDao.valid(adv.getAdvid(), false);
        else
            adminAdvDao.valid(adv.getAdvid(), true);
    }
    
    public  List<Map<String, String>> query()
    {
        List<Map<String, String>> result = new ArrayList<Map<String,String>>(0);
        for(Adv adv : adminAdvDao.query())
        {
            Map<String, String> temp = new HashMap<String, String>();
            temp.put("advid", adv.getAdvid());
            temp.put("memo", adv.getMemo());
            temp.put("url", adv.getUrl());
            temp.put("valid", ""+adv.getValid());
            temp.put("parking", ""+adv.getParking());
            temp.put("lastuploadtime", DateUtil.date2String(adv.getLastuploadtime()));
            temp.put("parkingNm",getAllTypes().get(adv.getParking().toString()));
            temp.put("link", adv.getLink());
            result.add(temp);
        }
        return result;
    }
    
    public List<Adv> queryValid()
    {
        return adminAdvDao.queryValid();
    }
    
    public Adv queryByParking(Integer parking)
    {
        return adminAdvDao.queryByParking(parking);
    }
    
    public Adv queryById(String id)
    {
        Adv adv = adminAdvDao.get(id);
        return adv;
    }
    
    public Map<String, String> getAllTypes()
    {
        if(null == types)
        {
            types = new HashMap<String, String>(0);
            for(String[] strings : StringUtils.toArray(advTypes, new String[]{";", ":"})  )
                types.put(strings[0].trim(),strings[1].trim());
        }
        return types;
    }
}
