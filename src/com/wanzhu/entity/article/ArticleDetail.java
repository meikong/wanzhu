 /**  
 *@Description:     
 */ 
package com.wanzhu.entity.article;  

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
@Table(name = "article_detail")  
public class ArticleDetail implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//帖子Id
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator", strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	/**所属文章ID **/
	@Column(name = "articleId")
	private Integer articleId;
	
	/**副标题 为空时不展示**/
	@Column(name = "subhead")
	private String subhead;
	
	/**内容**/
	@Column(name = "content")
	private String content;
	
	/**创建时间**/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	private Date createTime;
	
	/**最后更新时间**/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime", length = 19)
	private Date updateTime;
	/** 类型 暂保留**/
	@Column(name = "type")
	private Integer type;
	
	/** 0-被删除 1-可用**/
	@Column(name = "isEnable")
	private Integer isEnable;
	
	/** 分类**/
	@Column(name = "showorder")
	private Long showorder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
	
	
}
