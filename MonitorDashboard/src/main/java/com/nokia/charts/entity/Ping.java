package com.nokia.charts.entity;

/**
 * Server Monitor PING
 * 
 * @author weijid
 *
 */
public class Ping extends BaseEntity {

	private static final long serialVersionUID = -6998098382594105928L;

	private String thresholdCritical;

	private String thresholdWarning;

	public String getThresholdCritical() {
		return thresholdCritical;
	}

	public void setThresholdCritical(String thresholdCritical) {
		this.thresholdCritical = thresholdCritical;
	}

	public String getThresholdWarning() {
		return thresholdWarning;
	}

	public void setThresholdWarning(String thresholdWarning) {
		this.thresholdWarning = thresholdWarning;
	}

}
