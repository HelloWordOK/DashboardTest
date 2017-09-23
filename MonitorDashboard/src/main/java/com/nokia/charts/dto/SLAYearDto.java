package com.nokia.charts.dto;

import java.util.List;

public class SLAYearDto {

	private String server;
	private List<Double> valueList;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public List<Double> getValueList() {
		return valueList;
	}

	public void setValueList(List<Double> valueList) {
		this.valueList = valueList;
	}
}
