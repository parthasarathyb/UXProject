package com.exilant.logui.jsonutil;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadAction extends HttpServlet {
	static Logger LOGGER = Logger.getLogger(LogsUI.class);
	//JsonObject retObj = new JsonObject();
	private static final long serialVersionUID = 1L;

	static private final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : null, \"id\" : \"id\"}";
	static private final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
	static public final String SEP = System.getProperty("file.separator");
	static public final String TMPIODIR = System.getProperty("java.io.tmpdir");
	static public final String JSON = "application/json";
	static public final int BUF_SIZE = 99999999;
	
	FileInputStream fis;
	FileOutputStream fos;
	DataInputStream dis;
	BufferedOutputStream bos;
	private String FILE_DIR;
	private static final String FILE_TEXT_EXT = ".txt";
	int file_Size=0;
	//File file = new File(FILE_DIR);
	

	public UploadAction() {
		super();
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		System.out.println("param values  : " + request.getParameter("file"));
		System.out.println("param values  : " + request.getParameter("firstFile"));
		JSONObject jo=new JSONObject();
		receiveFileObject(request);
		response.setContentType("application/json");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin","*");
		response.addHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Accept");
		response.setHeader("Access-Control-Max-Age", "86400");
	//	retObj.addProperty("success", true);
		//retObj.addProperty("message", "Completed");
		jo.put("gridVal", read());
		//LOGGER.info("INSIDE DO POST" + retObj);
		System.out.println("JSON Final Output :"+jo.toString());
		response.setHeader("content-type", "application/json");
		out.println(jo);
		out.close();
	}

	public void receiveFileObject(HttpServletRequest request)
			throws IOException {
		String responseString = RESP_SUCCESS;
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ServletFileUpload upload = new ServletFileUpload();

			try {
				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					String name = item.getFieldName();
					InputStream input = item.openStream();

					// Handle a form field.
					if (item.isFormField()) {
						String itemName = item.getName();
					}

					// Handle a multi-part MIME encoded file.
					else {
						String itemName = item.getName();
						System.out.println("itemName in else : " + itemName);
						saveUploadFile(input, item,request);
					}
				}
			} catch (Exception e) {
				responseString = RESP_ERROR;
				e.printStackTrace();
			}
		}

		// Not a multi-part MIME request.
		else {
			responseString = RESP_ERROR;
		}

	}

	/**
	 * Saves the given file item (using the given input stream) to the web
	 * server's local temp directory.
	 * 
	 * @param input
	 *            The input stream to read the file from
	 * @param item
	 *            The multi-part MIME encoded file
	 */
	private void saveUploadFile(InputStream input, FileItemStream item,HttpServletRequest request)
			throws IOException {
		// bos = new BufferedOutputStream(fos);
		// File TempFileDirectory = new File(TMP + SEP);
		File TempFileDirectory=LookTempFolder(request);
		
		fos = new FileOutputStream(TempFileDirectory +SEP+"FusionLogsUI.log",true); // outputs
		bos = new BufferedOutputStream(fos);
		
		byte[] data = new byte[BUF_SIZE];
		int count;
		while ((count = input.read(data, 0, BUF_SIZE)) != -1) {
			bos.write(data, 0, count);
		}
		
		input.close();
		close();
	}
	
	
	private File LookTempFolder(HttpServletRequest request){
		// get the web applications temporary directory
	    File TempFileDirectory = new File(TMPIODIR);//(File) getServletContext(). getAttribute( "javax.servlet.context.tempdir" );
	    //ftp://localhost:2121/Software/
	    if(request.getParameter("firstFile").equalsIgnoreCase("true")){
	    	//String File="file://";
	    	//setFILE_DIR(TempFileDirectory +SEP+"FusionLogsUI.json");
	    	setFILE_DIR(TMPIODIR +SEP);
	    	File file = new java.io.File(TempFileDirectory +SEP+"FusionLogsUI.log");
	    	 if (file.exists()) {
	    		 file.delete();
	    	 }
	    	
	    }

	    return TempFileDirectory;
	}

	
	
	 /** Read the contents of the given file. */
	private JSONArray read() throws IOException {
//			Gson gson = new Gson();
//			BufferedReader br = new BufferedReader(
//					new FileReader(getFILE_DIR() + "FusionLogsUI.log"));
//		 
//				//convert the json string back to object
//			FusionJsonObj obj = gson.fromJson(br, FusionJsonObj.class);
			
		 InputStream is = new FileInputStream(getFILE_DIR()+"FusionLogsUI.log");
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
		 return json;
	}
	  
	
	public void close() throws IOException {
		bos.flush();
		bos.close();
		fos.close();

	}
	
	public String getFILE_DIR() {
		return FILE_DIR;
	}
	public void setFILE_DIR(String fILE_DIR) {
		FILE_DIR = fILE_DIR;
	}

}