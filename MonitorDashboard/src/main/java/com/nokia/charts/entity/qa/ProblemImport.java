package com.nokia.charts.entity.qa;

import com.nokia.charts.util.DateUtils;

public class ProblemImport {
	
	private int id;
	private String problem_ID;
	private String status;
	private String priority_Code;
	private String current_Phase;
	private String m_S_R_C_O_Assignment;
	private String open_Time;
	private String rootcause_Date;
	private String rootcause_Date_Interval;
	private String solution_Date;
	private String close_Time;
	private String sla_Breach;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProblem_ID() {
		return problem_ID;
	}

	public void setProblem_ID(String problem_ID) {
		this.problem_ID = problem_ID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority_Code() {
		return priority_Code;
	}

	public void setPriority_Code(String priority_Code) {
		this.priority_Code = priority_Code;
	}

	public String getCurrent_Phase() {
		return current_Phase;
	}

	public void setCurrent_Phase(String current_Phase) {
		this.current_Phase = current_Phase;
	}

	public String getM_S_R_C_O_Assignment() {
		return m_S_R_C_O_Assignment;
	}

	public void setM_S_R_C_O_Assignment(String m_S_R_C_O_Assignment) {
		this.m_S_R_C_O_Assignment = m_S_R_C_O_Assignment;
	}

	public String getOpen_Time() {
		return open_Time;
	}

	public void setOpen_Time(String open_Time) {
		if (open_Time != null && !open_Time.isEmpty()) {
			open_Time = DateUtils.formatDate(open_Time, DateUtils.DATE_FROMAT_EN, DateUtils.DATE_FROMAT_CN);
		}
		this.open_Time = open_Time;
	}

	public String getRootcause_Date() {
		return rootcause_Date;
	}

	public void setRootcause_Date(String rootcause_Date) {
		if (rootcause_Date != null && !rootcause_Date.isEmpty()) {
			rootcause_Date = DateUtils.formatDate(rootcause_Date, DateUtils.DATE_FROMAT_EN, DateUtils.DATE_FROMAT_CN);
		}
		this.rootcause_Date = rootcause_Date;
	}

	public String getRootcause_Date_Interval() {
		return rootcause_Date_Interval;
	}

	public void setRootcause_Date_Interval(String rootcause_Date_Interval) {
		this.rootcause_Date_Interval = rootcause_Date_Interval;
	}

	public String getSolution_Date() {
		return solution_Date;
	}

	public void setSolution_Date(String solution_Date) {
		if (solution_Date != null && !solution_Date.isEmpty()) {
			solution_Date = DateUtils.formatDate(solution_Date, DateUtils.DATE_FROMAT_EN, DateUtils.DATE_FROMAT_CN);
		}
		this.solution_Date = solution_Date;
	}

	public String getClose_Time() {
		return close_Time;
	}

	public void setClose_Time(String close_Time) {
		if (close_Time != null && !close_Time.isEmpty()) {
			close_Time = DateUtils.formatDate(close_Time, DateUtils.DATE_FROMAT_EN, DateUtils.DATE_FROMAT_CN);
		}
		this.close_Time = close_Time;
	}

	public String getSla_Breach() {
		return sla_Breach;
	}

	public void setSla_Breach(String sla_Breach) {
		this.sla_Breach = sla_Breach;
	}
}
