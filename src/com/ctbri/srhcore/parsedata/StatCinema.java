package com.ctbri.srhcore.parsedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import au.com.bytecode.opencsv.CSVParser;

import com.ctbri.segment.U;


public class StatCinema {
	
	public static void main(String[] args) {
		
		CSVParser cp = new CSVParser();
		String filepath = "poi/cinema_last.csv";
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		BufferedWriter bw1 = null;
		BufferedWriter bw2 = null;
		Map<String,Integer> map = new TreeMap<String,Integer>();
		Map<String,String> proname = new HashMap<String,String>();
		
		proname.put("11", "����");
		proname.put("12", "���");
		proname.put("13", "�ӱ�");
		proname.put("14", "ɽ��");
		proname.put("15", "����");
		proname.put("21", "����");
		proname.put("22", "����");
		proname.put("23", "������");
		proname.put("31", "�Ϻ�");
		proname.put("32", "����");
		proname.put("33", "�㽭");
		proname.put("34", "����");
		proname.put("35", "����");
		proname.put("36", "����");
		proname.put("37", "ɽ��");
		proname.put("41", "����");
		proname.put("42", "����");
		proname.put("43", "����");
		proname.put("44", "�㶫");
		proname.put("45", "����");
		proname.put("46", "����");
		proname.put("50", "����");
		proname.put("51", "�Ĵ�");
		proname.put("52", "����");
		proname.put("53", "����");
		proname.put("54", "����");
		proname.put("61", "����");
		proname.put("62", "����");
		proname.put("63", "�ຣ");
		proname.put("64", "����");
		proname.put("65", "�½�");
	
		
		try {
			br1 = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"GBK"));
			
//			bw1  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("dup.result")),"GBK"));
//			bw2  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("cinema_last.csv")),"GBK"));
//			bw1  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(resultfileName)),"GBK"));
//			bw2  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(csvfileName)),"GBK"));
			String content1 = "";
//			String content2 = "";
			int count1 = 0;
//			int count2 = 0;
			while((content1 = br1.readLine()) != null)
			{
				System.out.println(count1++);
				String[] ss1 = cp.parseLine(content1);
				if(ss1.length<10)
				{
					U.log.error("wrong line: "+ content1);
					continue;
				}
				String pro = ss1[3].substring(0, 2);
				if(map.containsKey(pro))
				{
					map.put(pro, map.get(pro)+1);
				}
				else
				{
					map.put(pro, 1);
				}
				
			}
			
			Set keys = map.keySet();
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(proname.containsKey(key))
					System.out.println(proname.get(key)+"  "+map.get(key));
				else
					System.out.println(key+"  "+map.get(key));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
