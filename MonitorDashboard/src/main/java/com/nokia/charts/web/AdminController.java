package com.nokia.charts.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.nokia.charts.util.JsonUtils;
import com.nokia.charts.web.base.EchartController;

@Controller
@RequestMapping("/admin")
public class AdminController extends EchartController {

	@Autowired
	private AdminService adminService;

	// Domain
	@RequestMapping("/input_domain")
	public String inputDomain(Model model) {
		return "admin/domain/input_domain";
	}

	/**
	 * 添加domain
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/input_domain_submit")
	@ResponseBody
	public String inputDomainSubmit(@RequestParam String param) {

		try {
			Domain domain = JsonUtils.json2object(param, Domain.class);
			int isExistDomain = adminService.getIsExistDomain(domain.getDomainName());
			if(isExistDomain!=0){
				return "fail";
			}
			adminService.addDomain(domain);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 获取domain list
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/domain_list")
	public String domainList(Model model) {
		List<Domain> domainList = new ArrayList<Domain>();
		try {
			domainList = adminService.getDomainList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("domainList", domainList);
		return "admin/domain/domain_list";
	}

	/**
	 * 根据id获取需要更新的domain
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/update_domain")
	@ResponseBody
	public Domain updateDomain(@RequestParam(value = "id") Integer id) {
		Domain domain = new Domain();
		try {
			domain = adminService.getDomainById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return domain;
	}

	/**
	 * 根据id更新domain
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/update_domain_submit")
	@ResponseBody
	public String updateDomainSubmit(String param) {
		try {
			Domain domain = JsonUtils.json2object(param, Domain.class);
			int isExistDomain = adminService.getIsExistDomain(domain.getDomainName());
			if(isExistDomain!=0){
				return "fail";
			}
			adminService.updateDomainById(domain);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 根据id 删除bundle
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_domain")
	@ResponseBody
	public String deleteDomain(Integer id) {
		try {
			adminService.deleteDomainById(id);
			adminService.deleteRelationByDomainId(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	// bundle
	@RequestMapping("/input_bundle")
	public String inputBundle(Model model) {
		return "admin/bundle/input_bundle";
	}

	/**
	 * 添加bundle
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/input_bundle_submit")
	@ResponseBody
	public String inputBundleSubmit(@RequestParam String param) {

		try {
			Bundle bundle = JsonUtils.json2object(param, Bundle.class);
			int isExistBundle = adminService.getIsExistBundle(bundle.getBundleName());
			if(isExistBundle!=0){
				return "fail";
			}
			adminService.addBundle(bundle);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取bundle list
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/bundle_list")
	public String bundleList(Model model) {
		List<Bundle> bundleList = new ArrayList<Bundle>();
		try {
			bundleList = adminService.getBundleList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("bundleList", bundleList);
		return "admin/bundle/bundle_list";
	}

	/**
	 * 根据id获取需要更新的bundle
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/update_bundle")
	@ResponseBody
	public Bundle updateBundle(@RequestParam(value = "id") Integer id) {
		Bundle bundle = new Bundle();
		try {
			bundle = adminService.getBundleById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return bundle;
	}

	/**
	 * 根据id更新bundle
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/update_bundle_submit")
	@ResponseBody
	public String updateBundleSubmit(String param) {
		try {
			Bundle bundle = JsonUtils.json2object(param, Bundle.class);
			int isExistBundle = adminService.getIsExistBundle(bundle.getBundleName());
			if(isExistBundle!=0){
				return "fail";
			}
			adminService.updateBundleById(bundle);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据id 删除bundle
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_bundle")
	@ResponseBody
	public String deleteBundle(Integer id) {
		try {
			adminService.deleteBundleById(id);
			adminService.deleteRelationByBundleId(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	// service
	@RequestMapping("/input_service")
	public String inputService(Model model) {
		return "admin/service/input_service";
	}

	/**
	 * 添加service
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/input_service_submit")
	@ResponseBody
	public String inputServiceSubmit(@RequestParam String param) {

		try {
			ServiceEntity service = JsonUtils.json2object(param, ServiceEntity.class);
			int isExistService = adminService.getIsExistService(service.getServiceCode());
			if(isExistService!=0){
				return "fail";
			}
			adminService.addService(service);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取service list
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/service_list")
	public String serviceList(Model model) {
		List<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();
		try {
			serviceList = adminService.getServiceList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("serviceList", serviceList);
		return "admin/service/service_list";
	}

	/**
	 * 根据id获取需要更新的service
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/update_service")
	@ResponseBody
	public ServiceEntity updateService(@RequestParam(value = "id") Integer id) {
		ServiceEntity service = new ServiceEntity();
		try {
			service = adminService.getServiceById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return service;
	}

	/**
	 * 根据id更新service
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/update_service_submit")
	@ResponseBody
	public String updateServiceSubmit(String param) {
		try {
			ServiceEntity service = JsonUtils.json2object(param, ServiceEntity.class);
			ServiceEntity exitsService = adminService.getServiceById(service.getId());
			int isExistService = adminService.getIsExistService(service.getServiceCode());
			if(!exitsService.getServiceCode().equals(service.getServiceCode())&&isExistService!=0){
				return "fail";
			}
			adminService.updateServiceById(service);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据id 删除service
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_service")
	@ResponseBody
	public String deleteService(Integer id) {
		try {
			adminService.deleteServiceById(id);
			adminService.deleteRelationByServiceId(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Server
	@RequestMapping("/input_server")
	public String inputServer(Model model) {
		List<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();
		try {
			serviceList = adminService.getServiceList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("serviceList", serviceList);
		return "admin/servers/input_server";
	}

	/**
	 * 添加server
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/input_server_submit")
	@ResponseBody
	public String inputServerSubmit(@RequestParam String param) {

		try {
			Server server = JsonUtils.json2object(param, Server.class);
			adminService.addServer(server);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取server list
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/server_list")
	public String serverList(Model model) {
		List<Server> serverList = new ArrayList<Server>();
		try {
			serverList = adminService.getServerList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("serverList", serverList);
		return "admin/servers/server_list";
	}

	/**
	 * 根据id获取需要更新的server
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/update_server")
	@ResponseBody
	public Server updateServer(@RequestParam(value = "id") Integer id) {
		Server server = new Server();
		try {
			server = adminService.getServerById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return server;
	}

	/**
	 * 根据id更新server
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/update_server_submit")
	@ResponseBody
	public String updateServerSubmit(String param) {
		try {
			Server server = JsonUtils.json2object(param, Server.class);
			adminService.updateServerById(server);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据id删除server
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_server")
	@ResponseBody
	public String deleteServer(Integer id) {
		try {
			adminService.deleteServerById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Group
	@RequestMapping("/input_group")
	public String inputGroup(Model model) {
		List<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();
		try {
			serviceList = adminService.getServiceList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("serviceList", serviceList);
		return "admin/groups/input_group";
	}

	/**
	 * 添加group
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/input_group_submit")
	@ResponseBody
	public String inputGroupSubmit(@RequestParam String param) {

		try {
			Group group = JsonUtils.json2object(param, Group.class);
			adminService.addGroup(group);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取group list
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/group_list")
	public String groupList(Model model) {
		List<Group> groupList = new ArrayList<Group>();
		try {
			groupList = adminService.getGroupList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("groupList", groupList);
		return "admin/groups/group_list";
	}

	/**
	 * 根据id删除group
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_group")
	@ResponseBody
	public String deleteGroup(Integer id) {
		try {
			adminService.deleteGroupById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据id获取需要更新的group
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/update_group")
	@ResponseBody
	public Group updateGroup(@RequestParam(value = "id") Integer id) {
		Group group = new Group();
		try {
			group = adminService.getGroupById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return group;
	}

	/**
	 * 根据id更新group
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/update_group_submit")
	@ResponseBody
	public String updateGroupSubmit(String param) {
		try {
			Group group = JsonUtils.json2object(param, Group.class);
			adminService.updateGroupById(group);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Relation
	@RequestMapping("/input_relation")
	public String inputRelation(Model model) {
		List<Domain> domainList = new ArrayList<Domain>();
		List<Bundle> bundleList = new ArrayList<Bundle>();
		List<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();
		try {
			domainList = adminService.getDomainList();
			bundleList = adminService.getBundleList();
			serviceList = adminService.getServiceList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("domainList", domainList);
		model.addAttribute("bundleList", bundleList);
		model.addAttribute("serviceList", serviceList);
		return "admin/relation/input_relation";
	}

	/**
	 * 添加domain bundle service 三者之间的关系
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/input_relation_submit")
	@ResponseBody
	public String inputRelationSubmit(@RequestParam String param) {
		try {
			DomainBundleServiceDto relation = JsonUtils.json2object(param, DomainBundleServiceDto.class);
			int isExistDomainBundleService = adminService.getIsExistDomainBundleService(relation);
			if(isExistDomainBundleService!=0){
				return "fail";
			}
			adminService.addRelation(relation);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 获取domain bundle service 三者之间的关系list
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/relation_list")
	public String relationList(Model model) {
		List<DomainBundleServiceDto> relationList = new ArrayList<DomainBundleServiceDto>();
		try {
			relationList = adminService.getRelationList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("relationList", relationList);
		return "admin/relation/relation_list";
	}

	/**
	 * 根据id删除domain bundle service 三者之间的关系
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_relation")
	@ResponseBody
	public String deleteRelation(Integer id) {
		try {
			adminService.deleteRelationById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据id获取需要更新的domain bundle service 三者之间的关系
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/update_relation")
	@ResponseBody
	public DomainBundleServiceDto updateRelation(@RequestParam(value = "id") Integer id) {
		DomainBundleServiceDto relation = new DomainBundleServiceDto();
		try {
			relation = adminService.getRelationById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return relation;
	}

	/**
	 * 根据id更新domain bundle service 三者之间的关系
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/update_relation_submit")
	@ResponseBody
	public String updateRelationSubmit(String param) {
		try {
			DomainBundleServiceDto relation = JsonUtils.json2object(param, DomainBundleServiceDto.class);
			int isExistDomainBundleService = adminService.getIsExistDomainBundleService(relation);
			if(isExistDomainBundleService!=0){
				return "fail";
			}
			adminService.updateRelationById(relation);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	// config_server_monitor
	@RequestMapping("/config_server_monitor")
	public String configServerMonitor(Model model) {
		List<Server> serverList = new ArrayList<Server>();
		List<Monitor> monitorList = new ArrayList<Monitor>();
		try {
			serverList = adminService.getServerList();
			monitorList = adminService.getMonitorList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("serverList", serverList);
		model.addAttribute("monitorList", monitorList);
		return "admin/relation/config_server_monitor";
	}
	
	/**
	 * 添加server monitor 之间的关系
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/config_server_monitor_submit")
	@ResponseBody
	public String configServerMonitorSubmit(@RequestParam String param) {
		try {
			ServerMonitorDto serverMonitor = JsonUtils.json2object(param, ServerMonitorDto.class);
			int isExistServerMonitor = adminService.getIsExistServerMonitor(serverMonitor);
			if(isExistServerMonitor!=0){
				return "fail";
			}
			adminService.addServerMonitor(serverMonitor);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 获取server monitor 之间的关系list
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/server_monitor_list")
	public String serverMonitorList(Model model) {
		List<ServerMonitorDto> serverMonitorList = new ArrayList<ServerMonitorDto>();
		try {
			serverMonitorList = adminService.getServerMonitorList();
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		model.addAttribute("serverMonitorList", serverMonitorList);
		return "admin/relation/server_monitor_list";
	}
	
	/**
	 * 根据id删除server monitor 之间的关系
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_server_monitor")
	@ResponseBody
	public String deleteServerMonitor(Integer id) {
		try {
			adminService.deleteServerMonitorById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 根据id获取需要更新server monitor 之间的关系
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/update_server_monitor")
	@ResponseBody
	public ServerMonitorDto updateServerMonitor(@RequestParam(value = "id") Integer id) {
		ServerMonitorDto serverMonitor = new ServerMonitorDto();
		try {
			serverMonitor = adminService.getServerMonitorById(id);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return serverMonitor;
	}
	
	/**
	 * 根据id更新server monitor 之间的关系
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/update_server_monitor_submit")
	@ResponseBody
	public String updateServerMonitorSubmit(String param) {
		try {
			ServerMonitorDto serverMonitor = JsonUtils.json2object(param, ServerMonitorDto.class);
			int isExistServerMonitor = adminService.getIsExistServerMonitor(serverMonitor);
			if(isExistServerMonitor!=0){
				return "fail";
			}
			adminService.updateServerMonitorById(serverMonitor);
		} catch (MonitorException e) {
			e.printStackTrace();
		}
		return "success";
	}

}
