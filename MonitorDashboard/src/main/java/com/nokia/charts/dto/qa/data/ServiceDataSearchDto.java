package com.nokia.charts.dto.qa.data;

public class ServiceDataSearchDto {

	private String serviceCode;
	private String dataType;
	private String[] slaBreach;
	private String startMonth;
	private String endMonth;
	private int id;
	private String real_breach;
	private String comment;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String[] getSlaBreach() {
		return slaBreach;
	}

	public void setSlaBreach(String[] slaBreach) {
		this.slaBreach = slaBreach;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReal_breach() {
		return real_breach;
	}

	public void setReal_breach(String real_breach) {
		this.real_breach = real_breach;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
