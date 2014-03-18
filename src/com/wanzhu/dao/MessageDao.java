package com.wanzhu.dao;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Message;
import com.wanzhu.utils.DateUtil;

@Repository
public class MessageDao extends BaseDao<Message> {
	
	/**
	 * 查询我的私信
	 * userid
	 * username
	 * portrait
	 * messageid
	 * createtime
	 * content
	 * count
	 * unreadcount
	 * @return 
	 */
	public String[][] queryMessages(String userId, int start, int size) {
		String[][] results = null;
		String sql = "select cast(u.userid as char(32)), u.name, u.portrait, w.maxmaxcreatetime from ( "+
						"select y.contactid, max(y.maxcreatetime) as maxmaxcreatetime from ( "+  
							"(select m.userid as contactid, max(m.createtime) as maxcreatetime from t_message m "+  
								"where m.friendid = '" + userId + "' and m.deletebyfriend = 0 "+   
								"group by contactid order by maxcreatetime " + 
							" ) "+  
							"union all "+  
							"(select m.friendid as contactid, max(m.createtime) as maxcreatetime from t_message m "+  
								"where m.userid = '" + userId + "' and m.deletebyuser = 0 "+ 
								"group by contactid order by maxcreatetime " +
							") "+
						") y group by y.contactid order by maxmaxcreatetime " +
					") w, t_user u where w.contactid = u.userid order by w.maxmaxcreatetime desc";
		List<Object[]> userList = this.getSession().createSQLQuery(sql).setFirstResult(start).setMaxResults(size).list();
		results = new String[userList.size()][8];
		for(int i = 0; i < userList.size(); i++) {
			results[i][0] = (String)userList.get(i)[0];
			results[i][1] = (String)userList.get(i)[1];
			results[i][2] = (String)userList.get(i)[2];
			results[i][4] = DateUtil.date2String((Date)userList.get(i)[3]);
			sql = "( " +
			        "select trim(count(m.messageid)) as col1, trim(max(    CONCAT(m.createtime, '--', m.content, '--', m.messageid))) as col2  " +
			        "from t_message m " +
			        "where (m.userid = '" + userId + "' and m.friendid = '" + results[i][0] + "' and m.deletebyuser = 0) " +
			        "or " +
			        "(m.userid = '" + results[i][0] + "' and m.friendid = '"  + userId + "' and m.deletebyfriend = 0) " +
			        ") " +
			        "UNION ALL " +
			        "( " +
			        "select trim(-sum(m.readss-1)) as col1, trim('0') as col2 " +
			        "from t_message m " +
			        "where  " +
			        "m.userid = '" + results[i][0] + "' and m.friendid = '" + userId + "' and m.deletebyfriend = 0 " +
			        ") ";
			
			List<Object[]> messageList = this.getSession().createSQLQuery(sql).list();
			String s = "";
			try {
				s = new String((byte[]) messageList.get(0)[1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			String temp = s;//((String)messageList.get(0)[1]);
			String messageId = temp.substring(temp.lastIndexOf("--")+2,  temp.length() - 1); 
			String content = temp.substring(temp.indexOf("--")+2, temp.lastIndexOf("--"));
			results[i][3] = messageId;
			results[i][5] = content;
			results[i][6] = new String((byte[]) messageList.get(0)[0]);
			results[i][7] = messageList.get(1)[0]==null ? "0"  : new String((byte[]) messageList.get(1)[0]);
		}
		return results;
	}
	
	
	/**
	 * 与某人私信条数
	 * @return
	 */
	public int getMessagesCountWithAFriend(String userId, String friendId) {
		String sql = "select count(*) from ( "+
					 	"(select m.userid as contactid from t_message m "+ 
					 		"where m.userid = '" + userId + "' and m.friendid = '" + friendId + "' and m.deletebyuser = 0 "+ 
					 	") "+  
					 	"union all "+  
					 	"(select m.userid as contactid from t_message m "+  
					 		"where m.userid = '" + friendId + "' and m.friendid = '" + userId + "' and m.deletebyfriend = 0 "+ 
					 	") "+ 
					 ") y";
		Query query = this.getSession().createSQLQuery(sql);
		return ((Number)query.list().get(0)).intValue(); 
	}
	
	/**
	 * 查询与某人的私信
	 * userid
	 * username
	 * portrait
	 * createtime
	 * content
	 * messageid
	 * read
	 * @return
	 */
	public String[][] queryMessagesWithAFriend(String userId, String friendId, int start, int size) {
		String[][] results = null;
		String sql = "select cast(u.userid as char(32)), u.name, u.portrait, y.createtime, y.content, cast(y.messageid as char(32)), y.readss from ( "+
				"(select m.userid as contactid, m.createtime, m.content, m.messageid, 1 as readss from t_message m "+ 
				 "where m.userid = '" + userId + "' and m.friendid = '" + friendId + "' and m.deletebyuser = 0 "+ 
				") "+  
				"union all "+  
				"(select m.userid as contactid, m.createtime, m.content, m.messageid, m.readss from t_message m "+  
				 "where m.userid = '" + friendId + "' and m.friendid = '" + userId + "' and m.deletebyfriend = 0 "+ 
				") "+ 
			") y, t_user u where y.contactid = u.userid order by y.createtime desc ";
		List<Object[]> list = this.getSession().createSQLQuery(sql).setFirstResult(start).setMaxResults(size).list();
		results = new String[list.size()][7];
		for(int i = 0; i < list.size(); i++) {
			results[i][0] = (String)list.get(i)[0];
			results[i][1] = (String)list.get(i)[1];
			results[i][2] = (String)list.get(i)[2];
			results[i][3] = DateUtil.date2String((Date)list.get(i)[3]);
			results[i][4] = (String)list.get(i)[4];
			results[i][5] = (String)list.get(i)[5];
			results[i][6] = list.get(i)[6].toString();
		}
		return results;
	}
	
	/**
	 *	设置私信已读 
	 */
	public void setMessageRead(List<String> messageIds) {
		if(messageIds == null || messageIds.size() == 0)
			return;
		StringBuilder sql = new StringBuilder("update t_message set readss = 1 where messageid in (");
		for(int i = 0; i < messageIds.size(); i++) {
			if(i > 0) {
				sql.append(",");
			}
			sql.append("'" + messageIds.get(i) + "'");
		}
		sql.append(")");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**
	 * 未读私信条数
	 * @return
	 */
	public int getUnreadMessageCount(String userId) {
		String hql = "select count(*) from Message m where m.userByFriendid.userid = ? and m.read = 0 and m.deletebyfriend = 0";
		Query query = this.getSession().createQuery(hql).setParameter(0, userId);
		return ((Number)query.iterate().next()).intValue(); 
	}

}
