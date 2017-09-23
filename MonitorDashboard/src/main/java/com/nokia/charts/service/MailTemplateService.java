package com.nokia.charts.service;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.MailTemplateDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.exception.MonitorException;

public interface MailTemplateService {

	public List<MailTemplateDto> selecTemplateByServiceId(String serviceId) throws MonitorException;

	public void insertTemplate(Map<String, Object> sourceMap) throws MonitorException;

	public MailTemplateDto selecTemplateByTemplateId(String tempId) throws MonitorException;

	public void insertSendMailHistory(Map<String, Object> sourceMap) throws MonitorException;

	public List<SelectionOptionDto> getServiceList() throws MonitorException;
	
	public List<MailTemplateDto> selectAllTemplate() throws MonitorException;

	public String selectServieNameByServiceId(Integer serviceId) throws MonitorException;
}
