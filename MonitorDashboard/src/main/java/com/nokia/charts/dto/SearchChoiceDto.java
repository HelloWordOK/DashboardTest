package com.nokia.charts.dto;

public class SearchChoiceDto {

	private int serverId;
	private String responseTimeStart;
	private String responseTimeEnd;
	private int monitorId;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getResponseTimeStart() {
		return responseTimeStart;
	}

	public void setResponseTimeStart(String responseTimeStart) {
		this.responseTimeStart = responseTimeStart;
	}

	public String getResponseTimeEnd() {
		return responseTimeEnd;
	}

	public void setResponseTimeEnd(String responseTimeEnd) {
		this.responseTimeEnd = responseTimeEnd;
	}

	public int getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(int monitorId) {
		this.monitorId = monitorId;
	}

}
