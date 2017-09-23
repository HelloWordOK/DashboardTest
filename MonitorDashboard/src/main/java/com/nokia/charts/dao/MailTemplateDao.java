package com.nokia.charts.dao;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.MailTemplateDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.exception.MonitorException;

public interface MailTemplateDao {

	public void insertMailTemplate(Map<String, Object> sourceMap) throws MonitorException;

	public List<MailTemplateDto> selecTemplateByServiceId(String serviceId) throws MonitorException;

	public MailTemplateDto selecTemplateByTemplateId(String tempId) throws MonitorException;

	public void insertSendMailHistory(Map<String, Object> sourceMap) throws MonitorException;

	public List<SelectionOptionDto> getServiceList() throws MonitorException;
	
	public List<MailTemplateDto> selectAllTemplate() throws MonitorException;

	public String selectServieNameByServiceId(Integer serviceId) throws MonitorException;
}
