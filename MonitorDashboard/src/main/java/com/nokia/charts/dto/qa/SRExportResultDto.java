package com.nokia.charts.dto.qa;

public class SRExportResultDto {

	private String interaction_id;
	private String status;
	private String submit_date;
	private String close_date;
	private String number;
	private String assigned_dept;
	private String ms_service_provider;
	private String m_s_total_duration;
	private String sla_breach;
	private String assigned_to;
	private String solution_code;

	public String getSolution_code() {
		return solution_code;
	}
	public void setSolution_code(String solution_code) {
		this.solution_code = solution_code;
	}
	public String getInteraction_id() {
		return interaction_id;
	}
	public void setInteraction_id(String interaction_id) {
		this.interaction_id = interaction_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubmit_date() {
		return submit_date;
	}
	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}
	public String getClose_date() {
		return close_date;
	}
	public void setClose_date(String close_date) {
		this.close_date = close_date;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAssigned_dept() {
		return assigned_dept;
	}
	public void setAssigned_dept(String assigned_dept) {
		this.assigned_dept = assigned_dept;
	}
	public String getMs_service_provider() {
		return ms_service_provider;
	}
	public void setMs_service_provider(String ms_service_provider) {
		this.ms_service_provider = ms_service_provider;
	}
	public String getM_s_total_duration() {
		return m_s_total_duration;
	}
	public void setM_s_total_duration(String m_s_total_duration) {
		this.m_s_total_duration = m_s_total_duration;
	}
	public String getSla_breach() {
		return sla_breach;
	}
	public void setSla_breach(String sla_breach) {
		this.sla_breach = sla_breach;
	}
	public String getAssigned_to() {
		return assigned_to;
	}
	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}

}
