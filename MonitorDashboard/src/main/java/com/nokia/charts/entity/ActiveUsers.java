package com.nokia.charts.entity;

public class ActiveUsers {

	private Integer id;
	private Integer serviceId;
	private Integer userCounts;
	private String countTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
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
