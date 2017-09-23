package com.nokia.charts.dto.qa;

public class SRResultDto {

	private String submit_Date;
	private String close_Date;
	private String sla_Breach;
	private String assigned_to;
	private String real_breach;

	public String getSubmit_Date() {
		return submit_Date;
	}

	public void setSubmit_Date(String submit_Date) {
		this.submit_Date = submit_Date;
	}

	public String getClose_Date() {
		return close_Date;
	}

	public void setClose_Date(String close_Date) {
		this.close_Date = close_Date;
	}

	public String getSla_Breach() {
		return sla_Breach;
	}

	public void setSla_Breach(String sla_Breach) {
		this.sla_Breach = sla_Breach;
	}

	public String getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}

	public String getReal_breach() {
		return real_breach;
	}

	public void setReal_breach(String real_breach) {
		this.real_breach = real_breach;
	}

}
