package com.nokia.charts.dao;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.qa.ProblemResultDto;
import com.nokia.charts.entity.qa.ProblemImport;
import com.nokia.charts.exception.MonitorException;

public interface ProblemImportDao {

	public void insertProblemList(List<ProblemImport> problemImportList) throws MonitorException;

	public List<ProblemResultDto> getProblemAllMonthOrWeekCreatedList(Map<String, Object> sourceMap)
			throws MonitorException;

	public List<ProblemResultDto> getProblemAllMonthOrWeekClosedList(Map<String, Object> sourceMap)
			throws MonitorException;

	public int getOpendBacklogCount(Map<String, Object> sourceMap) throws MonitorException;

	public int getClosedBacklogCount(Map<String, Object> sourceMap) throws MonitorException;

	public List<Map<String, Object>> getAllProblemMonthlyList(Map<String, Object> sourceMap) throws MonitorException;
	
	public List<Map<String, Object>> getProblemServiceClosedList(Map<String, Object> sourceMap) throws MonitorException;

	public void updateProblemRealBreach(Map<String, Object> sourceMap) throws MonitorException;
}
