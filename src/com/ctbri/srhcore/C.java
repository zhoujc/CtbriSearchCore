package com.ctbri.srhcore;

import java.util.Locale;
import java.util.ResourceBundle;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;



public class C {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("configSearch", Locale.CHINA);
	public static TokenizerModel segMode = TokenizerModel.COMMON;
	
	/** 索引的子目录 **/
	public static String INDEX_PATH = "index";
	
	public static String Index_Store_Path = "index_store";
	
		
	/**
	 *  电影检索目录
	 */
	public static String Cinema_Index = "cinema_last";
	
	public static String Food_Index = "food_poi";
	
	public static String Tuangou_Index = "tuangou/t_index";
	
	/** 数据文件的全路径 **/
	public static String DATA_PATH = "data";
	
	public static boolean extend_nearby = false;
	
	/**
	 * 用来设置周边搜的时候 范围是否需要缩小
	 */
	public static int nearbySeachTotalExtend = 600;
	
	/** 周边搜的默认的距离   **/
	public static int nearby_range = 1000;
	
	/**  POI转周边的默认距离  **/
	public static int POI_change_nearby_range = 0;
	
	/** 周边搜索范围的最大值，这个可以放到配置文件里。   **/
	public static int NearBy_Max_Range = 10000;
	
	/** POI搜索，搜索返回结果的最大的数目   **/
	public static int Search_total_POI = 200;
	
	/** 周边搜，搜索返回结果的最大的数目   **/
	public static int Search_total_Nearby = 200;
	
	/** 周边搜，自动扩充搜索结果的数目   **/
	public static int Search_Nearby_extend_num = 50;
	
	/** data目录下，类型 文件夹的文件名   **/
	public static String Subject_dic = "type";

	
	static
	{
		
		U.log.info("加载配置文件  configSearch");
		INDEX_PATH = rb.getString("indexName");
		Index_Store_Path = rb.getString("indexStorePath");
		DATA_PATH =  rb.getString("dataPath");
		U.log.info("数据文件的位置  indexPath 值是："+INDEX_PATH );
		U.log.info("数据文件的位置  Index_Store_Path 值是："+Index_Store_Path );
		U.log.info("数据文件的位置  dataPath 值是："+DATA_PATH);
		
		String temp = "";
		

		
		if(rb.containsKey("Search_total_POI"))
		{
			temp = rb.getString("Search_total_POI");
			try {
				C.Search_total_POI = Integer.parseInt(temp);
				U.log.info("成功读取配置文件  Search_total_POI 值是："+Search_total_POI);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.Search_total_POI = 200;
				U.log.error("错误 Search_total_POI 错误，默认值是："+Search_total_POI);
			}
		}
		
		if(rb.containsKey("nearbySeachTotalExtend"))
		{
			temp = rb.getString("nearbySeachTotalExtend");
			try {
				C.nearbySeachTotalExtend = Integer.parseInt(temp);
				U.log.info("成功读取配置文件  Search_total_POI 值是："+nearbySeachTotalExtend);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.nearbySeachTotalExtend = 600;
				U.log.error("错误 nearbySeachTotalExtend 错误，默认值是："+nearbySeachTotalExtend);
			}
		}
		
		
		
		if(rb.containsKey("Search_total_Nearby"))
		{
			temp = rb.getString("Search_total_Nearby");
			try {
				C.Search_total_Nearby = Integer.parseInt(temp);
				U.log.info("成功读取配置文件  Search_total_Nearby 值是："+Search_total_Nearby);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.Search_total_Nearby = 200;
				U.log.error("错误 Search_total_Nearby 错误，默认值是："+Search_total_Nearby);
			}
		}
		//========================
		if(rb.containsKey("NearBy_Max_Range"))
		{
			temp = rb.getString("NearBy_Max_Range");
			try {
				C.NearBy_Max_Range = Integer.parseInt(temp);
				U.log.info("成功读取配置文件  NearBy_Max_Range 值是："+NearBy_Max_Range);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.NearBy_Max_Range = 10000;
				U.log.error("错误 NearBy_Max_Range 错误，默认值是："+NearBy_Max_Range);
			}
		}
		
		if(rb.containsKey("nearby_range"))
		{
			temp = rb.getString("nearby_range");
			try {
				C.nearby_range = Integer.parseInt(temp);
				U.log.info("成功读取配置文件  nearby_range 值是："+nearby_range);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.nearby_range = 1000;
				U.log.error("错误 nearby_range 错误，默认值是："+nearby_range);
				
			}
		}//renren_range
		
		
		

		
		
	}
	
	public static void main(String[] args) {
		if(args != null)
		{
			int len = args.length;
			for (int i = 0; i < args.length; i++) {
				System.out.println(args[i]);
			}
		}
		System.out.println("tst");
	}
}
