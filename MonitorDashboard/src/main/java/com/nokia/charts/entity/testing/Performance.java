package com.nokia.charts.entity.testing;

import java.io.Serializable;

public class Performance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8768601624382674386L;
	private int id;
	private String project;
	private String environment;
	private String functionType;
	private String testDate;
	private String version;
	private String samples;
	private String average;
	private String median;
	private String ninetyPecLine;
	private String min;
	private String max;
	private String error;
	private String throughput;
	private String kbSec;
	private String result;
	private String isHistory;

	public String getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSamples() {
		return samples;
	}

	public void setSamples(String samples) {
		this.samples = samples;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getNinetyPecLine() {
		return ninetyPecLine;
	}

	public void setNinetyPecLine(String ninetyPecLine) {
		this.ninetyPecLine = ninetyPecLine;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getThroughput() {
		return throughput;
	}

	public void setThroughput(String throughput) {
		this.throughput = throughput;
	}

	public String getKbSec() {
		return kbSec;
	}

	public void setKbSec(String kbSec) {
		this.kbSec = kbSec;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the median
	 */
	public String getMedian() {
		return median;
	}

	/**
	 * @param median the median to set
	 */
	public void setMedian(String median) {
		this.median = median;
	}

	/**
	 * @return the min
	 */
	public String getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(String min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public String getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(String max) {
		this.max = max;
	}

}
