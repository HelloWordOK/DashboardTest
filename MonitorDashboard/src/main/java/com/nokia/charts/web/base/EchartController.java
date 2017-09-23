package com.nokia.charts.web.base;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.echarts.EchartLegend;
import com.nokia.charts.dto.echarts.EchartTitle;
import com.nokia.charts.dto.echarts.EchartXAxis;
import com.nokia.charts.dto.echarts.EchartYAxis;
import com.nokia.charts.entity.Url;

/**
 * Echarts 配置
 * @author weijid
 *
 */
public class EchartController extends BaseController {

	/**
	 * 设置Echarts 图表 title
	 * @param data
	 * @param title
	 * @return
	 */
	public Map<String, Object> setEchartTitle(Map<String, Object> data, String title) {
		// title
		EchartTitle echartTitle = new EchartTitle();
		echartTitle.setText(title);
		data.put("title", echartTitle);
		return data;
	}

	/**
	 * 设置Echarts 图表Y轴的值
	 * @param data
	 * @param yName
	 * @param yMax
	 * @return
	 */
	public Map<String, Object> setEchartYAxis(Map<String, Object> data, String yName, Object yMax) {
		// yAxis
		EchartYAxis echartYAxis = new EchartYAxis();
		echartYAxis.setName(yName);
		echartYAxis.setType("value");
		echartYAxis.setMax(yMax);
		data.put("yAxis", echartYAxis);
		return data;
	}

	/**
	 * 设置Echarts 图表X轴的值
	 * @param data
	 * @param xName
	 * @param xAxisList
	 * @return
	 */
	public Map<String, Object> setEchartXAxis(Map<String, Object> data, String xName, List<Object> xAxisList) {
		// xAxis
		EchartXAxis echartXAxis = new EchartXAxis();
		echartXAxis.setData(xAxisList.toArray());
		echartXAxis.setName(xName);
		echartXAxis.setType("category");
		data.put("xAxis", echartXAxis);
		return data;
	}

	public Map<String, Object> setEchartLegendPingData(Map<String, Object> data, Object threshold_warning,
			Object threshold_critical, List<String> legendList) {
		// legend
		EchartLegend echartLegend = new EchartLegend();
		echartLegend.setData(legendList.toArray());
		echartLegend.setThresholdCritical(threshold_critical);
		echartLegend.setThresholdWarning(threshold_warning);
		data.put("legend", echartLegend);
		return data;
	}

	/**
	 * 设置Echarts 图表的图例
	 * @param data
	 * @param legendList
	 * @return
	 */
	public Map<String, Object> setEchartLegendData(Map<String, Object> data, List<String> legendList) {
		// legend
		EchartLegend echartLegend = new EchartLegend();
		echartLegend.setData(legendList.toArray());
		data.put("legend", echartLegend);
		return data;
	}

	public Map<String, Object> setEchartLegendUrlData(Map<String, Object> data, Url url, List<String> legendList) {
		// legend
		EchartLegend echartLegend = new EchartLegend();
		echartLegend.setData(legendList.toArray());
		if (url.getUpperSpecThreshold() != null && !url.getUpperSpecThreshold().isEmpty()) {
			echartLegend.setUpperSpecThreshold(Double.valueOf(url.getUpperSpecThreshold()));
		}
		if (url.getUpperControlThreshold() != null && !url.getUpperControlThreshold().isEmpty()) {
			echartLegend.setUpperControlThreshold(Double.valueOf(url.getUpperControlThreshold()));
		}
		if (url.getAverage() != null && !url.getAverage().isEmpty()) {
			echartLegend.setAverageThreshold(Double.valueOf(url.getAverage()));
		}
		if (url.getLowerControlThreshold() != null && !url.getLowerControlThreshold().isEmpty()) {
			echartLegend.setLowerControlThreshold(Double.valueOf(url.getLowerControlThreshold()));
		}
		if (url.getLowerSpecThreshold() != null && !url.getLowerSpecThreshold().isEmpty()) {
			echartLegend.setLowerSpecThreshold(Double.valueOf(url.getLowerSpecThreshold()));
		}
		data.put("legend", echartLegend);
		return data;
	}
}
