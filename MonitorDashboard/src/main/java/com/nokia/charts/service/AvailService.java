package com.nokia.charts.service;

import java.util.List;

import com.nokia.charts.dto.SearchChoiceDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.dto.admin.DomainBundleServiceDto;
import com.nokia.charts.dto.shinepoint.MajorIncidentDto;
import com.nokia.charts.entity.AvailReport;
import com.nokia.charts.entity.Monitor;
import com.nokia.charts.entity.Ping;
import com.nokia.charts.entity.Url;
import com.nokia.charts.entity.admin.Server;
import com.nokia.charts.entity.shinepoint.MajorIncident;
import com.nokia.charts.exception.MonitorException;

public interface AvailService {

	public List<DomainBundleServiceDto> getServiceListToMonitor() throws MonitorException;

	public List<Ping> getPingList(SearchChoiceDto searchDto) throws MonitorException;

	public List<Url> getUrlList(SearchChoiceDto searchDto) throws MonitorException;

	public void insertMajorIncident(MajorIncident majorIncident) throws MonitorException;

	public List<MajorIncidentDto> getMajorIncidentList() throws MonitorException;

	public MajorIncidentDto getMajorDataById(Integer id) throws MonitorException;

	public void updateMajorDataById(MajorIncidentDto majorIncidentDto) throws MonitorException;

	public List<Server> getServerListByServiceId(int serviceId) throws MonitorException;

	public List<Monitor> getMonitorListByServerId(Integer serverId) throws MonitorException;

	public Server getServerById(int serverId) throws MonitorException;

	public String getMonitorDesp(Integer monitorId) throws MonitorException;

	public List<AvailReport> getAvailDailyReport(SearchChoiceDto searchChoiceDto) throws MonitorException;

	public List<SelectionOptionDto> getDomainList() throws MonitorException;

	public List<SelectionOptionDto> getBundleList() throws MonitorException;

	public List<SelectionOptionDto> getServiceList() throws MonitorException;

	public List<SelectionOptionDto> getRelationDomainList() throws MonitorException;

	public List<String> getAvailMonthlyYearList(String responseTimeStart, String responseTimeEnd) throws MonitorException;

	public List<AvailReport> getAvailMonthlyReport(String serverName, String responseTimeStart, String responseTimeEnd) throws MonitorException;
}
