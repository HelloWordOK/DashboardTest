package com.nokia.charts.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.nokia.charts.constant.SystemConstant.ChartType;
import com.nokia.charts.constant.SystemConstant.QAServicePeiordType;
import com.nokia.charts.constant.SystemConstant.QASourceType;
import com.nokia.charts.dto.qa.IncidentExportResultDto;
import com.nokia.charts.dto.qa.ProblemExportResultDto;
import com.nokia.charts.dto.qa.QAResultDataDto;
import com.nokia.charts.dto.qa.SRExportResultDto;
import com.nokia.charts.dto.qa.data.ServiceDataSearchDto;
import com.nokia.charts.entity.admin.Domain;
import com.nokia.charts.entity.admin.ServiceEntity;
import com.nokia.charts.entity.qa.IncidentEntity;
import com.nokia.charts.entity.qa.IncidentEnum;
import com.nokia.charts.entity.qa.IncidentImport;
import com.nokia.charts.entity.qa.ProblemEntity;
import com.nokia.charts.entity.qa.ProblemEnum;
import com.nokia.charts.entity.qa.ProblemImport;
import com.nokia.charts.entity.qa.SREntity;
import com.nokia.charts.entity.qa.SREnum;
import com.nokia.charts.entity.qa.SRImport;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.QAService;
import com.nokia.charts.util.DateUtils;
import com.nokia.charts.util.ExcelExportUtils;
import com.nokia.charts.util.JsonUtils;
import com.nokia.charts.web.base.EchartController;

/**
 * QA Service
 * @author weijid
 *
 */
@Controller
@RequestMapping("/qa")
public class QAController extends EchartController {

	@Autowired
	private QAService qaService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String qaIndex(Model model) {
		return "redirect:qa/service";
	}

	/**
	 * QA domain,service 查询界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/service","/domain"})
	public String qaService(Model model,boolean isDomain) {
		List<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();
		List<Domain> domainList = new ArrayList<Domain>();
		try {
			if(isDomain){
				domainList = qaService.getQADomainList();
			}else{
				serviceList = qaService.getQAServiceList();
			}
		} catch (MonitorException e) {
			logger.error(e);
		}
		if(isDomain){
			model.addAttribute("domainList", domainList);
			return "qa/domain/qa_domain_search";
		}else{
			model.addAttribute("serviceList", serviceList);
			return "qa/service/qa_service_search";
		}
	}

	/**
	 * QA domain,service 查询结果处理，图表展示
	 * @param model
	 * @param serviceCode
	 * @param periodType
	 * @return
	 */
	@RequestMapping(value = {"/qa_service_search","/qa_domain_search"})
	public String qaServiceSearch(Model model, String param, String periodType,boolean isDomain) {
		Map<String, Map<String, Object>> dataSource = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> incidentMap = null;
		Map<String, Object> incidentSLAMap = null;
		Map<String, Object> sRMap = null;
		Map<String, Object> sRSLAMap = null;
		Map<String, Object> problemMap = null;
		Map<String, Object> problemSLAMap = null;
		Map<String, Object> incidentTicketStatusMap = null;

		Date date = new Date();
		date = DateUtils.dateChange(date, Calendar.MONTH, +1);
		List<Object> xMonthList = new ArrayList<Object>();
		List<Object> monthList = new ArrayList<Object>();
		List<Object> weekList = new ArrayList<Object>();
		List<QAResultDataDto> incidentList = new ArrayList<QAResultDataDto>();
		List<QAResultDataDto> problemList = new ArrayList<QAResultDataDto>();
		List<QAResultDataDto> srList = new ArrayList<QAResultDataDto>();
		Map<Object, List<String>> weekMap = new LinkedHashMap<Object, List<String>>();
		try {
			for (int i = 12; i >= 0; i--) {
				if (periodType.equals(QAServicePeiordType.MONTHLY)) {
					String month = DateUtils.date2String(DateUtils.dateChange(date, Calendar.MONTH, -i),
							DateUtils.DATE_FROMAT_YYYY_MM);
					if (i >= 1) {
						xMonthList.add(month);
					}
					monthList.add(month);
				} else if (periodType.equals(QAServicePeiordType.WEEKLY)) {
					if (i == 12) {
						date = DateUtils.dateChange(date, Calendar.MONTH, -1);
						continue;
					}
					List<String> weekDayList = new ArrayList<String>();
					Date curDay = DateUtils.dateChange(date, Calendar.WEEK_OF_YEAR, -i);
					int week = DateUtils.getWeek(curDay, DateUtils.FIRST_DAY_EN);
					weekList.add("W" + week);
					weekDayList = DateUtils.getWeekDaysByCurrDate(curDay, DateUtils.DATE_FROMAT_YYYY_MM_dd,
							DateUtils.FIRST_DAY_EN);
					weekMap.put(week, weekDayList);
				}
			}
			List<String> groupList = new ArrayList<String>();
			String title = null;
			if(isDomain){
				groupList = qaService.getQAGroupNameByDomain(param);
				Domain domainDto = qaService.getQADomainById(param);
				title = domainDto.getDomainName();
			}else{
				groupList = qaService.getQAGroupNameByServiceCode(param);
				ServiceEntity serviceDto = qaService.getQAServiceByCode(param);
				title = serviceDto.getServiceName(); 
			}
			if(groupList!=null&&groupList.size()>0){
				if (periodType.equals(QAServicePeiordType.MONTHLY)) {
					incidentList = qaService.getIncidentMonthlyList(monthList, groupList);
					srList = qaService.getSRMonthlyList(monthList, groupList);
					problemList = qaService.getProblemMonthlyList(monthList, groupList);
				} else if (periodType.equals(QAServicePeiordType.WEEKLY)) {
					incidentList = qaService.getIncidentWeeklyList(weekMap, groupList);
					srList = qaService.getSRWeeklyList(weekMap, groupList);
					problemList = qaService.getProblemWeeklyList(weekMap, groupList);
				}
			}
			if (periodType.equals(QAServicePeiordType.MONTHLY)) {
				incidentMap = generateIncidentMap(incidentMap, incidentList, xMonthList, title);
				incidentSLAMap = generateIncidentSLAMap(incidentSLAMap, incidentList, xMonthList, title);
				sRMap = generateSRMap(sRMap, srList, xMonthList, title);
				problemMap = generateProblemMap(problemMap, problemList, xMonthList, title);
				problemSLAMap = generateProblemSLAMap(problemSLAMap, problemList, xMonthList, title);
				incidentTicketStatusMap = generateIncidentTicketStatus(incidentTicketStatusMap, incidentList, title);
			} else if (periodType.equals(QAServicePeiordType.WEEKLY)) {
				incidentMap = generateIncidentMap(incidentMap, incidentList, weekList, title);
				incidentSLAMap = generateIncidentSLAMap(incidentSLAMap, incidentList, weekList, title);
				sRMap = generateSRMap(sRMap, srList, weekList, title);
				problemMap = generateProblemMap(problemMap, problemList, weekList, title);
				problemSLAMap = generateProblemSLAMap(problemSLAMap, problemList, weekList, title);
				incidentTicketStatusMap = generateIncidentTicketStatus(incidentTicketStatusMap, incidentList, title);
			}
		} catch (MonitorException e) {
			logger.error(e);
		}
		dataSource.put(ChartType.CHART1, incidentMap);
		dataSource.put(ChartType.CHART2, incidentSLAMap);
		dataSource.put(ChartType.CHART3, sRMap);
		dataSource.put(ChartType.CHART4, sRSLAMap);
		dataSource.put(ChartType.CHART5, problemMap);
		dataSource.put(ChartType.CHART6, problemSLAMap);
		dataSource.put(ChartType.CHART7, incidentTicketStatusMap);
		model.addAttribute("dataSource", JsonUtils.object2jackson(dataSource));
		if(isDomain){
			return "qa/domain/qa_domain_result";
		}else{
			return "qa/service/qa_service_result";
		}
	}

	/**
	 * Incident Created/Closed/Backlog
	 * @param incidentMap
	 * @param incidentList
	 * @param xAxisList
	 * @param title
	 * @return
	 */
	private Map<String, Object> generateIncidentMap(Map<String, Object> incidentMap,
			List<QAResultDataDto> incidentList, List<Object> xAxisList, String title) {
		if (incidentMap == null) {
			incidentMap = new LinkedHashMap<String, Object>();
		}
		String xName = "";
		title = title + " Incident Created/Closed/Backlog";
		setEchartTitle(incidentMap, title);
		List<Integer> createdList = new ArrayList<Integer>();
		List<Integer> closedList = new ArrayList<Integer>();
		List<Integer> backlogList = new ArrayList<Integer>();
		List<Float> slaList = new ArrayList<Float>();
		for (QAResultDataDto incident : incidentList) {
			if (incident.equals(incidentList.get(12))) {
				continue;
			}
			createdList.add(incident.getCreated());
			closedList.add(incident.getClosed());
			backlogList.add(incident.getBacklog());
			slaList.add(incident.getSla());
		}
		setEchartXAxis(incidentMap, xName, xAxisList);
		String yName = "";
		setEchartYAxis(incidentMap, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Created");
		legendList.add("Closed");
		legendList.add("Backlog");
		legendList.add("% SLA");
		setEchartLegendData(incidentMap, legendList);
		incidentMap.put("created", createdList.toArray());
		incidentMap.put("closed", closedList.toArray());
		incidentMap.put("backlog", backlogList.toArray());
		incidentMap.put("sla", slaList.toArray());
		return incidentMap;
	}

	/**
	 * Incident Resolution Time SLA
	 * @param incidentMap
	 * @param incidentList
	 * @param xAxisList
	 * @param titleBean
	 * @return
	 */
	private Map<String, Object> generateIncidentSLAMap(Map<String, Object> incidentMap,
			List<QAResultDataDto> incidentList, List<Object> xAxisList, String title) {
		if (incidentMap == null) {
			incidentMap = new LinkedHashMap<String, Object>();
		}
		String xName = "";
		title = title + " Incident Resolution Time SLA";
		setEchartTitle(incidentMap, title);
		List<Float> slaP1List = new ArrayList<Float>();
		List<Float> slaP2List = new ArrayList<Float>();
		List<Float> slaP3List = new ArrayList<Float>();
		List<Float> slaP4List = new ArrayList<Float>();
		for (QAResultDataDto incident : incidentList) {
			if (incident.equals(incidentList.get(12))) {
				continue;
			}
			slaP1List.add(incident.getSlaP1());
			slaP2List.add(incident.getSlaP2());
			slaP3List.add(incident.getSlaP3());
			slaP4List.add(incident.getSlaP4());
		}
		setEchartXAxis(incidentMap, xName, xAxisList);
		String yName = "% SLA";
		setEchartYAxis(incidentMap, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("% SLA P1");
		legendList.add("% SLA P2");
		legendList.add("% SLA P3");
		legendList.add("% SLA P4");
		setEchartLegendData(incidentMap, legendList);
		incidentMap.put("slap1", slaP1List.toArray());
		incidentMap.put("slap2", slaP2List.toArray());
		incidentMap.put("slap3", slaP3List.toArray());
		incidentMap.put("slap4", slaP4List.toArray());
		return incidentMap;
	}

	/**
	 * Service Request Created/Closed/Backlog
	 * @param sRMap
	 * @param sRList
	 * @param xAxisList
	 * @param titleBean
	 * @return
	 */
	private Map<String, Object> generateSRMap(Map<String, Object> sRMap, List<QAResultDataDto> sRList,
			List<Object> xAxisList, String title) {
		if (sRMap == null) {
			sRMap = new LinkedHashMap<String, Object>();
		}
		String xName = "";
		title = title + " Service Request Created/Closed/Backlog";
		setEchartTitle(sRMap, title);
		List<Integer> createdList = new ArrayList<Integer>();
		List<Integer> closedList = new ArrayList<Integer>();
		List<Integer> backlogList = new ArrayList<Integer>();
		List<Float> slaList = new ArrayList<Float>();
		for (QAResultDataDto sr : sRList) {
			createdList.add(sr.getCreated());
			closedList.add(sr.getClosed());
			backlogList.add(sr.getBacklog());
			slaList.add(sr.getSla());
		}
		setEchartXAxis(sRMap, xName, xAxisList);
		String yName = "";
		setEchartYAxis(sRMap, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Created");
		legendList.add("Closed");
		legendList.add("Backlog");
		legendList.add("% SLA");
		setEchartLegendData(sRMap, legendList);
		sRMap.put("created", createdList.toArray());
		sRMap.put("closed", closedList.toArray());
		sRMap.put("backlog", backlogList.toArray());
		sRMap.put("sla", slaList.toArray());
		return sRMap;
	}

	/**
	 * Problem Created/Closed/Backlog
	 * @param problemMap
	 * @param problemList
	 * @param xAxisList
	 * @param titleBean
	 * @return
	 */
	private Map<String, Object> generateProblemMap(Map<String, Object> problemMap, List<QAResultDataDto> problemList,
			List<Object> xAxisList, String title) {
		if (problemMap == null) {
			problemMap = new LinkedHashMap<String, Object>();
		}
		String xName = "";
		title = title + " Problem Created/Closed/Backlog";
		setEchartTitle(problemMap, title);
		List<Integer> createdList = new ArrayList<Integer>();
		List<Integer> closedList = new ArrayList<Integer>();
		List<Integer> backlogList = new ArrayList<Integer>();
		List<Float> slaList = new ArrayList<Float>();
		for (QAResultDataDto problem : problemList) {
			createdList.add(problem.getCreated());
			closedList.add(problem.getClosed());
			backlogList.add(problem.getBacklog());
			slaList.add(problem.getSla());
		}
		setEchartXAxis(problemMap, xName, xAxisList);
		String yName = "";
		setEchartYAxis(problemMap, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("Created");
		legendList.add("Closed");
		legendList.add("Backlog");
		legendList.add("% SLA");
		setEchartLegendData(problemMap, legendList);
		problemMap.put("created", createdList.toArray());
		problemMap.put("closed", closedList.toArray());
		problemMap.put("backlog", backlogList.toArray());
		problemMap.put("sla", slaList.toArray());
		return problemMap;
	}

	/**
	 * Problem Resolution Time SLA
	 * @param problemMap
	 * @param problemList
	 * @param xAxisList
	 * @param titleBean
	 * @return
	 */
	private Map<String, Object> generateProblemSLAMap(Map<String, Object> problemMap,
			List<QAResultDataDto> problemList, List<Object> xAxisList, String title) {
		if (problemMap == null) {
			problemMap = new LinkedHashMap<String, Object>();
		}
		String xName = "";
		title = title + " Problem Resolution Time SLA";
		setEchartTitle(problemMap, title);
		List<Float> slaP1List = new ArrayList<Float>();
		List<Float> slaP2List = new ArrayList<Float>();
		List<Float> slaP3List = new ArrayList<Float>();
		List<Float> slaP4List = new ArrayList<Float>();
		for (QAResultDataDto problem : problemList) {
			slaP1List.add(problem.getSlaP1());
			slaP2List.add(problem.getSlaP2());
			slaP3List.add(problem.getSlaP3());
			slaP4List.add(problem.getSlaP4());
		}
		setEchartXAxis(problemMap, xName, xAxisList);
		String yName = "% SLA";
		setEchartYAxis(problemMap, yName, null);

		List<String> legendList = new ArrayList<String>();
		legendList.add("% SLA P1");
		legendList.add("% SLA P2");
		legendList.add("% SLA P3");
		legendList.add("% SLA P4");
		setEchartLegendData(problemMap, legendList);
		problemMap.put("slap1", slaP1List.toArray());
		problemMap.put("slap2", slaP2List.toArray());
		problemMap.put("slap3", slaP3List.toArray());
		problemMap.put("slap4", slaP4List.toArray());
		return problemMap;
	}

	/**
	 * Incident Status
	 * @param incidentTicketStatusMap
	 * @param incidentList
	 * @param titleBean
	 * @return
	 */
	private Map<String, Object> generateIncidentTicketStatus(Map<String, Object> incidentTicketStatusMap,
			List<QAResultDataDto> incidentList, String title) {
		if (incidentTicketStatusMap == null) {
			incidentTicketStatusMap = new LinkedHashMap<String, Object>();
		}
		title = title + " Incident Status";
		setEchartTitle(incidentTicketStatusMap, title);
		List<String> legendList = new ArrayList<String>();
		legendList.add("Resolved");
		legendList.add("Pending");
		legendList.add("Work in progress");
		legendList.add("Owner Assigned");
		int resolved = 0, pending = 0, workInProcess = 0, ownerAssinged = 0;
		setEchartLegendData(incidentTicketStatusMap, legendList);
		if(incidentList!=null&&incidentList.size()>0){
			resolved = incidentList.get(12).getResolved();
			pending = incidentList.get(12).getPending();
			workInProcess = incidentList.get(12).getWorkInProgress();
			ownerAssinged = incidentList.get(12).getOwnerAssinged();
		}

		incidentTicketStatusMap.put("resolved", resolved);
		incidentTicketStatusMap.put("pending", pending);
		incidentTicketStatusMap.put("workInProgress", workInProcess);
		incidentTicketStatusMap.put("ownerAssinged", ownerAssinged);
		return incidentTicketStatusMap;
	}

	/**
	 * QA Service 数据导入接口，界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String qaImport(Model model) {
		return "qa/import";
	}

	/**
	 * QA service 数据导入解析，数据插入
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String qaImportSubmit(HttpServletRequest request, HttpServletResponse response) {
		// 获取解析器
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断是否是文件
		if (resolver.isMultipart(request)) {
			// 进行转换
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			// 获取所有文件名称
			Iterator<String> it = multiRequest.getFileNames();
			while (it.hasNext()) {
				// 根据文件名称取文件
				MultipartFile file = multiRequest.getFile(it.next());
				String fileName = file.getOriginalFilename();
				String savePath = request.getSession().getServletContext().getRealPath("WEB-INF/upload");
				String localPath = savePath + "\\" + fileName;
				File newFile = new File(localPath);
				// 上传的文件写入到指定的文件中
				if (!newFile.exists()) {
					newFile.mkdirs();
				}
				try {
					file.transferTo(newFile);
					RandomAccessFile raf = new RandomAccessFile(newFile, "r");
					String importType = request.getParameter("import_type");
					long t = System.currentTimeMillis();
					String line = raf.readLine();
					List<IncidentImport> incidentImportList = new ArrayList<IncidentImport>();
					List<ProblemImport> problemListImport = new ArrayList<ProblemImport>();
					List<SRImport> sRImportList = new ArrayList<SRImport>();
					int index = 0;
					while (line != null) {
						String[] datas = line.split("\\t");
						int data_length = datas.length;
						int incident_length = IncidentEnum.values().length;
						int problem_length = ProblemEnum.values().length;
						int sr_length = SREnum.values().length;
						Map<String, Object> map = new LinkedHashMap<String, Object>();
						if (index != 0) {
							int i = 1;
							for (String result : datas) {
								if (result.contains("\"")) {
									result = result.replaceAll("\"", "");
								}
								if (importType.equalsIgnoreCase(QASourceType.INCIDENT)
										&& (incident_length == data_length)) {
									map.put(IncidentEnum.getName(i), result);
								} else if (importType.equalsIgnoreCase(QASourceType.PROBLEM)
										&& (problem_length == data_length)) {
									map.put(ProblemEnum.getName(i), result);
								} else if (importType.equalsIgnoreCase(QASourceType.SR) && (sr_length == data_length)) {
									map.put(SREnum.getName(i), result);
								}
								i++;
							}
							String jsonData = JsonUtils.object2jackson(map);
							if (importType.equalsIgnoreCase(QASourceType.INCIDENT) && (incident_length == data_length)) {
								IncidentImport incidentImport = JsonUtils.json2object(jsonData, IncidentImport.class);
								incidentImportList.add(incidentImport);
							} else if (importType.equalsIgnoreCase(QASourceType.PROBLEM)
									&& (problem_length == data_length)) {
								ProblemImport problemImport = JsonUtils.json2object(jsonData, ProblemImport.class);
								problemListImport.add(problemImport);
							} else if (importType.equalsIgnoreCase(QASourceType.SR) && (sr_length == data_length)) {
								SRImport sRImport = JsonUtils.json2object(jsonData, SRImport.class);
								sRImportList.add(sRImport);
							}
						}
						line = raf.readLine();
						index++;
					}
					if (importType.equalsIgnoreCase(QASourceType.INCIDENT)) {
						int j = incidentImportList.size();
						int x = j;
						for (int i = 0; i < j; i += 501) {
							if (x < 500) {
								qaService.insertIncidentList(incidentImportList.subList(i, j));
							} else {
								qaService.insertIncidentList(incidentImportList.subList(i, i + 500));
								x -= 500;
							}
						}
					} else if (importType.equalsIgnoreCase(QASourceType.PROBLEM)) {
						int j = problemListImport.size();
						int x = j;
						for (int i = 0; i < j; i += 501) {
							if (x < 500) {
								qaService.insertProblemList(problemListImport.subList(i, j));
							} else {
								qaService.insertProblemList(problemListImport.subList(i, i + 500));
								x -= 500;
							}
						}
					} else if (importType.equalsIgnoreCase(QASourceType.SR)) {
						int j = sRImportList.size();
						int x = j;
						for (int i = 0; i < j; i += 501) {
							if (x < 500) {
								qaService.insertSRList(sRImportList.subList(i, j));
							} else {
								qaService.insertSRList(sRImportList.subList(i, i + 500));
								x -= 500;
							}
						}
					}
					long s = System.currentTimeMillis();
					System.out.println("---------------------" + (s - t));
					raf.close();
					// Fixed bug, when importing was finished, release the file
					newFile.delete();
				} catch (MonitorException | IOException e) {
					logger.error(e);
				}
			}
			return "qa/success";
		} else {
			return "qa/fail";
		}
	}

	/**
	 * @author Kin
	 * @param serviceCode
	 * @param periodType
	 * @param chartType
	 * @throws IOException
	 */
	@RequestMapping(value = "/qa_data_export")
	public String qaExport(Model model, String param, String periodType, String chartType,boolean isDomain,
			HttpServletResponse response) {

		List<Object> monthList = new ArrayList<Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();

		try {
			// Get group list
			List<String> groupList = new ArrayList<String>();
			if(isDomain){
				groupList = qaService.getQAGroupNameByDomain(param);
			}else{
				groupList = qaService.getQAGroupNameByServiceCode(param);
			}
			// Get current month
			Date date = new Date();
			// Get the month of passed year with "yyyy-MM" format
			String starMonth = DateUtils.date2String(DateUtils.dateChange(date, Calendar.MONTH, -11),
					DateUtils.DATE_FROMAT_YYYY_MM);
			// Get the month of now with "yyyy-MM" format
			String endMonth = DateUtils.date2String(date, DateUtils.DATE_FROMAT_YYYY_MM);
			// 12 weeks ago
			Date curDay = DateUtils.dateChange(date, Calendar.WEEK_OF_YEAR, -11);
			// Get the first day of start week
			String startWeekDay = DateUtils
					.getWeekDaysByCurrDate(curDay, DateUtils.DATE_FROMAT_YYYY_MM_dd, DateUtils.FIRST_DAY_EN).get(0)
					.toString();
			// Get the end day of current week
			String endWeekDay = DateUtils
					.getWeekDaysByCurrDate(date, DateUtils.DATE_FROMAT_YYYY_MM_dd, DateUtils.FIRST_DAY_EN).get(6)
					.toString();
			// Generate file name
			String fileNameString = DateUtils.date2String(date, "yyyyMMdd");

			if (QAServicePeiordType.MONTHLY.equals(periodType)) {
				monthList.add(starMonth);
				monthList.add(endMonth);
			} else if (QAServicePeiordType.WEEKLY.equals(periodType)) {
				monthList.add(startWeekDay);
				monthList.add(endWeekDay);
			}

			// Check which chart types user chose to export
			if (QASourceType.INCIDENT.equalsIgnoreCase(chartType)) {
				// Get all incident list
				if(groupList!=null&&groupList.size()>0){
					resultList = qaService.getResultList(monthList, groupList, chartType);
				}

				// Get export files header
				Field[] fields = IncidentExportResultDto.class.getDeclaredFields();
				String[] fieldNames = new String[fields.length];
				for (int i = 0; i < fields.length; i++) {
					fieldNames[i] = fields[i].getName();
				}

				try {
					ServletOutputStream out = response.getOutputStream();
					// Set response information
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-disposition", "attachment; filename="
							+ new String(chartType + fileNameString + ".xls"));

					// Export data to ".xls" file
					ExcelExportUtils.exportXLSExcel(fieldNames, resultList, out, chartType);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (QASourceType.SR.equalsIgnoreCase(chartType)) {
				// Get all SR list
				if(groupList!=null&&groupList.size()>0){
					resultList = qaService.getResultList(monthList, groupList, chartType);
				}
				// Get export files header
				Field[] fields = SRExportResultDto.class.getDeclaredFields();
				String[] fieldNames = new String[fields.length];
				for (int i = 0; i < fields.length; i++) {
					fieldNames[i] = fields[i].getName();
				}

				try {
					ServletOutputStream out = response.getOutputStream();
					// Set response information
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-disposition", "attachment; filename="
							+ new String(chartType + fileNameString + ".xls"));

					// Export data to ".xls" file
					ExcelExportUtils.exportXLSExcel(fieldNames, resultList, out, chartType);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (QASourceType.PROBLEM.equalsIgnoreCase(chartType)) {
				// Get all problem list
				if(groupList!=null&&groupList.size()>0){
					resultList = qaService.getResultList(monthList, groupList, chartType);
				}
				// Get export files header
				Field[] fields = ProblemExportResultDto.class.getDeclaredFields();
				String[] fieldNames = new String[fields.length];
				for (int i = 0; i < fields.length; i++) {
					fieldNames[i] = fields[i].getName();
				}

				try {
					ServletOutputStream out = response.getOutputStream();

					// Set response information
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-disposition", "attachment; filename="
							+ new String(chartType + fileNameString + ".xls"));

					// Export data to ".xls" file
					ExcelExportUtils.exportXLSExcel(fieldNames, resultList, out, chartType);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (MonitorException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * QA service 数据查询， 手动 merge 状态，界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/service/data")
	public String qaServiceData(Model model) {
		List<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();
		try {
			serviceList = qaService.getQAServiceList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		return "qa/service/qa_service_data_search";
	}

	/**
	 * QA service 数据查询， 手动 merge 状态，查询处理
	 * @param model
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/qa_service_data_search")
	public String qaServiceDataSearch(Model model, @RequestParam String param) {
		ServiceDataSearchDto searchDto = JsonUtils.json2object(param, ServiceDataSearchDto.class);
		List<Map<String,Object>> closedList = new ArrayList<Map<String,Object>>();
		List<String> headTitle = new ArrayList<String>();
		Map<String, List<Object>> dataSource = new LinkedHashMap<String, List<Object>>();
		try {
			closedList = qaService.getServiceDataClosedList(searchDto);
			headTitle = generateHeadTitle(searchDto);
			for(Map<String, Object> closed : closedList){
				List<Object> closedData = new ArrayList<Object>();
				for(String head : headTitle){
					closedData.add(closed.get(head));
				}
				dataSource.put(String.valueOf(closed.get("id")), closedData);
			}
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("headTitle", headTitle);
		model.addAttribute("dataSource", dataSource);
		return "qa/service/qa_service_data_result";
	}

	private List<String> generateHeadTitle(ServiceDataSearchDto searchDto) {
		Field[] head = null;
		List<String> headTitle = new ArrayList<String>();
		String dataType = searchDto.getDataType();
		if(dataType.equalsIgnoreCase(QASourceType.INCIDENT)){
			head = IncidentEntity.class.getDeclaredFields();
		}else if(dataType.equalsIgnoreCase(QASourceType.SR)){
			head = SREntity.class.getDeclaredFields();
		}else if(dataType.equalsIgnoreCase(QASourceType.PROBLEM)){
			head = ProblemEntity.class.getDeclaredFields();
		}
		for(Field f : head){
			headTitle.add(f.getName());
		}
		return headTitle;
	}
	
	/**
	 * QA Service 数据查询， 手动 merge 状态 处理
	 * @param model
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/data_real_breach_submit")
	public String qaServiceDataRealBreachSubmint(Model model, @RequestParam String param) {
		ServiceDataSearchDto searchDto = JsonUtils.json2object(param, ServiceDataSearchDto.class);
		List<Map<String,Object>> closedList = new ArrayList<Map<String,Object>>();
		List<String> headTitle = new ArrayList<String>();
		Map<String, List<Object>> dataSource = new LinkedHashMap<String, List<Object>>();
		try {
			qaService.updateServiceRealBreach(searchDto);
			closedList = qaService.getServiceDataClosedList(searchDto);
			headTitle = generateHeadTitle(searchDto);
			for(Map<String, Object> closed : closedList){
				List<Object> closedData = new ArrayList<Object>();
				for(String head : headTitle){
					closedData.add(closed.get(head));
				}
				dataSource.put(String.valueOf(closed.get("id")), closedData);
			}
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("headTitle", headTitle);
		model.addAttribute("dataSource", dataSource);
		return "qa/service/qa_service_data_result";
	}
}
