package com.nokia.charts.dao;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.qa.SRResultDto;
import com.nokia.charts.entity.qa.SRImport;
import com.nokia.charts.exception.MonitorException;

public interface SRImportDao {

	public void insertSRList(List<SRImport> sRImportList) throws MonitorException;

	public List<SRResultDto> getSRAllMonthOrWeekCreatedList(Map<String, Object> sourceMap) throws MonitorException;

	public List<SRResultDto> getSRAllMonthOrWeekClosedList(Map<String, Object> sourceMap) throws MonitorException;

	public int getOpendBacklogCount(Map<String, Object> sourceMap) throws MonitorException;

	public int getClosedBacklogCount(Map<String, Object> sourceMap) throws MonitorException;

	public List<Map<String, Object>> getAllSRMonthlyList(Map<String, Object> sourceMap) throws MonitorException;
	
	public List<Map<String, Object>> getSRServiceClosedList(Map<String, Object> sourceMap) throws MonitorException;

	public void updateSRRealBreach(Map<String, Object> sourceMap) throws MonitorException;
}
