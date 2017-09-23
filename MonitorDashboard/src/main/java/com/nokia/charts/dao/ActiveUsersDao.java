package com.nokia.charts.dao;

import java.util.List;
import java.util.Map;

import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.entity.ActiveUsers;
import com.nokia.charts.exception.MonitorException;

public interface ActiveUsersDao {

	public void addActiveUser(ActiveUsers activeUsers) throws MonitorException;

	public List<ActiveUsers> getActiveUsersList(Map<String, Object> sourceMap) throws MonitorException;

	public List<Map<String, Object>> getActiveUsersReportData(Map<String, Object> sourceMap) throws MonitorException;

	public List<SelectionOptionDto> getActiveUserServiceListById(Map<String, Object> sourceMap) throws MonitorException;

	public List<SelectionOptionDto> getDomainList() throws MonitorException;

	public List<SelectionOptionDto> getBundleList() throws MonitorException;

	public List<SelectionOptionDto> getServiceList() throws MonitorException;

	public List<SelectionOptionDto> getRelationDomainList() throws MonitorException;
}
