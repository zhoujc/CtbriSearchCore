package com.ctbri.srhcore.pojo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import com.ctbri.srhcore.U;

public class POIObject implements Comparable<POIObject> {

	private String name;
    private String addr;
    private String strType;
    private String strTag;
	private double score;
	private double distance;
	private String city;

	private int doc;
	private int dir;
	private int style;

	private double dis2Center;
	private String direction;
//	private double distanceToCenter; //����Poi�����ĵ�ľ���
//	private String selfInfo;
//	private boolean isBrand;
	private String brand;
	private HashMap<String,String> fieldMap = new HashMap<String,String>();
	
//    private double dScore;
//	private double nameScore;
	private double addressScore;
	private String strCustomer; //�û���ʶ 
	
    private String self_info;//��ɫ��Ϣ�ֶ� addby zhoujiancheng 2010��9��21��13:31:22
	private double lon;
	private double lat;
    
    public Object clone()
    {
    	POIObject obj = new POIObject();
    	obj.self_info = this.self_info;
    	obj.addr = this.addr;
    	obj.fieldMap = this.fieldMap;
    	obj.distance = this.distance;
    	obj.score = this.score;
    	obj.addressScore = this.addressScore;
    	obj.addr = this.addr;
    	obj.brand = this.brand;
    	obj.dis2Center = this.dis2Center;
    	obj.direction = this.direction;
    	return obj;
    }
	@Override
	public int compareTo(POIObject arg0) {
		 int iScore = -(new Double(getScore())).compareTo(new Double(arg0.getScore()));
	        if(name == null){
	        	name = getFieldValue("title");	
	        }
	        if (iScore == 0){
	        	String name = null;
	        	try{
	        	name = arg0.getName();
	            iScore = (new Integer(name.length())).compareTo(new Integer((name.length())));
	        	}catch(Exception e){
	        	//	e.printStackTrace();
	        	}
	        }
	        return iScore;
	}
	
	/**
	 * ��ȡ��������lucene��doc����������Ը�ֵ��Map��
	 * @param doc
	 */
	public void setLDoc(Document doc) {
		List list = doc.getFields();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field f = (Field) iterator.next();
			String key = f.name();
			String value = f.stringValue();
			if(value == null || value.equals("") || value.equals("null")) continue;
			this.fieldMap.put(key, value);			
		}
	}
	
	/**
	 * ��ȡMap���󣬸��ݴ����key����value��<br>
	 * ���᷵��null�����valueΪnull������Ϊ""
	 * @param key
	 * @return
	 */
	public String getFieldValue(String key)
	{
		if(this.fieldMap == null) return "";
		String value = this.fieldMap.get(key);
		if(value == null) return "";
		return value;
	}
	
	/**
	 * ����������map�з���k-v�����֮ǰ�Ѿ�����key�����Ḳ����ǰ��value
	 * @param key
	 * @param value
	 */
	public void setFieldValue(String key,String value)
	{
		if(this.fieldMap == null) return ;
			this.fieldMap.put(key, value);
	}
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * ���ظ�poi��name����Ӧlucene�������� title
	 * @return
	 */
	public String getName() {
    	if(name == null){
    		name = getFieldValue("name").trim();
    	}
        return name;
    }
	
	/**
	 * �õ����poi�����ͣ����ֵ��lucene�����е�Field_type = "type"������
	 * <br>���᷵��null�����û��ֵ���᷵�ؿ��ַ���""��
	 * @return
	 */
	public String getSinglePhoto()
	{
		String photo_url = getFieldValue("photo");
//		if(this.getName().equals(C.keyMCY)|| photo_url.startsWith("img") )
//		{
//			return photo_url;
//		}
//		if(photo_url != null && photo_url.length()>0)
//		{
//			String[] pps = photo_url.split(";");
//			if(pps.length>0)
//				return C.POI_IMG_URL_PRE+pps[0];
//			else
//				return "";
//		}
//		else
			return "";
	}
	
	public String getCity() {
    	if(city == null){
    		city = getFieldValue("city").trim();
    	}
        return city;
    }
	
	public boolean IsBrand()//��ʾ�Ƿ�ΪƷ��
    {
    	if(brand == null){
    		brand = getFieldValue("brand");
    	}
    	
    	return !brand.equals("");
    }
	
	public String getAddr()
	{
		if(addr == null){
			addr = this.getFieldValue(U.Field_address).trim();
    	}
        return addr;
	}
	
    public String getPhone() {
        return this.getFieldValue("phone");
    }
    
    public String getContext() {

        return this.getFieldValue("content");
    }
    
    public String getPromotion() {

        return this.getFieldValue("promotion");
    }
    
    public String getReferer() {

        return this.getFieldValue("referer");
    }
    
    public String getDetail() {
        return this.getFieldValue("detail");
    }
    
    public void setDetail(String detail)
    {
    	this.setFieldValue("detail", detail);
    }
    
    public int getRank()
    {
    	String r = "";
    	int re = 0;
    	if(this.fieldMap.containsKey("rank"))
    		r = this.fieldMap.get("rank");
    	else if(this.fieldMap.containsKey("invol"))
    		r = this.fieldMap.get("invol");
    	else
    		return 0;
    	try {
			re = (int)Float.parseFloat(r);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			re = 0;
		}
		return re;
    }
    
    public void setRank(int rank)
    {
    	
    	if(this.fieldMap.containsKey("rank"))
    		this.fieldMap.put("rank", rank+"");
    	else if(this.fieldMap.containsKey("invol"))
    		this.fieldMap.put("invol", rank+"");
    }
    
    
    public void setName(String name)
    {
    	this.setFieldValue("name", name);
    }
    public String getDist() {
        return this.getFieldValue("district");
    }
    
    public String getIcon() {
        return this.getFieldValue("icon");
    }
    
    public String getKeywords() {

        return this.getFieldValue("keywords");
    }
    public String getPic() {

        return this.getFieldValue("picture");
    }
    
    public String getBrand(){//����Ʒ����

    	return getFieldValue("brand");
    }

    public String getId() {
        return this.getFieldValue("nid");
    }
    
    
	public String getCustomer() {
		return strCustomer;
	}

	public void setCustomer(String strCustomer) {
		this.strCustomer = strCustomer;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public int getDoc() {
		return doc;
	}

	public void setDoc(int doc) {
		this.doc = doc;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * �õ����poi�����ͣ����ֵ��lucene�����е�Field_type = "type"������
	 * <br>���᷵��null�����û��ֵ���᷵�ؿ��ַ���""��
	 * 2011-6-14
	 * @return
	 */
	public String getType()
	{
		if(strType == null){
    		strType = this.getFieldValue(U.Field_type);
    	}
        return strType != null ? strType : "";
	}
	
	/**
	 * �õ�������͵�tag�����ֵ��lucene�����е�Field_tag = "tag"������
	 * <br>���᷵��null�����û��ֵ���᷵�ؿ��ַ���""��
	 * 2011-6-14
	 * @return
	 */
	public String getTag()
	{
		if(strTag == null){
    		strTag = getFieldValue(U.Field_tag).trim();
    	}
    	return strTag != null ? strTag : "";
	}
	
	
	
	public HashMap<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(HashMap<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}

    public void setDirection(String direction){
    	this.direction = direction;
    }
    
    public String getDirection( ){
    	return this.direction;
    }
	
	public void setLat(double lat)
	{
//		this.setFieldValue(U.Field_latitude, lat+"");
		this.lat = lat;
	}
	public void setLon(double lon)
	{
//		this.setFieldValue(U.Field_longitude, lon+"");
		this.lon = lon;
	}
	
	/**
	 * �õ���poi�ľ�γ�ȣ������poiû�о�γ�ȣ�����0
	 * 2011-6-14
	 * @return
	 */
	public double getLon() {
	        return lon;
	}
	
	/**
	 * �õ���poi�ľ�γ�ȣ������poiû�о�γ�ȣ�����0
	 * 2011-6-14
	 * @return
	 */
	public double getLat() {

	        return lat;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}



}
