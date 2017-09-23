package com.nokia.charts.dto;

public class ActiveUsersReportDto {

	private String serviceName;
	private Integer userCounts;
	private String countTime;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getUserCounts() {
		return userCounts;
	}

	public void setUserCounts(Integer userCounts) {
		this.userCounts = userCounts;
	}

	public String getCountTime() {
		return countTime;
	}

	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}

}
