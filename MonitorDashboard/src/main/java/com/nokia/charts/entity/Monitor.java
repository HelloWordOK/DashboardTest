package com.nokia.charts.entity;

/**
 * Monitor entity
 * 
 * @author weijid
 *
 */
public class Monitor {

	private int id;
	private String monitorName;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
