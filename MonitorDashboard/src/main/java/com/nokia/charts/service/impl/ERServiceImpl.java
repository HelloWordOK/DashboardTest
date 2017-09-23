package com.nokia.charts.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.charts.dao.ERDao;
import com.nokia.charts.dto.ERDto;
import com.nokia.charts.dto.ERSumDto;
import com.nokia.charts.dto.ExportERDto;
import com.nokia.charts.entity.EREntity;
import com.nokia.charts.entity.admin.Bundle;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.ERService;

@Service
public class ERServiceImpl implements ERService {

	@Autowired
	private ERDao eRDao;

	@Override
	public List<String> getSprintListByBundleId(String bundleId) throws MonitorException {
		return eRDao.getSprintListByBundleId(bundleId);
	}

	@Override
	public ERSumDto getSumERData(String bundleId, String sprintStart, String sprintEnd) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("bundleId", bundleId);
		sourceMap.put("sprintStart", sprintStart);
		sourceMap.put("sprintEnd", sprintEnd);
		ERSumDto statisticsERSumDto = eRDao.getSumERData(sourceMap);
		String startDate = eRDao.getSumERStartDate(sourceMap);
		String endDate = eRDao.getSumEREndDate(sourceMap);
		if (statisticsERSumDto != null) {
			statisticsERSumDto.setStartDate(startDate);
			statisticsERSumDto.setEndDate(endDate);
		}
		return statisticsERSumDto;
	}

	@Override
	public List<ERDto> getBeforeTenData(String bundleId, String sprintEnd) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("bundleId", bundleId);
		sourceMap.put("sprintEnd", sprintEnd);
		List<ERDto> statisticsERList = eRDao.getBeforeTenData(sourceMap);
		return statisticsERList;
	}

	@Override
	public List<ExportERDto> getExportERData(String bundleId, String sprintStart, String sprintEnd)
			throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("bundleId", bundleId);
		sourceMap.put("sprintStart", sprintStart);
		sourceMap.put("sprintEnd", sprintEnd);
		return eRDao.getExportERData(sourceMap);
	}

	/**
	 * Check whether the release sprint exists
	 * @param relase
	 */
	@Override
	public Integer checkReleaseSprint(EREntity sER) throws MonitorException {
		return eRDao.checkReleaseSprint(sER);
	}

	/**
	 * Insert submit data
	 * @param relase
	 */
	@Override
	public Integer insertSubmitData(EREntity sER) throws MonitorException {
		return eRDao.insertSubmitData(sER);
	}

	/**
	 * Update submit data
	 * @param relase
	 */
	@Override
	public Integer updateSubmitData(EREntity sER) throws MonitorException {
		return eRDao.updateSubmitData(sER);
	}
	
	@Override
	public List<Bundle> getBundleListToSearch() throws MonitorException {
		return eRDao.getBundleListToSearch();
	}
	
	@Override
	public List<Bundle> getBundleListToInsert() throws MonitorException {
		return eRDao.getBundleListToInsert();
	}
}
