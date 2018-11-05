package com.jyh.video.common.enums;

public enum VideoStatusEnum {

	// 发布成功
	SUCCESS(1),

	// 禁止播放，管理员操作
	FORBID(2);
	
	public final int value;
	
	VideoStatusEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
