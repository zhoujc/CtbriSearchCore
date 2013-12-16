package com.ctbri.srhcore.parsedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import au.com.bytecode.opencsv.CSVReader;

import com.ctbri.srhcore.U;

public class CinemaC {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("configCinema", Locale.CHINA);
	public static String Fetch_cinema_dir="/home/zhoujc/web_app/task/";
	static
	{
		
		
		
		String temp = "";
		
		
		if(rb.containsKey("Fetch_cinema_dir"))
		{
			temp = rb.getString("Fetch_cinema_dir");
			try {
				Fetch_cinema_dir = temp;
				U.log.info("成功读取配置文件  Fetch_cinema_dir 值是："+Fetch_cinema_dir);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				
				U.log.error("错误 Fetch_cinema_dir 错误，默认值是："+Fetch_cinema_dir);
			}
		}
		
		
	}
	
	
	
	
	
	public static boolean delOldIndex = false;
	
	public static void main(String[] args) {
		CSVReader cr=null;
		BufferedReader br = null;
		try {
//			cr = new CSVReader(new FileReader(this.cityCSVFile), ',');
			cr = new CSVReader(new InputStreamReader(new FileInputStream("fun_movies.csv"),"GBK"));
//			br = new BufferedReader(new InputStreamReader(new FileInputStream(this.cityCSVFile),"GBK"));
			String[] poiFields ;
			while((poiFields = cr.readNext()) != null)
			{
				System.out.println(poiFields.length);
				for (int i = 0; i < poiFields.length; i++) {
					System.out.println(i+"   "+poiFields[i]);
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	

}
