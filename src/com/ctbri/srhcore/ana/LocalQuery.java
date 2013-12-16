package com.ctbri.srhcore.ana;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.ele.BrandWord;
import com.ctbri.srhcore.ana.ele.City;
import com.ctbri.srhcore.ana.ele.Entity;
import com.ctbri.srhcore.ana.ele.Subject;
import com.ctbri.srhcore.pojo.MapPoint;
import com.ctbri.srhcore.pojo.POIObject;


public class LocalQuery {

	private LinkedList<POIObject> poiResult = new LinkedList<POIObject>();
	private List<Subject> type_list = new ArrayList<Subject>();	
	
	private City city = new City("110000","北京市","北京市");
	//分析过程中，为这些变量赋值
	private String rawStr;//用户原始输入
	private String inputnocity;//用户的原始输入剔除了城市，为的是在大字典里，和geocoding中调用。 add zhou@2010年10月12日18:08:11
	private String segStr;	//待分词的原始字串
	private String[] segs;	//分词后的结果
	private BrandWord brandword;	//品牌词	
	public String noSpaceStr;//用户除去城市的原始输入，并进行了trim处理
	/**
	 * 在分词之前处理，将用户的输入按照空格分开，最经常的用法就是判断类似"东直门中学" 和 "东直门  中学"的这种输入。
	 */
	public String[] noSpace;
	
	private boolean isUsrPoint = false;//判断这个MapPoint 是用户输入，还是程序自己判断。
	private POIObject onlyPoint = null;
	public  MapPoint centerPoi = null; //中心点	
	public  String centerName="";
	private boolean isCityCenterPoi = true;//默认为城市中心
	private boolean hasBusStationType = false;	
	private String type;//use by subject	
	private Entity[] tokenEntities;
	private Query userQuery;//用户自定义查询语句
	private Query nearbyquery = null;// 目前的应用就是周边搜索将点转化为区域使用的lucene语句字符串
	
	
	////////////////////////////////////检索阶段的变量
	private int range; //周边的range
	private	int start;	//结果的开始值
	private int end;	//结果的结束值
	private int totalResult;	

	//分析完成后，查询的时候，为这些变量赋值，记录各个成分的数目，为后面的变化提供参考和依据。
	private boolean isStatic = false;
	
	
	public LocalQuery(){};
	/**
	 * 这个方法主要用来测试
	 * @param rawStr	传入的待处理的字符串
	 */
	public LocalQuery(String rawStr)
	{
		this.rawStr = rawStr;
		this.segStr = rawStr;
	}
	
	/**
     * 判断这个 str 在分词结果中的位置，并且这个成分Entity的order 小于 order，就返回当前的位置。
     * 如果找不到，或者这个成分已经赋值，返回-1。
     * @param str
     * @return
     */
    public int getSegStrIndex(String str, int order)
    {
    	if(this.segStr == null || this.segs == null ) return -1;
    	for (int i = 0; i < this.segs.length; i++) {
			if(segs[i].equals(str) /*&& ((this.tokenEntities[i] == null))*/)
			{
				if(this.tokenEntities[i] == null) return i;
				else if(this.tokenEntities[i].getOrder()<order) return i;
			}
		}
    	return -1;
    }
    
    public BooleanQuery toQuery()
    {
    	BooleanQuery totalQuery = new BooleanQuery();
    	Query tempQuery = null;
		
		
		if(!isStatic)
		{
			U.log.debug("LocalQuery is staticing ");
			this.statKeywords();
		}
				
		if(this.city != null)
		{
			tempQuery = this.city.toQuery();
			if(tempQuery != null)
				totalQuery.add(tempQuery, BooleanClause.Occur.MUST);
		}
		if(this.brandword != null)
		{

			tempQuery = this.brandword.toQuery();
			if(tempQuery != null)
				totalQuery.add(tempQuery, BooleanClause.Occur.MUST);
		}
		if(tokenEntities != null)
		{
			for (int i = 0; i < this.tokenEntities.length; i++) {
				if(this.tokenEntities[i] instanceof City ||
						 this.tokenEntities[i] instanceof BrandWord ) continue;
				
				tempQuery = this.tokenEntities[i].toQuery();
				if(tempQuery != null)
					totalQuery.add(tempQuery,BooleanClause.Occur.MUST);
			}
		}
		if(this.type != null&& this.type.length()>0)
			totalQuery.add(new TermQuery(new Term("typeCode",this.type)),BooleanClause.Occur.MUST);
		
		if(this.userQuery != null)
			totalQuery.add(this.userQuery,BooleanClause.Occur.MUST);
		
		if(this.nearbyquery!=null)
		{
			totalQuery.add(this.nearbyquery,BooleanClause.Occur.MUST);
		}
		return totalQuery;
		
   }
	public MapPoint getCenterPoi() {
		return centerPoi;
	}
	public void setCenterPoi(MapPoint centerPoi) {
		this.centerPoi = centerPoi;
	}
	public boolean isUsrPoint() {
		return isUsrPoint;
	}
	public void setUsrPoint(boolean isUsrPoint) {
		this.isUsrPoint = isUsrPoint;
	}
	public LinkedList<POIObject> getPoiResult() {
		return poiResult;
	}
	public void setPoiResult(LinkedList<POIObject> poiResult) {
		this.poiResult = poiResult;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getRawStr() {
		return rawStr;
	}
	public void setRawStr(String rawStr) {
		this.rawStr = rawStr;
	}
	public String getInputnocity() {
		return inputnocity;
	}
	public void setInputnocity(String inputnocity) {
		this.inputnocity = inputnocity;
	}
	public String getSegStr() {
		return segStr;
	}
	public void setSegStr(String segStr) {
		this.segStr = segStr;
		this.segs = segStr.split(" ");
		this.tokenEntities = new Entity[this.segs.length];
	}
	public String[] getSegs() {
		return segs;
	}
	public void setSegs(String[] segs) {
		this.segs = segs;
	}
	public BrandWord getBrandword() {
		return brandword;
	}
	public void setBrandword(BrandWord brandword) {
		this.brandword = brandword;
	}
	public POIObject getOnlyPoint() {
		return onlyPoint;
	}
	public void setOnlyPoint(POIObject onlyPoint) {
		this.onlyPoint = onlyPoint;
	}
	public boolean isCityCenterPoi() {
		return isCityCenterPoi;
	}
	public void setCityCenterPoi(boolean isCityCenterPoi) {
		this.isCityCenterPoi = isCityCenterPoi;
	}
	public boolean isHasBusStationType() {
		return hasBusStationType;
	}
	public void setHasBusStationType(boolean hasBusStationType) {
		this.hasBusStationType = hasBusStationType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Entity[] getTokenEntities() {
		return tokenEntities;
	}
	public void setTokenEntities(Entity[] tokenEntities) {
		this.tokenEntities = tokenEntities;
	}
	public Query getUserQuery() {
		return userQuery;
	}
	public void setUserQuery(Query userQuery) {
		this.userQuery = userQuery;
	}

	
	public Query getNearbyquery() {
		return nearbyquery;
	}
	public void setNearbyquery(Query nearbyquery) {
		this.nearbyquery = nearbyquery;
	}
	public List<Subject> getType_list() {
		return type_list;
	}
	public void setType_list(List<Subject> type_list) {
		this.type_list = type_list;
	}
	
	
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	/**
	 * 统计各个成分的数目
	 */
	public void statKeywords()
	{
//		city_no = this.city ==null ? 0:1;//this.EntityNumber(City.class);
//		brand_no = this.brandword == null ? 0:1;//this.EntityNumber(BrandWord.class);
//		xkey_no = this.EntityNumber(XKeyword.class);
//		same_no = this.EntityNumber(SameKeyword.class);
//		xtype_no = this.EntityNumber(XtypeKeyword.class);
//		tag_no = this.EntityNumber(TagWord.class);
//		subject_no = this.EntityNumber(Subject.class);
//		loc_no = this.EntityNumber(Location.class);
//		num_no = this.EntityNumber(NumKeyword.class);
//		eng_no = this.EntityNumber(EngWord.class);
//		distric_no = this.EntityNumber(District.class);
//		other_no = this.EntityNumber(OtherKeyword.class);
//		
//		this.total_no = this.tokenEntities !=null? this.tokenEntities.length : 0;
//		this.ex_total_no = this.total_no + this.tag_list.size()+ this.type_list.size();
//		if(this.brandword!= null) this.ex_total_no += 1;
		this.isStatic = true;
	}
	
	
	
}
