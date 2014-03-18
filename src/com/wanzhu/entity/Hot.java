package com.wanzhu.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_hot")
public class Hot
{
    private String hotId;
    private String typeId;
    private int hotSum;
    private int type;
    private int isAble;
    
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "hotid", unique = true, nullable = false, length = 32)
    public String getHotId() {
		return hotId;
	}
	public void setHotId(String hotId) {
		this.hotId = hotId;
	}
	@Column(name="typeid" , nullable = false)
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@Column(name="hotsum" , nullable = false)
	public int getHotSum() {
		return hotSum;
	}
	public void setHotSum(int hotSum) {
		this.hotSum = hotSum;
	}
	@Column(name="type" , nullable = false)
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Column(name="isable" , nullable = false)
	public int getIsAble() {
		return isAble;
	}
	public void setIsAble(int isAble) {
		this.isAble = isAble;
	}
}
