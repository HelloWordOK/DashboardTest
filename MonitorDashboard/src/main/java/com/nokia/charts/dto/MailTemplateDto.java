package com.nokia.charts.dto;

public class MailTemplateDto {

	private int tempId;
	private String tempName;
	private int serviceId;
	private String serviceName;
	private String mailTo;
	private String mailCc;
	private String mailSubject;
	private String mailContent;
	private String mailGroup;
	private String mailTimer;

	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailGroup() {
		return mailGroup;
	}

	public void setMailGroup(String mailGroup) {
		this.mailGroup = mailGroup;
	}

	public String getMailTimer() {
		return mailTimer;
	}

	public void setMailTimer(String mailTimer) {
		this.mailTimer = mailTimer;
	}
}
