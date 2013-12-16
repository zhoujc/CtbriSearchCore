package com.ctbri.srhcore.prc.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.ctbri.segment.U;
import com.ctbri.srhcore.pojo.MapPoint;
import com.ctbri.srhcore.pojo.POIObject;



public class USort {
	
	public int sortByRank()
	{
		return 0;
	}
	
	private static float compareFloat2String(float v1,String v2,float defaultValue)
	{
		float fv2 = 0f;
		if(v2.trim().length()>0)
		{
			try {
				fv2 = Float.parseFloat(v2);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				fv2 = defaultValue;
			}
			if(Float.compare(v1, fv2)<0)//如果v1<v2 返回v2 否则都是返回v1
				return v1;
			else
				return fv2;
		}
		
		return v1;
	}
	
	
	public static LinkedList<POIObject> sortByAvePerCost(LinkedList<POIObject> src)
	{
		U.log.debug("sort by aveCost");
		Collections.sort(src,new Comparator<POIObject>(){

			@Override
			public int compare(POIObject o1, POIObject o2) {
				// TODO Auto-generated method stub
				float aveCost1 = 10000f;
				float aveCost2 = 10000f;
				String t1 = o1.getFieldValue("avePerCost");

				try {
					
					aveCost1 = Float.parseFloat(t1);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					aveCost1 =10000f;
				}
				if(Float.compare(aveCost1, 0f) ==0)
					aveCost1 = 10000f;
				String t2 = o2.getFieldValue("avePerCost");
				try {
					aveCost2 = Float.parseFloat(t2);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					aveCost2 =10000f;
				}
				if(Float.compare(aveCost2, 0f) ==0)
					aveCost2 = 10000f;
								
				int re = Float.compare(aveCost1, aveCost2);
				
				if(re<0)
					return -1;
				else if(re>0)
					return 1;
				else
					return 0;

			}
			
		} );
		return src;	
		
	}
	
	public static LinkedList<POIObject> sortByDiscount(LinkedList<POIObject> src)
	{
		Collections.sort(src,new Comparator<POIObject>(){

			@Override
			public int compare(POIObject o1, POIObject o2) {
				// TODO Auto-generated method stub
				float discount1 = 100f;
				float discount2 = 100f;
				String t1 = o1.getFieldValue("zcDiscount");
				discount1 = compareFloat2String(discount1,t1,100f);
				String t2 = o1.getFieldValue("ftDiscount");
				discount1 = compareFloat2String(discount1,t2,100f);
				
				t1 = o2.getFieldValue("zcDiscount");
				discount2 = compareFloat2String(discount1,t1,100f);
				t2 = o2.getFieldValue("ftDiscount");
				discount2 = compareFloat2String(discount1,t2,100f);
				
				int re = Float.compare(discount1, discount2);
				
				if(re<0)
					return -1;
				else if(re>0)
					return 1;
				else
					return 0;

			}
			
		} );
		return src;
	}
	
	public static LinkedList<POIObject> sortByRank(LinkedList<POIObject> src)
	{
		U.log.debug("sort by rank!!!!!!!!!");
		Collections.sort(src, new Comparator<POIObject>(){

			@Override
			public int compare(POIObject o1, POIObject o2) {
				// TODO Auto-generated method stub

				int sr1 = o1.getRank();//o1.getFieldValue("rank");
				int sr2 = o2.getRank();//o2.getFieldValue("rank");
				
				return sr2-sr1;
			}
			
		});
		return src;
	}
	
	public static LinkedList<POIObject> setPOIDistanceandDirect(LinkedList<POIObject> source,int range,MapPoint center)
	{
		LinkedList<POIObject> re = new LinkedList<POIObject>();
		for (int i = 0; i < source.size(); i++) {
			POIObject p = source.get(i);
			double dis = USort.getDistance(p.getLat(), p.getLon(), center.getdLat(), center.getdLon());		
			if(dis > range)
			{
				System.out.println("===========================   " + dis);
				 continue;
			}
			String direct = USort.getDirect(center, p.getLon(), p.getLat(), dis);
			p.setDirection(direct);
			p.setDistance(dis);
			
			re.add(p);

		}
		return re;
	}
	
	public static double getDistance(double slat,double slon,double elat, double elon)
	{
		 double radLat1 = slat * Math.PI / 180; 
	        double radLat2 = elat * Math.PI / 180; 
	        double a = radLat1 - radLat2; 
	        double b = slon * Math.PI / 180 - elon * Math.PI / 180; 
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))); 
//	        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m) 
	        s = s * 6371008.7714;
	        s = Math.round(s * 10000) /(double)10000; 
	        return s;         

//		return Math.sqrt(Math.abs(slat - elat)* Math.abs(slat - elat)+ Math.abs(slon - elon)* Math.abs(slon - elon)) * 100000D;
	}
	
	public static String getDirect(MapPoint center,double lon,double lat,double dis)
	{
		double dx = (center.getdLon() - lon) * 100000D; //东西位移
        double nb = (center.getdLat() - lat) * 100000D; //南北位移
   
        String strDirection ="";
        if (dis > 50) {
            if (Math.abs(dx / dis) > 0.3) {
                if (dx > 0) {
                    strDirection = "西";
                } else {
                    strDirection = "东";
                }
            }
            if (Math.abs(nb / dis) > 0.3) {
                if (nb > 0) {
                    strDirection += "南";
                } else {
                    strDirection += "北";
                }
            }
        }
        return strDirection;
	}

}
