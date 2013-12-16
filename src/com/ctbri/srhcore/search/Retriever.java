package com.ctbri.srhcore.search;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.spatial.SpatialStrategy;
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree;
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree;
import org.apache.lucene.spatial.query.SpatialArgs;
import org.apache.lucene.spatial.query.SpatialOperation;
import org.apache.lucene.util.Version;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer;
import com.ctbri.srhcore.loadindex.IndexTimer;
import com.ctbri.srhcore.pojo.MapPoint;
import com.ctbri.srhcore.pojo.POIObject;
import com.ctbri.srhcore.search.util.UIndex;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;




public class Retriever {
	
	static
	{
		U.log.info("启动定时任务");
		IndexTimer.checkCinemaIndex(1000);
	}
	
	private String citycode;
	private LocalQuery lq ;
	
	public Retriever(){}
	public Retriever(LocalQuery lq)
	{
		this.lq = lq;
		this.citycode = lq.getCity().getCitycode();
	}
	
	public static SpatialContext ctx=SpatialContext.GEO;
	public static SpatialPrefixTree grid = new GeohashPrefixTree(ctx, 11);
	public static SpatialStrategy strategy = new RecursivePrefixTreeStrategy(grid, "geoField");

	
	public void POISearch(int start,int end, int total,boolean vague,String type,String usrQuery,boolean userbuy)
	{
		this.poiPointSearch(start, end, total, vague,type, usrQuery,userbuy);
	}
	
	public void NearbySearch(MapPoint point,int range,int start, int end,int total,String centerPoi,String type,String usrQuery,boolean custom)
	{
		if((point == null || !point.isValid()) && (usrQuery== null || usrQuery.length()==0 )) return ;
		if(point != null && point.isValid())
		{
			U.log.debug("周边搜索中心点：" + point.toQueryString());
			this.lq.setCenterPoi(point);
			this.lq.setUsrPoint(true);
		}
		U.log.info("C.extend_nearby "+ C.extend_nearby);
		
		if(range <= 0)
		{
			range = C.nearby_range;
			
		}		
		this.lq.setRange(range);
		
		
		
		
		double dis = DistanceUtils.dist2Degrees(range/1000.0D, DistanceUtils.EARTH_MEAN_RADIUS_KM);
		SpatialArgs args = new SpatialArgs(SpatialOperation.Intersects, ctx.makeCircle(point.getdLon(), point.getdLat(), dis));
		Query nearbyQuery = strategy.makeQuery(args);		
		this.lq.setNearbyquery(nearbyQuery);
		int temptotal = this.nearbyPointSearchOnlyReturnTotalNum(start, end, total, false, type, usrQuery, custom);
		int shrinkRange = range;
		while(temptotal>  C.Search_total_Nearby* 1.5)
		{
			shrinkRange /= 2;
			dis = DistanceUtils.dist2Degrees(shrinkRange/1000.0D, DistanceUtils.EARTH_MEAN_RADIUS_KM);
			args = new SpatialArgs(SpatialOperation.Intersects, ctx.makeCircle(point.getdLon(), point.getdLat(), dis));
			nearbyQuery = strategy.makeQuery(args);		
			this.lq.setNearbyquery(nearbyQuery);
			temptotal = this.nearbyPointSearchOnlyReturnTotalNum(start, end, total, false, type, usrQuery, custom);
			U.log.info("nearbyPointSearchOnlyReturnTotalNum "+ temptotal +"  range: "+ shrinkRange);
		}
		
		this.poiPointSearch(start, end, total, false, type, usrQuery, custom);
		if(shrinkRange != range)
		{
			int num = this.lq.getTotalResult();
			num = num * (range/shrinkRange)* (range/shrinkRange);
			this.lq.setTotalResult(num);
		}
	
		
	}
	
	/**
	 * 
	 * @param start 开始
	 * @param end 结束
	 * @param total 总数
	 * @param vague 是否模糊
	 * @param type 类型编码(频道定制) 可以传递多个编码
	 * @param usrQuery 自定义 也是为了频道留的接口
	 * @param custom 用户定义，是否排序作弊
	 */
	public void poiPointSearch(int start, int end ,int total, boolean vague,String type,String usrQuery,boolean custom)
	{
		if(end < start)
		{
			U.log.error("结果的结束值 小于开始值，严重的逻辑错误");
			return ;
		}
		Query q = null;
		if(usrQuery != null && !usrQuery.equals("null") && usrQuery.length()>0)
		{
			AnalyzingQueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"brand", new CtbriAnalyzer(C.segMode));
			
			try {
				q = qp.parse(usrQuery);
				U.log.info("userQuery: ["+q.toString()+"]");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
			this.lq.setUserQuery(q);

		}
		this.lq.setType(type);

		LinkedList<POIObject> poilist = this.searchPOIIndex(this.lq,total,vague);
		
		//关闭电影的reader
		try {
			if(UIndex.cic.reader != null)
				UIndex.cic.reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.lq.setPoiResult(poilist);		
	}
	
	public int nearbyPointSearchOnlyReturnTotalNum(int start,int end,int total,boolean vague,String type,String usrQuery,boolean custom)
	{
		Query q = null;
		if(usrQuery != null && !usrQuery.equals("null") && usrQuery.length()>0)
		{
			AnalyzingQueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"brand", new CtbriAnalyzer(C.segMode));
			
			try {
				q = qp.parse(usrQuery);
				U.log.info("userQuery: ["+q.toString()+"]");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
			this.lq.setUserQuery(q);

		}
		this.lq.setType(type);
		return this.searchPOIIndexOnlyreturnTotalNumber(lq, total, vague);

	}
	
	public static void main(String[] args) {
		Retriever rt = new Retriever();
		rt.testId();
		
	}
	
	public void testId()
	{
		IndexSearcher fs = this.getPOISearcher();
		TermQuery q2 = new TermQuery(new Term(U.Field_nid, "925000086"));
//		q2.setBoost(this.tag_weight);
		try {
			TopDocs tds = fs.search(q2, 1);
			ScoreDoc[] sd=tds.scoreDocs;
			POIObject poi = new POIObject();
			for (int i = 0; i < sd.length; i++) {
				Document doc = fs.doc(sd[i].doc);
//				POIObject poi = new POIObject();
				poi.setLDoc(doc);
				break;

			}
			System.out.println("     "+ poi.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private int searchPOIIndexOnlyreturnTotalNumber(LocalQuery lq,int total,boolean isVague)
	{
		IndexSearcher finalSearcher = this.getPOISearcher();
		BooleanQuery q_re = null;
		ScoreDoc[] tempsd = {};
		TopDocs tds = new TopDocs(0,tempsd,0);
		q_re = lq.toQuery();
		
		U.log.info("query: "+ q_re);
		try {
			tds = finalSearcher.search(q_re, null, total);		
			if(tds == null) return 0;
			int totalHit = tds.totalHits;
			U.log.info("POI检索index的数量是："+ totalHit);
			return totalHit;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}	
		
	}
	
	private LinkedList<POIObject> searchPOIIndex(LocalQuery lq, int total, boolean isVague)
	{
//		usrQuery = new TermQuery(new Term(U.Field_brand, "合伙人"));
		IndexSearcher finalSearcher = this.getPOISearcher();
		BooleanQuery q_re = null;
		ScoreDoc[] tempsd = {};
		TopDocs tds = new TopDocs(0,tempsd,0);
		LinkedList<POIObject> list = new LinkedList<POIObject>();
		q_re = lq.toQuery();
		
		U.log.info("query: "+ q_re);
		try {
			tds = finalSearcher.search(q_re, null, total);		
			if(tds == null) return null;
			int totalHit = tds.totalHits;
			U.log.info("POI检索index的数量是："+ totalHit);
			this.lq.setTotalResult(totalHit);
			
			
			ScoreDoc[] sd=tds.scoreDocs;
			for(int i=0;i<sd.length;i++){
				Document doc = finalSearcher.doc(sd[i].doc);
//				U.log.info(doc.get(strategy.getFieldName()));
				String[] lonlat = doc.get(strategy.getFieldName()).split(",");
				POIObject poi = new POIObject();
				poi.setLDoc(doc);
				poi.setScore(sd[i].score);
	//			if(poi.getFieldValue("invol") == null || poi.getFieldValue("invol").length()==0)
	//				poi.setFieldValue("invol", 1+"");
				if(lonlat == null || lonlat.length!=2)
				{
					U.log.error("数据经纬度错误 "+ poi.getId());
				}
				poi.setLon(Double.parseDouble(lonlat[0]));
				poi.setLat(Double.parseDouble(lonlat[1]));
				if(poi.getRank() == 0)
					poi.setRank(1);
				list.add(poi);			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private IndexSearcher getPOISearcher()
	{
		int totalReader = 0;
		IndexReader tuangouReader = null;
		tuangouReader = UIndex.readTuangouIndex();
		if(tuangouReader != null) totalReader++;
		
		IndexReader cinemaReader = null;
		U.log.debug("load cinema index begin ==================================");
		cinemaReader = UIndex.readCinemaIndex();
		U.log.debug("load cinema index over ==================================");
		if(cinemaReader != null) totalReader++;

		IndexReader foodReader = null;
		foodReader = UIndex.readFoodIndex();
		if(foodReader != null) totalReader++;
		
		
//		IndexReader poiReader = null;
//		poiReader = US.readPOIIndex();
//		if(poiReader != null) totalReaer++;
		
		if(totalReader ==0)
		{
			U.log.error("没有索引被读取");
		}
		IndexReader[] ss = new IndexReader[totalReader];
		totalReader = 0;
		if(cinemaReader != null)
			ss[totalReader++] = cinemaReader;
		if(foodReader != null)
			ss[totalReader++] = foodReader;
		if(tuangouReader != null)
			ss[totalReader++] = tuangouReader;
		
		IndexSearcher search = new IndexSearcher(new MultiReader(ss));
		
		return search;
		
	}
	
	
	
	public LocalQuery getLq() {
		return lq;
	}

	public void setLq(LocalQuery lq) {
		this.lq = lq;
	}
}
