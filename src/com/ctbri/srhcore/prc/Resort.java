package com.ctbri.srhcore.prc;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.pojo.MapPoint;
import com.ctbri.srhcore.pojo.POIObject;
import com.ctbri.srhcore.prc.util.USort;




public class Resort {
	
private LocalQuery lq;
	
	
	public Resort(LocalQuery lq)
	{
		this.lq = lq;
	}
	

	
	public LocalQuery getLq() {
		return lq;
	}
	
	public void setLq(LocalQuery lq) {
		this.lq = lq;
	}
	
	
	/**
	 * 对周边搜索的结果进行排序和判重。和POI不同的是这个过程可能会删除点<br>
	 * 结果数据按照距离排序
	 * 2011-7-5
	 * @return
	 */
	public int sortNearbyDistance()
	{
		if(lq == null || lq.getPoiResult() == null || lq.getPoiResult().size() == 0  )
			return 0;
		LinkedList<POIObject> source = lq.getPoiResult();		
		
		MapPoint cen = this.lq.getCenterPoi();
		int range = this.lq.getRange();

		LinkedList<POIObject> re = USort.setPOIDistanceandDirect(source, range, cen);
		int count = source.size() - re.size();
		this.lq.setTotalResult(this.lq.getTotalResult()- count);
		U.log.info("共有["+ (count)+"]被剔除");
//		this.lq.setPoiNum(this.lq.getPoiNum()-count);
		//这里可以实现其他别的排序方法。

		Collections.sort(re, new Comparator<POIObject> ()
		{
			public int compare(POIObject p1, POIObject p2) {
				// TODO Auto-generated method stub
				double dis1 = p1.getDistance();
				double dis2 = p2.getDistance();
				if(dis1-dis2>0)
					return 1;
				else 
					return -1;					
			}					
		}
		);
		
		this.lq.setPoiResult(re);

		return 0;
	}
	/**
	 * 对周边搜索的结果进行排序和判重。和POI不同的是这个过程可能会删除点<br>
	 * 排序也是按照距离，时间，关注度，匹配度 来最后进行综合评分。
	 * @return
	 */
	public int sortNearbyDefault()
	{
		
		if(lq == null || lq.getPoiResult() == null || lq.getPoiResult().size() == 0  )
			return 0;
		LinkedList<POIObject> source = lq.getPoiResult();	
		U.log.debug(source.size());
		MapPoint cen = this.lq.getCenterPoi();
		int range = this.lq.getRange();
		LinkedList<POIObject> re = USort.setPOIDistanceandDirect(source, range, cen);
		U.log.debug(re.size());
		U.log.debug("周边查询的range："+ range);
		int count = source.size() - re.size();

		U.log.info("共有["+ (count)+"]被剔除");
//		this.lq.setPoiNum(this.lq.getPoiNum()-count);

		//这里可以实现其他别的排序方法。

		this.lq.setPoiResult(re);
		return 0;
	}
	
	
	


}
