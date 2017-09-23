package com.nokia.charts.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.charts.dao.TestingDao;
import com.nokia.charts.dto.BaselineDto;
import com.nokia.charts.entity.testing.FunctionalFailLog;
import com.nokia.charts.entity.testing.FunctionalModule;
import com.nokia.charts.entity.testing.FunctionalSummary;
import com.nokia.charts.entity.testing.Performance;
import com.nokia.charts.entity.testing.PerformanceBaseLine;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.TestingService;

@Service
public class TestingServiceImpl implements TestingService {

	@Autowired
	private TestingDao testingDao;

	@Override
	public List<Performance> selectPerformanceList(String project, String environment, String functionType)
			throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("environment", environment);
		sourceMap.put("functionType", functionType);
		return testingDao.selectPerformanceList(sourceMap);
	}

	@Override
	public List<String> selectProjectList() throws MonitorException {
		return testingDao.selectProjectList();
	}

	@Override
	public List<String> selectFunctionTypeByProject(String project) throws MonitorException {
		return testingDao.selectFunctionTypeByProject(project);
	}

	@Override
	public List<String> selectFunctionTypeByProjectAndEnvironment(String project, String environment) throws MonitorException {
		return testingDao.selectFunctionTypeByProjectAndEnvironment(project,environment);
	}
	
	@Override
	public List<String> selectVersionByProjectAndEnvironment(String project, String environment) throws MonitorException {
		return testingDao.selectVersionByProjectAndEnvironment(project,environment);
	}
	
	
	@Override
	public PerformanceBaseLine selectPerformanceBaseLine(String project, String environment, String functionType)
			throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("environment", environment);
		sourceMap.put("functionType", functionType);
		return testingDao.selectPerformanceBaseLine(sourceMap);
	}

	@Override
	public List<FunctionalSummary> selectFunctionalSummary(String project, String environment, String testVersion,
			String testCycle) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("environment", environment);
		sourceMap.put("testVersion", testVersion);
		sourceMap.put("testCycle", testCycle);
		return testingDao.selectFunctionalSummary(sourceMap);
	}

	@Override
	public List<FunctionalModule> selectFunctionalModule(String project, String environment, String testVersion,
			String testCycle) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("environment", environment);
		sourceMap.put("testVersion", testVersion);
		sourceMap.put("testCycle", testCycle);
		return testingDao.selectFunctionalModule(sourceMap);
	}

	@Override
	public List<FunctionalFailLog> selectFunctionalFailLog(String project, String environment, String testVersion,
			String testCycle) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("environment", environment);
		sourceMap.put("testVersion", testVersion);
		sourceMap.put("testCycle", testCycle);
		return testingDao.selectFunctionalFailLog(sourceMap);
	}

	@Override
	public List<String> selectFunctionTestProject() throws MonitorException {
		return testingDao.selectFunctionTestProject();
	}

	@Override
	public List<String> selectFunctionTestVersion(String project) throws MonitorException {
		return testingDao.selectFunctionTestVersion(project);
	}

	@Override
	public List<String> selectFunctionTestCycle(String project,String testVersion,String environment) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("testVersion", testVersion);
		sourceMap.put("environment", environment);
		return testingDao.selectFunctionTestCycle(sourceMap);
	}

	@Override
	public void addBaseline(BaselineDto baselineDto) throws MonitorException {
		testingDao.addBaseline(baselineDto);
	}

	@Override
	public List<String> selectVersionByProject(String project) throws MonitorException {
		return testingDao.selectVersionByProject(project);
	}

	@Override
	public List<Performance> selectPerformanceListByVersion(String project,
			String environment, String version) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("environment", environment);
		sourceMap.put("version", version);
		return testingDao.selectPerformanceListByVersion(sourceMap);
	}

	@Override
	public List<PerformanceBaseLine> selectPerformanceBaseLineList(
			String project, String environment) throws MonitorException {
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("project", project);
		sourceMap.put("environment", environment);
		return testingDao.selectPerformanceBaseLineList(sourceMap);
	}

}
