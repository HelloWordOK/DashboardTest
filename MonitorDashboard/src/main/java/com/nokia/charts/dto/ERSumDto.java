package com.nokia.charts.dto;

public class ERSumDto {

	private String bundleName;
	private String startDate;
	private String endDate;
	private int sumPlanVelocity;
	private int sumPlanCapacity;
	private int sumActualVelocity;
	private Double sumProductivity;
	private Double sumCompletionRate;

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

	public int getSumPlanVelocity() {
		return sumPlanVelocity;
	}

	public void setSumPlanVelocity(int sumPlanVelocity) {
		this.sumPlanVelocity = sumPlanVelocity;
	}

	public int getSumPlanCapacity() {
		return sumPlanCapacity;
	}

	public void setSumPlanCapacity(int sumPlanCapacity) {
		this.sumPlanCapacity = sumPlanCapacity;
	}

	public int getSumActualVelocity() {
		return sumActualVelocity;
	}

	public void setSumActualVelocity(int sumActualVelocity) {
		this.sumActualVelocity = sumActualVelocity;
	}

	public Double getSumProductivity() {
		return sumProductivity;
	}

	public void setSumProductivity(Double sumProductivity) {
		sumProductivity = Double.valueOf(String.format("%.2f", sumProductivity));
		this.sumProductivity = sumProductivity;
	}

	public Double getSumCompletionRate() {
		return sumCompletionRate;
	}

	public void setSumCompletionRate(Double sumCompletionRate) {
		sumCompletionRate = Double.valueOf(String.format("%.2f", sumCompletionRate));
		this.sumCompletionRate = sumCompletionRate;
	}

}
