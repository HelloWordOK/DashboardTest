package com.nokia.charts.entity.testing;

public class FunctionalModule extends FunctionalEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3039692399963973980L;

	private String moduleTestCase;
	private String pass;
	private String fail;
	private String error;
	private String skip;
	private String incomplete;
	private String risk;
	private String modulePassRate;
	private String moduleResult;

	public String getModuleTestCase() {
		return moduleTestCase;
	}

	public void setModuleTestCase(String moduleTestCase) {
		this.moduleTestCase = moduleTestCase;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getSkip() {
		return skip;
	}

	public void setSkip(String skip) {
		this.skip = skip;
	}

	public String getIncomplete() {
		return incomplete;
	}

	public void setIncomplete(String incomplete) {
		this.incomplete = incomplete;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getModulePassRate() {
		return modulePassRate;
	}

	public void setModulePassRate(String modulePassRate) {
		this.modulePassRate = modulePassRate;
	}

	public String getModuleResult() {
		return moduleResult;
	}

	public void setModuleResult(String moduleResult) {
		this.moduleResult = moduleResult;
	}

}
