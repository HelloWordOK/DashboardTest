package com.nokia.charts.dto;

public class BaselineDto {

	private String project;

	private String environment;

	private String functionType;

	private String rtBaseline;

	private String rtRange;

	private String tBaseline;

	private String tRange;

	private String eBaseline;

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
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

	/**
	 * @return the rtBaseline
	 */
	public String getRtBaseline() {
		return rtBaseline;
	}

	/**
	 * @param rtBaseline the rtBaseline to set
	 */
	public void setRtBaseline(String rtBaseline) {
		this.rtBaseline = rtBaseline;
	}

	/**
	 * @return the rtRange
	 */
	public String getRtRange() {
		return rtRange;
	}

	/**
	 * @param rtRange the rtRange to set
	 */
	public void setRtRange(String rtRange) {
		this.rtRange = rtRange;
	}

	/**
	 * @return the tBaseline
	 */
	public String gettBaseline() {
		return tBaseline;
	}

	/**
	 * @param tBaseline the tBaseline to set
	 */
	public void settBaseline(String tBaseline) {
		this.tBaseline = tBaseline;
	}

	/**
	 * @return the tRange
	 */
	public String gettRange() {
		return tRange;
	}

	/**
	 * @param tRange the tRange to set
	 */
	public void settRange(String tRange) {
		this.tRange = tRange;
	}

	/**
	 * @return the eBaseline
	 */
	public String geteBaseline() {
		return eBaseline;
	}

	/**
	 * @param eBaseline the eBaseline to set
	 */
	public void seteBaseline(String eBaseline) {
		this.eBaseline = eBaseline;
	}
}
