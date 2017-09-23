package com.nokia.charts.service;

import java.util.List;

import com.nokia.charts.dto.ERDto;
import com.nokia.charts.dto.ERSumDto;
import com.nokia.charts.dto.ExportERDto;
import com.nokia.charts.entity.EREntity;
import com.nokia.charts.entity.admin.Bundle;
import com.nokia.charts.exception.MonitorException;

public interface ERService {

	public List<String> getSprintListByBundleId(String bundleId) throws MonitorException;

	public ERSumDto getSumERData(String bundleId, String sprintStart, String sprintEnd) throws MonitorException;

	public List<ERDto> getBeforeTenData(String team, String sprintEnd) throws MonitorException;

	public List<ExportERDto> getExportERData(String team, String sprintStart, String sprintEnd)
			throws MonitorException;

	/**
	 * Check release sprint whether exists
	 * @param sER
	 */
	public Integer checkReleaseSprint(EREntity sER) throws MonitorException;
	
	/**
	 * Insert submit data to database
	 * @param sER
	 */
	public Integer insertSubmitData(EREntity sER) throws MonitorException;
	
	/**
	 * Update submit data to database
	 * @param sER
	 */
	public Integer updateSubmitData(EREntity sER) throws MonitorException;

	public List<Bundle> getBundleListToSearch() throws MonitorException;

	public List<Bundle> getBundleListToInsert() throws MonitorException;
}
