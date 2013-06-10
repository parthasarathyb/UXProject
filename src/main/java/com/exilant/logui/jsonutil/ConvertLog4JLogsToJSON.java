package com.exilant.logui.jsonutil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.IOUtils;

public class ConvertLog4JLogsToJSON {

	public static void main(String args[]) {
		try{
		new ConvertLog4JLogsToJSON().process();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	protected void process() throws IOException{
		//String partialOutput = "\"level\":\"" + event.getLevel().toString() + "\"";
		InputStream is = new FileInputStream("/Users/partha/Workspace/"+"FusionTest.log");
	    String jsonTxt = IOUtils.toString( is );
	    //int len=jsonTxt.lastIndexOf(",");
	    if(jsonTxt.endsWith(",\n")){
	    	jsonTxt=jsonTxt.substring(0, jsonTxt.length()-2);
	    }
	    //jsonTxt=org.json.simple.JSONObject.escape(jsonTxt);
	    jsonTxt="["+jsonTxt+"]";
	    //JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );
	    JSONArray json = (JSONArray) JSONSerializer.toJSON(jsonTxt);
	   // System.out.println(outerArray);
	    System.out.println(json.toString());
	}

	private String quote(String aText) {
		String QUOTE = "'";
		return QUOTE + aText + QUOTE;
	}

}
