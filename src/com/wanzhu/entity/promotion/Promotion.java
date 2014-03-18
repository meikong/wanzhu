 /**  
 *@Description:     
 */ 
package com.wanzhu.entity.promotion;  

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "promotion")  
public class Promotion implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//帖子Id
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator", strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	/**首页幻灯片图片地址**/
	@Column(name = "flashUrl")
	private String flashUrl;
	
	/**标题 暂保留 **/
	@Column(name = "title")
	private String title;
	
	/**简介 暂保留**/
	@Column(name = "intro")
	private String intro;
	
	/**创建时间**/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	private Date createTime;
	
	/** 0-被删除 1-可用**/
	@Column(name = "isEnable")
	private Integer isEnable;
	
	/**展示顺序，保留**/
	@Column(name = "showorder")
	private Long showorder;
	
	/** 点击图片的动作**/
	@Column(name = "link")
	private String link;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlashUrl() {
		return flashUrl;
	}

	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public Long getShoworder() {
		return showorder;
	}

	public void setShoworder(Long showorder) {
		this.showorder = showorder;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}	
	
	
}
