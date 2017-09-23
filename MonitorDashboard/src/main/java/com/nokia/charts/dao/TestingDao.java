package com.nokia.charts.dao;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.BaselineDto;
import com.nokia.charts.entity.testing.FunctionalFailLog;
import com.nokia.charts.entity.testing.FunctionalModule;
import com.nokia.charts.entity.testing.FunctionalSummary;
import com.nokia.charts.entity.testing.Performance;
import com.nokia.charts.entity.testing.PerformanceBaseLine;
import com.nokia.charts.exception.MonitorException;

public interface TestingDao {

	public List<String> selectProjectList() throws MonitorException;

	public List<String> selectFunctionTypeByProject(String project) throws MonitorException;

	public List<String> selectFunctionTypeByProjectAndEnvironment(String project,String environment) throws MonitorException;
	
	public List<Performance> selectPerformanceList(Map<String, Object> sourceMap) throws MonitorException;

	public PerformanceBaseLine selectPerformanceBaseLine(Map<String, Object> sourceMap) throws MonitorException;

	public List<String> selectFunctionTestProject() throws MonitorException;

	public List<String> selectFunctionTestVersion(String project) throws MonitorException;

	public List<String> selectFunctionTestCycle(Map<String, Object> sourceMap) throws MonitorException;

	public List<FunctionalSummary> selectFunctionalSummary(Map<String, Object> sourceMap) throws MonitorException;

	public List<FunctionalModule> selectFunctionalModule(Map<String, Object> sourceMap) throws MonitorException;

	public List<FunctionalFailLog> selectFunctionalFailLog(Map<String, Object> sourceMap) throws MonitorException;

	public void addBaseline(BaselineDto baselineDto) throws MonitorException;

	public List<String> selectVersionByProject(String project) throws MonitorException;

	public List<Performance> selectPerformanceListByVersion(Map<String, Object> sourceMap) throws MonitorException;

	public List<PerformanceBaseLine> selectPerformanceBaseLineList(Map<String, Object> sourceMap) throws MonitorException;

	public List<String> selectVersionByProjectAndEnvironment(String project, String environment) throws MonitorException;
}
