package com.ctbri.srhcore.parsedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.ctbri.segment.U;
import com.ctbri.srhcore.C;


public class UPara {
	
	/** key 编码 value是标准城市名称     **/
	public static HashMap<String,String> gbcode = new HashMap<String,String>();
	
	/**  key 城市名称 value是编码    **/
	public static HashMap<String,String> regbcode = new HashMap<String,String>();
	
	
	static
	{
		loadGBCityCode();

		System.out.println(gbcode.get("110000"));
		System.out.println(regbcode.get("朝阳"));
	}
	
	public static void main(String[] args) {
		
	}
	public static boolean loadGBCityCode()
	{
		String filepath = "resource/file/gbcitycode.txt";
		String filepath2 = C.DATA_PATH + File.separator + "gbcitycode"+ File.separator + "gbcitycode.txt";
		File gbcodefile = new File(filepath);
		U.log.info("load file "+ filepath);
		if(!gbcodefile.exists())
		{
			gbcodefile = new File(filepath2);
			U.log.info("load file "+ filepath2 );
		}
			
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(gbcodefile)));

			String line = "";
			while((line = br.readLine()) != null)
			{
				if(line.length()<=6 || line.startsWith("//")) continue;
				String key = line.substring(6).trim().replace(" ", "");
				String vl = line.substring(0,6);//数字
//				U.log.debug(vl+"  "+key);
				gbcode.put(vl, key);
				if(regbcode.containsKey(key))
				{
					String tt = regbcode.get(key)+","+vl;
					regbcode.put(key, tt);
					
				}
				else
					regbcode.put(key, vl);
				if((key.endsWith("区") || key.endsWith("县")|| key.endsWith("市"))&& key.length()>2)
				{
					String newkey = key.substring(0, key.length()-1);
					if(regbcode.containsKey(newkey))
					{						
						String tt = regbcode.get(newkey)+","+vl;
						regbcode.put(newkey, tt);
					}
					else						
						regbcode.put(newkey, vl);
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
		return true;
	}

}
