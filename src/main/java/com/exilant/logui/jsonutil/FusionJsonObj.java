package com.exilant.logui.jsonutil;


public class FusionJsonObj {


	private String level;
	private String time;
	private String lineNumber;
	private String fileName;
	private String threadName;
	private String msg;

	
	private String jsonMsg;
	private String eventId;
	private String collationId;
	
	
	public String getLevel() {
		return level;
	}



	public void setLevel(String level) {
		this.level = level;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getLineNumber() {
		return lineNumber;
	}



	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getThreadName() {
		return threadName;
	}



	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public String getJsonMsg() {
		return jsonMsg;
	}



	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}



	public String getEventId() {
		return eventId;
	}



	public void setEventId(String eventId) {
		this.eventId = eventId;
	}



	public String getCollationId() {
		return collationId;
	}



	public void setCollationId(String collationId) {
		this.collationId = collationId;
	}



	
	@Override
	public String toString() {
	   return "{'level':"+level+", 'time':"+time+",'lineNumber':"+lineNumber+", 'fileName':"+lineNumber+",'threadName':"+lineNumber+", 'msg': "+lineNumber+"}";
	}
}

