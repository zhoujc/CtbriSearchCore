package com.ctbri.srhcore.parsedata.tuangou;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import au.com.bytecode.opencsv.CSVReader;

public class FormatFile {

	public String fileDir ;
	
	public static void main(String[] args) {
		String path = "./resource/tuangou/";
		String filename = "t_shop_group.csv";
		String tempFile = "t_temp.csv";
		String re_fileName = "t_index.csv";
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path+filename)),"UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path+tempFile)),"GBK"));
			String line;
//			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null)
			{
				
				line = line.replaceAll(" ", "");//替换一个utf-8的数据
				
				if(line.endsWith("\\"))
				{
//					line = line.replace("\\", "#");
					line = line.replace("\\", "");
					bw.write(line);
					
				}
				else if(line.endsWith("\""))
				{
					bw.write(line);
					bw.newLine();
				}
				else
				{
					bw.write(line);
//					bw.newLine();
				}	
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		{
			if(br != null)
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
		
		CSVReader cr=null;
		
		try {
			cr = new CSVReader(new InputStreamReader(new FileInputStream(new File(path+tempFile)),"GBK"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path+re_fileName)),"GBK"));
			String[] pf ;
			while((pf = cr.readNext()) != null)
			{
				StringBuffer sb = new StringBuffer();
				sb.append("\"").append(pf[0]).append("\"").append(",");//0
				sb.append("\"").append(pf[1]).append("\"").append(",");
				sb.append("\"").append(pf[2]).append("\"").append(",");
				sb.append("\"").append(pf[3]).append("\"").append(",");
				sb.append("\"").append(pf[4]).append("\"").append(",");
				sb.append("\"").append(pf[5]).append("\"").append(",");
				sb.append("\"").append(pf[6]).append("\"").append(",");//6
				sb.append("\"").append(pf[19]).append("\"").append(",");
				sb.append("\"").append("10000").append("\"").append(",");
				sb.append("\"").append(pf[9]).append("\"").append(",");//9
				sb.append("\"").append(pf[10]).append("\"").append(",");//10
				sb.append("\"").append("").append("\"").append(",");//district
				sb.append("\"").append("02").append("\"").append(",");//chanel
				sb.append("\"").append(pf[11]).append("\"").append(",");//invol
				sb.append("\"").append(pf[20]).append("\"").append(",");//brand
				sb.append("\"").append("0").append("\"").append(",");//price
				sb.append("\"").append("").append("\"").append(",");//traffic
				sb.append("\"").append(pf[14]).append("\"").append(",");//描述信息
				sb.append("\"").append(pf[15]).append("\"").append(",");//id
				sb.append("\"").append(pf[16]).append("\"").append(",");//图片
				sb.append("\"").append(pf[17]).append(",").append(pf[18]).append("\"");//link
				bw.write(sb.toString());
				bw.newLine();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
	}
	
}
