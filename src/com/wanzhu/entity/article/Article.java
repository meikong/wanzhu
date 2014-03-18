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
@Table(name = "article")  
public class Article implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//帖子Id
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator", strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	/**文章标题**/
	@Column(name = "title")
	private String title;
	
	/**简介**/
	@Column(name = "intro")
	private String intro;
	
	/**文章封面图**/
	@Column(name = "coverImage")
	private String coverImage;
	
	/**创建时间**/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	private Date createTime;
	
	/**最后更新时间**/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime", length = 19)
	private Date updateTime;
	
	/**文章来源**/
	@Column(name = "source")
	private String source;
	
	/**作者名**/
	@Column(name = "author")
	private String author;
	
	/** 作者id **/
	@Column(name = "authorId")
	private String authorId;
	
	/** 文章标签，以逗号分隔多个,分割  **/
	@Column(name = "labels")
	private String labels;
	
	/**赞个数**/
	@Column(name = "likes")
	private Integer likes;
	
	/**展示顺序，保留**/
	@Column(name = "showorder")
	private Long showorder;
	
	/** 文章类型：1-好店文章 2-城市达人文章 3-好店文章**/
	@Column(name = "type")
	private Integer type;
	
	/** 0-被删除 1-可用**/
	@Column(name = "isEnable")
	private Integer isEnable;
	
	/** 分类**/
	@Column(name = "category")
	private String category;	
	
}
