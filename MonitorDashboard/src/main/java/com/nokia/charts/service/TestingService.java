package com.nokia.charts.service;

import java.util.List;

import com.nokia.charts.dto.BaselineDto;
import com.nokia.charts.entity.testing.FunctionalFailLog;
import com.nokia.charts.entity.testing.FunctionalModule;
import com.nokia.charts.entity.testing.FunctionalSummary;
import com.nokia.charts.entity.testing.Performance;
import com.nokia.charts.entity.testing.PerformanceBaseLine;
import com.nokia.charts.exception.MonitorException;

public interface TestingService {

	public List<String> selectProjectList() throws MonitorException;

	public List<String> selectFunctionTypeByProject(String project) throws MonitorException;

	public List<Performance> selectPerformanceList(String project, String environment, String functionType)
			throws MonitorException;

	public PerformanceBaseLine selectPerformanceBaseLine(String project, String environment, String functionType)
			throws MonitorException;

	public List<String> selectFunctionTestProject() throws MonitorException;

	public List<String> selectFunctionTestVersion(String project) throws MonitorException;

	public List<String> selectFunctionTestCycle(String project,String testVersion,String environment) throws MonitorException;

	public List<FunctionalSummary> selectFunctionalSummary(String project, String environment, String testVersion,
			String testCycle) throws MonitorException;

	public List<FunctionalModule> selectFunctionalModule(String project, String environment, String testVersion,
			String testCycle) throws MonitorException;

	public List<FunctionalFailLog> selectFunctionalFailLog(String project, String environment, String testVersion,
			String testCycle) throws MonitorException;

	public void addBaseline(BaselineDto baselineDto) throws MonitorException;

	public List<String> selectVersionByProject(String project) throws MonitorException;

	public List<Performance> selectPerformanceListByVersion(String project, String environment, String version)
			throws MonitorException;

	public List<PerformanceBaseLine> selectPerformanceBaseLineList(String project, String environment) throws MonitorException;
	
	public List<String> selectFunctionTypeByProjectAndEnvironment(String project,String environment) throws MonitorException;
	
	public List<String> selectVersionByProjectAndEnvironment(String project, String environment) throws MonitorException;
}
