package com.wanzhu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * EventLabel entity. 
 */
@Entity
@Table(name = "t_event_label", uniqueConstraints = @UniqueConstraint(columnNames = {
		"labelid", "eventid" }))
public class EventLabel implements java.io.Serializable {
	private static final long serialVersionUID = -1287164927260501874L;
	
	private String assignid;
	private Event event;
	private Label label;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "assignid", unique = true, nullable = false, length = 32)
	public String getAssignid() {
		return this.assignid;
	}

	public void setAssignid(String assignid) {
		this.assignid = assignid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventid")
	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "labelid")
	public Label getLabel() {
		return this.label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

}