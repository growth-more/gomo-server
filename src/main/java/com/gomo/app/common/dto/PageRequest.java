package com.gomo.app.common.dto;

import java.util.UUID;

import lombok.Getter;

@Getter
public class PageRequest {

	private int size;
	private UUID lastElementId;

	private PageRequest(
		int size,
		UUID lastElementId
	) {
		this.size = size;
		this.lastElementId = lastElementId;
	}

	public static PageRequest of(
		int size,
		UUID lastElementId
	) {
		return new PageRequest(size, lastElementId);
	}
}
