package com.gomo.app.common.web;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PageRequest {

	private int size;
	private String lastElementId;
	private LocalDateTime lastElementTime;

	private PageRequest(int size, String lastElementId, LocalDateTime lastElementTime) {
		this.size = size;
		this.lastElementId = lastElementId;
		this.lastElementTime = lastElementTime;
	}

	public static PageRequest of(int size, String lastElementId, LocalDateTime lastElementTime) {
		return new PageRequest(size, lastElementId, lastElementTime);
	}
}
