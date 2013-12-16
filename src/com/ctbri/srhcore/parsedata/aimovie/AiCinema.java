package com.ctbri.srhcore.parsedata.aimovie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import au.com.bytecode.opencsv.CSVWriter;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.parsedata.CinemaC;

public class AiCinema {

	/**  key 城市名称 value是编码    **/
	public static HashMap<String,String> gbcode = new HashMap<String,String>();
	/** key 编码 value是标准城市名称     **/
	public static HashMap<String,String> regbcode = new HashMap<String,String>();
	
	public static String filepath = CinemaC.Fetch_cinema_dir+"/resource/aicsv/";
	public static String url_ai = "http://60.190.247.35:7777/Test.asmx";
	
	
	public static List<String> citylist_fun = new ArrayList<String>();
	/**  key是fun的area id，vaule是名称    **/
	public static Map<String,String> areaid_list_fun = new HashMap<String,String>();
	/**  key是fun的city id，vaule是国标代码    **/
	public static Map<String,String> cityid_list_fun = new HashMap<String,String>();
	
	static
	{
		U.log.info("load gbcitycode");
		loadGBCityCode();	
//		loadArea();
	}
	
	public static void main(String[] args) {
//		parseCity();
		parseMovie();
//		getCinemaList();
//		getCinemaDetail();
	}
	
	public static void parseMovie()
	{
		String target = "/GetMovieList";
		String paras = "<root><UserToken>2013</UserToken><ItemCount>100</ItemCount><Offset>0</Offset></root>";
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_ai+target);
	    HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 10*1000);  
		HttpConnectionParams.setSoTimeout(httppost.getParams(), 10 * 1000); 
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("reqStr", paras));  
	    
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity).trim();  
//	        String ss = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	        U.log.debug(body); 
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	public static void parseCity()
	{
		String target = "/GetAreaList";

		String paras = "<root><UserToken>2013</UserToken><ItemCount>100</ItemCount><Offset>0</Offset></root>";
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_ai+target);
	    HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 10*1000);  
		HttpConnectionParams.setSoTimeout(httppost.getParams(), 10 * 1000); 
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("reqStr", paras));  

	    
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity).trim();  
//	        String ss = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	        U.log.debug(body); 
	        
	        org.dom4j.io.SAXReader reader=new  org.dom4j.io.SAXReader();
	        reader.setEncoding("GBK");
	        Document doc = reader.read(new ByteArrayInputStream(body.getBytes()));//(body);
	        String xpath = "/AreaListResp/AreaList/Area";
	        List list = doc.selectNodes(xpath);
	        
	        File city = new File(filepath+"/ai_movies.csv");
	        CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
	        
	        List<String[]> citylist = new ArrayList<String[]>();
	        int len = list.size();
	        System.out.println(list.size());
	        for (Object object : list) {
				Element temp = (Element)object;
				System.out.println("AreaName "+ temp.elementText("AreaName"));
				String[] s = new String[5];
				s[0] = temp.elementText("AreaID").toString();
				s[1] = "nouse";//temp.elementText("no").toString();
				s[2] = temp.elementText("AreaName").toString();
				s[3] =  "nouse";//temp.elementText("code").toString();
				s[4] = regbcode.get(s[2]);
				if(s[4] !=null && s[4].length()>0)
					cityid_list_fun.put(s[0], s[4]);
				citylist_fun.add(s[0]);
				citylist.add(s);
				
			}
	        writer.writeAll(citylist);
			writer.close();
			
			U.log.info("parse city over");

	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	public static void getCinemaList()
	{
		String target = "/GetCinemaList";

		String paras = "<root><UserToken>2013</UserToken><ItemCount>100</ItemCount><Offset>0</Offset><AreaID>1</AreaID></root>";
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_ai+target);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("reqStr", paras));  

	    
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        U.log.debug(body); 
			
			U.log.info("parse movies over");
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	
	public static void getCinemaDetail()
	{
		String target = "/GetCinemaDetail";

		String paras = "<root><UserToken>2013</UserToken><CinemaID>222253</CinemaID></root>";
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_ai+target);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("reqStr", paras));  

	    
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        U.log.debug(body); 
			
			U.log.info("parse movies over");
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	public static boolean loadGBCityCode()
	{
		String filepath = CinemaC.Fetch_cinema_dir+"/resource/file/gbcitycode.txt";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				if(line.length()<=6 || line.startsWith("//")) continue;
				String key = line.substring(6).trim().replace(" ", "");
				String vl = line.substring(0,6);
				gbcode.put(vl, key);
				if(!regbcode.containsKey(key))
					regbcode.put(key, vl);
				else
				{
					String newv1 = regbcode.get(key)+","+ vl;
					regbcode.put(key, newv1);
					
				}
				if((key.endsWith("区") || key.endsWith("县")|| key.endsWith("市"))&& key.length()>2)
				{
					regbcode.put(key.substring(0, key.length()-1), vl);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(br !=null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		U.log.debug(gbcode.get("北京"));
		return true;
	}
}
