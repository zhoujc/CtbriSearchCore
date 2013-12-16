package com.ctbri.srhcore.parsedata;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import com.ctbri.srhcore.parsedata.zhizhu.ZhizhuCinema;

public class Cinema {

	
	/**  key 城市名称 value是编码    **/
	public static HashMap<String,String> gbcode = new HashMap<String,String>();
	/** key 编码 value是标准城市名称     **/
	public static HashMap<String,String> regbcode = new HashMap<String,String>();
	/**
	 * 用来标示movie文件的更新，每次都把索引文件的标示赋给这个变量，如果变化了就要重新加载文件
	 */
	public static String movie_tag = "cinema_last";
	
//	public static String Fetch_cinema_dir = "D:/workspace/CtbriCore/";
//	public static String Fetch_cinema_dir = "/home/poiuser/web_app/task/";
	
	
	//测试用户的帐号
//	public static String filepath = "resource/csv/";
//	public static String url_fun = "http://qa.fun-guide.mobi:7005/";
//	public static String secret_fun = "9EAECAA87375C808D4741003ADC4B448";
//	public static String key_fun = "key=LBS189CN";
	
	public static String filepath = CinemaC.Fetch_cinema_dir+"/resource/wanzhucsv/";
	public static String url_fun = "http://api.funguide.com.cn/";
	public static String secret_fun = "78d5555b0e15ecd636b1d96564d6164b";
	public static String key_fun = "key=LBS189CN";
	public static List<String> citylist_fun = new ArrayList<String>();
	
	/**  key是fun的area id，vaule是名称    **/
	public static Map<String,String> areaid_list_fun = new HashMap<String,String>();
	/**  key是fun的city id，vaule是国标代码    **/
	public static Map<String,String> cityid_list_fun = new HashMap<String,String>();
	
	/**
	 * 这个map里面只是存储了电影的id和名称
	 */
	public static Map<String,String> movie_list_fun = new HashMap<String,String>();
	public static Map<String,MovieWanZhu> movie_list ;
	
	static
	{
		U.log.info("load gbcitycode");
		loadGBCityCode();	
		loadArea();
	}
	
	public static void main(String[] args) {

//		System.out.println(loadShows("100768", "8252", 0));
		
//		parseCity();		
//		parseDistrict();		
//		StringBuffer sb = new StringBuffer();
//		String testDisCode = "140300";//140202,140302,140402,140502,320902,440602,441502,640102
//		sb.append(regbcode.get("城区")).append("   ");
//		sb.append(gbcode.get("朝阳区"));
//		System.out.println(sb.toString());
//		String districtCode = regbcode.get("城区");
//		int index = districtCode.indexOf(testDisCode.substring(0, 4));
//		System.out.println(districtCode.substring(index, index+6)+"###############");
		
		long ss = System.currentTimeMillis();	
		
		parseMovies();
		loadMovie();	
		
		
		int len = citylist_fun.size();
		while(len ==0)
		{
			loadCityList_Fun();
			len = citylist_fun.size();
		}
		for (int i = 0; i < len; i++) {
			parseCinemaInCity(citylist_fun.get(i));
		}
		
		mergeFile();
		mergeMovieFile();
		System.out.println("total time: "+(System.currentTimeMillis() -ss));	

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
		
		File mergeFile = new File(filepath + "wanzhu_movielist.csv");
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
							sb.append(movie_list_fun.get(ids[j])).append("|").append("2-").append(ids[j]).append(";");
					}
					if(sb.charAt(sb.length()-1) == '$') continue;
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
		
		File mergeFile = new File(filepath + "wanzhu_cinema.csv");
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
	
	/**
	 * 这个方法要设置请求时间啊
	 * @param cinemaid
	 * @return
	 */
	public static List<String> parseMovies4Cinema(String cinemaid)
	{
		U.log.debug("cinemaid "+ cinemaid);
		String url = url_fun+"v2/movies?";

		String para = key_fun + "&cinemaId="+ cinemaid;
		String sig = U.getMD5(para+ secret_fun);
		url +=para+"&sig="+sig;
		U.log.debug(url);
		
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpConnectionParams.setConnectionTimeout(get.getParams(), 10*1000);  
		HttpConnectionParams.setSoTimeout(get.getParams(), 10 * 1000); 
		HttpResponse hr = null;
		String result = "";

		try {

			hr = hc.execute(get);
			result = EntityUtils.toString(hr.getEntity());
			JSONObject re = new JSONObject(result);
			int status = Integer.parseInt(re.get("code").toString());
			if(status != 200)
			{
				U.log.error("解析指定电影院电影列表失败");
				return null;
			}
		
			List<String> movielist = new ArrayList<String>();
			JSONArray arr = re.getJSONArray("movies");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
	
				JSONObject temp = arr.getJSONObject(i);
				movielist.add(temp.get("id").toString());
				

			}
			
			U.log.debug("parse movies over");
			return movielist;
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
		}
		
		return null;
	}
	
	public static void parseMovies()
	{
		String url = url_fun+"v2/movies?";//城市列表

		String para = key_fun ;//+ "&cinemaId=104513";
//		String para = key_fun + "&cinemaId="+ cinemaid;
		String sig = U.getMD5(para+ secret_fun);
		url +=para+"&sig="+sig;
		U.log.debug(url);
		
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpConnectionParams.setConnectionTimeout(get.getParams(), 10*1000);  
		HttpConnectionParams.setSoTimeout(get.getParams(), 10 * 1000); 
		HttpResponse hr = null;
		String result = "";

		try {

			hr = hc.execute(get);
			result = EntityUtils.toString(hr.getEntity());
			System.out.println(result);
			JSONObject re = new JSONObject(result);
			System.out.println(result);
			int status = Integer.parseInt(re.get("code").toString());
			if(status != 200)
			{
				U.log.error("解析城市列表失败");
				return;
			}
			File city = new File(filepath+"/fun_movies.csv");
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			
			List<String[]> movielist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("movies");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[12];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("id").toString().replaceAll("\"", "“");//replaceAll("\"", "\\\\\"");
				s[1] = U.qj2bj(temp.get("name").toString().replaceAll("\"", "“"));//.replaceAll("\"", "\\\\\"");
				
				s[2] = U.qj2bj(temp.get("type").toString().replaceAll("\"", "“"));//.replaceAll("\"", "\\\\\"");
				s[3] = U.qj2bj(temp.get("language").toString().replaceAll("\"", "“"));//.replaceAll("\"", "\\\\\"");
				s[4] = temp.get("director").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[5] = temp.get("actor").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[6] = temp.get("release").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[7] = temp.get("duration").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");				
				s[8] = temp.get("trailer").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[9] = temp.get("picture").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[10] = temp.get("descript").toString().replace("\r\n", "").replace("\n", "").replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				s[11] = temp.get("change").toString().replaceAll("\"", "“");//.replaceAll("\"", "\\\\\"");
				

				movielist.add(s);
			}
			
			writer.writeAll(movielist);
			writer.close();
			
			U.log.info("parse movies over");
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
		}
		finally
		{
			hc.getConnectionManager().shutdown();
		}
	}
	
	
	/**
	 * 生成两组文件 某个城市的影院和 某个城市的电影
	 * 电影的文件里 只是记录的电影的id
	 * @param citycode_fun
	 */
	public static void parseCinemaInCity(String citycode_fun)
	{
		if(areaid_list_fun.isEmpty())
			areaid_list_fun = loadMap(filepath+ "/fun_area.csv");
		if(cityid_list_fun.isEmpty())
			cityid_list_fun = loadMap(filepath+ "/fun_city.csv");
		
		String url = url_fun+"v2/cinemas?";//
		String para = key_fun+"&isSale=1&cityId="+citycode_fun;
		String sig = U.getMD5(para+ secret_fun);
		url +=para+"&sig="+sig;
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
			
			int status = Integer.parseInt(re.get("code").toString());
			if(status != 200)
			{
				U.log.error("解析城市"+citycode_fun +" 影院列表失败");
				return;
			}
			System.out.println(re);			
			List<String[]> cinemalist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("cinemas");
			int len = arr.length();
			if(len ==0) return;
			File city = new File(filepath + "fun_cinema_"+citycode_fun+".csv");
			File cityMovie = new File(filepath + "fun_movie_"+cityid_list_fun.get(citycode_fun)+".csv");
			Set<String> movieids = new TreeSet<String>();
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cityMovie),"GBK"));

			for (int i = 0; i < len; i++) {
				
				String[] s = new String[21];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("id").toString();
				s[1] = "2-"+s[0];
				s[2] = "cityname";
				s[3] = temp.get("cityId").toString();
				s[4] = temp.get("name").toString();
				s[5] = temp.get("address").toString().replace("\r\n", "").replace("\n", "");
				s[6] = temp.get("contact").toString().replace("\r\n", "").replace("\n", "");
				s[7] = "电影院";
				s[8] = "250";
				s[9] = temp.get("latitude").toString();
				s[10] = temp.get("longitude").toString();
				s[11] = temp.get("areaId").toString();
				s[12] = "0";//"tag";
				s[13] = "null";//"invol";
				s[14] = "null";//"brand";
				s[15] = "0";
				s[16] = temp.get("traffic").toString();
				s[17] = temp.get("descript").toString().replace("\r\n", "").replace("\n", "");	
				s[18] = "null"; //temp.get("traffic").toString();
				s[19] = temp.get("pictrue").toString();//photo //picture 测试数据没有这个字段   temp.get("traffic").toString();
				s[20] = "null";//"url";//

				//为每个电影院指定放映的电影
				List<String> moviesid = parseMovies4Cinema(s[0]);
				movieids.addAll(moviesid);
				if(moviesid !=null && moviesid.size()>0)
				{
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < moviesid.size(); j++) {
						String tempname = movie_list_fun.get(moviesid.get(j));
						U.log.debug(tempname);
						if(tempname!=null && sb.indexOf(tempname)<0)
							sb.append(tempname).append("|2-").append(moviesid.get(j)).append(";");
					}
					U.log.info("放映电影 "+ sb.toString());
					if(sb.length()>0)
						s[14] = sb.substring(0, sb.length()-1);
					
				}
				
//				s[7] = temp.get("latitude").toString();
				s[3] = cityid_list_fun.get(s[3]);
				s[2] = gbcode.get(s[3]);
				s[11] = areaid_list_fun.get(s[11]);
				
				cinemalist.add(s);
			}
			StringBuffer sb = new StringBuffer();
			for (Iterator iterator = movieids.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				sb.append(string).append(",");
			}
			if(sb.length()>0 ) 
				bw.write(cityid_list_fun.get(citycode_fun)+"$"+sb.substring(0, sb.length()-1));
			bw.close();
			writer.writeAll(cinemalist);
			writer.close();
			
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
		}
		
		finally
		{
			hc.getConnectionManager().shutdown();
		}
	}
	
	public static void parseDistrict()
	{
		File city = new File(filepath+"/fun_area.csv");
		if(citylist_fun.size() == 0)
			loadCityList_Fun();
		try {
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			List<String[]> re = new ArrayList<String[]>();
			int len = citylist_fun.size();
			for (int i = 0; i < len; i++) {
				List<String[]> temp = parseDistrict4City(citylist_fun.get(i));
				re.addAll(temp);
			}
			
			//for test
//			List<String[]> temp = parseDistrict4City("10");
//			re.addAll(temp);
			
			writer.writeAll(re);
			writer.close();
			
			U.log.debug("parse district over");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static List<String[]> parseDistrict4City(String citycode_fun)
	{
		String url = url_fun+"v2/areas?";//城市列表
		String para = key_fun+"&cityId="+citycode_fun;
		String sig = U.getMD5(para+ secret_fun);
		url +=para+"&sig="+sig;
		U.log.debug(url);
		
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";

		try {

			hr = hc.execute(get);
			result = EntityUtils.toString(hr.getEntity());
			JSONObject re = new JSONObject(result);
			
			int status = Integer.parseInt(re.get("code").toString());
			if(status != 200)
			{
				U.log.error("解析城市"+ citycode_fun+" 区域列表失败");
				return null;
			}
	
			List<String[]> arealist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("areas");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[4];
				JSONObject temp = arr.getJSONObject(i);

				s[0] = temp.get("id").toString();
				s[1] = temp.get("name").toString();
				s[2] = temp.get("cityId").toString();
				s[3] = gbcode.get(s[1]);
				if(s[3]!= null && s[3].length()>0)
					areaid_list_fun.put(s[0], s[3]);
				arealist.add(s);
			}
			
			return arealist;

		} catch (IOException e1) {
		}
		return null;
		
	}

	
	
	/**根据id返回电影的详细信息
	 * @param movieId
	 * @return
	 */
	public static MovieWanZhu getMovieWanzhu(String movieId)
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
			loadMoveWanZhu();
			U.log.info("change movie tag "+ movie_tag);
		}
		return movie_list.get(movieId);
	}
	
	public static String loadShows(String cinemaId,String movieId,int time)
	{
		
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE,time);
		
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String a1=dateformat1.format(cal1.getTime());
		
		String url = url_fun+"v2/shows?";
		String para= key_fun+"&cinemaId="+cinemaId+"&movieId="+movieId+"&time="+a1;
		
		String sig = U.getMD5(para+ secret_fun);
		url +=para+"&sig="+sig;
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		
		HttpGet get = new HttpGet(url);
		HttpConnectionParams.setConnectionTimeout(get.getParams(), 3*1000);  
		HttpConnectionParams.setSoTimeout(get.getParams(), 3 * 1000); 
		HttpResponse hr = null;
		String result = "";
		
		try {


			hr = hc.execute(get);
			result = EntityUtils.toString(hr.getEntity());
			U.log.debug(result);
			JSONObject re = new JSONObject(result);
			int status = Integer.parseInt(re.get("code").toString());
			if(status != 200)
			{
				U.log.error("解析排期列表失败");
				return "";
			}

////			JSONArray arr = re.getJSONArray("shows");
////			return re.toString();				
//			return result;
			
			JSONArray arr = re.getJSONArray("shows");
			
			StringBuffer sb = new StringBuffer();

			int len = arr.length();
			for (int i = 0; i < len; i++) {
				JSONObject temp = arr.getJSONObject(i);
				sb.append("{");
				sb.append("\"id\":").append("\"").append(temp.get("id")).append("\"").append(",");
				sb.append("\"refer\":").append("\"").append("2").append("\"").append(",");
				sb.append("\"time\":").append("\"").append(temp.get("time")).append("\"").append(",");
				sb.append("\"hallId\":").append("\"").append(temp.get("hallId")).append("\"").append(",");
				sb.append("\"hallName\":").append("\"").append(temp.get("hallName")).append("\"").append(",");
				sb.append("\"cinemaId\":").append("\"").append(temp.get("cinemaId")).append("\"").append(",");
				sb.append("\"cinemaName\":").append("\"").append(temp.get("cinemaName")).append("\"").append(",");
				sb.append("\"movieId\":").append("\"").append(temp.get("movieId")).append("\"").append(",");
				sb.append("\"movieName\":").append("\"").append(temp.get("movieName")).append("\"").append(",");
				sb.append("\"price\":").append("\"").append(temp.get("price")).append("\"").append(",");//商户价格
				sb.append("\"standard\":").append("\"").append(temp.get("standard")).append("\"").append(",");//标准价格
				sb.append("\"lowest\":").append("\"").append(temp.get("lowest")).append("\"").append(",");//用户价格
				sb.append("\"retails\":").append("\"").append(temp.get("retails")).append("\"").append(",");//用户价格
				sb.append("\"highest\":").append("\"").append(temp.get("highest")).append("\"");
				sb.append("},");
				
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
	
	/**
	 * 为cityid_list_fun赋值<br>
	 * 为citylist_fun赋值
	 */
	public static void parseCity() {
		String url = url_fun+"v2/cities?";//城市列表
//		String url = url_fun+"/cities?";//城市列表
		String para = key_fun;
		String sig = U.getMD5(para+ secret_fun);
		url +=para+"&sig="+sig;
		U.log.debug(url);
		
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";

		try {

			hr = hc.execute(get);
			result = EntityUtils.toString(hr.getEntity());
			U.log.debug(result);
			JSONObject re = new JSONObject(result);
			
			int status = Integer.parseInt(re.get("code").toString());
			if(status != 200)
			{
				U.log.error("解析城市列表失败");
				return;
			}
			File city = new File(filepath+"/fun_city.csv");
			CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city),"GBK")));
			
			List<String[]> citylist = new ArrayList<String[]>();
			JSONArray arr = re.getJSONArray("cities");
			int len = arr.length();
			for (int i = 0; i < len; i++) {
				String[] s = new String[5];
				JSONObject temp = arr.getJSONObject(i);
//				sb.append("\"").append(temp.get("id")).append("\"").append(",");
//				sb.append("\"").append(temp.get("no")).append("\"").append(",");
//				sb.append("\"").append(temp.get("name")).append("\"").append(",");
//				sb.append("\"").append(temp.get("code")).append("\"");
//				arr.equals(i);
				s[0] = temp.get("id").toString();
				s[1] = temp.get("no").toString();
				s[2] = temp.get("name").toString();
				s[3] = temp.get("code").toString();
				s[4] = regbcode.get(s[2]);
				if(s[4] !=null && s[4].length()>0)
					cityid_list_fun.put(s[0], s[4]);
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
	
	/**
	 * 这个方法load id 和MovieWanZhu
	 * @return
	 */
	public static boolean loadMoveWanZhu()
	{
		movie_list = new HashMap<String,MovieWanZhu>();
		String areafilepath = filepath+ "fun_movies.csv";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(areafilepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				CSVParser cp = new CSVParser();
				String[] m = cp.parseLine(line);
				MovieWanZhu movie = new MovieWanZhu();
				movie.id = m[0];
				movie.name = m[1];
				movie.type = m[2];
				movie.language = m[3];
				movie.director = m[4];
				movie.actor = m[5];
				movie.release = m[6];
				movie.duration = m[7];
				movie.trailer = m[8];
				movie.picture = m[9];
				movie.descript = m[10];	
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
