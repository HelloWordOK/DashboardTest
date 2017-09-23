package com.nokia.charts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.charts.dao.AdminDao;
import com.nokia.charts.dto.admin.DomainBundleServiceDto;
import com.nokia.charts.dto.admin.ServerMonitorDto;
import com.nokia.charts.entity.Monitor;
import com.nokia.charts.entity.admin.Bundle;
import com.nokia.charts.entity.admin.Domain;
import com.nokia.charts.entity.admin.Group;
import com.nokia.charts.entity.admin.Server;
import com.nokia.charts.entity.admin.ServiceEntity;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

	@Override
	public void addDomain(Domain domain) throws MonitorException {
		adminDao.addDomain(domain);
	}

	@Override
	public void addBundle(Bundle bundle) throws MonitorException {
		adminDao.addBundle(bundle);
	}

	@Override
	public void addService(ServiceEntity service) throws MonitorException {
		adminDao.addService(service);
	}

	@Override
	public void addServer(Server server) throws MonitorException {
		adminDao.addServer(server);
	}

	@Override
	public void addGroup(Group group) throws MonitorException {
		adminDao.addGroup(group);
	}

	@Override
	public void addRelation(DomainBundleServiceDto relation) throws MonitorException {
		adminDao.addRelation(relation);
	}

	@Override
	public List<Domain> getDomainList() throws MonitorException {
		return adminDao.getDomainList();
	}

	@Override
	public List<Bundle> getBundleList() throws MonitorException {
		return adminDao.getBundleList();
	}

	@Override
	public List<ServiceEntity> getServiceList() throws MonitorException {
		return adminDao.getServiceList();
	}

	@Override
	public List<Server> getServerList() throws MonitorException {
		return adminDao.getServerList();
	}

	@Override
	public List<Group> getGroupList() throws MonitorException {
		return adminDao.getGroupList();
	}

	@Override
	public List<DomainBundleServiceDto> getRelationList() throws MonitorException {
		return adminDao.getRelationList();
	}

	@Override
	public Bundle getBundleById(Integer id) throws MonitorException {
		return adminDao.getBundleById(id);
	}

	@Override
	public Domain getDomainById(Integer id) throws MonitorException {
		return adminDao.getDomainById(id);
	}

	@Override
	public ServiceEntity getServiceById(Integer id) throws MonitorException {
		return adminDao.getServiceById(id);
	}

	@Override
	public Group getGroupById(Integer id) throws MonitorException {
		return adminDao.getGroupById(id);
	}

	@Override
	public Server getServerById(Integer id) throws MonitorException {
		return adminDao.getServerById(id);
	}

	@Override
	public DomainBundleServiceDto getRelationById(Integer id) throws MonitorException {
		return adminDao.getRelationById(id);
	}

	@Override
	public void deleteGroupById(Integer id) throws MonitorException {
		adminDao.deleteGroupById(id);
	}

	@Override
	public void deleteServerById(Integer id) throws MonitorException {
		adminDao.deleteServerById(id);
	}

	@Override
	public void deleteDomainById(Integer id) throws MonitorException {
		adminDao.deleteDomainById(id);
	}

	@Override
	public void deleteBundleById(Integer id) throws MonitorException {
		adminDao.deleteBundleById(id);
	}

	@Override
	public void deleteServiceById(Integer id) throws MonitorException {
		adminDao.deleteServiceById(id);
	}

	@Override
	public void deleteRelationById(Integer id) throws MonitorException {
		adminDao.deleteRelationById(id);
	}

	@Override
	public void deleteRelationByDomainId(Integer domainId) throws MonitorException {
		adminDao.deleteRelationByDomainId(domainId);
	}

	@Override
	public void deleteRelationByBundleId(Integer bundleId) throws MonitorException {
		adminDao.deleteRelationByBundleId(bundleId);
	}

	@Override
	public void deleteRelationByServiceId(Integer serviceId) throws MonitorException {
		adminDao.deleteRelationByServiceId(serviceId);
	}

	@Override
	public void updateGroupById(Group group) throws MonitorException {
		adminDao.updateGroupById(group);
	}

	@Override
	public void updateServerById(Server server) throws MonitorException {
		adminDao.updateServerById(server);
	}

	@Override
	public void updateDomainById(Domain domain) throws MonitorException {
		adminDao.updateDomainById(domain);
	}

	@Override
	public void updateBundleById(Bundle bundle) throws MonitorException {
		adminDao.updateBundleById(bundle);
	}

	@Override
	public void updateServiceById(ServiceEntity service) throws MonitorException {
		adminDao.updateServiceById(service);
	}

	@Override
	public void updateRelationById(DomainBundleServiceDto relation) throws MonitorException {
		adminDao.updateRelationById(relation);
	}

	@Override
	public List<Monitor> getMonitorList() throws MonitorException {
		return adminDao.getMonitorList();
	}
	
	@Override
	public void addServerMonitor(ServerMonitorDto serverMonitor) throws MonitorException {
		adminDao.addServerMonitor(serverMonitor);
	}
	
	@Override
	public List<ServerMonitorDto> getServerMonitorList() throws MonitorException {
		return adminDao.getServerMonitorList();
	}
	
	@Override
	public void deleteServerMonitorById(Integer id) throws MonitorException {
		adminDao.deleteServerMonitorById(id);
	}

	@Override
	public ServerMonitorDto getServerMonitorById(Integer id) throws MonitorException {
		return adminDao.getServerMonitorById(id);
	}
	
	@Override
	public void updateServerMonitorById(ServerMonitorDto relation) throws MonitorException {
		adminDao.updateServerMonitorById(relation);
	}

	@Override
	public int getIsExistDomain(String domianName) throws MonitorException {
		return adminDao.getIsExistDomain(domianName);
	}

	@Override
	public int getIsExistBundle(String bundleName) throws MonitorException {
		return adminDao.getIsExistBundle(bundleName);
	}

	@Override
	public int getIsExistService(String serviceCode) throws MonitorException {
		return adminDao.getIsExistService(serviceCode);
	}
	
	@Override
	public int getIsExistDomainBundleService(DomainBundleServiceDto dataDto) throws MonitorException {
		return adminDao.getIsExistDomainBundleService(dataDto);
	}
	
	@Override
	public int getIsExistServerMonitor(ServerMonitorDto serverMonitor) throws MonitorException {
		return adminDao.getIsExistServerMonitor(serverMonitor);
	}

}
