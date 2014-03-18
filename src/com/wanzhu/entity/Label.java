package com.wanzhu.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * Label entity. 
 */
@Entity
@Table(name = "t_label", uniqueConstraints = @UniqueConstraint(columnNames = "label"))
public class Label implements java.io.Serializable {
	private static final long serialVersionUID = -4412002911070809394L;
	
	private String labelid;
	private String label;
	private String memo;
	private String userid;//创建用户的id
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	private Integer type;
	private Long showorder;
	private Integer recommendation;
	private Set<EventLabel> eventLabels = new HashSet<EventLabel>(0);
	

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "labelid", unique = true, nullable = false, length = 32)
	public String getLabelid() {
		return this.labelid;
	}

	public void setLabelid(String labelid) {
		this.labelid = labelid;
	}

	@Column(name = "label", unique = true, nullable = false, length = 32)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "userid", length = 32)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Column(name = "createtime", length = 29)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "showorder", nullable = false)
	public Long getShoworder() {
		return showorder;
	}

	public void setShoworder(Long showorder) {
		this.showorder = showorder;
	}

	@Column(name = "recommendation", nullable = false)
	public Integer getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Integer recommendation) {
		this.recommendation = recommendation;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "label")
	public Set<EventLabel> getEventLabels() {
		return this.eventLabels;
	}

	public void setEventLabels(Set<EventLabel> eventLabels) {
		this.eventLabels = eventLabels;
	}

}