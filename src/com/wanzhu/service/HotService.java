package com.wanzhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.wanzhu.dao.HotDao;
import com.wanzhu.dao.RemarkDao;
import com.wanzhu.jsonvo.LabelVO;
import com.wanzhu.jsonvo.RemarkHotVo;

@Service("hot.service")
public class HotService{

	@Autowired
	private HotDao hotDao;
    @Autowired
    private RemarkDao remarkDao;
    /**
	 * @return
	 * @Date:2013-5-7  
	 * @Author:xuguangyun  
	 * @Description:获取话题热点的所有ID
	 */
    public List<RemarkHotVo> queryByParking(){
        return remarkDao.queryRemarkHotList();
    }
    
    /**
     * 更新
     * @Date:2013-5-14  
     * @Author:Guibin Zhang  
     * @Description:
     */
    public void syncHotLabelsToHotCache(){
    	
    }

    /**
     * 首页热门标签
     * @param maxHotLabelCount
     * @return
     * @Date:2013-5-14  
     * @Author:Guibin Zhang  
     * @Description:
     * 热门标签
     */
	public List<LabelVO> queryHotLabels(int maxHotLabelCount) {
		HibernateTemplate hdao = hotDao.getHibernateTemplate();
		hdao.find("select new com.wanzhu.jsonvo.LabelVO(l.labelid, l.label) from Label l where recommendation=1 order by showorder desc, createtime desc");
		return null;
	}
}
