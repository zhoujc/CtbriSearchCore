package com.ctbri.srhcore.parsedata.zhizhu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.parsedata.Cinema;
import com.ctbri.srhcore.parsedata.CinemaC;

public class ZhizhuCinema {
	

	public static String filepath = CinemaC.Fetch_cinema_dir+"/resource/zhizhucsv/";
//	public static String url_zhizhu = "http://test.spider.com.cn:9391/test/";
	public static String url_zhizhu = "http://film.spider.com.cn/haobai/";
	
	/**
	 * 用来标示movie文件的更新，每次都把索引文件的标示赋给这个变量，如果变化了就要重新加载文件
	 */
	public static String movie_tag = "cinema_last";
	
//	public static String secret_zhizhu = "0051657788";
//	public static String key_zhizhu= "test";
	
	public static String secret_zhizhu = "1059133381";
	public static String key_zhizhu= "haobai";
	
	
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
	
	static
	{
		loadArea();
		loadHall();
	}
	
	public static void main(String[] args) {
		
//		getHallList4Cinema("31124201");
//		getHalljList();
//		System.out.println("re  "+loadShows("31125601","001105232013",0));
//		showList("",0);
		
//		getCityList();
//		getRegionList();
	
		
		getFilmList();
		loadMovie();
		int len = citylist_fun.size();
		while(len ==0)
		{
			loadCityList_Fun();
			len = citylist_fun.size();
		}
		for (int i = 0; i < len; i++) {
			getCinemaListInCity(citylist_fun.get(i));
		}
		mergeFile();
		mergeMovieFile();
		
	}
	
	
	public static void mergeMovieFile()
	{

		File dir = new File(filepath);
		File[] files = dir.listFiles(new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				// TODO Auto-generated method stub
				String filename = arg0.getName();
				if(filename.startsWith("fun_movie_"))
					return true;
				return false;
			}
			
		});
		
		File mergeFile = new File(filepath + "zhizhu_movielist.csv");
		BufferedWriter bw = null;
		BufferedReader br = null;
		
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mergeFile),"GBK"));
			String content = "";
			for (int i = 0; i < files.length; i++) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(files[i]),"GBK"));
				while((content = br.readLine()) != null)
				{
					int index = content.indexOf("$");
					String[] ids = content.substring(index+1).split(",");
					StringBuffer sb = new StringBuffer();
					sb.append(content.substring(0, index)).append("$");
					for (int j = 0; j < ids.length; j++) {
						if(movie_list_fun.containsKey(ids[j]))
							sb.append(movie_list_fun.get(ids[j])).append("|").append("3-").append(ids[j]).append(";");
					}
					if(sb.charAt(sb.length()-1) ==';') sb.deleteCharAt(sb.length()-1);
					bw.write(sb.toString());
					bw.newLine();
				}				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		{
			try {
				if(br != null) br.close();
				if(bw != null) bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
	}
	
	public static void mergeFile()
	{
		File dir = new File(filepath);
		File[] files = dir.listFiles(new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				// TODO Auto-generated method stub
				String filename = arg0.getName();
				if(filename.startsWith("fun_cinema_"))
					return true;
				return false;
			}
			
		});
		
		File mergeFile = new File(filepath + "zhizhu_cinema.csv");
		BufferedWriter bw = null;
		BufferedReader br = null;
		
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mergeFile),"GBK"));
			String content = "";
			for (int i = 0; i < files.length; i++) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(files[i]),"GBK"));
				while((content = br.readLine()) != null)
				{
					bw.write(content);
					bw.newLine();
				}				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		{
			try {
				if(br != null) br.close();
				if(bw != null) bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static String loadShows(String cinemaId,String movieId,int time)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE,time);
		
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String v_showDate=dateformat1.format(cal1.getTime());
		
		String para = "cinemaId="+cinemaId+"&filmId="+movieId+"&showDate="+v_showDate+"&"+"key="+key_zhizhu;
		String url = url_zhizhu+"showList.html?";
		String sig = U.getMD5(cinemaId+movieId+v_showDate+""+key_zhizhu+secret_zhizhu);
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		try {

			hr = hc.execute(get);
			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
			JSONObject re = new JSONObject(result);
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析film列表失败");
				return "";
			}
			JSONArray arr = re.getJSONArray("showInfo");
			
			StringBuffer sb = new StringBuffer();

			int len = arr.length();
			for (int i = 0; i < len; i++) {
				JSONObject temp = arr.getJSONObject(i);
				sb.append("{");
				sb.append("\"id\":").append("\"").append(temp.get("showId")).append("\"").append(",");
				float floattime = Integer.parseInt(temp.get("showTime").toString())/100.0f;
				DecimalFormat   fnum   =   new   DecimalFormat("##0.00");  
				String   strtime=fnum.format(floattime).replaceFirst("\\.", ":");
				sb.append("\"refer\":").append("\"").append("3").append("\"").append(",");
				sb.append("\"time\":").append("\"").append(temp.get("showDate")).append(" ").append(strtime).append("\"").append(",");
				sb.append("\"hallId\":").append("\"").append(temp.get("hallId")).append("\"").append(",");
				String hallname = hall_list_zhizhu.get(temp.get("cinemaId")+"-"+temp.get("hallId"));
				if(hallname!= null)
					sb.append("\"hallName\":").append("\"").append(hallname).append("\"").append(",");
				sb.append("\"cinemaId\":").append("\"").append(temp.get("cinemaId")).append("\"").append(",");
				sb.append("\"cinemaName\":").append("\"").append(temp.get("cinemaName")).append("\"").append(",");
				sb.append("\"movieId\":").append("\"").append(temp.get("filmId")).append("\"").append(",");
				sb.append("\"movieName\":").append("\"").append(temp.get("filmName")).append("\"").append(",");
				sb.append("\"price\":").append("\"").append(temp.get("merPrice")).append("\"").append(",");//商户价格
				sb.append("\"standard\":").append("\"").append(temp.get("staPrice")).append("\"").append(",");//标准价格
				sb.append("\"userPrice\":").append("\"").append(temp.get("userPrice")).append("\"").append(",");//用户价格
				sb.append("\"feePrice\":").append("\"").append(temp.get("feePrice")).append("\"").append(",");//服务费
				sb.append("\"cardType\":").append("\"").append(temp.get("cardType")).append("\"");
				sb.append("},");
				
			}
			if(sb.length()>0)
				return "{\"code\":200,\"shows\":["+sb.substring(0, sb.length()-1)+"]}";
			return "{\"code\":200,\"shows\":["+sb.toString()+"]}";
		}
		catch (IOException e1) {
			return "{\"code\":408,\"shows\":[]}";
		}
		

	}
	
	public static List<String> showListId(String cinemaId,int date)
	{
		
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE,date);
		
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String v_showDate=dateformat1.format(cal1.getTime());
		
		String para = "cinemaId="+cinemaId+"&showDate="+v_showDate+"&"+"key="+key_zhizhu;
		String url = url_zhizhu+"showList.html?";
		String sig = U.getMD5(cinemaId+v_showDate+""+key_zhizhu+secret_zhizhu);
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		List<String> moviesid = new ArrayList<String>();
		try {

			hr = hc.execute(get);
			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
			JSONObject re = new JSONObject(result);
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析film列表失败");
				return moviesid;
			}
			JSONArray arr = re.getJSONArray("showInfo");
			TreeSet<String> filmIdSet = new TreeSet<String>();
			StringBuffer sb = new StringBuffer();

			int len = arr.length();
			for (int i = 0; i < len; i++) {
				JSONObject temp = arr.getJSONObject(i);
				if(filmIdSet.contains(temp.get("filmId")))
				{
					continue;
				}
				else
				{
					filmIdSet.add(temp.get("filmId").toString());
					moviesid.add(temp.get("filmId").toString());
				}
				
			}
//			for (Iterator iterator = filmIdSet.iterator(); iterator.hasNext();) {
//				String string = (String) iterator.next();
//				sb.append(string).append(",");
//			}
//			if(sb.length()>0)
//				return sb.substring(0, sb.length()-1).toString();
		}
		catch (IOException e1) {
		}
		return moviesid;
	
	}
	
	/**
	 * 得到这个这个影院的放映的电影的信息
	 */
	public static String showList(String cinemaId,int date)
	{	
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE,date);
		
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String v_showDate=dateformat1.format(cal1.getTime());
		
		String para = "cinemaId="+cinemaId+"&showDate="+v_showDate+"&"+"key="+key_zhizhu;
		String url = url_zhizhu+"showList.html?";
		String sig = U.getMD5(cinemaId+v_showDate+""+key_zhizhu+secret_zhizhu);
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		try {

			hr = hc.execute(get);
			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
			JSONObject re = new JSONObject(result);
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析film列表失败");
				return "";
			}
			JSONArray arr = re.getJSONArray("showInfo");
			Set<String> filmIdSet = new HashSet<String>();
			StringBuffer sb = new StringBuffer();

			int len = arr.length();
			for (int i = 0; i < len; i++) {
				JSONObject temp = arr.getJSONObject(i);
				if(filmIdSet.contains(temp.get("filmId")))
				{
					continue;
				}
				else
				{
					filmIdSet.add(temp.get("filmId").toString());
					sb.append(temp.get("filmName")).append("|").append("3-").append(temp.get("filmId")).append(";");
				}
				
			}
			if(sb.length()>0)
				return sb.substring(0, sb.length()-1).toString();
		}
		catch (IOException e1) {
		}
		return "";
	}
	
	public static void getFilmList()
	{
		String para = "key="+key_zhizhu ;
		String url = url_zhizhu+"filmList.html?";
		String sig = U.getMD5(key_zhizhu+secret_zhizhu);
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		try {

			hr = hc.execute(get);			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
			JSONObject re = new JSONObject(result);
			System.out.println(result);
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析film列表失败");
				return;
			}
			File city = new File(filepath+"/fun_movies.csv");
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			
			List<String[]> movielist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("filmInfo");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[10];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("filmId").toString().replaceAll("\"", "“");//replaceAll("\"", "\\\\\"");
				s[1] = U.qj2bj(temp.get("filmName").toString().replaceAll("\"", "“"));//.replaceAll("\"", "\\\\\"");
				s[2] = U.qj2bj(temp.get("englishName").toString().replaceAll("\"", "“"));//.replaceAll("\"", "\\\\\"");
				s[3] = temp.get("openingDate").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[4] = temp.get("director").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[5] = temp.get("actor").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[6] = temp.get("duration").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");				
				s[7] = temp.get("picture").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[8] = temp.get("description").toString().replace("\r\n", "").replace("\n", "").replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[9] = temp.get("country").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				

				movielist.add(s);
			}
			
			writer.writeAll(movielist);
			writer.close();
			
			U.log.info("parse movies over");
		}
		catch (IOException e1) {
		}
		return;
	}
	
	/**
	 * 得到这个影片的信息
	 */
	public static List<String> getFilmList4Cinema(String cinemaId)
	{
		String para = "filmId=shanghai&"+"key="+key_zhizhu ;
		String url = url_zhizhu+"filmList.html?";
		String sig = U.getMD5("shanghai"+key_zhizhu+secret_zhizhu);
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		try {

			hr = hc.execute(get);
			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
		}
		catch (IOException e1) {
		}
		return null;
	}
	
	
	
	/**
	 * 得到这个城市所有的影院列表
	 */
	public static void getCinemaListInCity(String citycode)
	{
		if(areaid_list_fun.isEmpty())
			areaid_list_fun = loadMap(filepath+ "/fun_area.csv");
		if(cityid_list_fun.isEmpty())
			cityid_list_fun = loadMap(filepath+ "/fun_city.csv");
		
		String para = "cityCode="+citycode+"&"+"key="+key_zhizhu ;
		String url = url_zhizhu+"cinemaList.html?";
		String sig = U.getMD5(citycode+key_zhizhu+secret_zhizhu);
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpConnectionParams.setConnectionTimeout(get.getParams(), 15*1000);  
		HttpConnectionParams.setSoTimeout(get.getParams(), 15 * 1000); 
		HttpResponse hr = null;
		String result = "";		
		
		try {

			hr = hc.execute(get);			
			result = EntityUtils.toString(hr.getEntity());
			JSONObject re = new JSONObject(result);
			
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析城市列表失败");
				return;
			}
			List<String[]> cinemalist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("cinemaInfo");
			int len = arr.length();
			if(len ==0) return;
			File city = new File(filepath + "fun_cinema_"+citycode+".csv");
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			File cityMovie = new File(filepath + "fun_movie_"+cityid_list_fun.get(citycode)+".csv");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cityMovie),"GBK"));
			Set<String> movieids = new TreeSet<String>();
			for (int i = 0; i < len; i++) {
				
				String[] s = new String[21];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("cinemaId").toString();
				s[1] = "3-"+s[0];
				s[2] = "cityname";
				s[3] = citycode;//temp.get("cityId").toString();
				s[4] = temp.get("cinemaName").toString();
				s[5] = temp.get("cinemaAdd").toString().replace("\n", "");
				s[6] = "";//temp.get("contact").toString().replace("\n", "");
				s[7] = "电影院";
				s[8] = "250";
				s[9] = temp.get("latitude").toString();
				s[10] = temp.get("longitude").toString();
				s[11] = temp.get("regionCode").toString();
				s[12] = "0";//"tag";
				s[13] = "null";//"invol";
				s[14] = "null";//"brand";
				s[15] ="0";
				s[16] = "";//temp.get("traffic").toString();
				s[17] ="";//no descript temp.get("descript").toString().replace("\n", "");	
				s[18] = "null"; //temp.get("traffic").toString();
				s[19] = temp.get("cinemaLogo").toString();//photo //picture 测试数据没有这个字段   temp.get("traffic").toString();
				s[20] = "null";//"url";//

				//为每个电影院指定放映的电影
				String moviesid = showList(s[0], 0);
				List<String> moviesListid = showListId(s[0], 0);
				movieids.addAll(moviesListid);
				if(moviesid !=null )
				{
					s[14] = moviesid;
				}
				
				s[3] = cityid_list_fun.get(s[3]);
				s[2] = Cinema.gbcode.get(s[3]);
				s[11] = areaid_list_fun.get(s[11]);
				
				cinemalist.add(s);
			}

			StringBuffer sb = new StringBuffer();
			for (Iterator iterator = movieids.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				sb.append(string).append(",");
			}
			if(sb.length()>0 ) 
				bw.write(cityid_list_fun.get(citycode)+"$"+sb.substring(0, sb.length()-1));
			bw.close();
			
			writer.writeAll(cinemalist);
			writer.close();

		}
		catch (IOException e1) {
		}
		
		
	}
	
	public static void getHallList()
	{
		File mergeFile = new File(filepath + "zhizhu_cinema.csv");
		CSVReader cr=null;
		CSVWriter writer = null;
		File hall = new File(filepath + "fun_hall.csv");
		
		try {
			cr = new CSVReader(new FileReader(mergeFile), ',');
			writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(hall),"GBK")));
			List<String[]> poiList=null;			
			poiList = cr.readAll();
			for (int i = 0; i < poiList.size(); i++) {
				String[] tt = poiList.get(i);
				String cinemaId = tt[1].split("-")[1];
				List<String[]> re = getHallList4Cinema(cinemaId);
				writer.writeAll(re);
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
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}		
	}
	
	public static List<String[]> getHallList4Cinema(String cinemaId)
	{
		String para = "cinemaId="+cinemaId+"&key="+key_zhizhu ;
		String url = url_zhizhu+"hallList.html?";
		String sig = U.getMD5(cinemaId+key_zhizhu+secret_zhizhu);
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		try {

			hr = hc.execute(get);
			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
			
			JSONObject re = new JSONObject(result);
			
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析影厅信息 in cinema "+ cinemaId+" 列表失败");
				return null;
			}
			List<String[]> halllist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("hallInfo");
			int len = arr.length();
			if(len ==0) return null;
			for (int i = 0; i < len; i++) {
				
				String[] s = new String[5];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("hallId").toString();
				s[1] = temp.get("hallName").toString();
				s[2] = temp.get("cinamaId").toString();
				s[3] = temp.get("seatCount").toString();
				s[4] = temp.get("vipFlag").toString();
				halllist.add(s);
			}
			return halllist;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void getRegionList()
	{
		File city = new File(filepath+"/fun_area.csv");
		if(citylist_fun.size() == 0)
			loadCityList_Fun();
		
		try {
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			List<String[]> re = new ArrayList<String[]>();
			int len = citylist_fun.size();
			for (int i = 0; i < len; i++) {
				List<String[]> temp = getRegionList4City(citylist_fun.get(i));
				re.addAll(temp);
			}
			
			writer.writeAll(re);
			writer.close();
			
			U.log.debug("parse district over");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 得到这个市下面所有的区
	 */
	public static List<String[]> getRegionList4City(String cityCode)
	{
//		String para = "key="+key_zhizhu+"&cityCode=beijing" ;
		String para = "cityCode="+cityCode+"&"+"key="+key_zhizhu ;
		String url = url_zhizhu+"regionList.html?";
		String sig = U.getMD5(cityCode+key_zhizhu+secret_zhizhu);
		
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		try {

			hr = hc.execute(get);
			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
			JSONObject re = new JSONObject(result);
			
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析城市列表失败");
				return null;
			}
			List<String[]> arealist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("regionInfo");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[4];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("regionCode").toString();
				s[1] = temp.get("regionName").toString();
				s[2] = cityCode;//temp.get("regionName").toString();
				s[3] = Cinema.gbcode.get(s[1]);
				if(s[3]!= null && s[3].length()>0)
					areaid_list_fun.put(s[0], s[3]);
				arealist.add(s);
			}
			
			return arealist;
			
		}
		catch (IOException e1) {
		}
		
		return null;
		
	}
	
	/**
	 * 得到city 写入到文件中
	 */
	public static void getCityList()
	{
		String para = "key="+key_zhizhu ;
		String url = url_zhizhu+"cityList.html?";
		String sig = U.getMD5(key_zhizhu+secret_zhizhu);
		
		url+= para+"&sign="+sig+"&filetype=json";
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
		
		try {

			hr = hc.execute(get);
			
			result = EntityUtils.toString(hr.getEntity());
			System.out.println("result:"+result);
			JSONObject re = new JSONObject(result);
			
			String status = re.get("message").toString();
			if(!status.equals("请求成功"))
			{
				U.log.error("解析城市列表失败");
				return;
			}
			File city = new File(filepath+"/fun_city.csv");
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			List<String[]> citylist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("cityInfo");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[4];
				JSONObject temp = arr.getJSONObject(i);
//				sb.append("\"").append(temp.get("id")).append("\"").append(",");
//				sb.append("\"").append(temp.get("no")).append("\"").append(",");
//				sb.append("\"").append(temp.get("name")).append("\"").append(",");
//				sb.append("\"").append(temp.get("code")).append("\"");
//				arr.equals(i);
				s[0] = temp.get("cityCode").toString();
				s[1] = temp.get("cityName").toString();
				s[2] = temp.get("cityType").toString();
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
	public static Map<String,MovieZhizhu> movie_list ;
	public static MovieZhizhu getMovieZhizhu(String movieId)
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
			
			loadMoveWanZhu();
			movie_tag = content;
			U.log.info("change movie tag "+ movie_tag);
		}
		
		return movie_list.get(movieId);
	}
	
	/**
	 * 这个方法load id 和MovieWanZhu
	 * @return
	 */
	public static boolean loadMoveWanZhu()
	{
		movie_list = new HashMap<String,MovieZhizhu>();
		String areafilepath = filepath+ "fun_movies.csv";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(areafilepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				CSVParser cp = new CSVParser();
				String[] m = cp.parseLine(line);
				MovieZhizhu movie = new MovieZhizhu();
				movie.id = m[0];
				movie.name = m[1];
				movie.englishName = m[2];
				movie.openingDate = m[3];
				movie.director = m[4];
				movie.actor = m[5];
				movie.duration = m[6];
				movie.picture = m[7];
				movie.description = m[8];
				movie.country = m[9];
				movie.refer ="2";

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
		U.log.info("load file sucess "+ areafilepath);
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
		U.log.info(movie_list_fun.get("5846"));
		return true;
	}
	
	/**
	 * 加载hall的id 和name的 k-v对
	 * @return
	 */
	public static boolean loadHall()
	{
		String areafilepath = filepath+ "fun_hall.csv";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(areafilepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				CSVParser cp = new CSVParser();
				String[] m = cp.parseLine(line);
				hall_list_zhizhu.put(m[2]+"-"+m[0], m[1]);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			U.log.error("加载影厅 id-名称 失败");
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

		return true;
	}
	
	
	public static boolean loadArea()
	{
		String areafilepath = filepath+ "fun_area.csv";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(areafilepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				CSVParser cp = new CSVParser();
				String[] m = cp.parseLine(line);
				areaid_list_fun.put(m[0], m[1]);
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

		return true;
		
	}

}
