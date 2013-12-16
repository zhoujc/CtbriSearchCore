package com.ctbri.srhcore.ana.analysis;

import com.ctbri.segment.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.ele.City;

public class CityAnalyzer extends Analyzer {/**
	 * ��key�н�����������ĳ��е����֣�����������ȥ���󣬷��ش�������key�ַ���
	 * @param lq
	 * @param key
	 * @return
	 */
	public String parse(LocalQuery lq, String key)
	{
		String[] strs = key.split(" ");
		String s_city_name = null;
		String first_citycode = null;

		for (int i = 0; i < strs.length; i++) {
//			U.log.info("CityAnalyzer "+ strs[i]);
			U.log.debug("���з�������: "+ key );
			String citycode = City.GetCityCode(strs[i]);
			if(citycode != null && i == 0)
			{
				first_citycode =  citycode;
				City city = new City();
				city.setKey(strs[i]);
				city.setCitycode(citycode);
				s_city_name = City.GetCityNameByAreaCode(citycode);
				if( s_city_name== null)
					s_city_name = strs[i];
				city.setStandName(s_city_name);
				String tmpCity = s_city_name.replaceAll("��", "").replaceAll("������", "").replaceAll("��������", "");
				city.setValue(s_city_name+","+tmpCity);
				key = key.replaceFirst(strs[i], "");
				lq.setCity(city);	
//				lq.setInputnocity(key.trim());
				break;// ����ҵ���һ�����У�������ѭ��
			}			
			if(citycode != null && i!=0 && first_citycode!=null)
			{
				key = key.replace(strs[i], "");				
			}
		}
		if(s_city_name == null)
		{
			City city = new City();
			city.setValue("ȫ��");
			city.setKey("ȫ��");
			city.setCitycode("000");
			city.setStandName("ȫ��");
			lq.setCity(city);
			key = key.replace("ȫ��", "");
			lq.setInputnocity(key.trim());
		}
		key = key.replaceAll(" +", " ").trim();
		return key;
	}
	public CityAnalyzer()
	{
		this.order = 10;
	}
}
