package com.wanzhu.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Adv;

@Repository
public class AdminAdvDao extends BaseDao<Adv>
{

    /**
     * 获取全部的广告
     * @return
     */
    public List<Adv> query()
    {
        String hql = "FROM Adv as o  order by o.lastuploadtime desc";
        return find(hql);
    }
    
    /**
     * 检索所有的有效广告
     * @return
     */
    public List<Adv> queryValid()
    {
        String hql = "FROM Adv as o WHERE o.valid=0  order by o.lastuploadtime desc";
        return find(hql, new Object[]{});
    }
    
    /**
     * 检索对应的广告位
     * 
     * @param parking
     * @return
     */
    public Adv queryByParking(Integer parking)
    {
        String hql = "FROM Adv as o WHERE o.valid=0 and o.parking=? ";
        return findEntity(hql, new Object[]{parking});
    }
    
    /**
     * 有效/失效
     * 
     * @param id
     * @param isValid
     */
    public void valid(String id,boolean isValid)
    {
        int i = isValid?1:0;
        String hql = "UPDATE Adv as o SET o.valid=?  WHERE o.advid=?";
        createQuery(hql, new Object[]{i,id}).executeUpdate();
    }
    
    public void delByParking(Integer parking)
    {
        String hql = "DELETE FROM Adv as o WHERE o.parking =? ";
        createQuery(hql, new Object[]{parking}).executeUpdate();
    }
    
}
