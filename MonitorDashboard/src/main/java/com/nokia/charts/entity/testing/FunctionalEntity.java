package com.nokia.charts.entity.testing;

import java.io.Serializable;

public class FunctionalEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6098296618007288008L;

	private int id;
	private String project;
	private String environment;
	private String version;
	private String module;
	private String cycle;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

}
