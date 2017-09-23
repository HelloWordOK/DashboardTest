package com.nokia.charts.service;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.qa.QAResultDataDto;
import com.nokia.charts.dto.qa.data.ServiceDataSearchDto;
import com.nokia.charts.entity.admin.Domain;
import com.nokia.charts.entity.admin.ServiceEntity;
import com.nokia.charts.entity.qa.IncidentImport;
import com.nokia.charts.entity.qa.ProblemImport;
import com.nokia.charts.entity.qa.SRImport;
import com.nokia.charts.exception.MonitorException;

public interface QAService {

	public void insertIncidentList(List<IncidentImport> incidentImportList) throws MonitorException;

	public void insertProblemList(List<ProblemImport> problemImportList) throws MonitorException;

	public void insertSRList(List<SRImport> sRImportList) throws MonitorException;

	public List<ServiceEntity> getQAServiceList() throws MonitorException;
	
	public List<ServiceEntity> getServiceListToMajor() throws MonitorException;
	
	public List<Domain> getQADomainList() throws MonitorException;

	public ServiceEntity getQAServiceByCode(String serviceCode) throws MonitorException;
	
	public Domain getQADomainById(String domainId) throws MonitorException;

	public List<QAResultDataDto> getIncidentMonthlyList(List<Object> monthList, List<String> groupList)
			throws MonitorException;

	public List<QAResultDataDto> getIncidentWeeklyList(Map<Object, List<String>> weekMap, List<String> groupList)
			throws MonitorException;

	public List<QAResultDataDto> getSRMonthlyList(List<Object> monthList, List<String> groupList)
			throws MonitorException;

	public List<QAResultDataDto> getSRWeeklyList(Map<Object, List<String>> weekMap, List<String> groupList)
			throws MonitorException;

	public List<QAResultDataDto> getProblemMonthlyList(List<Object> monthList, List<String> groupList)
			throws MonitorException;

	public List<QAResultDataDto> getProblemWeeklyList(Map<Object, List<String>> weekMap, List<String> groupList)
			throws MonitorException;

	public List<String> getQAGroupNameByServiceCode(String serviceCode) throws MonitorException;
	
	public List<String> getQAGroupNameByDomain(String domainId) throws MonitorException;

	public List<Map<String, Object>> getResultList(List<Object> monthList, List<String> groupList, String chartType)
			throws MonitorException;

	public List<Map<String, Object>> getServiceDataClosedList(ServiceDataSearchDto searchDto) throws MonitorException;

	public void updateServiceRealBreach(ServiceDataSearchDto searchDto) throws MonitorException;
}
