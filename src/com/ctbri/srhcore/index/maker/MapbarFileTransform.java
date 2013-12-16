package com.ctbri.srhcore.index.maker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class MapbarFileTransform {

	
	private String inputfilePath = "resource/mapbar/010_北京市_909525.csv";
//	private String inputfilePath = "resource/mapbar/beijingtest.csv";
	//beijingtest.csv
	String outputfilePath = "110000.110000.010.csv";
	
	static HashMap<String,String> typeTableMap = new HashMap<String,String>();
	
	static
	{
		loadTypeTable();
		System.out.println("loadTypeTable success");
	}
	
	
	public static void main(String[] args) {
		
		MapbarFileTransform mft = new MapbarFileTransform();
		CSVReader cr=null;
		CSVWriter writer = null;
		try {
			cr = new CSVReader(new FileReader(new File(mft.inputfilePath)), ',');
			File city = new File(mft.outputfilePath);
			writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<String[]> poiList=null;
		List<String[]> relist = new ArrayList<String[]>();
		try {
			poiList = cr.readAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int count = 0;
		for(String[] poi:poiList){
			count ++;
//			System.out.println("process "+ count);
			if(count%10000 ==0)
				System.out.println("process "+ count);
			if(poi.length< 48)
			{
				System.out.println(poi[0]);
				continue;
			}
			String[] re = new String[23];
			re[0] = poi[0];
			re[1] = "null";
			re[2] = "北京市";
			re[3] = "010";
			re[4] = poi[5];
			re[5] = poi[9];
			re[6] = poi[8];
			String typename = null;
			typename = typeTableMap.get(poi[1]);
			re[7] = typename;
			re[8] = poi[1];
			re[9] = poi[18];
			re[10] =  poi[19];
			re[11] = poi[4];
			re[12] = poi[34];
			re[13] = poi[35];
			re[14] = poi[36];
			re[15] = poi[44];
			re[16] = poi[13];
			re[17] = poi[47];
			re[18] = poi[3];
			re[19] = "null";
			re[20] = "null";
			re[21] = "null";
			re[22] = "null";
			relist.add(re);
			
		}
		

		
		writer.writeAll(relist);
		if(writer != null)
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public static void loadTypeTable()
	{
		String typeTable = "resource/mapbar/infotype.csv";
		CSVReader cr=null;
		try {
			cr = new CSVReader(new FileReader(typeTable), ',');
			List<String[]> poiList=null;			
			poiList = cr.readAll();
			for (int i = 0; i < poiList.size(); i++) {
				String[] t = poiList.get(i);
				typeTableMap.put(t[0],t[1]);
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

}
