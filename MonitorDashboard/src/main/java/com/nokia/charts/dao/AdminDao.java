package com.nokia.charts.dao;

import java.util.List;

import com.nokia.charts.dto.admin.DomainBundleServiceDto;
import com.nokia.charts.dto.admin.ServerMonitorDto;
import com.nokia.charts.entity.Monitor;
import com.nokia.charts.entity.admin.Bundle;
import com.nokia.charts.entity.admin.Domain;
import com.nokia.charts.entity.admin.Group;
import com.nokia.charts.entity.admin.Server;
import com.nokia.charts.entity.admin.ServiceEntity;
import com.nokia.charts.exception.MonitorException;

public interface AdminDao {

	//add
	public void addDomain(Domain domain) throws MonitorException;

	public void addBundle(Bundle bundle) throws MonitorException;

	public void addService(ServiceEntity service) throws MonitorException;
	
	public void addServer(Server server) throws MonitorException;
	
	public void addGroup(Group group) throws MonitorException;
	
	public void addRelation(DomainBundleServiceDto relation) throws MonitorException;
	
	public void addServerMonitor(ServerMonitorDto serverMonitor) throws MonitorException;

	//get
	public List<Domain> getDomainList() throws MonitorException;

	public List<Bundle> getBundleList() throws MonitorException;

	public List<ServiceEntity> getServiceList() throws MonitorException;

	public List<Server> getServerList() throws MonitorException;
	
	public List<Group> getGroupList() throws MonitorException;
	
	public List<DomainBundleServiceDto> getRelationList() throws MonitorException;
	
	public List<Monitor> getMonitorList() throws MonitorException;

	public List<ServerMonitorDto> getServerMonitorList() throws MonitorException;
	
	public Domain getDomainById(Integer id) throws MonitorException;
	
	public Bundle getBundleById(Integer id) throws MonitorException;
	
	public ServiceEntity getServiceById(Integer id) throws MonitorException;
	
	public Server getServerById(Integer id) throws MonitorException;
	
	public Group getGroupById(Integer id) throws MonitorException;
	
	public DomainBundleServiceDto getRelationById(Integer id) throws MonitorException;
	
	public ServerMonitorDto getServerMonitorById(Integer id) throws MonitorException;
	
	public int getIsExistDomain(String domianName) throws MonitorException;
	
	public int getIsExistBundle(String bundleName) throws MonitorException;
	
	public int getIsExistService(String serviceCode) throws MonitorException;
	
	public int getIsExistDomainBundleService(DomainBundleServiceDto sourceDto) throws MonitorException;

	// update
	public void updateDomainById(Domain domain) throws MonitorException;
	
	public void updateBundleById(Bundle bundle) throws MonitorException;
	
	public void updateServiceById(ServiceEntity service) throws MonitorException;
	
	public void updateGroupById(Group group) throws MonitorException;

	public void updateServerById(Server server) throws MonitorException;

	public void updateRelationById(DomainBundleServiceDto relation) throws MonitorException;
	
	public void updateServerMonitorById(ServerMonitorDto serverMonitor) throws MonitorException;

	// delete
	public void deleteDomainById(Integer id) throws MonitorException;
	
	public void deleteBundleById(Integer id) throws MonitorException;
	
	public void deleteServiceById(Integer id) throws MonitorException;
	
	public void deleteGroupById(Integer id) throws MonitorException;

	public void deleteServerById(Integer id) throws MonitorException;

	public void deleteRelationById(Integer id) throws MonitorException;

	public void deleteRelationByDomainId(Integer domainId) throws MonitorException;

	public void deleteRelationByBundleId(Integer bundleId) throws MonitorException;

	public void deleteRelationByServiceId(Integer serviceId) throws MonitorException;

	public void deleteServerMonitorById(Integer id) throws MonitorException;

	public int getIsExistServerMonitor(ServerMonitorDto serverMonitor) throws MonitorException;

}
