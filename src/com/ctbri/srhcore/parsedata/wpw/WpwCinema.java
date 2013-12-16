package com.ctbri.srhcore.parsedata.wpw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.parsedata.Cinema;
import com.ctbri.srhcore.parsedata.CinemaC;
import com.ctbri.srhcore.parsedata.MovieWanZhu;
import com.ctbri.srhcore.parsedata.UPara;
import com.ctbri.srhcore.parsedata.zhizhu.ZhizhuCinema;

public class WpwCinema {

	public static String filepath = CinemaC.Fetch_cinema_dir+"/resource/wpwcsv/";
	/**
	 * 用来标示movie文件的更新，每次都把索引文件的标示赋给这个变量，如果变化了就要重新加载文件
	 */
	public static String movie_tag = "cinema_last";
	
	
//	public static String url_wpw = "http://test.api.wangpiao.com/";
//	public static String username = "WP_YZBUseApi";
//	public static String testKey = "Wfc8KmceEG3x5jJ5";
	
	public static String url_wpw = "http://channel.api.wangpiao.com/2.0/";
	public static String username = "WP_YZBWPWAPI";
	public static String testKey = "ZhWAPN4CJmuadzJm";
	
	public static String city_outputfilepath = "wangpiao_city.csv";
	public static String cinema_outputfilepath = "wangpiao.csv";
	
	public static HashMap<String,String> citycodetable = new HashMap<String,String>();
	
	public static List<String> citylist_fun = new ArrayList<String>();
	/**  key是fun的area id，vaule是名称    **/
	public static Map<String,String> areaid_list_fun = new HashMap<String,String>();
	
	public static Map<String,String> hall_list_zhizhu = new HashMap<String,String>();
	/**  key是fun的city id，vaule是国标代码    **/
	public static Map<String,String> cityid_list_fun = new HashMap<String,String>();
	

	/**
	 * 这个map里面只是存储了电影的id和名称
	 */
	public static Map<String,String> movie_list_fun = new HashMap<String,String>();
	public static Map<String,MovieWpw> movie_list;
	
	static
	{
//		loadCityCodeTable();
//		System.out.println(citycodetable.get("9"));
	}
	public static void main(String[] args) {
		
//		extractFilmId("无人区|1-14382;地心引力|1-25080;扫毒|1-25092;森林战士|1-24928;");
		System.out.println(loadShows("25092", "1081", 0));
		System.out.println("sds "+loadShows("1081", "25092", 0));
		
//		/*
		WpwCinema.getCityList();
		getFilmList();
		loadMovie();
		
		WpwCinema.getCinemaList();
		
//		*/
//		WpwCinema.parseCity();
//		WpwCinema.parserCinema();
//		City.postMethod();
	}
	
	/**得到这个电影院放映的电影
	 * @param cinemaid
	 * @param cityid
	 * @return
	 */
	public static String getFilmF4Cinema(String cinemaid,String cityid)
	{

		String target = "Base_Film";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("UserName", username);
	    map.put("Target", target);
	    map.put("CityID", cityid);
	    map.put("CinemaID", cinemaid);
	    String codestr = "";
	    for(String value: map.values())
	    {
	    	codestr += value;
	    }
	    codestr += testKey;
		String sig = U.getMD5(codestr);	
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_wpw);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("UserName", username));  
	    params.add(new BasicNameValuePair("Target", target));  
	    params.add(new BasicNameValuePair("CityID", cityid));
	    params.add(new BasicNameValuePair("CinemaID", cinemaid));
	    params.add(new BasicNameValuePair("Sign", sig));
	    
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        JSONObject jo = new JSONObject(body);
	        U.log.debug(body); 
	        StringBuffer sb = new StringBuffer();
	        
//	        File city = new File(filepath+"/fun_movies.csv");
//			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			
//			List<String[]> movielist = new ArrayList<String[]>();
			JSONArray arr = jo.getJSONArray("Data");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				JSONObject temp = arr.getJSONObject(i);
				sb.append(temp.get("Name").toString()).append("|").append("1-").append(temp.get("ID").toString()).append(";");
//				movielist.add(s);
			}
			return sb.toString();
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return null;
	
	}
	
	public static void getFilmList()
	{
		String target = "Base_Film";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("UserName", username);
	    map.put("Target", target);
	    String codestr = "";
	    for(String value: map.values())
	    {
	    	codestr += value;
	    }
	    codestr += testKey;
		String sig = U.getMD5(codestr);	
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_wpw);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("UserName", username));  
	    params.add(new BasicNameValuePair("Target", target));  
	    params.add(new BasicNameValuePair("Sign", sig));
	    
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        JSONObject jo = new JSONObject(body);
	        U.log.info(body); 
	        
	        
	        File city = new File(filepath+"/fun_movies.csv");
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			
			
			List<String[]> movielist = new ArrayList<String[]>();
			JSONArray arr = jo.getJSONArray("Data");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[14];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("ID").toString().replaceAll("\"", "“");//replaceAll("\"", "\\\\\"");
				s[1] = temp.get("Name").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[2] = temp.get("Sort").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[3] = temp.get("ShowDate").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[4] = temp.get("Director").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[5] = temp.get("MP").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[6] = temp.get("Duration").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");				
				s[7] = temp.get("SPhoto").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				
				s[8] = temp.get("Des").toString().replace("\r\n", "").replace("\n", "").replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[9] = temp.get("Area").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");

				s[10] = temp.get("Lphoto").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[11] = temp.get("Grade").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[12] = temp.get("Msg").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[13] = temp.get("Type").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");

				movielist.add(s);
			}
			
			writer.writeAll(movielist);
			writer.close();
			
			U.log.info("parse movies over");
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	public static void getCinemaList()
	{
		if(cityid_list_fun.isEmpty())
			cityid_list_fun = loadMap(filepath+ "/fun_city.csv");
		
		String target = "Base_Cinema";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("UserName", username);
	    map.put("Target", target);
	    String codestr = "";
	    for(String value: map.values())
	    {
	    	codestr += value;
	    }
	    codestr += testKey;
		String sig = U.getMD5(codestr);	
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_wpw);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("UserName", username));  
	    params.add(new BasicNameValuePair("Target", target));  
	    params.add(new BasicNameValuePair("Sign", sig));
	    BufferedWriter bw = null;
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        U.log.debug(body); 
	        JSONObject jo = new JSONObject(body);
	        
	        List<String[]> cinemalist = new ArrayList<String[]>();
			File city = new File(filepath+"/wpw_cinema.csv");			
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			
			TreeMap<String,Set<String>> moviesTreeMap = new TreeMap<String,Set<String>>();
			File cityMovie = new File(filepath + "wpw_movielist.csv");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cityMovie),"GBK"));
			
			JSONArray arr = jo.getJSONArray("Data");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[21];
				JSONObject temp = arr.getJSONObject(i);
				System.out.println(temp);
				s[0] = temp.get("ID").toString();
				s[1] = "1-"+s[0];
				s[2] = "cityname";
				s[3] = temp.get("CityID").toString();
			
				s[4] = temp.get("Name").toString().replaceAll("\"", "“").replaceAll(" ", "");
				s[5] = temp.get("Address").toString().replaceAll("\"", "“").replaceAll("\r\n", "");
				s[6] = "";
				s[7] = "电影院";
				s[8] = "250";
				String coord = temp.get("coord").toString();
				if(coord == null|| coord.trim().length()== 0 || coord.trim().indexOf(",")<0 ) continue;
				s[9] = coord.split(",")[1];
				s[10] = coord.split(",")[0];
				s[11] = temp.get("DistID").toString().replaceAll("\"", "“");
				s[12] = "0";//"tag";
				s[13] = "null";//"invol";
				String film4Cinema = getFilmF4Cinema(s[0], s[3]);
				List<String> filmids = extractFilmId(film4Cinema);				
				s[14] = film4Cinema;//getFilmF4Cinema(s[0], s[3]);
				s[15] ="0";
				s[16] = (temp.get("BusLine").toString()+temp.get("SubWay").toString()).replaceAll("\"", "“").replace("\r\n", "").replace("\n", "");;//temp.get("traffic").toString();
				s[17] = (temp.get("Des").toString()+temp.get("SpeicalDes").toString()).replaceAll("\"", "“").replace("\r\n", "").replace("\n", "");	;//no descript temp.get("descript").toString().replace("\n", "");	
				s[18] = null; //temp.get("traffic").toString();
				s[19] = temp.get("Photo").toString();//photo //picture 测试数据没有这个字段   temp.get("traffic").toString();
				s[20] = temp.get("Url").toString();//"url";//
				
				s[3] = cityid_list_fun.get(s[3]);
				s[2] = Cinema.gbcode.get(s[3]);
//				U.log.info(s[9]+"  ====================");
				
				
				if(!moviesTreeMap.containsKey(s[3]))
				{
					Set<String> set = new HashSet<String>();
					moviesTreeMap.put(s[3], set);
				}
				moviesTreeMap.get(s[3]).addAll(filmids);
				
				cinemalist.add(s);
			}
			
			
			Set<String> keys  = moviesTreeMap.keySet();
			for (String string : keys) {
				StringBuffer sb1 = new StringBuffer();
				sb1.append(string).append("$");
				StringBuffer sb = new StringBuffer();
				Set<String> values = moviesTreeMap.get(string);
				for (Iterator iterator = values.iterator(); iterator.hasNext();) {
					String filmid = (String) iterator.next();
					if(movie_list_fun.get(filmid) == null) continue;
					sb.append(movie_list_fun.get(filmid)).append("|").append("1-").append(filmid).append(";");
				}
				if(sb.toString().trim().length()>0)
				{
					bw.write(sb1.append(sb).toString());
					bw.newLine();
				}
				
			}
			
			writer.writeAll(cinemalist);
			writer.close();
			
			U.log.info("parse cinema over");
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    finally{
	    	try {
				if(bw!= null)bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	}
	
	/**输入是一个电影院放映的电影，输出是这些电影的id
	 * @param film4Cinema
	 * @return
	 */
	private static List<String> extractFilmId(String film4Cinema)
	{
		List<String> re = new ArrayList<String>();
		if(film4Cinema != null)
		{
			String[] ss = film4Cinema.split(";");
			for (int i = 0; i < ss.length; i++) {
				int index = ss[i].indexOf("-");
//				System.out.println(ss[i].substring(index+1));
				re.add(ss[i].substring(index+1));
			}
		}
		return re;
	}
	
	
	public static String loadShows(String cinemaId,String movieId,int time)
	{
		
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE,time);
		
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a1=dateformat1.format(cal1.getTime());
		
		String target = "Base_FilmShow";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("UserName", username);
	    map.put("Target", target);
		map.put("Date", a1);
		map.put("FilmID", movieId);	    
	    map.put("CinemaID", cinemaId);
	    
	    
	    String codestr = "";
	    for(String value: map.values())
	    {
	    	codestr += value;
	    }
	    codestr += testKey;
	    U.log.info(codestr);
		String sig = U.getMD5(codestr);	
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_wpw);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("UserName", username));  
	    params.add(new BasicNameValuePair("Target", target));  
	    params.add(new BasicNameValuePair("Date", a1));  
	    params.add(new BasicNameValuePair("FilmID", movieId));  
	    params.add(new BasicNameValuePair("CinemaID", cinemaId));  
	    params.add(new BasicNameValuePair("Sign", sig));
	    BufferedWriter bw = null;
	    try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        U.log.info(body); 
	        
	        JSONObject jo = new JSONObject(body);
	        StringBuffer sb = new StringBuffer();
	        JSONArray arr = jo.getJSONArray("Data");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				JSONObject temp = arr.getJSONObject(i);
				
				sb.append("{");
				sb.append("\"id\":").append("\"").append(temp.get("ShowIndex")).append("\"").append(",");
				sb.append("\"refer\":").append("\"").append("1").append("\"").append(",");
				sb.append("\"time\":").append("\"").append(temp.get("ShowTime")).append("\"").append(",");
				sb.append("\"hallId\":").append("\"").append(temp.get("HallID")).append("\"").append(",");
				sb.append("\"hallName\":").append("\"").append(temp.get("HallName")).append("\"").append(",");
				sb.append("\"cinemaId\":").append("\"").append(temp.get("CinemaID")).append("\"").append(",");
//				sb.append("\"cinemaName\":").append("\"").append(temp.get("cinemaName")).append("\"").append(",");
				sb.append("\"movieId\":").append("\"").append(temp.get("FilmID")).append("\"").append(",");
				sb.append("\"movieName\":").append("\"").append(temp.get("FilmName"));
				if(temp.get("IsImax").toString().equalsIgnoreCase("true"))
					sb.append(" imax");
				sb.append("\"").append(",");
				sb.append("\"CPrice\":").append("\"").append(temp.get("CPrice")).append("\"").append(",");//影院标准价格
				sb.append("\"UPrice\":").append("\"").append(temp.get("UPrice")).append("\"").append(",");//网票网普通用户价格
				sb.append("\"standard\":").append("\"").append(temp.get("CPrice")).append("\"").append(",");//网票网普通用户价格
				sb.append("\"VPrice\":").append("\"").append(temp.get("VPrice")).append("\"").append(",");//渠道结算价格
				sb.append("\"UWPrice\":").append("\"").append(temp.get("UWPrice")).append("\"").append(",");//网票卡价格
				sb.append("\"SPType\":").append("\"").append(temp.get("SPType")).append("\"").append(",");//产品支持
				sb.append("\"SPPrice\":").append("\"").append(temp.get("SPPrice")).append("\"").append(",");//产品价格
				sb.append("\"IsImax\":").append("\"").append(temp.get("IsImax")).append("\"").append(",");//
				sb.append("\"Dimensional\":").append("\"").append(temp.get("Dimensional")).append("\"").append(",");//
				sb.append("\"SeatCount\":").append("\"").append(temp.get("SeatCount")).append("\"");
				sb.append("},");
//				movielist.add(s);
			}
			if(sb.length()>0)
				return "{\"code\":200,\"shows\":["+sb.substring(0, sb.length()-1)+"]}";
			return "{\"code\":200,\"shows\":["+sb.toString()+"]}";

			
		} catch (Exception e1) {
			e1.printStackTrace();
			return "{\"code\":408,\"shows\":[]}";
		}
		finally
		{
			hc.getConnectionManager().shutdown();
		}
		
	}
	
	public static void getCityList()
	{
		String target = "Base_CityBll";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("UserName", username);
	    map.put("Target", target);
	    String codestr = "";
	    for(String value: map.values())
	    {
	    	codestr += value;
	    }
	    codestr += testKey;
		String sig = U.getMD5(codestr);	
		
		HttpClient hc = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_wpw);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("UserName", username));  
	    params.add(new BasicNameValuePair("Target", target));  
	    params.add(new BasicNameValuePair("Sign", sig));   

		try {

			// Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = hc.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        U.log.debug("body "+body); 
	        JSONObject jo = new JSONObject(body);
	        
			File city = new File(filepath+"/fun_city.csv");
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			List<String[]> citylist = new ArrayList<String[]>();
			JSONArray arr = jo.getJSONArray("Data");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[4];
				JSONObject temp = arr.getJSONObject(i);
				s[0] = temp.get("ID").toString();
				s[1] = temp.get("Name").toString();
				s[2] = temp.get("Type").toString();
				s[3] = Cinema.regbcode.get(s[1]);
				if(s[3] !=null && s[3].length()>0)
					cityid_list_fun.put(s[0], s[3]);
				citylist_fun.add(s[0]);
				citylist.add(s);
			}
			
			writer.writeAll(citylist);
			writer.close();
			
			U.log.info("parse city over");
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
		}

	}
    
	public static void loadCityCodeTable()
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(city_outputfilepath))));
			String content  = "";
			while((content = br.readLine()) != null)
			{
				String[] ss = content.split(",");
				if(ss!= null && ss.length != 6)
				{
					System.out.println("error: "+ content);
					continue;
				}
				citycodetable.put(ss[0].replace("\"", ""), ss[5].replace("\"", ""));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void parseCity()
	{		
		String target = "Base_CityBll";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("UserName", username);
	    map.put("Target", target);
	    String result = "";
	    for(String value: map.values())
	    {
	    	result += value;
	    }
	    result += testKey;
		String sig = U.getMD5(result);

		
		HttpClient client = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_wpw); 

	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("UserName", username));  
	    params.add(new BasicNameValuePair("Target", target));  
	    params.add(new BasicNameValuePair("Sign", sig));   
	    
	    BufferedWriter br = null;
	    CSVWriter writer = null;
	    try {  
//	    	br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputfilepath))));
	    	writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city_outputfilepath))));
	        // Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = client.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        System.out.println(body); 
	        JSONObject jo = new JSONObject(body);
	        JSONArray ja = jo.getJSONArray("Data");
	        int len = ja.length();
	        System.out.println(len );
	        ArrayList<String[]> list = new ArrayList<String[]>();
	        for (int i = 0; i < len; i++) {
	        	String[] s = new String[6];
				JSONObject temp = ja.getJSONObject(i);

				s[0] = temp.get("ID").toString();
				s[1] = temp.get("Code").toString();
				s[2] = temp.get("Name").toString();
				s[3] = temp.get("SName").toString();
				s[4] = temp.get("PName").toString();
				s[5] = UPara.regbcode.get(s[2]);

				
				list.add(s);

			}
	        writer.writeAll(list);
			writer.close();
	        
	        
  
	    } catch (Exception e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }
	    
	    loadCityCodeTable();

	}
	
	public static void parserCinema()
	{
		
		String target = "Base_Cinema";
		TreeMap<String,String> map = new TreeMap<String,String>();
		map.put("UserName", username);
	    map.put("Target", target);
	    String result = "";
	    for(String value: map.values())
	    {
	    	result += value;
	    }
	    result += testKey;
		String sig = U.getMD5(result);

		
		HttpClient client = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost(url_wpw); 

	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    params.add(new BasicNameValuePair("UserName", username));  
	    params.add(new BasicNameValuePair("Target", target));  
	    params.add(new BasicNameValuePair("Sign", sig));   
	    
	    BufferedWriter br = null;
	    
	    CSVWriter writer = null;
	    try {  
//	    	br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputfilepath))));
	    	writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cinema_outputfilepath))));
	        // Post请求  
	        // 设置参数  
	        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
	        // 发送请求  
	        HttpResponse httpresponse = client.execute(httppost);  
	        HttpEntity entity = httpresponse.getEntity();  
	        String body = EntityUtils.toString(entity);  
	        System.out.println(body); 
	        JSONObject jo = new JSONObject(body);
	        JSONArray ja = jo.getJSONArray("Data");
	        int len = ja.length();
	        System.out.println(len );
	        ArrayList<String[]> list = new ArrayList<String[]>();
	        for (int i = 0; i < len; i++) {
	        	String[] s = new String[21];
				JSONObject temp = ja.getJSONObject(i);

				s[0] = temp.get("ID").toString();
				s[1] = "1-"+s[0];
				s[2] = "cityname";
				s[3] = temp.get("CityID").toString();
				s[4] = temp.get("Name").toString().trim();
				s[5] = temp.get("Address").toString().trim();
				s[6] = temp.get("Tel").toString();
				s[7] = "电影院";
				s[8] = "250";
				s[9] = temp.get("coord").toString().split(",")[1];
				s[10] = temp.get("coord").toString().split(",")[0];
				s[11] = temp.get("DistID").toString();
				s[12] = "tag";
				s[13] = temp.get("Grade").toString();//"invol";
				s[14] = temp.get("CinemaLine").toString();//brand
				s[15] ="0";
				s[16] = temp.get("BusLine").toString();
				s[17] = temp.get("Des").toString();		
				s[18] = temp.get("SpeicalDes").toString();	
				s[19] = temp.get("Photo").toString();	
				s[20] = temp.get("Url").toString();	

				s[3] = citycodetable.get(s[3]);
				s[2] = UPara.gbcode.get(s[3]);
//				List<String> moviesid = parseMovies4Cinema(s[0]);
//				if(moviesid !=null && moviesid.size()>0)
//				{
//					StringBuffer sb = new StringBuffer();
//					for (int j = 0; j < moviesid.size(); j++) {						
//						sb.append(moviesid.get(j)).append(";");
//					}
//					s[14] = sb.substring(0, sb.length()-1);
//				}
//				
////				s[7] = temp.get("latitude").toString();
//				s[3] = cityid_list_fun.get(s[3]);
//				s[2] = gbcode.get(s[3]);
//				s[11] = areaid_list_fun.get(s[11]);
				
				list.add(s);

			}
	        writer.writeAll(list);
			writer.close();
	        
	        
  
	    } catch (Exception e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  	
	}
	
	public static MovieWpw getMovieWpw(String movieid)
	{
		String path = ZhizhuCinema.class.getResource("/").getPath();
		File lockfile = new File(path+"/"+"cinemalock.index");
		String content = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lockfile)));
			content = br.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br!= null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		if(movie_list == null || !content.equals(movie_tag))
		{
			movie_tag = content;
			loadMovieWpw();
			U.log.info("change movie tag "+ movie_tag);
		}
		return movie_list.get(movieid);
	}
	
	public static boolean loadMovieWpw()
	{
		movie_list = new HashMap<String,MovieWpw>();
		String moviefilepath = filepath + "fun_movies.csv";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(moviefilepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				CSVParser cp = new CSVParser();
				String[] m = cp.parseLine(line);
				MovieWpw movie = new MovieWpw();
				movie.id = m[0];
				movie.name = m[1];
				movie.sort = m[2];
				movie.showDate = m[3];
				movie.director = m[4];
				movie.mp = m[5];
//				movie.release = m[6];
				movie.duration = m[6];
				movie.sphoto = m[7];
				movie.des = m[8];
				movie.area = m[9];
				movie.lphoto = m[10];
				movie.grade = m[11];
				movie.msg = m[12];
				movie.type = m[13];
				movie_list.put(m[0], movie);
			}
		}
		catch(Exception e)
		{
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
		U.log.info("load file sucess "+ moviefilepath);
		return true;
		
	}
	
	
	
	/**
	 * 这个方法加载的是id 和name
	 * @return
	 */
	public static boolean loadMovie()
	{
		String areafilepath = filepath+ "fun_movies.csv";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(areafilepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				CSVParser cp = new CSVParser();
				String[] m = cp.parseLine(line);
				movie_list_fun.put(m[0], m[1]);
			}
		}
		catch(Exception e)
		{
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
//		U.log.info(movie_list_fun.get("5846"));
		return true;
	}

	private static void loadCityList_Fun()
	{
		String fun_city_path = filepath + "fun_city.csv";
		CSVReader cr=null;
		try {
			cr = new CSVReader(new FileReader(fun_city_path), ',');
			List<String[]> poiList=null;			
			poiList = cr.readAll();
			for (int i = 0; i < poiList.size(); i++) {
				citylist_fun.add(poiList.get(i)[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		finally
		{
			if(cr != null)
				try {
					cr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	private static HashMap<String,String> loadMap(String filename)
	{
		U.log.debug("load file "+ filename);
		HashMap<String,String> temp = new HashMap<String,String>();;
		CSVReader cr=null;
		try {
			cr = new CSVReader(new FileReader(filename), ',');
			List<String[]> poiList=null;			
			poiList = cr.readAll();
			for (int i = 0; i < poiList.size(); i++) {
				String[] tt = poiList.get(i);
				temp.put(tt[0], tt[tt.length-1]);
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(cr != null)
				try {
					cr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}	
		return temp;
	}

	
}
