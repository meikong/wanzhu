 /**  
 *@Description:     
 */ 
package com.wanzhu.entity.shop;  

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
@Table(name = "shop") 
public class Shop implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator", strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	/**店名**/
	@Column(name = "title")
	private String title;
	
	/**店简介**/
	@Column(name = "intro")
	private String intro;
	
	/**店头图 **/
	@Column(name = "logo")
	private String logo;
	
	/**地址 **/
	@Column(name = "address")
	private String address;
	
	/**地图横坐标  **/
	@Column(name = "locationX")
	private String locationX;
	
	/**地图纵坐标 **/
	@Column(name = "locationY")
	private String locationY;
	
	/**人均消费 **/
	@Column(name = "consumption")
	private String consumption;
	
	/**评分**/
	@Column(name = "score")
	private Float score;
	
	/**联系电话**/
	@Column(name = "tel")
	private String tel;
	
	/**店标签 ,号隔开**/
	@Column(name = "labels")
	private String labels;
	
	/**营业时间**/
	@Column(name = "openTime")
	private String openTime;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocationX() {
		return locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public String getLocationY() {
		return locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
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
	
}
