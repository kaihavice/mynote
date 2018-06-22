package com.xuyazhou.mynote.model.bean;

/**
 * 会员状态类型
 * 
 * @author WQC
 * 
 * @date 2016-07-20
 *
 */
public enum UserStatusType {

	// 可用
	NORMAL(0),
	// 禁用
	DISABLE(1);

	private int status;

	UserStatusType(int status) {
		this.status = status;
	}

	public int getValue() {
		return status;
	}

	public static UserStatusType getMemberStatusTypeByValue(int value) {
		for (UserStatusType userStatus : values()) {
			if (userStatus.getValue() == value) {
				return userStatus;
			}
		}
		return null;
	}
}
