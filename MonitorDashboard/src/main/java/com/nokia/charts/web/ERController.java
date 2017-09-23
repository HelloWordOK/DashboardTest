package com.nokia.charts.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nokia.charts.dto.ERDto;
import com.nokia.charts.dto.ERSumDto;
import com.nokia.charts.dto.ExportERDto;
import com.nokia.charts.entity.EREntity;
import com.nokia.charts.entity.admin.Bundle;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.ERService;
import com.nokia.charts.util.JsonUtils;
import com.nokia.charts.web.base.EchartController;

/**
 * ER(Agile)
 * @author weijid
 *
 */
@Controller
@RequestMapping("/er")
public class ERController extends EchartController {

	@Autowired
	private ERService eRService;
	
	/**
	 * ER 查询界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String er(Model model) {

		List<Bundle> bundleList = new ArrayList<Bundle>();
		try {
			bundleList = eRService.getBundleListToSearch();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("bundleList", bundleList);
		return "/er/search";
	}

	@RequestMapping("/searchsprint")
	public String searchsprint(Model model, String bundleId) {

		List<String> sprintList = new ArrayList<String>();
		try {
			sprintList = eRService.getSprintListByBundleId(bundleId);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("sprintList", sprintList);
		return "/er/searchsprintlist";
	}

	/**
	 * 根据查询条件，生成ER图表
	 * @param model
	 * @param bundleId
	 * @param sprintStart
	 * @param sprintEnd
	 * @return
	 */
	@RequestMapping("/ersubmit")
	public String ersubmit(Model model, String bundleId, String sprintStart, String sprintEnd) {
		Map<String, Map<String, Object>> dataSource = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> chartER1 = null;
		Map<String, Object> chartER2 = null;
		Map<String, Object> chartER3 = null;
		ERSumDto statisticsERSumDto = null;
		List<ERDto> statisticsERList = new ArrayList<ERDto>();
		try {
			statisticsERSumDto = eRService.getSumERData(bundleId, sprintStart, sprintEnd);
			statisticsERList = eRService.getBeforeTenData(bundleId, sprintEnd);
			chartER1 = generateERChart1(chartER1, statisticsERList);
			chartER2 = generateERChart2(chartER2, statisticsERList);
			chartER3 = generateERChart3(chartER3, statisticsERList);
			dataSource.put("chartER1", chartER1);
			dataSource.put("chartER2", chartER2);
			dataSource.put("chartER3", chartER3);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("statisticsERSumDto", statisticsERSumDto);
		model.addAttribute("dataSource", JsonUtils.object2jackson(dataSource));
		return "/er/search_result";
	}

	/**
	 * Velocity Chart
	 * @param chartER1
	 * @param statisticsERDtoList
	 * @return
	 */
	private Map<String, Object> generateERChart1(Map<String, Object> chartER1, List<ERDto> statisticsERDtoList) {

		if(chartER1==null){
			chartER1 = new LinkedHashMap<String, Object>();
		}
		String title = "Velocity Chart";
		setEchartTitle(chartER1, title);
		String xName = "sprint";
		List<Object> xAxisList = new ArrayList<Object>();
		List<Integer> velocityList = new ArrayList<Integer>();
		List<Integer> actualVelocityList = new ArrayList<Integer>();
		List<Integer> velocityThresholdList = new ArrayList<Integer>();
		List<Integer> releaseNoteListIndex = new ArrayList<Integer>();
		List<String> releaseNoteList = new ArrayList<String>();
		int i=0;
		for (ERDto statisticsERDto : statisticsERDtoList) {
			xAxisList.add(statisticsERDto.getReleaseSprint());
			velocityList.add(statisticsERDto.getPlanVelocity());
			actualVelocityList.add(statisticsERDto.getActualVelocity());
			velocityThresholdList.add(statisticsERDto.getVelocityThreshold());
			releaseNoteList.add(statisticsERDto.getReleaseNote());
			releaseNoteListIndex.add(i);
			i++;
		}
		setEchartXAxis(chartER1, xName, xAxisList);
		String yName = "value(points)";
		setEchartYAxis(chartER1, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Planned Velocity");
		legendList.add("Actual Velocity");
		setEchartLegendPingData(chartER1, null, null, legendList);
		chartER1.put("velocityList", velocityList.toArray());
		chartER1.put("actualVelocityList", actualVelocityList.toArray());
		chartER1.put("releaseNoteListIndex", releaseNoteListIndex.toArray());
		chartER1.put("releaseNoteList", releaseNoteList.toArray());
		chartER1.put("velocityThresholdList", velocityThresholdList.toArray());
		return chartER1;
	}

	/**
	 * Capacity Chart
	 * @param chartER2
	 * @param statisticsERDtoList
	 * @return
	 */
	private Map<String, Object> generateERChart2(Map<String, Object> chartER2, List<ERDto> statisticsERDtoList) {
		if(chartER2==null){
			chartER2 = new LinkedHashMap<String, Object>();
		}
		String title = "Capacity Chart";
		setEchartTitle(chartER2, title);
		String xName = "sprint";
		List<Object> xAxisList = new ArrayList<Object>();
		List<Integer> capacityList = new ArrayList<Integer>();
		List<Integer> capacityThresholdList = new ArrayList<Integer>();
		for (ERDto statisticsERDto : statisticsERDtoList) {
			xAxisList.add(statisticsERDto.getReleaseSprint());
			capacityList.add(statisticsERDto.getPlanCapacity());
			capacityThresholdList.add(statisticsERDto.getCapacityThreshold());
		}
		setEchartXAxis(chartER2, xName, xAxisList);
		String yName = "value(man/day)";
		setEchartYAxis(chartER2, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Planned Capacity");
		setEchartLegendPingData(chartER2, null, null, legendList);
		chartER2.put("capacityList", capacityList.toArray());
		chartER2.put("capacityThresholdList", capacityThresholdList.toArray());
		return chartER2;
	}

	/**
	 * Prod. & Completion Rate Chart
	 * @param chartER3
	 * @param statisticsERDtoList
	 * @return
	 */
	private Map<String, Object> generateERChart3(Map<String, Object> chartER3, List<ERDto> statisticsERDtoList) {

		if(chartER3==null){
			chartER3 = new LinkedHashMap<String, Object>();
		}
		String title = "Prod. & Completion Rate";
		setEchartTitle(chartER3, title);
		String xName = "sprint";
		List<Object> xAxisList = new ArrayList<Object>();
		List<Double> productivityList = new ArrayList<Double>();
		List<Double> completionRateList = new ArrayList<Double>();
		for (ERDto statisticsDtoER : statisticsERDtoList) {
			xAxisList.add(statisticsDtoER.getReleaseSprint());
			productivityList.add(Double.valueOf(String.format("%.2f", statisticsDtoER.getProductivity())));
			completionRateList.add(Double.valueOf(String.format("%.2f", statisticsDtoER.getCompletionRate())));
		}
		setEchartXAxis(chartER3, xName, xAxisList);
		String yName = "value(%)";
		setEchartYAxis(chartER3, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Productivity");
		legendList.add("Completion Rate");
		setEchartLegendPingData(chartER3, null, null, legendList);
		chartER3.put("productivityList", productivityList.toArray());
		chartER3.put("completionRateList", completionRateList.toArray());
		return chartER3;
	}

	/**
	 * ER 数据导出初始化界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/export")
	public String erExport(Model model) {

		List<Bundle> bundleList = new ArrayList<Bundle>();
		try {
			bundleList = eRService.getBundleListToSearch();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("bundleList", bundleList);
		return "/er/export_search";
	}

	/**
	 * ER数据导出，数据处理
	 * @param model
	 * @param bundleId
	 * @param sprintStart
	 * @param sprintEnd
	 * @return
	 */
	@RequestMapping("/exportsubmit")
	public String exportSubmit(Model model, String bundleId, String sprintStart, String sprintEnd) {
		List<ExportERDto> statisticsExportERList = new ArrayList<ExportERDto>();
		try {
			statisticsExportERList = eRService.getExportERData(bundleId, sprintStart, sprintEnd);
			for (ExportERDto se : statisticsExportERList) {
				se.setProductivity(Float.valueOf(String.format("%.2f", se.getProductivity())));
				se.setCompletionRate(Float.valueOf(String.format("%.2f", se.getCompletionRate())));
			}
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("statisticsExportERList", statisticsExportERList);
		return "/er/export_search_result";
	}
	

	/**
	 * ER数据插入界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping("/input")
	public String insert(Model model) {
		List<Bundle> bundleList = new ArrayList<Bundle>();
		try {
			bundleList = eRService.getBundleListToInsert();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("bundleList", bundleList);
		return "/er/input";
	}

	/**
	 * ER数据插入，数据处理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/inputsubmit")
	@ResponseBody
	public String inputSubmit(Model model, String param) {
		try {
			EREntity sER = JsonUtils.json2object(param, EREntity.class);
			// Check release sprint status
			Integer result = eRService.checkReleaseSprint(sER);

			if (result.intValue() == 0) {
				// Insert
				eRService.insertSubmitData(sER);
				return "1";
			} else{
				// Update
				eRService.updateSubmitData(sER);
				return "2";
			}
		} catch (MonitorException e) {
			e.printStackTrace();
			return "0";
		}
	}
}
