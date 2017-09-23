package com.nokia.charts.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.charts.dao.AvailDao;
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
import com.nokia.charts.service.AvailService;

@Service
public class AvailServiceImpl implements AvailService {

	@Autowired
	private AvailDao availDao;

	@Override
	public List<DomainBundleServiceDto> getServiceListToMonitor() throws MonitorException {
		return availDao.getServiceListToMonitor();
	}

	@Override
	public void insertMajorIncident(MajorIncident majorIncident) throws MonitorException {
		availDao.insertMajorIncident(majorIncident);
	}

	@Override
	public List<MajorIncidentDto> getMajorIncidentList() throws MonitorException {
		return availDao.getMajorIncidentList();
	}

	@Override
	public MajorIncidentDto getMajorDataById(Integer id) throws MonitorException {
		return availDao.getMajorDataById(id);
	}

	@Override
	public void updateMajorDataById(MajorIncidentDto majorIncidentDto) throws MonitorException {
		availDao.updateMajorDataById(majorIncidentDto);
	}

	@Override
	public List<Ping> getPingList(SearchChoiceDto searchDto) throws MonitorException {
		return availDao.getPingList(searchDto);
	}

	@Override
	public List<Url> getUrlList(SearchChoiceDto searchDto) throws MonitorException {
		return availDao.getUrlList(searchDto);
	}

	@Override
	public List<Server> getServerListByServiceId(int serviceId) throws MonitorException {
		return availDao.getServerListByServiceId(serviceId);
	}

	@Override
	public List<Monitor> getMonitorListByServerId(Integer serverId) throws MonitorException {
		return availDao.getMonitorListByServerId(serverId);
	}

	@Override
	public Server getServerById(int serverId) throws MonitorException {
		return availDao.getServerById(serverId);
	}
	
	@Override
	public String getMonitorDesp(Integer monitorId) throws MonitorException {
		return availDao.getMonitorDesp(monitorId);
	}
	
	@Override
	public List<AvailReport> getAvailDailyReport(SearchChoiceDto searchChoiceDto) throws MonitorException {
		return availDao.getAvailDailyReport(searchChoiceDto);
	}

	@Override
	public List<SelectionOptionDto> getDomainList() throws MonitorException {
		return availDao.getDomainList();
	}

	@Override
	public List<SelectionOptionDto> getBundleList() throws MonitorException {
		return availDao.getBundleList();
	}

	@Override
	public List<SelectionOptionDto> getServiceList() throws MonitorException {
		return availDao.getServiceList();
	}

	@Override
	public List<SelectionOptionDto> getRelationDomainList() throws MonitorException {
		return availDao.getRelationDomainList();
	}

	@Override
	public List<String> getAvailMonthlyYearList(String responseTimeStart, String responseTimeEnd)
			throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("responseTimeStart", responseTimeStart);
		sourceMap.put("responseTimeEnd", responseTimeEnd);
		return availDao.getAvailMonthlyYearList(sourceMap);
	}
	
	@Override
	public List<AvailReport> getAvailMonthlyReport(String serverName, String responseTimeStart,
			String responseTimeEnd) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("serverName", serverName);
		sourceMap.put("responseTimeStart", responseTimeStart);
		sourceMap.put("responseTimeEnd", responseTimeEnd);
		return availDao.getAvailMonthlyReport(sourceMap);
	}
}
