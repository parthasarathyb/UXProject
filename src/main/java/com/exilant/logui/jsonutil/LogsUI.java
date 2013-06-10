package com.exilant.logui.jsonutil;


import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

public class LogsUI {
	static Logger logger = Logger.getLogger(LogsUI.class);
	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		
		new LogsUI().print();
	}
	public void print(){
		String str="{\"eventId\":\"outlookproxy_MAIN.load\",\"collationId\":\"_NO_COLLATION_\",\"notification\":{\"mode\":\"DIRECT\",\"method\":\"loadAspects\",\"params\":{\"onComplete\":\"outlookproxy_MAIN\"}},\"status\":\"success\",\"appId\":\"outlookproxy\",\"pageId\":\"outlookproxy_MAIN\"}";
		JSONObject jo = (JSONObject) JSONSerializer.toJSON( str );
		
		
		logger.debug("THREAD STARTED \n"+jo.toString());
		logger.info("THREAD STARTED \n"+jo.toString());
		logger.fatal("THREAD STARTED \n"+jo.toString());
		logger.error("THREAD STARTED \n"+jo.toString());
	}
}
