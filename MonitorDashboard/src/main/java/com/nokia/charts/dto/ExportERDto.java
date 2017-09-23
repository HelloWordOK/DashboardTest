package com.nokia.charts.dto;

public class ExportERDto {

	private String bundleName;
	private String releaseSprint;
	private int planVelocity;
	private int planCapacity;
	private int actualVelocity;
	private float productivity;
	private float completionRate;
	private String startDate;
	private String endDate;
	private String releaseNote;

	public String getReleaseNote() {
		return releaseNote;
	}

	public void setReleaseNote(String releaseNote) {
		this.releaseNote = releaseNote;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReleaseSprint() {
		return releaseSprint;
	}

	public void setReleaseSprint(String releaseSprint) {
		this.releaseSprint = releaseSprint;
	}

	public int getPlanVelocity() {
		return planVelocity;
	}

	public void setPlanVelocity(int planVelocity) {
		this.planVelocity = planVelocity;
	}

	public int getPlanCapacity() {
		return planCapacity;
	}

	public void setPlanCapacity(int planCapacity) {
		this.planCapacity = planCapacity;
	}

	public int getActualVelocity() {
		return actualVelocity;
	}

	public void setActualVelocity(int actualVelocity) {
		this.actualVelocity = actualVelocity;
	}

	public float getProductivity() {
		return productivity;
	}

	public void setProductivity(float productivity) {
		this.productivity = productivity;
	}

	public float getCompletionRate() {
		return completionRate;
	}

	public void setCompletionRate(float completionRate) {
		this.completionRate = completionRate;
	}

}
