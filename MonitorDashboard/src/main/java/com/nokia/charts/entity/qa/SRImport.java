package com.nokia.charts.entity.qa;

import com.nokia.charts.util.DateUtils;

public class SRImport {

	private int id;
	private String interaction_ID;
	private String status;
	private String submit_Date;
	private String close_Date;
	private String number;
	private String assigned_Dept;
	private String ms_Service_Provider;
	private String m_S_Total_Duration;
	private String sla_Breach;
	private String assigned_to;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInteraction_ID() {
		return interaction_ID;
	}

	public void setInteraction_ID(String interaction_ID) {
		this.interaction_ID = interaction_ID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmit_Date() {
		return submit_Date;
	}

	public void setSubmit_Date(String submit_Date) {
		if (submit_Date != null && !submit_Date.isEmpty()) {
			submit_Date = DateUtils.formatDate(submit_Date, DateUtils.DATE_FROMAT_EN, DateUtils.DATE_FROMAT_CN);
		}
		this.submit_Date = submit_Date;
	}

	public String getClose_Date() {
		return close_Date;
	}

	public void setClose_Date(String close_Date) {
		if (close_Date != null && !close_Date.isEmpty()) {
			close_Date = DateUtils.formatDate(close_Date, DateUtils.DATE_FROMAT_EN, DateUtils.DATE_FROMAT_CN);
		}
		this.close_Date = close_Date;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAssigned_Dept() {
		return assigned_Dept;
	}

	public void setAssigned_Dept(String assigned_Dept) {
		this.assigned_Dept = assigned_Dept;
	}

	public String getMs_Service_Provider() {
		return ms_Service_Provider;
	}

	public void setMs_Service_Provider(String ms_Service_Provider) {
		this.ms_Service_Provider = ms_Service_Provider;
	}

	public String getM_S_Total_Duration() {
		return m_S_Total_Duration;
	}

	public void setM_S_Total_Duration(String m_S_Total_Duration) {
		this.m_S_Total_Duration = m_S_Total_Duration;
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

}
