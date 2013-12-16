package test.com.ctbri.srhcore.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVParser;

import com.ctbri.srhcore.parsedata.Cinema;
import com.ctbri.srhcore.parsedata.zhizhu.ZhizhuCinema;

public class TestGBCityCodeFile {

	
	@Test
	public void testGBCityCode()
	{
		String filepath = "resource/file/gbcitycode.txt";
		String outputfilepath = "resource/file/gbcitycode__.txt";
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputfilepath))));

			String line = "";
			while((line = br.readLine()) != null)
			{
				if(line.length()<=6 || line.startsWith("//")) continue;
				String key = line.substring(6).trim().replace(" ", "");
				String vl = line.substring(0,6);
				if(vl.endsWith("00") && !key.endsWith("省") )
				{
					bw.write(vl+"="+key);
					bw.newLine();
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
			if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
	
	@Test
	public void testGBCityNameCode()
	{
		String filepath = "resource/file/gbcitycode.txt";
		String outputfilepath = "resource/file/gbcityname_code.txt";
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputfilepath))));

			String line = "";
			while((line = br.readLine()) != null)
			{
				if(line.length()<=6 || line.startsWith("//")) continue;
				String key = line.substring(6).trim().replace(" ", "");
				String vl = line.substring(0,6);
				if(vl.endsWith("00")&& !key.endsWith("省"))
				{
//					bw.write(vl+"="+key);
					bw.write(key+"="+vl);
					bw.newLine();
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
			if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
	
	@Test
	public void testFoodPOI()
	{
		String filepath = "resource/file/testFoodPOI.txt";
		BufferedReader br = null;

		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
			String content = "";
			while((content = br.readLine()) != null)
			{
				CSVParser cp = new CSVParser();
				String[] ss = cp.parseLine(content);
				System.out.println(ss.length);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testss()
	{
		  

        int time =-1;
        
        Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE,time);
		
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a1=dateformat1.format(cal1.getTime());
		System.out.println(a1);
		String ss= dateformat1.format(new Date(1373452200000l));
		System.out.println(ss);
	}
	
	@Test
	public void testStr()
	{
		String ss = "sss.s-ss.s";
		System.out.println(ss.replaceFirst("\\.", ":"));
	}
	
	@Test
	public void testMovieShow()
	{
		String cinemaId = "2-101604";
//		String movieId = "摩登年代|3-00110480;侏罗纪公园（数字３Ｄ）|3-05120054;怪兽大学（数字３Ｄ）|3-05120070;周恩来的四个昼夜|3-00110147;环太平洋（数字３Ｄ）|3-05120067;回到爱开始的地方|3-00110197;激战|3-00110229;诡魇|2-7881";
		String movieId = "2-8227";
		String[] cinemaIds = cinemaId.split(";");
		String[] movieIds = movieId.split(",");
		Map<String,String> cinemaMap = new HashMap<String,String>();
		
		for(int i =0;i< cinemaIds.length;i++)
		{
			String[] temp = cinemaIds[i].split("-");
			cinemaMap.put(temp[0],temp[1]);
		}
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();
		for (int i = 0; i < movieIds.length; i++) {
			String[] movie = movieIds[i].split("-");
			String c_id = cinemaMap.get(movie[0]);
			String m_id = movie[1];
			String re = "";
			if(movie[0].equals("2"))
			{
				re = Cinema.loadShows(c_id, m_id, 0);
				JSONObject jo = new JSONObject(re);
				if(jo.get("code").equals("200"))
				{
					JSONArray arr = jo.getJSONArray("shows");
					for (int j = 0; j < arr.length(); j++) {
						JSONObject temp = arr.getJSONObject(j);
						list.add(temp);
					}
				}
				
				
			}
			else if(movie[0].equals("3"))
			{
				re = ZhizhuCinema.loadShows(c_id, m_id, 0);
				JSONObject jo = new JSONObject(re);
				if(jo.get("code").equals("200"))
				{
					JSONArray arr = jo.getJSONArray("shows");
					for (int j = 0; j < arr.length(); j++) {
						JSONObject temp = arr.getJSONObject(j);
						list.add(temp);
					}
				}
				
			}				
		}
		
		Collections.sort(list, new Comparator<JSONObject>(){

			@Override
			public int compare(JSONObject arg0, JSONObject arg1) {
				// TODO Auto-generated method stub
				float s1 = 10000;
				float s2 = 10000;
				try {
					s1 = Float.parseFloat(arg0.get("standard").toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					s1=10000;
				}
				try {
					s2 = Float.parseFloat(arg1.get("standard").toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					s2 = 10000;
				}
				int re = Float.compare(s1, s2);
				if(re<0)
				{
					return -1;
				}
				else if(re ==0)
					return 0;
				else
					return 1;				

			}
			
		});
		
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < list.size(); j++) {
			sb.append(list.get(j));
		}
		
		
	}
	
	@Test
	public void testParseMovie()
	{
		String ss = ZhizhuCinema.loadShows("31050301","05120054",0);
		System.out.println(ss);
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();
		
		JSONObject jo = new JSONObject(ss);
		System.out.println("code "+jo.get("code"));
		if(jo.get("code").toString().equals("200"))
		{
			JSONArray arr = jo.getJSONArray("shows");
			for (int j = 0; j < arr.length(); j++) {
				JSONObject temp = arr.getJSONObject(j);
				list.add(temp);
			}
		}
		Collections.sort(list, new Comparator<JSONObject>(){

			@Override
			public int compare(JSONObject arg0, JSONObject arg1) {
				// TODO Auto-generated method stub
				float s1 = 10000;
				float s2 = 10000;
				try {
					s1 = Float.parseFloat(arg0.get("standard").toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					s1=10000;
				}
				try {
					s2 = Float.parseFloat(arg1.get("standard").toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					s2 = 10000;
				}
				int re = Float.compare(s1, s2);
				if(re<0)
				{
					return -1;
				}
				else if(re ==0)
					return 0;
				else
					return 1;				

			}
			
		});
		
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < list.size(); j++) {
			sb.append(list.get(j)).append("\n");
		}
		System.out.println("=======================");
		System.out.println(sb.toString());
		
	}
	

}
