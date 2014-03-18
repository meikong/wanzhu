package com.wanzhu.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_recommendation")
public class Recommendation{
    private String recommendationId;
    private String typeId;
    private int recommendationSum;/*序号*/
    private int type;
    private int isAble;/* 0可用 1不可用*/
    @Column(name = "arg01", length = 512)
	private String arg01;
	@Column(name = "arg02", length = 128)
	private String arg02;
	@Column(name = "arg03", length = 128)
	private String arg03;
	@Column(name = "arg04", length = 128)
	private String arg04;
	@Column(name = "arg05", length = 128)
	private String arg05;
	@Column(name = "arg06", length = 128)
	private String arg06;
	@Column(name = "arg07", length = 128)
	private String arg07;
	@Column(name = "arg08", length = 128)
	private String arg08;
	@Column(name = "arg09", length = 128)
	private String arg09;
	@Column(name = "arg10", length = 128)
	private String arg10;
	@Column(name = "arg11", length = 128)
	private String arg11;
	@Column(name = "arg12", length = 128)
	private String arg12;
	@Column(name = "arg13", length = 128)
	private String arg13;
	@Column(name = "arg14", length = 128)
	private String arg14;
	@Column(name = "arg15", length = 128)
	private String arg15;
	@Column(name = "arg16", length = 128)
	private String arg16;
	@Column(name = "arg17", length = 128)
	private String arg17;
	@Column(name = "arg18", length = 128)
	private String arg18;
	@Column(name = "arg19", length = 128)
	private String arg19;
	@Column(name = "arg20", length = 128)
	private String arg20;
    
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "recommendationid", unique = true, nullable = false, length = 32)
	public String getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(String recommendationId) {
		this.recommendationId = recommendationId;
	}
	@Column(name="typeid" , nullable = false)
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@Column(name="recommendationsum" , nullable = false)
	public int getRecommendationSum() {
		return recommendationSum;
	}
	public void setRecommendationSum(int recommendationSum) {
		this.recommendationSum = recommendationSum;
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
	public String getArg01() {
		return arg01;
	}
	public void setArg01(String arg01) {
		this.arg01 = arg01;
	}
	public String getArg02() {
		return arg02;
	}
	public void setArg02(String arg02) {
		this.arg02 = arg02;
	}
	public String getArg03() {
		return arg03;
	}
	public void setArg03(String arg03) {
		this.arg03 = arg03;
	}
	public String getArg04() {
		return arg04;
	}
	public void setArg04(String arg04) {
		this.arg04 = arg04;
	}
	public String getArg05() {
		return arg05;
	}
	public void setArg05(String arg05) {
		this.arg05 = arg05;
	}
	public String getArg06() {
		return arg06;
	}
	public void setArg06(String arg06) {
		this.arg06 = arg06;
	}
	public String getArg07() {
		return arg07;
	}
	public void setArg07(String arg07) {
		this.arg07 = arg07;
	}
	public String getArg08() {
		return arg08;
	}
	public void setArg08(String arg08) {
		this.arg08 = arg08;
	}
	public String getArg09() {
		return arg09;
	}
	public void setArg09(String arg09) {
		this.arg09 = arg09;
	}
	public String getArg10() {
		return arg10;
	}
	public void setArg10(String arg10) {
		this.arg10 = arg10;
	}
	public String getArg11() {
		return arg11;
	}
	public void setArg11(String arg11) {
		this.arg11 = arg11;
	}
	public String getArg12() {
		return arg12;
	}
	public void setArg12(String arg12) {
		this.arg12 = arg12;
	}
	public String getArg13() {
		return arg13;
	}
	public void setArg13(String arg13) {
		this.arg13 = arg13;
	}
	public String getArg14() {
		return arg14;
	}
	public void setArg14(String arg14) {
		this.arg14 = arg14;
	}
	public String getArg15() {
		return arg15;
	}
	public void setArg15(String arg15) {
		this.arg15 = arg15;
	}
	public String getArg16() {
		return arg16;
	}
	public void setArg16(String arg16) {
		this.arg16 = arg16;
	}
	public String getArg17() {
		return arg17;
	}
	public void setArg17(String arg17) {
		this.arg17 = arg17;
	}
	public String getArg18() {
		return arg18;
	}
	public void setArg18(String arg18) {
		this.arg18 = arg18;
	}
	public String getArg19() {
		return arg19;
	}
	public void setArg19(String arg19) {
		this.arg19 = arg19;
	}
	public String getArg20() {
		return arg20;
	}
	public void setArg20(String arg20) {
		this.arg20 = arg20;
	}
	
}
