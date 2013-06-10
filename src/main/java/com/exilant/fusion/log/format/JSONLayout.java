package com.exilant.fusion.log.format;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import com.exilant.fusion.log.format.JSONLayoutException;

public class JSONLayout extends Layout {

    private final JsonFactory jsonFactory;
    private String[] mdcKeys = new String[0];

    public JSONLayout() {
        jsonFactory = new JsonFactory();
    }

    @Override
    public String format(LoggingEvent event) {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonGenerator g = createJsonGenerator(stringWriter);
            g.writeStartObject();
            writeBasicFields(event, g);
            writeMDCValues(event, g);
            writeThrowableEvents(event, g);
            writeNDCValues(event, g);
            g.writeEndObject();
            g.close();
            stringWriter.append(",\n");
            return stringWriter.toString();
        } catch (IOException e) {
            throw new JSONLayoutException(e);
        }
    }

    private JsonGenerator createJsonGenerator(StringWriter stringWriter) throws IOException {
        JsonGenerator g = jsonFactory.createJsonGenerator(stringWriter);
        return g;
    }

    private void writeBasicFields(LoggingEvent event, JsonGenerator g) throws IOException {
    	g.writeStringField("level", event.getLevel().toString());
    	g.writeStringField("fileName", event.getLoggerName());
        g.writeStringField("threadName", event.getThreadName());
        String msg[]=event.getMessage().toString().split("\n");
        msg[1]="{\"eventId\":\"outlookproxy_MAIN.load\",\"collationId\":\"_NO_COLLATION_\",\"notification\":{\"mode\":\"DIRECT\",\"method\":\"loadAspects\",\"params\":{\"onComplete\":\"outlookproxy_MAIN\"}},\"status\":\"success\",\"appId\":\"outlookproxy\",\"pageId\":\"outlookproxy_MAIN\"}";
        if(null!=msg[1] && !msg[1].equals("")){
        	JSONObject jo = (JSONObject) JSONSerializer.toJSON( msg[1] );
        	g.writeStringField("jsonMsg",jo.toString());
	        Iterator<?> keys = jo.keys();
	
			while (keys.hasNext()) {
				String key = (String) keys.next();
				//System.out.println(jo.get(key));
				boolean firstLoop=(jo.get(key) instanceof JSONObject)?true:false;
				if(!firstLoop){g.writeObjectField(key, jo.get(key));}
				if (firstLoop) {
					JSONObject jo1 = (JSONObject) jo.get(key);
					Iterator<?> keys1 = jo1.keys();
					while (keys1.hasNext()) {
						String key1 = (String) keys1.next();
						//System.out.println(jo1.get(key1));
						boolean secondLoop=(jo1.get(key1) instanceof JSONObject)?true:false;
						if(!secondLoop){g.writeObjectField(key1, jo1.get(key1));}
						if (secondLoop) {
							JSONObject jo2 = (JSONObject) jo1.get(key1);
							Iterator<?> keys2 = jo2.keys();
								String key2 = (String) keys2.next();
								//System.out.println(jo2.get(key2));
								g.writeObjectField(key2, jo2.get(key2));
						}
					}
				}
			}
	        
	        g.writeStringField("msg", msg[0]);
	        
        }else{
        	 g.writeStringField("msg", "");
        }
        String date=DateFormat(event);
        g.writeObjectField("time", date);
    }

    private void writeNDCValues(LoggingEvent event, JsonGenerator g) throws IOException {
        if (event.getNDC() != null) {
            g.writeStringField("NDC", event.getNDC());
        }
    }
    private String DateFormat(LoggingEvent event){
    	String pattern = "MM/dd/yyyy";
    	SimpleDateFormat format = new SimpleDateFormat(pattern);
    	String date=null;
		//date = format.parse(new Date(event.timeStamp).toString());
		date = format.format(new Date(event.timeStamp));
    	return date;
    }
    private void writeThrowableEvents(LoggingEvent event, JsonGenerator g) throws IOException {
        String throwableString;
        String[] throwableStrRep = event.getThrowableStrRep();
        throwableString = "";
        if (throwableStrRep != null) {
            for (String s : throwableStrRep) {
                throwableString += s + "\n";
            }
        }
        if (throwableString.length() > 0) {
            g.writeStringField("throwable", throwableString);
        }
    }

    private void writeMDCValues(LoggingEvent event, JsonGenerator g) throws IOException {
        if (mdcKeys.length > 0) {
            event.getMDCCopy();

            g.writeObjectFieldStart("MDC");
            for (String s : mdcKeys) {
                Object mdc = event.getMDC(s);
                if (mdc != null) {
                    g.writeStringField(s, mdc.toString());
                }
            }
            g.writeEndObject();
        }
    }

    public String[] getMdcKeys() {
        return Arrays.copyOf(mdcKeys, mdcKeys.length);
    }

    public void setMdcKeysToUse(String mdcKeystoUse){
        if (StringUtils.isNotBlank(mdcKeystoUse)){
            this.mdcKeys = mdcKeystoUse.split(",");
        }
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

	public void activateOptions() {
		// TODO Auto-generated method stub
		
	}

  
}
