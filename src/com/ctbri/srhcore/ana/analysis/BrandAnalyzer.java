package com.ctbri.srhcore.ana.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.ele.BrandWord;





public class BrandAnalyzer extends Analyzer{
	
	public BrandAnalyzer()
	{
		this.order = 15;
		this.isReplace = false;
	}
	
	/**
	 * 查询品牌文件，识别出字符串里的品牌词，并将这个词从字典中删除<br>
	 * @param lq
	 * @param key
	 * @return
	 */
	public String parse(LocalQuery lq , String key)
	{
		int startIndex = 0;
		String[] keys = key.split(" ");
		for (int i = 0; i < keys.length; i++) {
			if(brand_DICTIONARY.containsKey(keys[i]))
			{
				BrandWord b = new BrandWord();
				b.setBrand((String)brand_DICTIONARY.get(keys[i]));
				b.setKey(keys[i]);
				
				lq.setBrandword(b);
			
				int index = lq.getSegStrIndex(keys[i],b.getOrder());
				if(index >-1)
					lq.getTokenEntities()[index]  = b;
				key = key.substring(0, startIndex)+ key.substring(startIndex).replaceFirst(keys[i], "");
                startIndex = startIndex - keys[i].length();
//				key = key.replaceFirst(keys[i], "");
			}
			startIndex += keys[i].length()+1;
		}
		key = key.replaceAll(" +", " ").trim();
		return key;
	}
	
	/**
	 * 增加解析字符串功能
	 * @param key
	 * @return
	 */
	public static BrandWord parse(String key)
	{
		String[] keys = key.split(" ");
		for (int i = 0; i < keys.length; i++) {
			if(brand_DICTIONARY.containsKey(keys[i]))
			{
				BrandWord b = new BrandWord();
				b.setBrand((String)brand_DICTIONARY.get(keys[i]));
				b.setKey(keys[i]);
				
				return b;
			}
		}
		return null;
	}
	
	private static HashMap<String,String> brand_DICTIONARY = new HashMap<String,String>();
	
	static 
	{
		try
		{
			String dicDir = C.DATA_PATH + File.separator
			+ "brand_dic" + File.separator + "brand_new" + ".dic";
			BufferedReader br = new BufferedReader(new FileReader(dicDir)); 
			for(String s2 = null; (s2 = br.readLine()) != null;) {
                s2 = s2.trim();
                if(s2 != null && s2.length() > 0)
                {
                    int idx = s2.indexOf(":");
                    if(idx > 0)
                    {
                        String typeName = s2.substring(0,idx).trim();
                        String[] brand = s2.substring(idx+1).trim().split(",");
                        for(int i=0; i<brand.length; i++) {
                        	brand[i] = brand[i].trim();
                            if(brand[i].length()>0) {
                            	brand_DICTIONARY.put(brand[i], typeName);
                            }
                        }
                    }
                }
            }
            br.close();
		}
		catch(Exception e)
		{
			U.log.error(e);
		}
	}

}
