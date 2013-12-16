package com.ctbri.srhcore.pojo;

public class MapPoint {
	
	public static final int TYPE_PRECISE = 0;
	public static final int TYPE_DISTRICT = 1;
	public static final int TYPE_VILLAGE = 2;
	public static final int TYPE_CAMPUS = 3;
	public static final int TYPE_BRIDGE = 4;
	public static final int TYPE_CLUSTER = 5;
	public static final int TYPE_OTHERS = 6;
	public static final int TYPE_ROAD = 7;
	
	// dLatitude and dLongitude in geographic degrees
	private double	dLat;
	private double	dLon;
	
	public MapPoint(){}
	
	public MapPoint(double lon,double lat)
	{
		this.dLon = lon;
		this.dLat = lat;
	}
	
	public double getdLat() {
		return dLat;
	}
	public void setdLat(double dLat) {
		this.dLat = dLat;
	}
	public double getdLon() {
		return dLon;
	}
	public void setdLon(double dLon) {
		this.dLon = dLon;
	}
	
	public boolean isValid()
	{
		return (this.dLat != 0 || this.dLon != 0);
	}

	public String toQueryString()
	{
		return "%LL= " + this.dLon + "," + this.dLat ;
	}
	
}
