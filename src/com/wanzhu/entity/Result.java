package com.wanzhu.entity;

/**
 * JSON数据对象
 */
public class Result {
	
	private boolean success;
	private String msg;
	private Integer code;
	private Object content;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "Result [success=" + success + ", msg=" + msg + ", code=" + code
				+ ", content=" + content + "]";
	}
	
}
