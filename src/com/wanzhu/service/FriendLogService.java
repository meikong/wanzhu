package com.wanzhu.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.dao.FriendLogDao;


/**
 * 
 * @author ZOUSY
 */
@Service("friendlog.service")
@Transactional(readOnly=true)
public class FriendLogService
{

    @Autowired
    private FriendLogDao friendLogDao;
    
}
