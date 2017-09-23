package com.nokia.charts.entity;

/**
 * @author weijid
 *
 */
public class EREntity {

	private int id;
	private int bundleId;
	private String bundleName;
	private String releaseSprint;
	private int planVelocity;
	private int actualVelocity;
	private int planCapacity;
	private String productivity;
	private String completionRate;
	private String startDate;
	private String endDate;
	private int velocityThreshold;
	private int capacityThreshold;
	private String releaseNote;

	public int getBundleId() {
		return bundleId;
	}

	public void setBundleId(int bundleId) {
		this.bundleId = bundleId;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
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

	public int getActualVelocity() {
		return actualVelocity;
	}

	public void setActualVelocity(int actualVelocity) {
		this.actualVelocity = actualVelocity;
	}

	public int getPlanCapacity() {
		return planCapacity;
	}

	public void setPlanCapacity(int planCapacity) {
		this.planCapacity = planCapacity;
	}

	public String getProductivity() {
		return productivity;
	}

	public void setProductivity(String productivity) {
		this.productivity = productivity;
	}

	public String getCompletionRate() {
		return completionRate;
	}

	public void setCompletionRate(String completionRate) {
		this.completionRate = completionRate;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVelocityThreshold() {
		return velocityThreshold;
	}

	public void setVelocityThreshold(int velocityThreshold) {
		this.velocityThreshold = velocityThreshold;
	}

	public int getCapacityThreshold() {
		return capacityThreshold;
	}

	public void setCapacityThreshold(int capacityThreshold) {
		this.capacityThreshold = capacityThreshold;
	}

	public String getReleaseNote() {
		return releaseNote;
	}

	public void setReleaseNote(String releaseNote) {
		this.releaseNote = releaseNote;
	}

}
