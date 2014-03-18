package com.wanzhu.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BaseSQLDao<T>
{

    /**
     * 从spring容器获取HibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate()
    {
        return  AppliactionContextHelper.getBean(HibernateTemplate.class);
    }
    
    /**
     * 使用sql语句进行分页查询,返回的结果封装成List<bean>对象
     * 
     * @param sql
     *            sql语句
     * @param values
     *            参数
     * @param offSet
     *            第一条记录序号
     * @param pageSize
     *            每页要显示的记录数
     * @param beanClass
     *            将查询结果转换为<tt>T</tt>对象
     * @param fieldList
     *            查询Bean的成员变量名称
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> list(final String sql, final Object[] values, final int offSet, final int pageSize, final Class<T> beanClass, final List<String> fieldList)
    {
        try
        {
            List<T> list = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException
                {
                    SQLQuery sqlQuery = session.createSQLQuery(sql);
                    // 添加要查询字段的标量
                    AddScalar.addSclar(sqlQuery, beanClass, fieldList);
                    Query query = sqlQuery;
                    // 转换查询结果为T
                    if(beanClass!=null)
                    {
                        query.setResultTransformer(Transformers.aliasToBean(beanClass));
                    }
                    if((values!=null)&&values.length>0)
                    {
                        int i = 0;
                        for(Object obj : values)
                        {
                            query.setParameter(i++, obj);
                        }
                    }
                    if(offSet>-1)
                    {
                        query.setFirstResult(offSet);
                    }
                    if(pageSize>0)
                    {
                        query.setMaxResults(pageSize);
                    }
                    session.flush();  
                    session.close();
                    return query.list();
                }
            });
            return list;
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 使用sql语句进行分页查询,返回的结果封装成List<map>
     * 
     * @param sql
     *            sql语句
     * @param values
     *            参数
     * @param offSet
     *            第一条记录序号
     * @param pageSize
     *            每页要显示的记录数
     * @param beanClass
     *            将查询结果转换为<tt>T</tt>对象
     * @param fieldList
     *            查询Bean的成员变量名称
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<Map> list(final String sql, final Object[] values, final int offSet, final int pageSize)
    {
        try
        {
            List<Map> list = getHibernateTemplate().executeFind(new HibernateCallback()
            {
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException
                {
                    SQLQuery sqlQuery = session.createSQLQuery(sql);
                    Query query = sqlQuery;
                    // 转换查询结果为Map
                    query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                    if((values!=null)&&values.length>0)
                    {
                        int i = 0;
                        for(Object obj : values)
                        {
                            query.setParameter(i++, obj);
                        }
                    }
                    if(offSet>-1)
                        query.setFirstResult(offSet);
                    if(pageSize>0)
                        query.setMaxResults(pageSize);
                    session.flush();  
                    session.close();
                    return query.list();
                }
            });
            return list;
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        return null;
    }
}
