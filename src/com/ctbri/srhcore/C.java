package com.ctbri.srhcore;

import java.util.Locale;
import java.util.ResourceBundle;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;



public class C {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("configSearch", Locale.CHINA);
	public static TokenizerModel segMode = TokenizerModel.COMMON;
	
	/** ��������Ŀ¼ **/
	public static String INDEX_PATH = "index";
	
	public static String Index_Store_Path = "index_store";
	
		
	/**
	 *  ��Ӱ����Ŀ¼
	 */
	public static String Cinema_Index = "cinema_last";
	
	public static String Food_Index = "food_poi";
	
	public static String Tuangou_Index = "tuangou/t_index";
	
	/** �����ļ���ȫ·�� **/
	public static String DATA_PATH = "data";
	
	public static boolean extend_nearby = false;
	
	/**
	 * ���������ܱ��ѵ�ʱ�� ��Χ�Ƿ���Ҫ��С
	 */
	public static int nearbySeachTotalExtend = 600;
	
	/** �ܱ��ѵ�Ĭ�ϵľ���   **/
	public static int nearby_range = 1000;
	
	/**  POIת�ܱߵ�Ĭ�Ͼ���  **/
	public static int POI_change_nearby_range = 0;
	
	/** �ܱ�������Χ�����ֵ��������Էŵ������ļ��   **/
	public static int NearBy_Max_Range = 10000;
	
	/** POI�������������ؽ����������Ŀ   **/
	public static int Search_total_POI = 200;
	
	/** �ܱ��ѣ��������ؽ����������Ŀ   **/
	public static int Search_total_Nearby = 200;
	
	/** �ܱ��ѣ��Զ����������������Ŀ   **/
	public static int Search_Nearby_extend_num = 50;
	
	/** dataĿ¼�£����� �ļ��е��ļ���   **/
	public static String Subject_dic = "type";

	
	static
	{
		
		U.log.info("���������ļ�  configSearch");
		INDEX_PATH = rb.getString("indexName");
		Index_Store_Path = rb.getString("indexStorePath");
		DATA_PATH =  rb.getString("dataPath");
		U.log.info("�����ļ���λ��  indexPath ֵ�ǣ�"+INDEX_PATH );
		U.log.info("�����ļ���λ��  Index_Store_Path ֵ�ǣ�"+Index_Store_Path );
		U.log.info("�����ļ���λ��  dataPath ֵ�ǣ�"+DATA_PATH);
		
		String temp = "";
		

		
		if(rb.containsKey("Search_total_POI"))
		{
			temp = rb.getString("Search_total_POI");
			try {
				C.Search_total_POI = Integer.parseInt(temp);
				U.log.info("�ɹ���ȡ�����ļ�  Search_total_POI ֵ�ǣ�"+Search_total_POI);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.Search_total_POI = 200;
				U.log.error("���� Search_total_POI ����Ĭ��ֵ�ǣ�"+Search_total_POI);
			}
		}
		
		if(rb.containsKey("nearbySeachTotalExtend"))
		{
			temp = rb.getString("nearbySeachTotalExtend");
			try {
				C.nearbySeachTotalExtend = Integer.parseInt(temp);
				U.log.info("�ɹ���ȡ�����ļ�  Search_total_POI ֵ�ǣ�"+nearbySeachTotalExtend);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.nearbySeachTotalExtend = 600;
				U.log.error("���� nearbySeachTotalExtend ����Ĭ��ֵ�ǣ�"+nearbySeachTotalExtend);
			}
		}
		
		
		
		if(rb.containsKey("Search_total_Nearby"))
		{
			temp = rb.getString("Search_total_Nearby");
			try {
				C.Search_total_Nearby = Integer.parseInt(temp);
				U.log.info("�ɹ���ȡ�����ļ�  Search_total_Nearby ֵ�ǣ�"+Search_total_Nearby);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.Search_total_Nearby = 200;
				U.log.error("���� Search_total_Nearby ����Ĭ��ֵ�ǣ�"+Search_total_Nearby);
			}
		}
		//========================
		if(rb.containsKey("NearBy_Max_Range"))
		{
			temp = rb.getString("NearBy_Max_Range");
			try {
				C.NearBy_Max_Range = Integer.parseInt(temp);
				U.log.info("�ɹ���ȡ�����ļ�  NearBy_Max_Range ֵ�ǣ�"+NearBy_Max_Range);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.NearBy_Max_Range = 10000;
				U.log.error("���� NearBy_Max_Range ����Ĭ��ֵ�ǣ�"+NearBy_Max_Range);
			}
		}
		
		if(rb.containsKey("nearby_range"))
		{
			temp = rb.getString("nearby_range");
			try {
				C.nearby_range = Integer.parseInt(temp);
				U.log.info("�ɹ���ȡ�����ļ�  nearby_range ֵ�ǣ�"+nearby_range);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				C.nearby_range = 1000;
				U.log.error("���� nearby_range ����Ĭ��ֵ�ǣ�"+nearby_range);
				
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
