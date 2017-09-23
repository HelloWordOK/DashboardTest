package com.nokia.charts.dao;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.ERDto;
import com.nokia.charts.dto.ERSumDto;
import com.nokia.charts.dto.ExportERDto;
import com.nokia.charts.entity.EREntity;
import com.nokia.charts.entity.admin.Bundle;
import com.nokia.charts.exception.MonitorException;

public interface ERDao {

	public List<String> getSprintListByBundleId(String bundleId) throws MonitorException;

	public ERSumDto getSumERData(Map<String, Object> sourceMap) throws MonitorException;

	public String getSumERStartDate(Map<String, Object> sourceMap) throws MonitorException;

	public String getSumEREndDate(Map<String, Object> sourceMap) throws MonitorException;

	public List<ERDto> getBeforeTenData(Map<String, Object> sourceMap) throws MonitorException;

	public List<ExportERDto> getExportERData(Map<String, Object> sourceMap) throws MonitorException;

	public Integer checkReleaseSprint(EREntity sER) throws MonitorException;
	
	public Integer insertSubmitData(EREntity sER) throws MonitorException;
	
	public Integer updateSubmitData(EREntity sER) throws MonitorException;

	public List<Bundle> getBundleListToSearch() throws MonitorException;

	public List<Bundle> getBundleListToInsert() throws MonitorException;
}
