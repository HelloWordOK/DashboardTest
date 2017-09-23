package com.nokia.charts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.charts.dao.MailTemplateDao;
import com.nokia.charts.dto.MailTemplateDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.MailTemplateService;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {

	@Autowired
	private MailTemplateDao mailTemplateDao;

	@Override
	public List<MailTemplateDto> selecTemplateByServiceId(String serviceId)
			throws MonitorException {
		return mailTemplateDao.selecTemplateByServiceId(serviceId);
	}

	@Override
	public void insertTemplate(Map<String, Object> sourceMap)
			throws MonitorException {
		mailTemplateDao.insertMailTemplate(sourceMap);
	}

	@Override
	public MailTemplateDto selecTemplateByTemplateId(String tempId)
			throws MonitorException {
		return mailTemplateDao.selecTemplateByTemplateId(tempId);
	}

	@Override
	public void insertSendMailHistory(Map<String, Object> sourceMap)
			throws MonitorException {
		mailTemplateDao.insertSendMailHistory(sourceMap);;
	}

	@Override
	public List<SelectionOptionDto> getServiceList() throws MonitorException {
		return mailTemplateDao.getServiceList();
	}

	@Override
	public List<MailTemplateDto> selectAllTemplate() throws MonitorException {
		return mailTemplateDao.selectAllTemplate();
	}
	
	@Override
	public String selectServieNameByServiceId(Integer serviceId) throws MonitorException {
		return mailTemplateDao.selectServieNameByServiceId(serviceId);
	}

}
