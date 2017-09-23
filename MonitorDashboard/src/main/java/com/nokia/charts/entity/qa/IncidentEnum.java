package com.nokia.charts.entity.qa;
public enum IncidentEnum {
	Incident_ID("incident_ID", 1), 
	Interaction_ID("interaction_ID", 2), 
	Status("status", 3), 
	Priority("priority", 4),
	Logical_Name("logical_Name", 5), 
	Affected_Item("affected_Item", 6), 
	Assignment("assignment", 7), 
	Service_Provider("service_Provider", 8), 
	First_Touch("first_Touch", 9), 
	Open_Time("open_Time", 10), 
	Resolved_Time("resolved_Time", 11), 
	Close_Time("close_Time", 12), 
	Current_Elapsed_Time("current_Elapsed_Time", 13),
	Ms_Ttr_Breach("ms_Ttr_Breach", 14), 
	Reopen_Time("reopen_Time", 15), 
	TTO("tTO", 16), 
	TTR("tTR", 17),
	Reassignment_Count("reassignment_Count", 18),
	Resolution_Code("resolution_Code", 19),
	Ms_Breach_Reason("ms_Breach_Reason", 20),
	Assignee("assignee", 21);
	
	private String name;
	private int index;

	private IncidentEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static String getName(int index) {  
        for (IncidentEnum in : IncidentEnum.values()) {  
            if (in.getIndex() == index) {  
                return in.name;  
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
