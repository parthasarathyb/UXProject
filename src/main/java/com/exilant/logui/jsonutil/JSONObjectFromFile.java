package com.exilant.logui.jsonutil;

import java.io.FileInputStream;
import java.io.InputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.IOUtils;







public class JSONObjectFromFile {
public static void main(String args[]){
	try{
		JSONObject jo=new JSONObject();
	 InputStream is = new FileInputStream("/Users/partha/Json/FusionLogsUI.json");
	    String jsonTxt = IOUtils.toString( is );
	    //JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );
	    JSONArray outerArray = (JSONArray) JSONSerializer.toJSON(jsonTxt);
	    jo.put("gridVal", outerArray);
	    System.out.println(jo);
	    //System.out.println(outerArray);
	 
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
