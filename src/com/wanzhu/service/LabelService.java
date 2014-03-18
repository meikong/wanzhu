package com.wanzhu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.dao.LabelDao;
import com.wanzhu.dao.admin.AdminEventDao;
import com.wanzhu.dao.admin.AdminEventLabelDao;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.EventLabel;
import com.wanzhu.entity.Label;
import com.wanzhu.jsonvo.LabelVO;
import com.wanzhu.utils.UUIDHexGenerator;

@Service
@Transactional(readOnly=true)
public class LabelService {
	
	private static final int USER_LABEL = 1;
	private static final int SYSTEM_LABEL = 0;

	@Autowired
	private LabelDao labelDao;
	@Autowired
	private AdminEventDao adminEventDao;
	@Autowired
	private AdminEventLabelDao adminEventLabelDao;
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void createLabel(Label label) {
		this.labelDao.save(label);
	}

	public List<LabelVO> queryLabelsWithEventCount(int recent, String cityCode) {
		return this.labelDao.queryLabelsWithEventCount(recent, cityCode);
	}

	public List<LabelVO> queryRecommendLables() {
		return this.labelDao.queryRecommendLables();
	}

	/**
	 * 删除活动标签
	 * @param eventid
	 * @param labelid
	 * @Date:2013-4-28  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@Transactional(readOnly=false)
	public boolean delLabel(String eventid, String labelid) {
		Label label = labelDao.get(labelid);
		//删除关联关系
		labelDao.execute("delete from EventLabel el where el.event.eventid='" + eventid + "' and el.label.labelid='" + labelid + "'");
		if(label.getType() == USER_LABEL){//如果是用户标签
			long count = labelDao.count("select count(*) from EventLabel el where el.label.labelid=?", labelid);
			if(count == 0){//没被别的活动使用的用户标签将被删除
				labelDao.execute("delete from Label where labelid='" + labelid + "' and type=1");
			}
		}
		return true;
	}

	/**
	 * 用户添加活动标签
	 * @param labelid
	 * @param label
	 * @param eventid
	 * @return
	 * @Date:2013-4-28  
	 * @Author:Guibin Zhang  
	 * @Description:
	 * 分两种情况：
	 * 1.已经在系统标签中找到名字相同的标签--判断关联关系是否存在，不存在直接建立关联关系，存在什么都不做
	 * 2.系统中不存在用户写入的标签--先建立用户标签，在建立关联关系.
	 * 返回值为LabelVO
	 * 
	 */
	@Deprecated
	@Transactional(readOnly=false)
	private LabelVO saveUserLabel(String labelid, String labelName, String userid, String eventid) {
		boolean flag = false;//关联关系是否存在
		LabelVO result = new LabelVO();
		EventLabel eventLabel = new EventLabel();//预先建立关联关系
		Event event = new Event();
		event.setEventid(eventid);
		eventLabel.setEvent(event);
		if(labelid == null || labelid.trim().length() == 0){//系统中不存在这个标签,先建立这个标签
			Label label = new Label();
			label.setLabel(labelName);
			label.setUserid(userid);
			label.setCreatetime(new Date());
			label.setType(USER_LABEL);
			label.setShoworder(System.currentTimeMillis());
			label.setRecommendation(0);//默认不推荐
			labelDao.getHibernateTemplate().save(label);
			//建立返回结果
			result.setLabel(label.getLabel());
			result.setLabelId(label.getLabelid());
			//labelDao.getHibernateTemplate().merge(label);
			//建立关系
			eventLabel.setLabel(label);
		}else{ //系统中存在这个标签
			List<EventLabel> list = adminEventLabelDao.getHibernateTemplate().find("from EventLabel where event.eventid='" + eventid + "' and label.label='" + labelName + "'");
			if(list == null || list.size() == 0){//判断
				Label label = new Label();
				label.setLabelid(labelid);
				eventLabel.setLabel(label);
				//建立返回结果
				result.setLabel(labelName);
				result.setLabelId(labelid);
			}else{
				flag = true;//关系已经存在
			}
			
		}
		if(!flag)//关系不存在
			adminEventLabelDao.getHibernateTemplate().merge(eventLabel);
		
		return result;
	}

	/**
	 * 为活动添加 标签
	 * 
	 * @param labelName
	 * @param userid
	 * @param eventid
	 * @return
	 * @Date:2013-5-14  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@Transactional(readOnly=false)
	@Deprecated
	public LabelVO saveUserLabel(String labelName, String userid, String eventid) {
		List<Label> systemLabels = labelDao.getHibernateTemplate().find("from Label where type=0");//查询系统标签
		Label findLabel = null;
		if(labelName == null)
			labelName = "";
		for (Label label : systemLabels) {
			if(labelName.equals(label.getLabel())){
				findLabel = label;
				break;
			}
		}
		if(findLabel == null)
			return this.saveUserLabel(null, labelName, userid, eventid);
		else
			return this.saveUserLabel(findLabel.getLabelid(), labelName, userid, eventid);
	}
	
	/**
	 * 给活动加入关联关系
	 * @param labelName
	 * @param userid
	 * @param eventid
	 * @return
	 * @Date:2013-5-15  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@Transactional(readOnly=false)
	public LabelVO saveUserLabelWithOutId(String labelName, String userid, String eventid){
		//首先判断是否存在这种同名关联关系
		List<EventLabel> list = adminEventLabelDao.getHibernateTemplate().find("from EventLabel where event.eventid='" + eventid + "' and label.label='" + labelName + "'");
		if(list == null || list.size() == 0){//不存在关联,先建立该标签，在建立关系
			System.out.println("不存在名为 " + labelName + " 的关联关系");
			return setLabelToEvent(labelName, userid, eventid);
		}else{//存在关联，直接构造vo
			Label existLabel = list.get(0).getLabel();
			if(existLabel != null){//正常查找到该标签
				System.out.println("已存在名为 " + labelName + " 的关联关系,且实体还存在");
				LabelVO result = new LabelVO();
				String existLabelId = existLabel.getLabelid();
				Label label = labelDao.get(existLabelId);
				result.setLabelId(label.getLabelid());
				result.setLabel(label.getLabel());
				return result;
			}else{//此关联关系已经失效,删除此关联关系，按不存在关联执行
				System.out.println("已存在名为 " + labelName + " 的关联关系,但实体已不存在");
				return setLabelToEvent(labelName, userid, eventid);
			}
		}
	}
	
	//不存在关联，如果不存在该标签，新建标签，并加入关联
	private LabelVO setLabelToEvent(String labelName, String userid, String eventid){
		LabelVO result = new LabelVO();
		EventLabel eventLabel = new EventLabel();//预先建立关联关系
		Event event = adminEventDao.get(eventid);
		eventLabel.setEvent(event);
		List<Label> systemLabels = labelDao.getHibernateTemplate().find("from Label where type=0");//查询系统标签
		Label findLabel = null;
		if(labelName == null)
			labelName = "";
		for (Label label : systemLabels) {
			if(labelName.equals(label.getLabel())){
				findLabel = label;
				break;
			}
		}
		if(findLabel == null){//系统中不存在这个标签，需要新建
			System.out.println("系统标签中不存在名为 " + labelName + " 的实体， 需要新建");
			findLabel = saveLabel(labelName, userid);
		}
		//建立关联关系
		eventLabel.setLabel(findLabel);
		//String uuid = new UUIDHexGenerator().generateUUID();
		//System.out.println("生成UUID ： " + uuid);
		//labelDao.getJdbcTemplate().update("insert into t_event_label (assignid, labelid, eventid) values('" + uuid + "','" +  findLabel.getLabelid()+ "','" + event.getEventid() + "') ");
		adminEventLabelDao.getHibernateTemplate().merge(eventLabel);
		System.out.println("建立关联关系" + eventLabel.getEvent().getEventid() + " --- " + eventLabel.getLabel().getLabelid() + "成功");
		result.setLabel(findLabel.getLabel());
		result.setLabelId(findLabel.getLabelid());
		//建立关联关系
		return result;
	}
	
	private Label saveLabel(String labelName, String userid){
		Label findLabel = new Label();
		findLabel.setLabel(labelName);
		findLabel.setUserid(userid);
		findLabel.setCreatetime(new Date());
		findLabel.setType(USER_LABEL);
		findLabel.setShoworder(System.currentTimeMillis());
		findLabel.setRecommendation(0);//默认不推荐
		labelDao.save(findLabel);
		labelDao.getHibernateTemplate().merge(findLabel);
		//labelDao.getSession().getTransaction().commit();
		return findLabel;
	}
}
