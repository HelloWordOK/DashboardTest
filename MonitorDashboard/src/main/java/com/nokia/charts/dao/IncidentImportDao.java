package com.nokia.charts.dao;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.qa.IncidentResultDto;
import com.nokia.charts.entity.admin.Domain;
import com.nokia.charts.entity.admin.ServiceEntity;
import com.nokia.charts.entity.qa.IncidentImport;
import com.nokia.charts.exception.MonitorException;

public interface IncidentImportDao {

	public void insertIncidentList(List<IncidentImport> incidentImportList) throws MonitorException;

	public List<ServiceEntity> getQAServiceList() throws MonitorException;
	
	public List<ServiceEntity> getServiceListToMajor() throws MonitorException;
	
	public List<Domain> getQADomainList() throws MonitorException;

	public ServiceEntity getQAServiceByCode(String serviceCode) throws MonitorException;

	public Domain getQADomainById(String domainId) throws MonitorException;
	
	public List<String> getQAGroupNameByServiceCode(String serviceCode) throws MonitorException;
	
	public List<String> getQAGroupNameByDomain(String domainId) throws MonitorException;
	
	public List<IncidentResultDto> getIncidentAllMonthOrWeekCreatedList(Map<String, Object> sourceMap)
			throws MonitorException;

	public List<IncidentResultDto> getIncidentAllMonthOrWeekClosedList(Map<String, Object> sourceMap)
			throws MonitorException;

	public int getOpendBacklogCount(Map<String, Object> sourceMap) throws MonitorException;

	public int getClosedBacklogCount(Map<String, Object> sourceMap) throws MonitorException;
	
	public List<Map<String, Object>> getAllIncidentMonthlyList(Map<String, Object> sourceMap)
			throws MonitorException;
	
	public List<Map<String, Object>> getIncidentServiceClosedList(Map<String, Object> sourceMap) throws MonitorException;

	public void updateIncidentRealBreach(Map<String, Object> sourceMap) throws MonitorException;

}
