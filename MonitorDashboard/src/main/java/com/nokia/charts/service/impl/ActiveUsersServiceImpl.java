package com.nokia.charts.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.charts.dao.ActiveUsersDao;
import com.nokia.charts.dto.ActiveUsersDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.entity.ActiveUsers;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.ActiveUsersService;

@Service
public class ActiveUsersServiceImpl implements ActiveUsersService {

	@Autowired
	private ActiveUsersDao activeUsersDao;

	@Override
	public void addActiveUser(ActiveUsers activeUsers) throws MonitorException {
		activeUsersDao.addActiveUser(activeUsers);
	}

	@Override
	public Map<String, List<ActiveUsers>> getActiveUsers(ActiveUsersDto activeUsersDto) throws MonitorException {
		Map<String, List<ActiveUsers>> activeUserListMap = new LinkedHashMap<String, List<ActiveUsers>>();
		List<Integer> serviceIdList = new ArrayList<Integer>();
		Collections.addAll(serviceIdList, activeUsersDto.getServiceIdList());
		List<ActiveUsers> activeUsersList = new ArrayList<ActiveUsers>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		String startTime = activeUsersDto.getStartTime();
		String endTime = activeUsersDto.getEndTime();
		sourceMap.put("startTime", startTime);
		sourceMap.put("endTime", endTime);
		for (Integer serviceId : serviceIdList) {
			sourceMap.put("serviceId", serviceId);
			activeUsersList = activeUsersDao.getActiveUsersList(sourceMap);
			activeUserListMap.put(String.valueOf(serviceId), activeUsersList);
		}
		return activeUserListMap;
	}

	@Override
	public List<Map<String, Object>> getActiveUsersReportData(ActiveUsersDto activeUsersDto) throws MonitorException {
		List<Integer> serviceIdList = new ArrayList<Integer>();
		Collections.addAll(serviceIdList, activeUsersDto.getServiceIdList());
		List<Map<String, Object>> activeUsersList = new ArrayList<Map<String, Object>>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		String startTime = activeUsersDto.getStartTime();
		String endTime = activeUsersDto.getEndTime();
		sourceMap.put("startTime", startTime);
		sourceMap.put("endTime", endTime);
		sourceMap.put("serviceIdList", serviceIdList);
		activeUsersList = activeUsersDao.getActiveUsersReportData(sourceMap);
		return activeUsersList;
	}

	@Override
	public List<SelectionOptionDto> getActiveUserServiceListById(Map<String, Object> sourceMap) throws MonitorException {
		return activeUsersDao.getActiveUserServiceListById(sourceMap);
	}

	@Override
	public List<SelectionOptionDto> getBundleList() throws MonitorException {
		return activeUsersDao.getBundleList();
	}

	@Override
	public List<SelectionOptionDto> getDomainList() throws MonitorException {
		return activeUsersDao.getDomainList();
	}

	@Override
	public List<SelectionOptionDto> getServiceList() throws MonitorException {
		return activeUsersDao.getServiceList();
	}
	
	@Override
	public List<SelectionOptionDto> getRelationDomainList() throws MonitorException {
		return activeUsersDao.getRelationDomainList();
	}
}
