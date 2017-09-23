package com.nokia.charts.dto.echarts;

public class EchartLegend {

	private Object[] data;
	private Object thresholdCritical;
	private Object thresholdWarning;
	private Object upperSpecThreshold;
	private Object upperControlThreshold;
	private Object averageThreshold;
	private Object lowerControlThreshold;
	private Object lowerSpecThreshold;

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public Object getThresholdCritical() {
		return thresholdCritical;
	}

	public void setThresholdCritical(Object thresholdCritical) {
		this.thresholdCritical = thresholdCritical;
	}

	public Object getThresholdWarning() {
		return thresholdWarning;
	}

	public void setThresholdWarning(Object thresholdWarning) {
		this.thresholdWarning = thresholdWarning;
	}

	public Object getUpperSpecThreshold() {
		return upperSpecThreshold;
	}

	public void setUpperSpecThreshold(Object upperSpecThreshold) {
		this.upperSpecThreshold = upperSpecThreshold;
	}

	public Object getUpperControlThreshold() {
		return upperControlThreshold;
	}

	public void setUpperControlThreshold(Object upperControlThreshold) {
		this.upperControlThreshold = upperControlThreshold;
	}

	public Object getAverageThreshold() {
		return averageThreshold;
	}

	public void setAverageThreshold(Object averageThreshold) {
		this.averageThreshold = averageThreshold;
	}

	public Object getLowerControlThreshold() {
		return lowerControlThreshold;
	}

	public void setLowerControlThreshold(Object lowerControlThreshold) {
		this.lowerControlThreshold = lowerControlThreshold;
	}

	public Object getLowerSpecThreshold() {
		return lowerSpecThreshold;
	}

	public void setLowerSpecThreshold(Object lowerSpecThreshold) {
		this.lowerSpecThreshold = lowerSpecThreshold;
	}

}
