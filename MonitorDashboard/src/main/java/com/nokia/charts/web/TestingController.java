package com.nokia.charts.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nokia.charts.constant.SystemConstant.ChartType;
import com.nokia.charts.dto.BaselineDto;
import com.nokia.charts.dto.echarts.EchartLegend;
import com.nokia.charts.entity.testing.FunctionalEntity;
import com.nokia.charts.entity.testing.FunctionalExport;
import com.nokia.charts.entity.testing.FunctionalFailLog;
import com.nokia.charts.entity.testing.FunctionalModule;
import com.nokia.charts.entity.testing.FunctionalSummary;
import com.nokia.charts.entity.testing.Performance;
import com.nokia.charts.entity.testing.PerformanceBaseLine;
import com.nokia.charts.entity.testing.PerformanceExport;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.TestingService;
import com.nokia.charts.util.JsonUtils;
import com.nokia.charts.util.PDFUtils;
import com.nokia.charts.web.base.EchartController;

/**
 * 测试展示界面
 * @author weijid
 *
 */
@Controller
@RequestMapping("/testing")
public class TestingController extends EchartController {

	@Autowired
	private TestingService testingService;
	
	/**
	 * performance 测试查询界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping({ "", "/performance" })
	public String performanceTesting(Model model) {
		List<String> projectList = new ArrayList<String>();
		try {
			projectList = testingService.selectProjectList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("projectList", projectList);
		return "testing/performance_search";
	}

	@RequestMapping("/ajaxfunctionType")
	public String functionTypeByProject(String project,String type,String environment, Model model) {
		List<String> functionTypeList = new ArrayList<String>();
		try {
			if ("1".equals(type)) {
				functionTypeList = testingService.selectFunctionTypeByProjectAndEnvironment(project, environment);
				model.addAttribute("ctype", type);
			} else {
				functionTypeList = testingService.selectVersionByProjectAndEnvironment(project , environment);
				model.addAttribute("ctype", type);
			}
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("functionTypeList", functionTypeList);
		return "testing/performance_function_type";
	}

	/**
	 * performance 查询结果处理，图表展示
	 * @param project
	 * @param environment
	 * @param functionType
	 * @param model
	 * @return
	 */
	@RequestMapping("/performancesubmit")
	public String performanceTestingSubmit(String project, String environment, String functionType, Model model) {

		Map<String, Map<String, Object>> dataSource = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> averageResponseMap = null;
		Map<String, Object> throughputMap = null;
		Map<String, Object> errorMap = null;
		Map<String, Object> resultTable = new LinkedHashMap<String, Object>();

		List<Performance> performanceList = new ArrayList<Performance>();
		try {
			performanceList = testingService.selectPerformanceList(project, environment, functionType);
			PerformanceBaseLine baseLine = testingService.selectPerformanceBaseLine(project, environment, functionType);
			if(!performanceList.isEmpty()){
				averageResponseMap = generateAverageResponseMap(averageResponseMap, performanceList, baseLine);
				throughputMap = generateThroughputMap(throughputMap, performanceList, baseLine);
				errorMap = generateErrorMap(errorMap,performanceList,baseLine);
				Performance per = new Performance();
				per.setProject(project);
				per.setEnvironment(environment);
				per.setFunctionType(functionType);
				resultTable.put("per", per);
				resultTable.put("performanceList", performanceList);
				resultTable.put("perBaseline", baseLine);
			}
			dataSource.put(ChartType.CHART1, averageResponseMap);
			dataSource.put(ChartType.CHART2, throughputMap);
			dataSource.put(ChartType.CHART3, errorMap);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("dataSource", JsonUtils.object2jackson(dataSource));
		model.addAttribute("resultTable", resultTable);
		return "testing/performance_result";
	}

	private Map<String, Object> generateErrorMap(Map<String, Object> errorMap, List<Performance> performanceList,
			PerformanceBaseLine baseLine) {
		if (baseLine == null) {
			baseLine = new PerformanceBaseLine();
		}
		if (errorMap == null) {
			errorMap = new LinkedHashMap<String, Object>();
		}
		String xName = "version";
		String title = "Error(%)";
		setEchartTitle(errorMap, title);
		List<Double> errorList = new ArrayList<Double>();
		List<Object> xAxisList = new ArrayList<Object>();
		for (Performance per : performanceList) {
			errorList.add(Double.valueOf(per.getError()));
			xAxisList.add(per.getVersion());
		}
		setEchartXAxis(errorMap, xName, xAxisList);

		List<String> legendList = new ArrayList<String>();
		legendList.add("error(%)");
		EchartLegend echartLegend = new EchartLegend();
		echartLegend.setData(legendList.toArray());
		Double yMax = null;
		if (baseLine.getErrorBaseline() != null) {
			Float throughputAveragebaseline = generateStringToFloat(baseLine.getErrorBaseline());
			echartLegend.setAverageThreshold(throughputAveragebaseline);
			yMax = 100.0;
		}
		String yName = "";
		setEchartYAxis(errorMap, yName, yMax);
		errorMap.put("legend", echartLegend);
		errorMap.put("errorData", errorList.toArray());
		return errorMap;
	}

	private Map<String, Object> generateThroughputMap(Map<String, Object> throughputMap,
			List<Performance> performanceList, PerformanceBaseLine baseLine) {
		if (baseLine == null) {
			baseLine = new PerformanceBaseLine();
		}
		if (throughputMap == null) {
			throughputMap = new LinkedHashMap<String, Object>();
		}
		String xName = "version";
		String title = "Throughput";
		setEchartTitle(throughputMap, title);
		List<Double> throughputList = new ArrayList<Double>();
		List<Object> xAxisList = new ArrayList<Object>();
		for (Performance per : performanceList) {
			throughputList.add(Double.valueOf(per.getThroughput()));
			xAxisList.add(per.getVersion());
		}
		setEchartXAxis(throughputMap, xName, xAxisList);

		List<String> legendList = new ArrayList<String>();
		legendList.add("throughput");
		EchartLegend echartLegend = new EchartLegend();
		echartLegend.setData(legendList.toArray());
		Float yMax = null;
		if (baseLine.getThroughputBaseline() != null && baseLine.getThroughputRange() != null) {
			Float throughputAveragebaseline = generateStringToFloat(baseLine.getThroughputBaseline());
			Float throughputBaselineRange = generateStringToFloat(baseLine.getThroughputRange());
			echartLegend.setAverageThreshold(throughputAveragebaseline);
			Float throughputUpperbaseline = throughputAveragebaseline + throughputBaselineRange;
			echartLegend.setUpperControlThreshold(throughputUpperbaseline);
			Float throughputLowerbaseline = throughputAveragebaseline - throughputBaselineRange;
			echartLegend.setLowerControlThreshold(throughputLowerbaseline);
			yMax = throughputUpperbaseline + 10;
		}
		String yName = "";
		setEchartYAxis(throughputMap, yName, yMax);
		throughputMap.put("legend", echartLegend);
		throughputMap.put("throughputData", throughputList.toArray());
		return throughputMap;
	}

	private Map<String, Object> generateAverageResponseMap(Map<String, Object> averageResponseMap,
			List<Performance> performanceList, PerformanceBaseLine baseLine) {
		if (baseLine == null) {
			baseLine = new PerformanceBaseLine();
		}
		if (averageResponseMap == null) {
			averageResponseMap = new LinkedHashMap<String, Object>();
		}
		String xName = "version";
		String title = "Average Response Time";
		setEchartTitle(averageResponseMap, title);
		List<Integer> averageList = new ArrayList<Integer>();
		List<Object> xAxisList = new ArrayList<Object>();
		for (Performance per : performanceList) {
			averageList.add(Integer.valueOf(per.getAverage()));
			xAxisList.add(per.getVersion());
		}
		setEchartXAxis(averageResponseMap, xName, xAxisList);
		List<String> legendList = new ArrayList<String>();
		legendList.add("average response time");
		EchartLegend echartLegend = new EchartLegend();
		echartLegend.setData(legendList.toArray());
		Integer yMax = null;
		if (baseLine.getRtBaseline() != null && baseLine.getRtRange() != null) {
			Integer rtAveragebaseline = generateStringToInt(baseLine.getRtBaseline());
			Integer rtBaselineRange = generateStringToInt(baseLine.getRtRange());
			echartLegend.setAverageThreshold(rtAveragebaseline);
			Integer rtUpperbaseline = rtAveragebaseline + rtBaselineRange;
			echartLegend.setUpperControlThreshold(rtUpperbaseline);
			Integer rtLowerbaseline = rtAveragebaseline - rtBaselineRange;
			echartLegend.setLowerControlThreshold(rtLowerbaseline);
			yMax = rtUpperbaseline + 2000;
		}
		String yName = "";
		setEchartYAxis(averageResponseMap, yName, yMax);
		averageResponseMap.put("legend", echartLegend);
		averageResponseMap.put("averageData", averageList.toArray());
		return averageResponseMap;
	}

	private Integer generateStringToInt(String data) {
		return Integer.valueOf(data);
	}

	private Float generateStringToFloat(String data) {
		return Float.valueOf(data);
	}

	/**
	 * functional 查询界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping("/functional")
	public String functionalTesting(Model model) {
		List<String> projectList = new ArrayList<String>();
		try {
			projectList = testingService.selectFunctionTestProject();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("projectList", projectList);
		return "testing/functional_search";
	}

	@RequestMapping("/ajaxfunctiontestversion")
	public String functionalVersionByProject(String project, Model model) {
		List<String> testVersionList = new ArrayList<String>();
		try {
			testVersionList = testingService.selectFunctionTestVersion(project);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("testVersionList", testVersionList);
		return "testing/functional_version";
	}

	@RequestMapping("/ajaxfunctiontestcycle")
	public String functionalCycleByVersion(String project,String testVersion, String environment,Model model) {
		List<String> testCycleList = new ArrayList<String>();
		try {
			testCycleList = testingService.selectFunctionTestCycle(project,testVersion,environment);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("testCycleList", testCycleList);
		return "testing/functional_cycle";
	}

	/**
	 * functional 查询结果处理，图表展示
	 * @param project
	 * @param environment
	 * @param testVersion
	 * @param testCycle
	 * @param model
	 * @return
	 */
	@RequestMapping("/functionalsubmit")
	public String functionalTestingSubmit(String project, String environment, String testVersion, String testCycle,
			Model model) {
		Map<String, Map<String, Object>> dataSource = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> failTestCaseMap = null;
		Map<String, Object> resultTable = new LinkedHashMap<String, Object>();
		List<FunctionalSummary> summaryList = new ArrayList<FunctionalSummary>();
		List<FunctionalModule> moduleList = new ArrayList<FunctionalModule>();
		List<FunctionalFailLog> faillogList = new ArrayList<FunctionalFailLog>();
		try {
			summaryList = testingService.selectFunctionalSummary(project, environment, testVersion, testCycle);
			moduleList = testingService.selectFunctionalModule(project, environment, testVersion, testCycle);
			faillogList = testingService.selectFunctionalFailLog(project, environment, testVersion, testCycle);
			resultTable.put("summaryList", summaryList);
			resultTable.put("moduleList", moduleList);
			resultTable.put("faillogList", faillogList);
			failTestCaseMap = generateFailTestCaseMap(failTestCaseMap, moduleList);
			FunctionalEntity fun = new FunctionalEntity();
			fun.setProject(project);
			fun.setEnvironment(environment);
			fun.setVersion(testVersion);
			fun.setCycle(testCycle);
			resultTable.put("fun", fun);
		} catch (MonitorException e) {
			logger.error(e);
		}
		dataSource.put(ChartType.CHART1, failTestCaseMap);
		model.addAttribute("resultTable", resultTable);
		model.addAttribute("dataSource", JsonUtils.object2jackson(dataSource));
		return "testing/functional_result";
	}

	/**
	 * Module Fail/Error/Skip Case
	 * @param failTestCaseMap
	 * @param moduleList
	 * @return
	 */
	private Map<String, Object> generateFailTestCaseMap(Map<String, Object> failTestCaseMap,
			List<FunctionalModule> moduleList) {
		if (failTestCaseMap == null) {
			failTestCaseMap = new LinkedHashMap<String, Object>();
		}
		String xName = "module";
		String title = "Module Fail/Error/Skip Case";
		setEchartTitle(failTestCaseMap, title);
		List<Integer> fail = new ArrayList<Integer>();
		List<Integer> error = new ArrayList<Integer>();
		List<Integer> skip = new ArrayList<Integer>();
		List<Object> xAxisList = new ArrayList<Object>();
		for (FunctionalModule module : moduleList) {
			fail.add(Integer.valueOf(module.getFail()));
			error.add(Integer.valueOf(module.getError()));
			skip.add(Integer.valueOf(module.getSkip()));
			xAxisList.add(module.getModule());
		}
		setEchartXAxis(failTestCaseMap, xName, xAxisList);
		String yName = "";
		setEchartYAxis(failTestCaseMap, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Fail");
		legendList.add("Error");
		legendList.add("Skip");
		setEchartLegendData(failTestCaseMap, legendList);
		failTestCaseMap.put("fail", fail.toArray());
		failTestCaseMap.put("error", error.toArray());
		failTestCaseMap.put("skip", skip.toArray());
		return failTestCaseMap;
	}
	
	@RequestMapping("/exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, FunctionalExport function) {
		String project; String environment; String testVersion; String testCycle; String picBase64Info;
		project = function.getProject1();
		environment = function.getEnvironment1();
		testVersion = function.getTestVersion1();
		testCycle = function.getTestCycle1();
		picBase64Info = function.getImg();
		System.out.println(picBase64Info);

		StringBuffer fileName = new StringBuffer();
		// Generate PDF file name
		fileName = fileName.append(project).append(" ").append(environment).append(" ").append(testVersion).append(" ").append(testCycle);

		Map<String, Object> resultTable = new LinkedHashMap<String, Object>();
		List<FunctionalSummary> summaryList = new ArrayList<FunctionalSummary>();
		List<FunctionalModule> moduleList = new ArrayList<FunctionalModule>();
		List<FunctionalFailLog> faillogList = new ArrayList<FunctionalFailLog>();
		try {
			summaryList = testingService.selectFunctionalSummary(project, environment, testVersion, testCycle);
			moduleList = testingService.selectFunctionalModule(project, environment, testVersion, testCycle);
			faillogList = testingService.selectFunctionalFailLog(project, environment, testVersion, testCycle);
			resultTable.put("summaryList", summaryList);
			resultTable.put("moduleList", moduleList);
			resultTable.put("faillogList", faillogList);
			resultTable.put("picBase64Info", picBase64Info);

			OutputStream os = response.getOutputStream();

			new PDFUtils().createPdf(os, fileName.toString(), resultTable);
			os.flush();
			os.close();
			// Set response information
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.toString() + ".pdf"));
		} catch (MonitorException e) {
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	@RequestMapping("/baseline")
	public String performanceBaseline(Model model) {
		return "testing/performance_baseline";
	}

	@RequestMapping("/baselinesubmit")
	@ResponseBody
	public String performanceBaselineSubmit(Model model, String param) {
		BaselineDto baselineDto = JsonUtils.json2object(param, BaselineDto.class);
		String stutas = "1";
		try {
			testingService.addBaseline(baselineDto);
		} catch (MonitorException e) {
			logger.error(e);
			stutas = "-1";
		}
		return stutas;
	}
	
	@RequestMapping("/performancebyversion" )
	public String performanceTestingSearchByVersion(Model model) {
		List<String> projectList = new ArrayList<String>();
		try {
			projectList = testingService.selectProjectList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("projectList", projectList);
		return "testing/performance_search_version";
	}

	/**
	 * performance 查询结果处理，图表展示
	 * @param project
	 * @param environment
	 * @param version
	 * @param model
	 * @return
	 */
	@RequestMapping("/performanceVersion")
	public String performanceTestingSearch(String project, String environment, String version, Model model) {

		Map<String, Map<String, Object>> dataSource = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> allTypeResponseMap = null;
		Map<String, Object> throughputMap = null;
		Map<String, Object> resultTable = new LinkedHashMap<String, Object>();
		List<Object> xAxisList = new ArrayList<Object>();

		List<Performance> performanceList = new ArrayList<Performance>();
		List<PerformanceBaseLine> baseLineList = new ArrayList<PerformanceBaseLine>();
		try {
			performanceList = testingService.selectPerformanceListByVersion(project, environment, version);
			baseLineList = testingService.selectPerformanceBaseLineList(project, environment);
			allTypeResponseMap = generateAllResponseMap(allTypeResponseMap, performanceList, xAxisList);
			throughputMap = generateAllThroughputMap(throughputMap, performanceList);
			Performance per = new Performance();
			per.setProject(project);
			per.setEnvironment(environment);
			per.setVersion(version);
			resultTable.put("per", per);
			resultTable.put("performanceList", performanceList);
			resultTable.put("baseLineList", baseLineList);
			dataSource.put(ChartType.CHART1, allTypeResponseMap);
			dataSource.put(ChartType.CHART2, throughputMap);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("dataSource", JsonUtils.object2jackson(dataSource));
		model.addAttribute("resultTable", resultTable);
		return "testing/performance_result_version";
	}

	private Map<String, Object> generateAllResponseMap(Map<String, Object> performanceMap,
			List<Performance> performanceList, List<Object> xAxisList) {
		if (performanceMap == null) {
			performanceMap = new LinkedHashMap<String, Object>();
		}
		String xName = "Function Type";
		String title = "Test scenario Summary Report";
		setEchartTitle(performanceMap, title);
		List<String> averageList = new ArrayList<String>();
		List<String> medianList = new ArrayList<String>();
		List<String> ninetyPerList = new ArrayList<String>();
		List<String> minList = new ArrayList<String>();
		List<String> maxList = new ArrayList<String>();
		for (Performance per : performanceList) {
			averageList.add(per.getAverage());
			medianList.add(per.getMedian());
			ninetyPerList.add(per.getNinetyPecLine());
			minList.add(per.getMin());
			maxList.add(per.getMax());
			xAxisList.add(per.getFunctionType());
		}
		setEchartXAxis(performanceMap, xName, xAxisList);
		String yName = "Response Time(ms)";
		setEchartYAxis(performanceMap, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Average");
		legendList.add("Median");
		legendList.add("90% line");
		legendList.add("Min");
		legendList.add("Max");
		setEchartLegendData(performanceMap, legendList);
		performanceMap.put("average", averageList.toArray());
		performanceMap.put("median", medianList.toArray());
		performanceMap.put("ninetyline", ninetyPerList.toArray());
		performanceMap.put("min", minList.toArray());
		performanceMap.put("max", maxList.toArray());
		return performanceMap;
	}
	
	private Map<String, Object> generateAllThroughputMap(Map<String, Object> throughputMap,
			List<Performance> performanceList) {
		if (throughputMap == null) {
			throughputMap = new LinkedHashMap<String, Object>();
		}
		String xName = "Function Type";
		String title = "The throughput of each function";
		setEchartTitle(throughputMap, title);
		List<Double> throughputList = new ArrayList<Double>();
		List<Object> xAxisList = new ArrayList<Object>();
		for (Performance per : performanceList) {
			throughputList.add(Double.valueOf(per.getThroughput()));
			xAxisList.add(per.getFunctionType());
		}
		setEchartXAxis(throughputMap, xName, xAxisList);

		List<String> legendList = new ArrayList<String>();
		legendList.add("throughput");
		EchartLegend echartLegend = new EchartLegend();
		echartLegend.setData(legendList.toArray());
		String yName = "Time (min)";
		setEchartYAxis(throughputMap, yName, null);
		throughputMap.put("legend", echartLegend);
		throughputMap.put("throughputData", throughputList.toArray());
		return throughputMap;
	}


	@RequestMapping("/exportPerformancePDF")
	public void exportPerformancePDF(HttpServletRequest request, HttpServletResponse response, PerformanceExport pExport) {
		String project; String environment; String testVersion;
		project = pExport.getProject1();
		environment = pExport.getEnvironment1();
		testVersion = pExport.getTestVersion1();

		StringBuffer fileName = new StringBuffer();
		// Generate PDF file name
		fileName = fileName.append(pExport.getProject1()).append(" ").append(pExport.getEnvironment1());

		Map<String, Object> resultTable = new LinkedHashMap<String, Object>();
		List<Performance> performanceList = new ArrayList<Performance>();
		List<PerformanceBaseLine> baseLineList = new ArrayList<PerformanceBaseLine>();
		
		try {
			performanceList = testingService.selectPerformanceListByVersion(project, environment, testVersion);
			baseLineList = testingService.selectPerformanceBaseLineList(project, environment);
			resultTable.put("performanceList", performanceList);
			resultTable.put("baseLineList", baseLineList);
			resultTable.put("pExport", pExport);

			OutputStream os = response.getOutputStream();
			new PDFUtils().createPerformancePdf(os, fileName.toString(), resultTable);
			os.flush();
			os.close();
			// Set response information
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.toString() + ".pdf"));
		} catch (MonitorException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
