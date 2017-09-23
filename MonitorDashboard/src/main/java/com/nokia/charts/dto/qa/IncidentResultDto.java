package com.nokia.charts.dto.qa;

public class IncidentResultDto {

	private String open_Time;
	private String close_Time;
	private String priority;
	private String ms_Ttr_Breach;
	private String status;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getMs_Ttr_Breach() {
		return ms_Ttr_Breach;
	}

	public void setMs_Ttr_Breach(String ms_Ttr_Breach) {
		this.ms_Ttr_Breach = ms_Ttr_Breach;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReal_breach() {
		return real_breach;
	}

	public void setReal_breach(String real_breach) {
		this.real_breach = real_breach;
	}

}
