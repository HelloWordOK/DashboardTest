package com.nokia.charts.entity.qa;
public enum SREnum {
	
	Interaction_ID("interaction_ID", 1), 
	Status("status", 2), 
	Submit_Date("submit_Date", 3),
	Close_Date("close_Date", 4), 
	Number("number", 5), 
	Assigned_Dept("assigned_Dept", 6), 
	Ms_Service_Provider("ms_Service_Provider", 7), 
	M_S_Total_Duration("m_S_Total_Duration", 8), 
	Sla_Breach("sla_Breach", 9),
	Assigned_To("assigned_to", 10);
	
	private String name;
	private int index;

	private SREnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static String getName(int index) {  
        for (SREnum in : SREnum.values()) {  
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
