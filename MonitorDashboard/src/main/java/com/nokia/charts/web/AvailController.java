package com.nokia.charts.web;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nokia.charts.constant.SystemConstant.MonitorStatus;
import com.nokia.charts.constant.SystemConstant.MonitorType;
import com.nokia.charts.constant.SystemConstant.PingValueType;
import com.nokia.charts.constant.SystemConstant.UrlValueType;
import com.nokia.charts.dto.ActiveUsersDto;
import com.nokia.charts.dto.ActiveUsersReportDto;
import com.nokia.charts.dto.ErrorMessageDto;
import com.nokia.charts.dto.MailTemplateDto;
import com.nokia.charts.dto.SLAYearDto;
import com.nokia.charts.dto.SearchChoiceDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.dto.admin.DomainBundleServiceDto;
import com.nokia.charts.dto.shinepoint.MajorIncidentDto;
import com.nokia.charts.entity.ActiveUsers;
import com.nokia.charts.entity.AvailReport;
import com.nokia.charts.entity.Monitor;
import com.nokia.charts.entity.Ping;
import com.nokia.charts.entity.Url;
import com.nokia.charts.entity.admin.Server;
import com.nokia.charts.entity.admin.ServiceEntity;
import com.nokia.charts.entity.shinepoint.MajorIncident;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.ActiveUsersService;
import com.nokia.charts.service.AvailService;
import com.nokia.charts.service.MailTemplateService;
import com.nokia.charts.service.QAService;
import com.nokia.charts.util.DateUtils;
import com.nokia.charts.util.ExcelExportUtils;
import com.nokia.charts.util.JsonUtils;
import com.nokia.charts.util.MathUtil;
import com.nokia.charts.util.StringSplitUtils;
import com.nokia.charts.web.base.EchartController;

/**
 * Capacity
 * @author weijid
 *
 */
@Controller
public class AvailController extends EchartController {

	@Autowired
	private ActiveUsersService activeUsersService;
	
	@Autowired
	private QAService qaService;
	
	@Autowired
	private AvailService availService;

	@Autowired
	private MailTemplateService mailTemplateService;
	
	
	/**
	 * 监控查询页面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping("/search")
	public String search(Model model) {
		List<DomainBundleServiceDto> serviceList = new ArrayList<DomainBundleServiceDto>();
		try {
			serviceList = availService.getServiceListToMonitor();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		return "avail/search/search";
	}

	@RequestMapping("/search/searchserver")
	public String searchServer(Model model, int serviceId) {
		List<Server> serverList = new ArrayList<Server>();
		try {
			serverList = availService.getServerListByServiceId(serviceId);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serverList", serverList);
		return "avail/search/searchserver";
	}

	@RequestMapping("/search/searchmonitor")
	public String searchMonitor(Model model, Integer serverId) {
		List<Monitor> serverMonitorList = new ArrayList<Monitor>();
		try {
			serverMonitorList = availService.getMonitorListByServerId(serverId);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serverMonitorList", serverMonitorList);
		return "avail/search/searchmonitor";
	}

	/**
	 * 监控查询结果处理，生成图表
	 * @param model
	 * @param param
	 * @return
	 */
	@RequestMapping("/search/searchsubmit")
	public String searchsubmit(Model model, @RequestParam String param) {
		SearchChoiceDto searchDto = JsonUtils.json2object(param, SearchChoiceDto.class);
		Map<String, Object> data = new HashMap<String, Object>();
		List<String> legendList = new ArrayList<String>();
		List<Object> xAxisList = new ArrayList<Object>();
		List<Double> xData = new ArrayList<Double>();

		List<Double> msgData = new ArrayList<Double>();
		Double avg = 0.0;
		Map<String, Object> msg = new LinkedHashMap<String, Object>();

		Server server = new Server();
		String monitorType = "";
		try {
			server = availService.getServerById(searchDto.getServerId());
			monitorType = availService.getMonitorDesp(searchDto.getMonitorId());
		} catch (MonitorException e) {
			logger.error(e);
		}

		if (monitorType.equals(MonitorType.PINGTYPE)) {
			List<Ping> pingList = new ArrayList<Ping>();
			try {
				pingList = availService.getPingList(searchDto);
				String title = "Server Response Time Chart";
				setEchartTitle(data, title);
				Date date = null;
				Ping ping_threshold = new Ping();
				Double threshold_critical = null;
				Double threshold_warning = null;
				for (Ping ping : pingList) {
					Date temp = DateUtils.String2Date(ping.getCheckTime(), DateUtils.DATE_FROMAT_CN);
					if (date != null) {
						if (date.getTime() < temp.getTime()) {
							date = temp;
							ping_threshold = ping;
						}
					} else {
						date = temp;
					}
					xAxisList.add(ping.getCheckTime());
					if (!ping.getMessage().contains("|")) {
						xData.add(1000.0);
					} else {
						Double perdata = StringSplitUtils.SplitData(ping.getMessage(), PingValueType.PINGVALUE,
								PingValueType.PINGUNIT);
						xData.add(MathUtil.formatDouble(perdata));
						msgData.add(MathUtil.formatDouble(perdata));
						avg = avg + MathUtil.formatDouble(perdata);
					}
				}
				legendList.add(server.getServerName());

				if (ping_threshold.getThresholdCritical() != null) {
					threshold_critical = StringSplitUtils.SplitThreshold(ping_threshold.getThresholdCritical());
				}
				if (ping_threshold.getThresholdWarning() != null) {
					threshold_warning = StringSplitUtils.SplitThreshold(ping_threshold.getThresholdWarning());
				}
				setEchartLegendPingData(data, threshold_warning, threshold_critical, legendList);
				if (!msgData.isEmpty()) {
					avg = MathUtil.formatDouble(avg / msgData.size());
					setMsg(msgData, msg, avg);
				}
				data.put("xData", xData.toArray());
				String xName = "Time";
				setEchartXAxis(data, xName, xAxisList);
				String yName = "Value(ms)";
				Double yMax = null;
				if (threshold_critical != null) {
					Double xDataMax = Collections.max(xData);
					if (threshold_critical <= xDataMax) {
						yMax = xDataMax + 100;
					} else {
						yMax = threshold_critical + 100;
					}
				}
				setEchartYAxis(data, yName, yMax);
				pingErrorMessage(pingList, model);
			} catch (MonitorException e) {
				logger.error(e);
			}
		} else if (monitorType.equals(MonitorType.URLTYPE)) {
			List<Url> urlList = new ArrayList<Url>();
			try {
				urlList = availService.getUrlList(searchDto);
				Url url_threshold = new Url();
				Date date = null;
				for (Url url : urlList) {
					Date temp = DateUtils.String2Date(url.getCheckTime(), DateUtils.DATE_FROMAT_CN);
					if (date != null) {
						if (date.getTime() < temp.getTime()) {
							date = temp;
							url_threshold = url;
						}
					} else {
						date = temp;
					}
					xAxisList.add(url.getCheckTime());
					if (!url.getMessage().contains("|")) {
						xData.add(10.0);
					} else {
						Double perdata = StringSplitUtils.SplitData(url.getMessage(), UrlValueType.URLVALUE,
								UrlValueType.URLUNIT);
						xData.add(MathUtil.formatDouble(perdata));
						msgData.add(MathUtil.formatDouble(perdata));
						avg = avg + MathUtil.formatDouble(perdata);
					}
				}
				String title = "WebSite Response Time Chart";
				setEchartTitle(data, title);
				legendList.add(server.getServerName());
				setEchartLegendUrlData(data, url_threshold, legendList);

				if (!msgData.isEmpty()) {
					avg = MathUtil.formatDouble(avg / msgData.size());
					setMsg(msgData, msg, avg);
				}
				Double yMax = null;
				if (url_threshold != null && url_threshold.getUpperSpecThreshold() != null) {
					Double xDataMax = Collections.max(xData);
					Double upper_spec_threshold = Double.valueOf(url_threshold.getUpperSpecThreshold());
					if (upper_spec_threshold <= xDataMax) {
						yMax = xDataMax + 1;
					} else {
						yMax = upper_spec_threshold + 1;
					}
				}
				data.put("xData", xData.toArray());
				String yName = "Value(s)";
				setEchartYAxis(data, yName, yMax);

				String xName = "Time";
				setEchartXAxis(data, xName, xAxisList);
				urlErrorMessage(urlList, model);
			} catch (MonitorException e) {
				logger.error(e);
			}
		}
		model.addAttribute("msg", msg);
		model.addAttribute("server", server);
		model.addAttribute("data", JsonUtils.object2jackson(data));
		return "avail/search/echart";
	}

	private void setMsg(List<Double> msgData, Map<String, Object> msg, Double avg) {
		Double top;
		Double lowest;
		top = Collections.max(msgData);
		lowest = Collections.min(msgData);
		msg.put("top", top);
		msg.put("avg", avg);
		msg.put("lowest", lowest);
	}

	/**
	 * 监控动态查询界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping("/dynamic")
	public String dynamic(Model model) {
		List<DomainBundleServiceDto> serviceList = new ArrayList<DomainBundleServiceDto>();
		try {
			serviceList = availService.getServiceListToMonitor();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		return "avail/dynamic/dynamic";
	}

	/**
	 * 监控动态查询结果处理
	 * @param model
	 * @param serverId
	 * @param monitorId
	 * @return
	 */
	@RequestMapping("/dynamic/4hour")
	public @ResponseBody Map<String, Object> dynamicchart(Model model, Integer serverId, int monitorId) {
		Map<String, Object> data = new HashMap<String, Object>();
		SearchChoiceDto searchChoiceDto = setDynamicParam(serverId, monitorId);
		dynamicChartGenerate(data, model, searchChoiceDto);
		return data;
	}

	/**
	 * 监控动态查询结果message处理
	 * @param model
	 * @param serverId
	 * @param monitorId
	 * @return
	 */
	@RequestMapping("/dynamic/4hourmessage")
	public String dynamicmessage(Model model, Integer serverId, int monitorId) {
		SearchChoiceDto searchChoiceDto = setDynamicParam(serverId, monitorId);
		Server server = new Server();
		String monitorType = null;
		try {
			server = availService.getServerById(serverId);
			monitorType = availService.getMonitorDesp(monitorId);
			if (monitorType.equals(MonitorType.PINGTYPE)) {
				List<Ping> pingList = new ArrayList<Ping>();
				pingList = availService.getPingList(searchChoiceDto);
				pingErrorMessage(pingList, model);
			} else if (monitorType.equals(MonitorType.URLTYPE)) {
				List<Url> urlList = new ArrayList<Url>();
				urlList = availService.getUrlList(searchChoiceDto);
				urlErrorMessage(urlList, model);
			}
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("server", server);
		return "avail/dynamic/dynamicmessage";
	}
	
	private SearchChoiceDto setDynamicParam(Integer serverId, int monitorId) {
		Date date = new Date();
		String curDate = DateUtils.date2String(date, DateUtils.DATE_FROMAT_CN);
		String before4Date = DateUtils.date2String(DateUtils.dateChange(date, Calendar.HOUR_OF_DAY, -4),
				DateUtils.DATE_FROMAT_CN);
		SearchChoiceDto searchChoiceDto = new SearchChoiceDto();
		searchChoiceDto.setServerId(serverId);
		searchChoiceDto.setResponseTimeStart(before4Date);
		searchChoiceDto.setResponseTimeEnd(curDate);
		searchChoiceDto.setMonitorId(monitorId);
		return searchChoiceDto;
	}

	/**
	 * 监控动态查询，生成图表
	 * @param data
	 * @param model
	 * @param searchChoiceDto
	 */
	private void dynamicChartGenerate(Map<String, Object> data, Model model, SearchChoiceDto searchChoiceDto) {
		List<String> legendList = new ArrayList<String>();
		List<Object> xAxisList = new ArrayList<Object>();
		List<Double> xData = new ArrayList<Double>();
		Server server = new Server();
		String monitorType = "";
		try {
			server = availService.getServerById(searchChoiceDto.getServerId());
			monitorType = availService.getMonitorDesp(searchChoiceDto.getMonitorId());
		} catch (MonitorException e1) {
			e1.printStackTrace();
		}
		if (monitorType.equals(MonitorType.PINGTYPE)) {
			List<Ping> pingList = new ArrayList<Ping>();
			try {
				pingList = availService.getPingList(searchChoiceDto);

				String title = "Server Response Time in 4Hours";
				setEchartTitle(data, title);

				Double threshold_critical = null;
				Double threshold_warning = null;
				Ping ping_threshold = new Ping();
				Date date = null;
				for (Ping ping : pingList) {
					Date temp = DateUtils.String2Date(ping.getCheckTime(), DateUtils.DATE_FROMAT_CN);
					if (date == null) {
						date = temp;
					} else {
						if (date.getTime() < temp.getTime()) {
							date = temp;
							ping_threshold = ping;
						}
					}
					xAxisList.add(ping.getCheckTime());
					if (!ping.getMessage().contains("|")) {
						xData.add(1000.0);
					} else {
						Double perdata = StringSplitUtils.SplitData(ping.getMessage(), PingValueType.PINGVALUE,
								PingValueType.PINGUNIT);
						xData.add(MathUtil.formatDouble(perdata));
					}
				}

				if (ping_threshold.getThresholdCritical() != null) {
					threshold_critical = StringSplitUtils.SplitThreshold(ping_threshold.getThresholdCritical());
				}
				if (ping_threshold.getThresholdWarning() != null) {
					threshold_warning = StringSplitUtils.SplitThreshold(ping_threshold.getThresholdWarning());
				}
				legendList.add(server.getServerName());
				setEchartLegendPingData(data, threshold_warning, threshold_critical, legendList);

				String xName = "Time";
				setEchartXAxis(data, xName, xAxisList);

				String yName = "Value(/ms)";
				Double yMax = null;
				if (threshold_critical != null) {
					yMax = threshold_critical + 100;
				}
				setEchartYAxis(data, yName, yMax);
				data.put("xData", xData.toArray());
				pingErrorMessage(pingList, model);
			} catch (MonitorException e) {
				logger.error(e);
			}
		} else if (monitorType.equals(MonitorType.URLTYPE)) {
			List<Url> urlList = new ArrayList<Url>();
			try {
				urlList = availService.getUrlList(searchChoiceDto);
				String title = "WebSite Response Time in 4Hours";
				setEchartTitle(data, title);

				legendList.add(server.getServerName());
				Url url_threshold = new Url();
				Date date = null;
				for (Url url : urlList) {
					Date temp = DateUtils.String2Date(url.getCheckTime(), DateUtils.DATE_FROMAT_CN);
					if (date == null) {
						date = temp;
					} else {
						if (date.getTime() < temp.getTime()) {
							date = temp;
							url_threshold = url;
						}
					}
					xAxisList.add(url.getCheckTime());
					if (!url.getMessage().contains("|")) {
						xData.add(10.0);
					} else {
						Double perdata = StringSplitUtils.SplitData(url.getMessage(), UrlValueType.URLVALUE,
								UrlValueType.URLUNIT);
						xData.add(MathUtil.formatDouble(perdata));
					}
				}
				setEchartLegendUrlData(data, url_threshold, legendList);
				String xName = "Date";
				setEchartXAxis(data, xName, xAxisList);
				String yName = "Value(/s)";
				Double yMax = null;
				if (url_threshold.getUpperSpecThreshold() != null) {
					yMax = Double.valueOf(url_threshold.getUpperSpecThreshold()) + 1;
				}
				setEchartYAxis(data, yName, yMax);
				data.put("xData", xData.toArray());
				urlErrorMessage(urlList, model);
			} catch (MonitorException e) {
				logger.error(e);
			}
		}
		model.addAttribute("data", JsonUtils.object2jackson(data));
	}

	private void pingErrorMessage(List<Ping> pingList, Model model) {
		List<ErrorMessageDto> errorMessageList = new ArrayList<ErrorMessageDto>();
		for (Ping ping : pingList) {
			if (!ping.getStatusId().equals(MonitorStatus.OK)) {
				ErrorMessageDto errorMessageDto = new ErrorMessageDto();
				errorMessageDto.setMessage(StringSplitUtils.SplitMessage(ping.getMessage()));
				errorMessageDto.setResponseTime(ping.getCheckTime());
				if (ping.getStatusId().equals(MonitorStatus.WARNING)) {
					errorMessageDto.setStatus("Warning");
				}
				if (ping.getStatusId().equals(MonitorStatus.CRITICAL)) {
					errorMessageDto.setStatus("Critical");
				}
				if (ping.getStatusId().equals(MonitorStatus.UNKNOWN)) {
					errorMessageDto.setStatus("Unknown");
				}
				errorMessageList.add(errorMessageDto);
			}
		}
		model.addAttribute("errorMessageList", errorMessageList);
	}

	private void urlErrorMessage(List<Url> urlList, Model model) {
		List<ErrorMessageDto> errorMessageList = new ArrayList<ErrorMessageDto>();
		for (Url url : urlList) {
			if (!url.getStatusId().equals(MonitorStatus.OK)) {
				ErrorMessageDto errorMessageDto = new ErrorMessageDto();
				errorMessageDto.setMessage(StringSplitUtils.SplitMessage(url.getMessage()));
				errorMessageDto.setResponseTime(url.getCheckTime());
				if (url.getStatusId().equals(MonitorStatus.WARNING)) {
					errorMessageDto.setStatus("Warning");
				}
				if (url.getStatusId().equals(MonitorStatus.CRITICAL)) {
					errorMessageDto.setStatus("Critical");
				}
				if (url.getStatusId().equals(MonitorStatus.UNKNOWN)) {
					errorMessageDto.setStatus("Unknown");
				}
				errorMessageList.add(errorMessageDto);
			}
		}
		model.addAttribute("errorMessageList", errorMessageList);
	}

	/**
	 * 监控Daily Report 界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping("/sla/daily_website_chart")
	public String DailyWebsiteChart(Model model) {
		List<DomainBundleServiceDto> serviceList = new ArrayList<DomainBundleServiceDto>();
		try {
			serviceList = availService.getServiceListToMonitor();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		return "avail/sla/daily_website_chart_search";
	}

	/**
	 * 监控Daily Report 结果展示，图表生成
	 * @param model
	 * @param param
	 * @return
	 */
	@RequestMapping("/sla/daily_website_chart_submit")
	public String DailyWebsiteChartSubmit(Model model, String param) {
		SearchChoiceDto searchDto = JsonUtils.json2object(param, SearchChoiceDto.class);
		Map<String, Object> data = new HashMap<String, Object>();
		List<AvailReport> dataList = new ArrayList<AvailReport>();
		List<String> legendList = new ArrayList<String>();
		List<Object> xAxisList = new ArrayList<Object>();
		generateDailyXAxisList(searchDto, xAxisList);
		List<Double> xData = new ArrayList<Double>();
		int yMax = 0;
		try {
			dataList = availService.getAvailDailyReport(searchDto);
			int i = 0;
			double temp = 0.0;
			for (Object date : xAxisList) {
				xData.add(null);
				for (AvailReport dailyReport : dataList) {
					if (date.equals(dailyReport.getDate())) {
						Double xValue = Double.valueOf(dailyReport.getAvail());
						if (xValue > temp) {
							temp = xValue;
						}
						xData.set(i, Double.valueOf(dailyReport.getAvail()));
						break;
					}
				}
				i++;
			}
			yMax = (int) temp;
		} catch (MonitorException e) {
			logger.error(e);
		}
		String title = "WebSite Daily Response Time Chart";
		setEchartTitle(data, title);

		Url url_threshold = new Url();
		legendList.add("Response time");
		setEchartLegendUrlData(data, url_threshold, legendList);

		String yName = "Value(s)";
		setEchartYAxis(data, yName, yMax + 2);

		String xName = "Time";
		setEchartXAxis(data, xName, xAxisList);
		data.put("xData", xData.toArray());
		model.addAttribute("data", JsonUtils.object2jackson(data));
		return "avail/sla/daily_website_chart_reslut";
	}

	/**
	 * 设置Daily Report 的X轴值
	 * @param searchDto
	 * @param xAxisList
	 */
	private void generateDailyXAxisList(SearchChoiceDto searchDto, List<Object> xAxisList) {
		Date start = DateUtils.String2Date(searchDto.getResponseTimeStart());
		Date end = DateUtils.String2Date(searchDto.getResponseTimeEnd());
		long timeDay = (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
		for (long i = 0; i <= timeDay; i++) {
			if (i == 0) {
				start.setTime(start.getTime());
			} else {
				start.setTime(start.getTime() + (1000 * 60 * 60 * 24));
			}
			String dayTime = DateUtils.date2String(start);
			xAxisList.add(dayTime);
		}
	}

	/**
	 * 根据不同的domain,bundle,service查询server的sla情况，界面初始化
	 * @param model
	 * @return
	 */
	@RequestMapping("/sla/server_avail_report")
	public String ServerAvailReport(Model model) {
		List<SelectionOptionDto> domainList = new ArrayList<SelectionOptionDto>();
		List<SelectionOptionDto> bundleList = new ArrayList<SelectionOptionDto>();
		List<SelectionOptionDto> serviceList = new ArrayList<SelectionOptionDto>();
		List<SelectionOptionDto> relationList = new ArrayList<SelectionOptionDto>();
		try {
			domainList = availService.getDomainList();
			bundleList = availService.getBundleList();
			serviceList = availService.getServiceList();
			relationList = availService.getRelationDomainList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("domainList", domainList);
		model.addAttribute("bundleList", bundleList);
		model.addAttribute("serviceList", serviceList);
		model.addAttribute("relationList", relationList);
		return "avail/sla/server_avail_report_search";
	}

	/**
	 * 根据不同的domain,bundle,service查询server的sla情况，结果展示界面
	 * @param model
	 * @param param
	 * @param serverNames
	 * @return
	 */
	@RequestMapping("/sla/server_avail_report_submit")
	public String ServerAvailReportSubmit(Model model, String param, String[] serverNames) {
		List<SLAYearDto> dataList = new ArrayList<SLAYearDto>();
		List<String> yearList = new ArrayList<String>();
		SearchChoiceDto searchDto = JsonUtils.json2object(param, SearchChoiceDto.class);
		String responseTimeStart = searchDto.getResponseTimeStart();
		String responseTimeEnd = searchDto.getResponseTimeEnd();
		try {
			yearList = availService.getAvailMonthlyYearList(responseTimeStart, responseTimeEnd);

			for (String serverName : serverNames) {
				List<AvailReport> list = availService.getAvailMonthlyReport(serverName, responseTimeStart,
						responseTimeEnd);
				SLAYearDto slaYearDto = new SLAYearDto();
				List<Double> valueList = new ArrayList<Double>();
				slaYearDto.setServer(serverName);
				int i = 0;
				for (String year : yearList) {
					valueList.add(100.0);
					for (AvailReport availReport : list) {
						if (year.equals(availReport.getDate())) {
							valueList.set(i, Double.valueOf(availReport.getAvail().replace("%", "")));
							break;
						}
					}
					i++;
				}
				slaYearDto.setValueList(valueList);
				dataList.add(slaYearDto);
			}
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("yearList", yearList);
		return "avail/sla/server_avail_report_result";
	}

	/**
	 * Active User 查询初始化界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/active/users_chart")
	public String ActiveUsersChart(Model model) {
		List<SelectionOptionDto> domainList = new ArrayList<SelectionOptionDto>();
		List<SelectionOptionDto> bundleList = new ArrayList<SelectionOptionDto>();
		List<SelectionOptionDto> serviceList = new ArrayList<SelectionOptionDto>();
		List<SelectionOptionDto> relationList = new ArrayList<SelectionOptionDto>();
		try {
			domainList = availService.getDomainList();
			bundleList = availService.getBundleList();
			serviceList = availService.getServiceList();
			relationList = availService.getRelationDomainList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("domainList", domainList);
		model.addAttribute("bundleList", bundleList);
		model.addAttribute("serviceList", serviceList);
		model.addAttribute("relationList", relationList);
		return "avail/active/search_active_user";
	}

	/**
	 * Active User 结果，图表展示
	 * @param model
	 * @param param
	 * @return
	 */
	@RequestMapping("/active/users_chart_submit")
	public String ActiverUsersChartSubmit(Model model, String param) {
		ActiveUsersDto activeUsersDto = JsonUtils.json2object(param, ActiveUsersDto.class);
		//Map<String, List<ActiveUsers>>:key(String) 代表serviceName，List<ActiveUsers> 代表同一个service下多个ActiveUsers
		Map<String, List<ActiveUsers>> activeUsersMap = new LinkedHashMap<String, List<ActiveUsers>>();
		Map<String, String> serviceNameMap = new LinkedHashMap<String, String>();
		List<Integer> serviceIdList = new ArrayList<Integer>();
		Collections.addAll(serviceIdList, activeUsersDto.getServiceIdList());
		try {
			activeUsersMap = activeUsersService.getActiveUsers(activeUsersDto);
			Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
			sourceMap.put("serviceIdList", serviceIdList);
			List<SelectionOptionDto> serviceList = activeUsersService.getActiveUserServiceListById(sourceMap);
			for (SelectionOptionDto service : serviceList) {
				serviceNameMap.put(String.valueOf(service.getServiceId()), service.getServiceName());
			}
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		List<String> legendList = new ArrayList<String>();
		List<Object> xAxisList = DateUtils.getDateToDateMonthList(activeUsersDto.getStartTime(), activeUsersDto.getEndTime(),
				DateUtils.DATE_FROMAT_YYYY_MM);

		Map<String, Object> xDataMap = new LinkedHashMap<String, Object>();
		String title = "Active Users Chart";
		setEchartTitle(data, title);
		for (String mapKey : activeUsersMap.keySet()) {
			legendList.add(serviceNameMap.get(mapKey));
			List<Object> xData = new ArrayList<Object>();
			List<ActiveUsers> list = activeUsersMap.get(mapKey);
			for(Object month : xAxisList){
				//先默认给所以列设置值为'-'，然后根据情况替换相应的值
				xData.add('-');
				for(ActiveUsers ac : list){
					if(ac.getCountTime().equals(month)){
						xData.set(xAxisList.indexOf(month), ac.getUserCounts());
					}
				}
			}
			xDataMap.put(serviceNameMap.get(mapKey), xData);
		}
		setEchartLegendPingData(data, null, null, legendList);
		data.put("xDataMap", xDataMap);
		String xName = "Time(Month)";
		setEchartXAxis(data, xName, xAxisList);
		String yName = "Value(user)";
		Double yMax = null;
		setEchartYAxis(data, yName, yMax);
		model.addAttribute("dataSource", JsonUtils.object2jackson(data));
		model.addAttribute("exportParam", param);
		return "avail/active/search_active_user_result";
	}

	/**
	 * Active User 数据插入，界面初始化 
	 * @param model
	 * @return
	 */
	@RequestMapping("/active/users_input")
	public String ActiveUsersInput(Model model) {
		List<SelectionOptionDto> serviceList = new ArrayList<SelectionOptionDto>();
		try {
			serviceList = activeUsersService.getServiceList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		return "avail/active/input_active_user";
	}

	/**
	 * Active User 数据插入
	 * @param model
	 * @param param
	 * @return
	 */
	@RequestMapping("/active/users_input_submit")
	public @ResponseBody String ActiverUsersInputSubmit(Model model, String param) {
		ActiveUsers activeUsers = JsonUtils.json2object(param, ActiveUsers.class);
		try {
			activeUsersService.addActiveUser(activeUsers);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Active user 数据导出
	 * @param model
	 * @param param
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/active/users_data_export")
	public String activeUserExport(Model model, String param, HttpServletResponse response) {
		ActiveUsersDto activeUsersDto = JsonUtils.json2object(param, ActiveUsersDto.class);
		try {
			List<Map<String, Object>> usersReportData = activeUsersService.getActiveUsersReportData(activeUsersDto);

			Field[] fields = ActiveUsersReportDto.class.getDeclaredFields();
			String[] fieldNames = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				fieldNames[i] = fields[i].getName();
			}
			String fileNameString = "Active_Users_Data_Report";
			ServletOutputStream out = response.getOutputStream();
			// Set response information
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileNameString + ".xls"));

			// Export data to ".xls" file
			ExcelExportUtils.exportXLSExcel(fieldNames, usersReportData, out, fileNameString);

		} catch (MonitorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * major Incident 数据结果展示界面
	 * @param model
	 * @return
	 */
	@RequestMapping("shinepoint/major_incident_list")
	public String majorIncidentList(Model model){
		List<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();
		List<MajorIncidentDto> majorIncidentList = new ArrayList<MajorIncidentDto>();
		try {
			serviceList = qaService.getServiceListToMajor();
			majorIncidentList = availService.getMajorIncidentList();
			
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("serviceList", serviceList);
		model.addAttribute("majorIncidentList", majorIncidentList);
		return "avail/shinepoint/major_incident_list";
	}
	
	@RequestMapping("/avail/searchTemplateByServiceId")
	public String searchTemplateByServiceId(Model model, String serviceId) {

		List<MailTemplateDto> templateList = new ArrayList<MailTemplateDto>();
		try {
			templateList = mailTemplateService.selecTemplateByServiceId(serviceId);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("templateList", templateList);
		return "/avail/shinepoint/template";
	}
	
	@RequestMapping("/avail/searchTemplate")
	public String searchTemplate(Model model) {

		List<MailTemplateDto> templateList = new ArrayList<MailTemplateDto>();
		try {
			templateList = mailTemplateService.selectAllTemplate();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("templateList", templateList);
		return "/avail/shinepoint/template";
	}
	
	
	/**
	 * major Incident 数据插入
	 * @param param
	 * @return
	 */
	@RequestMapping("shinepoint/major_data_input")
	public @ResponseBody String majorDataInput(String param){
		MajorIncident majorIncident = JsonUtils.json2object(param, MajorIncident.class);
		if(majorIncident==null){
			return "fail";
		}
		try {
			availService.insertMajorIncident(majorIncident);
		} catch (MonitorException e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	/**
	 * major Incident 数据获取
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("shinepoint/get_major_data")
	public @ResponseBody MajorIncidentDto getMajorData(Integer id,HttpServletRequest request, Model model){
		MajorIncidentDto majorIncidentDto = new MajorIncidentDto();
		try {
			majorIncidentDto = availService.getMajorDataById(id);
			
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return majorIncidentDto;
	}
	
	/**
	 * major Incident 数据更新
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("shinepoint/major_data_update")
	public @ResponseBody String majorDataUpdate(String param,HttpServletRequest request){
		MajorIncidentDto majorIncidentDto = JsonUtils.json2object(param, MajorIncidentDto.class);
		try {
			availService.updateMajorDataById(majorIncidentDto);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
}
