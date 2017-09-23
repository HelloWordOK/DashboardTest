package com.nokia.charts.dto.qa;

public class ProblemResultDto {

	private String open_Time;
	private String close_Time;
	private String priority_Code;
	private String sla_Breach;
	private String real_breach;

	public String getOpen_Time() {
		return open_Time;
	}

	public void setOpen_Time(String open_Time) {
		this.open_Time = open_Time;
	}

	public String getClose_Time() {
		return close_Time;
	}

	public void setClose_Time(String close_Time) {
		this.close_Time = close_Time;
	}

	public String getPriority_Code() {
		return priority_Code;
	}

	public void setPriority_Code(String priority_Code) {
		this.priority_Code = priority_Code;
	}

	public String getSla_Breach() {
		return sla_Breach;
	}

	public void setSla_Breach(String sla_Breach) {
		this.sla_Breach = sla_Breach;
	}

	public String getReal_breach() {
		return real_breach;
	}

	public void setReal_breach(String real_breach) {
		this.real_breach = real_breach;
	}

}
