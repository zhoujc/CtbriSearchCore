package com.ctbri.srhcore.index.maker;

public class POIInfo {

	private String	nid;
	private String	cid ="000";
	private String	cityName;
	private String	cityCode;
	private String	title;
	private String	address;
	private String	phone;
	private String	typeName;
	private String	typeCode;
	private double	longitude;
	private double	latitude;
	private String	district;
	private String	tag;
	private String	invol;
	private String	brand;
	private String	confidenceLevel;
	private String	strtimeoflastupdate;
	private String	self_info;
	private String	province;
	private String	nation;
	private int hit;
	private int pointWeight;
	private int pointWeightOfCity;
	//以下几个为快速上线独有字段
	private String description;
	private String webUrl;
	private String photo;
	
	
	//电影院
	private String ctraffic;
	private String cdes;
	private String cspeicalDes;
	
	//餐饮
	private String ffavorite;//招牌菜
	private String freviews;
	private String fpark;
	private String froom;
	private String frefer;
	
	
	private String tbegin;
	private String tend;
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("\"").append(nid)
		.append("\",\"").append(cid)
		.append("\",\"").append(cityName)
		.append("\",\"").append(cityCode)
		.append("\",\"").append(title)
		.append("\",\"").append(address)
		.append("\",\"").append(phone)
		.append("\",\"").append(typeName)
		.append("\",\"").append(typeCode)
		.append("\",\"").append(longitude)
		.append("\",\"").append(latitude)
		.append("\",\"").append(district)
		.append("\",\"").append(tag)
		.append("\",\"").append(invol)
		.append("\",\"").append(brand)
		.append("\",\"").append(confidenceLevel)
		.append("\",\"").append(strtimeoflastupdate)
		.append("\",\"").append(self_info)
		.append("\",\"").append(province)
		.append("\",\"").append(nation)
		.append("\",\"").append(hit)
		.append("\",\"").append(pointWeight)
		.append("\",\"").append(pointWeightOfCity)
		//以下几个为快速上线独有字段
		.append("\",\"").append(description)
		.append("\",\"").append(webUrl)
		.append("\",\"").append(photo).append("\"\r\n");
				
		return sb.toString();
	}
	public int getPointWeightOfCity() {
		return pointWeightOfCity;
	}
	public void setPointWeightOfCity(int pointWeightOfCity) {
		this.pointWeightOfCity = pointWeightOfCity;
	}
	public int getPointWeight() {
		return pointWeight;
	}
	public void setPointWeight(int pointWeight) {
		this.pointWeight = pointWeight;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getInvol() {
		return invol;
	}
	public void setInvol(String invol) {
		this.invol = invol;
	}
	public String getConfidenceLevel() {
		return confidenceLevel;
	}
	public void setConfidenceLevel(String confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
	public String getStrtimeoflastupdate() {
		return strtimeoflastupdate;
	}
	public void setStrtimeoflastupdate(String strtimeoflastupdate) {
		this.strtimeoflastupdate = strtimeoflastupdate;
	}
	public String getSelf_info() {
		return self_info;
	}
	public void setSelf_info(String self_info) {
		this.self_info = self_info;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getCtraffic() {
		return ctraffic;
	}
	public void setCtraffic(String ctraffic) {
		this.ctraffic = ctraffic;
	}
	public String getCdes() {
		return cdes;
	}
	public void setCdes(String cdes) {
		this.cdes = cdes;
	}
	public String getCspeicalDes() {
		return cspeicalDes;
	}
	public void setCspeicalDes(String cspeicalDes) {
		this.cspeicalDes = cspeicalDes;
	}
	public String getFfavorite() {
		return ffavorite;
	}
	public void setFfavorite(String ffavorite) {
		this.ffavorite = ffavorite;
	}
	public String getFreviews() {
		return freviews;
	}
	public void setFreviews(String freviews) {
		this.freviews = freviews;
	}
	public String getFpark() {
		return fpark;
	}
	public void setFpark(String fpark) {
		this.fpark = fpark;
	}
	public String getFroom() {
		return froom;
	}
	public void setFroom(String froom) {
		this.froom = froom;
	}
	public String getFrefer() {
		return frefer;
	}
	public void setFrefer(String frefer) {
		this.frefer = frefer;
	}
	public String getTbegin() {
		return tbegin;
	}
	public void setTbegin(String tbegin) {
		this.tbegin = tbegin;
	}
	public String getTend() {
		return tend;
	}
	public void setTend(String tend) {
		this.tend = tend;
	}
	
	

	
	
	
	
	
}
