package com.nokia.charts.entity.qa;

public enum ProblemEnum {

	Problem_ID("problem_ID",1),
	Status("status",2),
	Priority_Code("priority_Code",3),
	Current_Phase("current_Phase",4),
	M_S_R_C_O_Assignment("m_S_R_C_O_Assignment",5),
	Open_Time("open_Time",6),
	Rootcause_Date("rootcause_Date",7),
	Rootcause_Date_Interval("rootcause_Date_Interval",8),
	Solution_Date("solution_Date",9),
	Close_Time("close_Time",10),
	Sla_Breach("sla_Breach",11);
	
	private String name;
	private int index;

	private ProblemEnum(String name, int index) {
		this.index = index;
		this.name = name;
	}

	public static String getName(int index) {
		for (ProblemEnum pb : ProblemEnum.values()) {
			if (pb.getIndex() == index) {
				return pb.name;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
