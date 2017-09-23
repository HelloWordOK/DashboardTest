package com.nokia.charts.dto;

public class ActiveUsersDto {

	private Integer[] serviceIdList;
	private String startTime;
	private String endTime;

	public Integer[] getServiceIdList() {
		return serviceIdList;
	}

	public void setServiceIdList(Integer[] serviceIdList) {
		this.serviceIdList = serviceIdList;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
