package com.gomo.app.point.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class HistoryListPointResponse {

	private List<HistoryReadPointResponse> histories;

	private HistoryListPointResponse(List<HistoryReadPointResponse> histories) {
		this.histories = histories;
	}

	public static HistoryListPointResponse of(List<HistoryReadPointResponse> histories) {
		return new HistoryListPointResponse(histories);
	}
}
