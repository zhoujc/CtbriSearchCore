package com.ctbri.srhcore;

import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class U {
	
	/** 日志对象，内容打印到控制台上   **/
	public static Log log = LogFactory.getLog("map.info");
	
	/** 日志对象，内容输出到文件里。   **/
	public static Log fileLog = LogFactory.getLog("map.info.file");
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * 计算两点的距离，具体的参数就不解释了、
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
	        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m) 
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

        // 把密文转换成十六进制的字符串形式

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
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal) 
     */  
    static final char DBC_CHAR_START = 33; // 半角!  
  
    /** 
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal) 
     */  
    static final char DBC_CHAR_END = 126; // 半角~  
  
    /** 
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281 
     */  
    static final char SBC_CHAR_START = 65281; // 全角！  
  
    /** 
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374 
     */  
    static final char SBC_CHAR_END = 65374; // 全角～  
  
    /** 
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移 
     */  
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔  
  
    /** 
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理 
     */  
    static final char SBC_SPACE = 12288; // 全角空格 12288  
  
    /** 
     * 半角空格的值，在ASCII中为32(Decimal) 
     */  
    static final char DBC_SPACE = ' '; // 半角空格  

    /** 
     * <PRE> 
     * 半角字符->全角字符转换   
     * 只处理空格，!到&tilde;之间的字符，忽略其他 
     * </PRE> 
     */  
    public static String bj2qj(String src) {  
        if (src == null) {  
            return src;  
        }  
        StringBuilder buf = new StringBuilder(src.length());  
        char[] ca = src.toCharArray();  
        for (int i = 0; i < ca.length; i++) {  
            if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代  
                buf.append(SBC_SPACE);  
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符  
                buf.append((char) (ca[i] + CONVERT_STEP));  
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理  
                buf.append(ca[i]);  
            }  
        }  
        return buf.toString();  
    } 
    
	/** 
     * <PRE> 
     * 全角字符->半角字符转换   
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他 
     * </PRE> 
     */  
    public static String qj2bj(String src) {  
        if (src == null) {  
            return src;  
        }  
        StringBuilder buf = new StringBuilder(src.length());  
        char[] ca = src.toCharArray();  
        for (int i = 0; i < src.length(); i++) {  
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内  
                buf.append((char) (ca[i] - CONVERT_STEP));  
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格  
                buf.append(DBC_SPACE);  
            } else { // 不处理全角空格，全角！到全角～区间外的字符  
                buf.append(ca[i]);  
            }  
        }  
        return buf.toString();  
    }  
	public static void main(String[] args) {
		U.log.info("test");
		U.log.info(getMD5("key=magus&showId=1234"));
		String qj = "（数字３Ｄ）（中文半角括号3D）（中文圆角括号３Ｄ）";
		String bj = U.qj2bj(qj);
		System.out.println(qj);
		System.out.println(bj);
	}

}
