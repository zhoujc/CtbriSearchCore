package com.ctbri.srhcore.ana.ele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;


public class City  extends Entity{
	


	

	private String standName;
	private String citycode;
	
	
	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getStandName() {
		return standName;
	}

	public void setStandName(String standName) {
		this.standName = standName;
	}

	public City(){}
	public City(String citycode,String key,String standName)
	{
		this.key = key;
		this.citycode = citycode;
		this.standName = standName;
	}

	public Query toQuery() {
		// TODO Auto-generated method stub
		if(this.key == null || this.citycode.equals("000")) return null;
		TermQuery q = new TermQuery(new Term(U.Field_city,this.citycode));
		
		U.log.debug("[city/toQuery] "+q.toString());
		return q;
	}
	private static HashMap<String,String> ht_city = new HashMap<String,String>();//the relationship between the city name and the city code
	private static HashMap<String,String> ht_codes_name = new HashMap<String,String>();//the relationship between the the area code and the city code
	private static HashMap<String,String> ht_city_city = new HashMap<String,String>();//the relationship between the standard city name and other cityname
	private static String cityname_file = C.DATA_PATH + File.separator + "citycode" + File.separator + "citycode.txt";
	private static String code_cityname_file = C.DATA_PATH + File.separator + "citycode" + File.separator + "code_cityname.txt";
	private static String differentcity_file     = C.DATA_PATH + File.separator + "citycode" + File.separator + "DifferentCity.txt";

	/**
	 * 根据城市名称，返回城市编码
	 * @param cityName
	 * @return
	 */
	public static String GetCityCode(String cityName){
		if(cityName == null) return null;
		return ht_city.get(cityName);
	}
	
	/**
	 * 
	 * @param areacode
	 * @return
	 */
	public static String GetCityNameByAreaCode(String areacode){
		if(areacode == null) return null;
		return ht_codes_name.get(areacode);
	}
	
	/**
	 * 根据编码 得到城市标准的城市名称
	 * @param cityName
	 * @return
	 */
	public static String GetStandardCityName(String cityName){
		if(cityName == null) return null;
		String strCity = ht_city_city.get(cityName);
		if(strCity == null)
			return cityName;
		else
			return strCity;
	}
	
	static{
			ht_city = loadCityNameCode(cityname_file);
			ht_city_city = LoadCodeFile(differentcity_file);
			ht_codes_name = LoadCodeFile(code_cityname_file);
		}
	
	private static HashMap<String,String> LoadCodeFile(String filename){
		U.log.info("load file:"+ filename+" with charset encode: GBK");
		HashMap<String,String> ht = new HashMap<String,String>();
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)),"GBK"));
		   String line = "";
		   while((line = br.readLine())!=null){
			   if(line.trim().equals("")) continue;
			   String[] items = line.split("=");
			   ht.put(items[0].trim(), items[1].trim());
			   
		   }
		   br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ht;
		
	}
	private static HashMap<String,String> loadCityNameCode(String filename){
		
		U.log.info("load file:"+ filename +" with charset encode: GBK");
		HashMap<String,String> ht = new HashMap<String,String>();
		BufferedReader br = null;
		try{
		   br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)),"GBK"));
		   String line = "";
		   while((line = br.readLine())!=null){
			   if(line.trim().equals("")) continue;
			   String[] items = line.split("=");
			   ht.put(items[0].trim(), items[1].trim());
			   if(items[0].trim().endsWith("市"))
			   {
				   String tt = items[0].trim();
				   ht.put(tt.substring(0, tt.length()-1), items[1].trim());
			   }
			   
		   }
		   br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ht;
		
	}
	
	
		


}
