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
	 * ���ܱ������Ľ��������������ء���POI��ͬ����������̿��ܻ�ɾ����<br>
	 * ������ݰ��վ�������
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
		U.log.info("����["+ (count)+"]���޳�");
//		this.lq.setPoiNum(this.lq.getPoiNum()-count);
		//�������ʵ������������򷽷���

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
	 * ���ܱ������Ľ��������������ء���POI��ͬ����������̿��ܻ�ɾ����<br>
	 * ����Ҳ�ǰ��վ��룬ʱ�䣬��ע�ȣ�ƥ��� ���������ۺ����֡�
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
		U.log.debug("�ܱ߲�ѯ��range��"+ range);
		int count = source.size() - re.size();

		U.log.info("����["+ (count)+"]���޳�");
//		this.lq.setPoiNum(this.lq.getPoiNum()-count);

		//�������ʵ������������򷽷���

		this.lq.setPoiResult(re);
		return 0;
	}
	
	
	


}
