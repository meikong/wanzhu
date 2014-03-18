package com.wanzhu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.BaseSQLDao;
import com.wanzhu.entity.Remark;
import com.wanzhu.jsonvo.ActivityVO;
import com.wanzhu.jsonvo.RemarkHotVo;
import com.wanzhu.utils.DateUtil;

/**
 * 评论
 * @author zhanglei
 *
 */
@Repository
public class RemarkDao extends BaseDao<Remark> {

	/**
	 * 查询评论
	 * remarkid
	 * userid
	 * username
	 * createtime
	 * content
	 * portrait
	 * @return
	 */
	public String[][] queryRemarks(String topicId, int start, int size) {
		String[][] result = null;
		String sql = "select cast(r.remarkid as char(32)), cast(u.userid as char(32)), u.name, r.createtime, r.content, u.portrait,r.agreecount,r.disagreecount  " +
			     "from t_remark r, t_user u " +
			     "where r.userid = u.userid and r.topicid = '" + topicId + "' " +
			     "order by r.createtime desc";
		List<Object[]> list = this.getSession().createSQLQuery(sql).setFirstResult(start).setMaxResults(size).list();
		result = new String[list.size()][8];
		for(int i = 0; i < list.size(); i++) {
			result[i][0] = (String)list.get(i)[0];
			result[i][1] = (String)list.get(i)[1];
			result[i][2] = (String)list.get(i)[2];
			result[i][3] = DateUtil.date2String((Date)list.get(i)[3]);
			result[i][4] = (String)list.get(i)[4];
			result[i][5] = (String)list.get(i)[5];
			if(list.get(i)[6]==null){
				result[i][6]="0";
			}
			else{
				
				result[i][6] = list.get(i)[6].toString();
			}
			if(list.get(i)[7]==null){
				result[i][7]="0";
			}
			else{
				result[i][7] = (String)list.get(i)[7].toString();
			}
			
		}
		return result;
	}
	

	/**
	 * @param id
	 * @return
	 * @Date:2013-5-6  
	 * @Author:xuguangyun  
	 * @Description:从话题表中获取热点话题
	 */
    public List<RemarkHotVo> queryRemarkHotList()
    {
    	
        String sql = "SELECT MIN(topicid) hotId ,COUNT(remarkid) hotSum  FROM t_remark GROUP BY topicid ORDER BY COUNT(remarkid) DESC  LIMIT 0,5 ";
        List<String> fieldList = new ArrayList<String>();
        fieldList.add("hotId");
        fieldList.add("hotSum");
        return new BaseSQLDao<RemarkHotVo>().list(sql, null, -1,-1, RemarkHotVo.class, fieldList);
    }
    
    public int updateRemark(String remarkId,Integer agreecount,Integer disagreecount){
    	String sql="update t_remark set agreecount="+agreecount+" ,disagreecount="+disagreecount+" where remarkid='"+remarkId+"'";
    	return  this.getSession().createSQLQuery(sql.toString()).executeUpdate();
    }
    
}
