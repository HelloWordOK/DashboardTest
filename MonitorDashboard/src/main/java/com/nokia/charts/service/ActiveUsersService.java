package com.nokia.charts.service;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.ActiveUsersDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.entity.ActiveUsers;
import com.nokia.charts.exception.MonitorException;

public interface ActiveUsersService {

	public void addActiveUser(ActiveUsers activeUsers) throws MonitorException;

	public Map<String, List<ActiveUsers>> getActiveUsers(ActiveUsersDto activeUsersDto) throws MonitorException;

	public List<Map<String, Object>> getActiveUsersReportData(ActiveUsersDto activeUsersDto) throws MonitorException;

	public List<SelectionOptionDto> getActiveUserServiceListById(Map<String, Object> sourceMap)	throws MonitorException;

	public List<SelectionOptionDto> getDomainList() throws MonitorException;

	public List<SelectionOptionDto> getBundleList() throws MonitorException;

	public List<SelectionOptionDto> getServiceList() throws MonitorException;

	public List<SelectionOptionDto> getRelationDomainList() throws MonitorException;
}
