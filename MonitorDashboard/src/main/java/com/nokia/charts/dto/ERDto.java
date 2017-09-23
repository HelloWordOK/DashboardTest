package com.nokia.charts.dto;

public class ERDto {

	private String releaseSprint;
	private int planVelocity;
	private int planCapacity;
	private int actualVelocity;
	private float productivity;
	private float completionRate;
	private int velocityThreshold;
	private int capacityThreshold;
	private String releaseNote;

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
