package com.nokia.charts.constant;

public class SystemConstant {

	public final static Integer DATA_FLAG = 2147483647;
	
	public interface MonitorStatus {
		public static String CRITICAL = "2";
		public static String WARNING = "1";
		public static String UNKNOWN = "3";
		public static String OK = "0";
	}

	public interface MonitorType {
		public static String PINGTYPE = "check_ping";
		public static String URLTYPE = "check_url";
	}

	public interface PingValueType {
		public static String PINGVALUE = "rta";
		public static String PINGUNIT = "ms";
	}

	public interface UrlValueType {
		public static String URLVALUE = "time";
		public static String URLUNIT = "s";
	}

	public interface ChartType {
		public static String CHART1 = "chart1";
		public static String CHART2 = "chart2";
		public static String CHART3 = "chart3";
		public static String CHART4 = "chart4";
		public static String CHART5 = "chart5";
		public static String CHART6 = "chart6";
		public static String CHART7 = "chart7";
	}

	public interface QASourceType {
		public static String INCIDENT = "incident";
		public static String PROBLEM = "problem";
		public static String SR = "sr";
	}

	public interface QAServicePeiordType {
		public static String WEEKLY = "weekly";
		public static String MONTHLY = "monthly";
	}

	public interface QATicketPriorityType {
		public static String P1 = "Critical";
		public static String P2 = "High";
		public static String P3 = "Average";
		public static String P4 = "Low";
	}

	public interface QAClosedSLAType {
		public static String BREACH = "false";
	}
	
}
