package com.wanzhu.jsonvo;

import com.wanzhu.entity.User;

public class UserVO {
	private String userId;
	private String name;
	
	public static UserVO convertFromEntity(User user) {
		UserVO vo = new UserVO();
		vo.setUserId(user.getUserid());
		vo.setName(user.getName());
		return vo;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
