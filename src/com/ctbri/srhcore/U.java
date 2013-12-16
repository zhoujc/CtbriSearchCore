package com.ctbri.srhcore;

import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class U {
	
	/** ��־�������ݴ�ӡ������̨��   **/
	public static Log log = LogFactory.getLog("map.info");
	
	/** ��־��������������ļ��   **/
	public static Log fileLog = LogFactory.getLog("map.info.file");
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * ��������ľ��룬����Ĳ����Ͳ������ˡ�
	 * @param slat
	 * @param slon
	 * @param elat
	 * @param elon
	 * @return
	 */
	public static double getDistance(double slat,double slon,double elat, double elon)
	{
		 double radLat1 = slat * Math.PI / 180; 
	        double radLat2 = elat * Math.PI / 180; 
	        double a = radLat1 - radLat2; 
	        double b = slon * Math.PI / 180 - elon * Math.PI / 180; 
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))); 
	        s = s * 6378137.0;// ȡWGS84��׼�ο������еĵ��򳤰뾶(��λ:m) 
	        s = Math.round(s * 10000) /(double)10000; 
	        return s;         

//		return Math.sqrt(Math.abs(slat - elat)* Math.abs(slat - elat)+ Math.abs(slon - elon)* Math.abs(slon - elon)) * 100000D;
	}

	/** lucene index Filed:nid   **/
	public static final String Field_nid = "nid";
	
	public static final String Field_cid = "cid";
	/** lucene index Filed:city   **/
	public static final String Field_city = "cityCode";
	/** lucene index Filed: city   **/
	public static final String Field_title = "name";
	/** lucene index Filed: title_addition   **/
	public static final String Field_title_addition = "title_addition";
	/** lucene index Filed: address   **/
	public static final String Field_address = "address";
	/** lucene index Filed: phone   **/
	public static final String Field_phone = "phone";
	/** lucene index Filed: type   **/
	public static final String Field_type = "typeCode";
	/** lucene index Filed: longitude   **/
	public static final String Field_longitude = "longitude";
	/** lucene index Filed: latitude   **/
	public static final String Field_latitude = "latitude";
	/** lucene index Filed: longitude5   **/
	public static final String Field_longitude5 = "longitude5";
	/** lucene index Filed: latitude5   **/
	public static final String Field_latitude5 = "latitude5";
	/** lucene index Filed: longitude7   **/
	public static final String Field_longitude7 = "longitude7";
	/** lucene index Filed: latitude7   **/
	public static final String Field_latitude7 = "latitude7";
	/** lucene index Filed: tag   **/
	public static final String Field_tag = "tag";
	/** lucene index Filed: brand   **/
	public static final String Field_brand = "brand";
	/** lucene index Filed: invol   **/
	public static final String Field_invol = "invol";
	/** lucene index Filed: confidenceLevel   **/
	public static final String Field_confidenceLevel = "confidenceLevel";
	
	public static final String Field_province="province";
	
	public static final String Field_nation="nation";
	
	public static final String Field_photo = "photo";

	private static String getFormattedText(byte[] bytes) {

        int len = bytes.length;

        StringBuilder buf = new StringBuilder(len * 2);

        // ������ת����ʮ�����Ƶ��ַ�����ʽ

        for (int j = 0; j < len; j++) {          
        	buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);

            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);

        }

        return buf.toString();

    }
	
	public static String getMD5(String str)
	{
		if (str == null) {
			return null;
		}
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/** 
     * ASCII���пɼ��ַ���!��ʼ��ƫ��λֵΪ33(Decimal) 
     */  
    static final char DBC_CHAR_START = 33; // ���!  
  
    /** 
     * ASCII���пɼ��ַ���~������ƫ��λֵΪ126(Decimal) 
     */  
    static final char DBC_CHAR_END = 126; // ���~  
  
    /** 
     * ȫ�Ƕ�Ӧ��ASCII��Ŀɼ��ַ��ӣ���ʼ��ƫ��ֵΪ65281 
     */  
    static final char SBC_CHAR_START = 65281; // ȫ�ǣ�  
  
    /** 
     * ȫ�Ƕ�Ӧ��ASCII��Ŀɼ��ַ�����������ƫ��ֵΪ65374 
     */  
    static final char SBC_CHAR_END = 65374; // ȫ�ǡ�  
  
    /** 
     * ASCII���г��ո���Ŀɼ��ַ����Ӧ��ȫ���ַ������ƫ�� 
     */  
    static final int CONVERT_STEP = 65248; // ȫ�ǰ��ת�����  
  
    /** 
     * ȫ�ǿո��ֵ����û�������ASCII�����ƫ�ƣ����뵥������ 
     */  
    static final char SBC_SPACE = 12288; // ȫ�ǿո� 12288  
  
    /** 
     * ��ǿո��ֵ����ASCII��Ϊ32(Decimal) 
     */  
    static final char DBC_SPACE = ' '; // ��ǿո�  

    /** 
     * <PRE> 
     * ����ַ�->ȫ���ַ�ת��   
     * ֻ����ո�!��&tilde;֮����ַ����������� 
     * </PRE> 
     */  
    public static String bj2qj(String src) {  
        if (src == null) {  
            return src;  
        }  
        StringBuilder buf = new StringBuilder(src.length());  
        char[] ca = src.toCharArray();  
        for (int i = 0; i < ca.length; i++) {  
            if (ca[i] == DBC_SPACE) { // ����ǰ�ǿո�ֱ����ȫ�ǿո����  
                buf.append(SBC_SPACE);  
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // �ַ���!��~֮��Ŀɼ��ַ�  
                buf.append((char) (ca[i] + CONVERT_STEP));  
            } else { // ���Կո��Լ�ascii���������ɼ��ַ�֮����ַ����κδ���  
                buf.append(ca[i]);  
            }  
        }  
        return buf.toString();  
    } 
    
	/** 
     * <PRE> 
     * ȫ���ַ�->����ַ�ת��   
     * ֻ����ȫ�ǵĿո�ȫ�ǣ���ȫ�ǡ�֮����ַ����������� 
     * </PRE> 
     */  
    public static String qj2bj(String src) {  
        if (src == null) {  
            return src;  
        }  
        StringBuilder buf = new StringBuilder(src.length());  
        char[] ca = src.toCharArray();  
        for (int i = 0; i < src.length(); i++) {  
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // ���λ��ȫ�ǣ���ȫ�ǡ�������  
                buf.append((char) (ca[i] - CONVERT_STEP));  
            } else if (ca[i] == SBC_SPACE) { // �����ȫ�ǿո�  
                buf.append(DBC_SPACE);  
            } else { // ������ȫ�ǿո�ȫ�ǣ���ȫ�ǡ���������ַ�  
                buf.append(ca[i]);  
            }  
        }  
        return buf.toString();  
    }  
	public static void main(String[] args) {
		U.log.info("test");
		U.log.info(getMD5("key=magus&showId=1234"));
		String qj = "�����֣��ģ������İ������3D��������Բ�����ţ��ģ�";
		String bj = U.qj2bj(qj);
		System.out.println(qj);
		System.out.println(bj);
	}

}
