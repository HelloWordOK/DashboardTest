package com.nokia.charts.entity.testing;

public class PerformanceBaseLine {

	private String functionType;
	private String rtBaseline;
	private String rtRange;
	private String throughputBaseline;
	private String throughputRange;
	private String errorBaseline;

	public String getRtBaseline() {
		return rtBaseline;
	}

	public void setRtBaseline(String rtBaseline) {
		this.rtBaseline = rtBaseline;
	}

	public String getRtRange() {
		return rtRange;
	}

	public void setRtRange(String rtRange) {
		this.rtRange = rtRange;
	}

	public String getThroughputBaseline() {
		return throughputBaseline;
	}

	public void setThroughputBaseline(String throughputBaseline) {
		this.throughputBaseline = throughputBaseline;
	}

	public String getThroughputRange() {
		return throughputRange;
	}

	public void setThroughputRange(String throughputRange) {
		this.throughputRange = throughputRange;
	}

	public String getErrorBaseline() {
		return errorBaseline;
	}

	public void setErrorBaseline(String errorBaseline) {
		this.errorBaseline = errorBaseline;
	}

	/**
	 * @return the functionType
	 */
	public String getFunctionType() {
		return functionType;
	}

	/**
	 * @param functionType the functionType to set
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

}
