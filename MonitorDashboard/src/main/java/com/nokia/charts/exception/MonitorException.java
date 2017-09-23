package com.nokia.charts.exception;

public class MonitorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7917139050189772270L;
	private String msgDes; // 异常对应的描述信息

	public MonitorException() {
		super();
	}

	public MonitorException(String message) {
		super(message);
		msgDes = message;
	}

	public String getMsgDes() {
		return msgDes;
	}
}
