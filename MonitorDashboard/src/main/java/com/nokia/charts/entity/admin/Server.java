package com.nokia.charts.entity.admin;

/**
 * Server entity
 * 
 * @author weijid
 *
 */
public class Server {

	private int id;
	private String serverName;
	private String ipAddress;
	private String serverDetails;
	private int serviceId;
	private String serviceName;
	private String serviceCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getServerDetails() {
		return serverDetails;
	}

	public void setServerDetails(String serverDetails) {
		this.serverDetails = serverDetails;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
}
