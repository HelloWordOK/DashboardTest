package com.nokia.charts.entity;

/**
 * Server Monitor URL
 * 
 * @author weijid
 *
 */
public class Url extends BaseEntity {

	private static final long serialVersionUID = -5719591280501383173L;

	private String upperSpecThreshold;
	private String upperControlThreshold;
	private String average;
	private String lowerControlThreshold;
	private String lowerSpecThreshold;

	public String getUpperSpecThreshold() {
		return upperSpecThreshold;
	}

	public void setUpperSpecThreshold(String upperSpecThreshold) {
		this.upperSpecThreshold = upperSpecThreshold;
	}

	public String getUpperControlThreshold() {
		return upperControlThreshold;
	}

	public void setUpperControlThreshold(String upperControlThreshold) {
		this.upperControlThreshold = upperControlThreshold;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getLowerControlThreshold() {
		return lowerControlThreshold;
	}

	public void setLowerControlThreshold(String lowerControlThreshold) {
		this.lowerControlThreshold = lowerControlThreshold;
	}

	public String getLowerSpecThreshold() {
		return lowerSpecThreshold;
	}

	public void setLowerSpecThreshold(String lowerSpecThreshold) {
		this.lowerSpecThreshold = lowerSpecThreshold;
	}

}
