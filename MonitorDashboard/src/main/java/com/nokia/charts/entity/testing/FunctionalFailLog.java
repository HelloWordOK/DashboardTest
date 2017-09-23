package com.nokia.charts.entity.testing;

public class FunctionalFailLog extends FunctionalEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7835463036850020323L;
	private String function;
	private String caseName;
	private String screenshot;
	private String log;
	private String caseResult;

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getCaseResult() {
		return caseResult;
	}

	public void setCaseResult(String caseResult) {
		this.caseResult = caseResult;
	}
}
