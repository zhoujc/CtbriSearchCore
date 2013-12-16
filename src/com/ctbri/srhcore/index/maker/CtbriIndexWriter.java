package com.ctbri.srhcore.index.maker;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.spatial.SpatialStrategy;
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree;
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.shape.Shape;

public class CtbriIndexWriter {

	public static int MAX_LEVEL = 11;
	private String indexPath;
	private IndexWriter writer;
	private POIFields fields;
	private SpatialContext ctx;
	private SpatialStrategy strategy;
	private TokenizerModel segModel;
	
	
	public CtbriIndexWriter(String indexPath,TokenizerModel segModel)
	{
		this.indexPath = indexPath;
		this.segModel=segModel;
		this.fields = new POIFields();
		this.ctx = SpatialContext.GEO;
		SpatialPrefixTree grid = new GeohashPrefixTree(ctx,MAX_LEVEL);
		this.strategy = new RecursivePrefixTreeStrategy(grid, "geoField");
		this.writer = initIndexWriter();
	}
	
	private IndexWriter initIndexWriter() {
		Directory dir=null;
		try {
			dir = FSDirectory.open(new File(this.indexPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Analyzer analyzer=new CtbriAnalyzer(this.segModel);
		IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_40, analyzer);
		config.setOpenMode(OpenMode.CREATE);
		config.setMaxBufferedDocs(10000);
		LogByteSizeMergePolicy policy=new LogByteSizeMergePolicy();
		policy.setMaxMergeMB(1024);
		policy.setMergeFactor(5);
		policy.setNoCFSRatio(1.0);
		policy.setUseCompoundFile(true);
		config.setMergePolicy(policy);
		IndexWriter iw=null;
		try {
			iw = new IndexWriter(dir, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return iw;
	}
	
	
	public void addPOIStore(POIInfo poi)
	{
		StringBuffer lable=new StringBuffer();
		Document doc=new Document();
		fields.nidField.setStringValue(poi.getNid()); //0
		doc.add(fields.nidField);
		
		Field cidStoreField = new StoredField("cid",""); 
		cidStoreField.setStringValue(poi.getCid()); //1
		doc.add(cidStoreField);
		
		if(poi.getCityName()!=null){
			Field cityNameStoreField = new StoredField("cityName",""); 
			cityNameStoreField.setStringValue(poi.getCityName());
			doc.add(cityNameStoreField);
		}
		
		if(poi.getCityCode()!=null){
			Field cityCodeStoreField = new StoredField("cityCode",""); 
			cityCodeStoreField.setStringValue(poi.getCityCode());
			doc.add(cityCodeStoreField);
		}
		
		Field nameStoreField = new StoredField("name",""); 
		nameStoreField.setStringValue(poi.getTitle());
		doc.add(nameStoreField);
		
		if(poi.getAddress()!=null){
			Field addressStoreField = new StoredField("address",""); 
			addressStoreField.setStringValue(poi.getAddress());
			doc.add(addressStoreField);
		}
		
		if(poi.getPhone()!=null){
			Field phoneStoreField = new StoredField("phone",""); 
			phoneStoreField.setStringValue(poi.getPhone());
			doc.add(phoneStoreField);
		}
		
		if(poi.getTypeName()!=null){
			Field typenameStoreField = new StoredField("typeName",""); 
			typenameStoreField.setStringValue(poi.getTypeName());
			doc.add(typenameStoreField);
		}

		doc.add(new StoredField("latitude",poi.getLatitude()+""));
		doc.add(new StoredField("longitude",poi.getLongitude()+""));
		
		
		fields.latitudeField.setDoubleValue(poi.getLatitude());
		fields.longitudeField.setDoubleValue(poi.getLongitude());
		String typeCode=poi.getTypeCode();
		if(typeCode!=null && !typeCode.endsWith("00")){
			String topTypeCode=typeCode.substring(0, 1)+"00";
			doc.add(new TextField("typeCode",topTypeCode,Store.YES));
		}
		if(typeCode!=null)
		{
			fields.typeCodeField.setStringValue(typeCode);
			doc.add(fields.typeCodeField);
		}		
		
		if(poi.getDistrict()!=null){
			fields.districtField.setStringValue(poi.getDistrict());
			doc.add(new StoredField("district",poi.getDistrict()));
		}
		
		if(poi.getTag()!=null){
			doc.add(new StoredField("tag",poi.getTag()));

		}
		if(poi.getInvol()!=null){
			doc.add(new StoredField("rank",poi.getInvol()));
		}
		if(poi.getBrand()!=null){
			doc.add(new StoredField("brand",poi.getBrand()));
		}
		if(poi.getConfidenceLevel()!=null){
			doc.add(new StoredField("confidenceLevel",poi.getConfidenceLevel()));
		}
		
		if(poi.getCtraffic() != null &&poi.getCtraffic().length()>0)
		{
			doc.add(new StoredField("traffic",poi.getCtraffic()));
		}			
		
		if(poi.getCdes() != null)
		{
			doc.add(new StoredField("des",poi.getCdes()));
		}
			
		if(poi.getCspeicalDes()!= null)
		{
			doc.add(new StoredField("spicalDes",poi.getCspeicalDes()));
		}
		if(poi.getSelf_info()!= null)
		{
			doc.add(new StoredField("info",poi.getSelf_info()));
		}
		if(poi.getTbegin()!= null)
		{
			doc.add(new StoredField("tbegin",poi.getTbegin()));
		}
		if(poi.getTend()!= null)
		{
			doc.add(new StoredField("tend",poi.getTend()));
		}
			
		
		if(poi.getWebUrl()!=null){
			doc.add(new StoredField("webUrl",poi.getWebUrl()));
		}
		if(poi.getPhoto()!=null){
			doc.add(new StoredField("photo",poi.getPhoto()));
		}
		
		if(poi.getFfavorite()!=null)
			doc.add(new StoredField("favorite",poi.getFfavorite()));
		
		if(poi.getFreviews() !=null)
			doc.add(new StoredField("reviews",poi.getFreviews()));
		
		if(poi.getFpark() !=null)
			doc.add(new StoredField("is_park_vailable",poi.getFpark()));
		
		if(poi.getFroom() !=null)
			doc.add(new StoredField("is_room_available",poi.getFroom()));
		
		if(poi.getFrefer() !=null)
			doc.add(new StoredField("refer",poi.getFrefer()));
		
		
//		if(poi.getSelf_info()!=null){
//			fields.descriptionField.setStringValue(poi.getSelf_info());
//			doc.add(fields.descriptionField);
//			lable.append(poi.getSelf_info());
//		}
		
		if(poi instanceof POIFood)
		{
			POIFood food = (POIFood)poi;
			if(food.getAvgPerCost()!= null)
				doc.add(new StoredField("avePerCost",food.getAvgPerCost()));
			if(food.getTrafficRoute() != null)
				doc.add(new StoredField("trafficRoute",food.getTrafficRoute()));
			if(food.getIntro() != null)
				doc.add(new StoredField("intro",food.getIntro()));
			if(food.getHeadPic() != null)
				doc.add(new StoredField("headPic",food.getHeadPic()));
			
			if(food.getSpecialty() != null)
				doc.add(new StoredField("specialty",food.getSpecialty()));
			
			if(food.getComment() != null)
				doc.add(new StoredField("comment",food.getComment()));
			if(food.getIsPark() != null)
				doc.add(new StoredField("isPark",food.getIsPark()));
			if(food.getIspack() != null)
				doc.add(new StoredField("ispack",food.getIspack()));
			if(food.getSource() != null)
				doc.add(new StoredField("source",food.getSource()));
			if(food.getIsBookingStatus() != null)
				doc.add(new StoredField("isBookingStatus",food.getIsBookingStatus()));
			
			if(food.getRecommendValue() != null)
				doc.add(new StoredField("recommendValue",food.getRecommendValue()));
			if(food.getZcDisount() != null)
				doc.add(new StoredField("zcDiscount",food.getZcDisount()));
			if(food.getFtDiscount() != null)
				doc.add(new StoredField("ftDiscount",food.getFtDiscount()));
			if(food.getAres() != null)
				doc.add(new StoredField("area",food.getAres()));
			if(food.getMsId() != null)
				doc.add(new StoredField("msId",food.getMsId()));
			
			if(food.getMsDiscount() != null)
				doc.add(new StoredField("msDiscount",food.getMsDiscount()));
			if(food.getMsIsFree() != null)
				doc.add(new StoredField("msIsfree",food.getMsIsFree()));
			
			if(food.getIsSupportCard() != null)
				doc.add(new StoredField("isSupportCard",food.getIsSupportCard()));
			
		}
		

//		fields.hitField.setIntValue(poi.getHit());
//		doc.add(fields.hitField);
//		fields.pointWeightField.setIntValue(poi.getPointWeight());
//		doc.add(fields.pointWeightField);
//		fields.pointWeightOfCityField.setIntValue(poi.getPointWeightOfCity());
//		doc.add(fields.pointWeightOfCityField);
		
		doc.add(new TextField("lable", lable.toString(), Store.NO));
		try {
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void addPOI(POIInfo poi)
	{

		StringBuffer lable=new StringBuffer();
		Document doc=new Document();
		fields.nidField.setStringValue(poi.getNid()); //0
		fields.cidField.setStringValue(poi.getCid()); //1
		fields.nameField.setStringValue(poi.getTitle());
		lable.append(poi.getTitle());
		fields.latitudeField.setDoubleValue(poi.getLatitude());
		fields.longitudeField.setDoubleValue(poi.getLongitude());
		String typeCode=poi.getTypeCode();
		if(typeCode!= null && !typeCode.endsWith("00")){
			String topTypeCode=typeCode.substring(0, 1)+"00";
			doc.add(new TextField("typeCode",topTypeCode,Store.YES));
		}
		if(typeCode!= null)
		{
			fields.typeCodeField.setStringValue(typeCode);
			doc.add(fields.typeCodeField);
		}
		
		doc.add(fields.nidField);
		doc.add(fields.cidField);
		if(poi.getAddress()!=null){
			fields.addressField.setStringValue(poi.getAddress());
			doc.add(fields.addressField);
			lable.append(poi.getAddress());
		}
		doc.add(fields.nameField);
		
		
		//add spatial field...
//		System.out.println("lon: "+poi.getLongitude()+" lat: "+poi.getLatitude());
		if(poi.getLongitude()>180 || poi.getLongitude()< -180|| poi.getLatitude()>90 || poi.getLatitude()<-90)
		{
			U.log.debug("lon or lat is wrong "+ poi.getNid());
			return;
		}
		Shape shape=this.ctx.makePoint(poi.getLongitude(), poi.getLatitude());
		Field[] shapeFields=this.strategy.createIndexableFields(shape);
		for(IndexableField f:shapeFields){
			doc.add(f);
		}
		doc.add(new StoredField(this.strategy.getFieldName(), poi.getLongitude()+","+poi.getLatitude()));
		if(poi.getCityCode()!=null){
			String citycode = poi.getCityCode();
			if(citycode.startsWith("11") && citycode.length() ==6) citycode = "110000";
			else if(citycode.startsWith("31") && citycode.length() ==6 ) citycode = "310000";
			else if(citycode.startsWith("12") && citycode.length() ==6 ) citycode = "120000";
			else if(citycode.startsWith("50") && citycode.length() ==6 ) citycode = "500000";
			fields.cityCodeField.setStringValue(citycode);
			doc.add(fields.cityCodeField);
		}
		
		
		
		if(poi.getCityName()!=null){
			fields.cityNameField.setStringValue(poi.getCityName());
			doc.add(fields.cityNameField);
			lable.append(poi.getCityName());
		}
		if(poi.getPhone()!=null){
			fields.phoneField.setStringValue(poi.getPhone());
			doc.add(fields.phoneField);
		}
		if(poi.getTypeName()!=null){
			fields.typeNameField.setStringValue(poi.getTypeName());
			doc.add(fields.typeNameField);
			lable.append(poi.getTypeName());
		}
		if(poi.getDistrict()!=null){
			fields.districtField.setStringValue(poi.getDistrict());
			doc.add(fields.districtField);
			lable.append(poi.getDistrict());
		}
		if(poi.getTag()!=null){
			fields.tagField.setStringValue(poi.getTag()+" 10");
			doc.add(fields.tagField);
			lable.append(poi.getTag());
		}
		if(poi.getInvol()!=null){
			fields.rankField.setStringValue(poi.getInvol());
			doc.add(fields.rankField);
		}
		if(poi.getBrand()!=null){
			fields.brandField.setStringValue(poi.getBrand());
			doc.add(fields.brandField);
			lable.append(poi.getBrand());
		}
		if(poi.getConfidenceLevel()!=null){
			fields.confidencelevelField.setStringValue(poi.getConfidenceLevel());
			doc.add(fields.confidencelevelField);
		}
		if(poi.getStrtimeoflastupdate()!=null){
			fields.lastUpdateTimeField.setStringValue(poi.getStrtimeoflastupdate());
			doc.add(fields.lastUpdateTimeField);
		}
		if(poi.getSelf_info()!=null){
			fields.infoField.setStringValue(poi.getSelf_info());
			doc.add(fields.infoField);
			lable.append(poi.getSelf_info());
		}
		if(poi.getDescription()!=null){
			fields.descriptionField.setStringValue(poi.getDescription());
			doc.add(fields.descriptionField);
		}

//		
//		fields.hitField.setIntValue(poi.getHit());
//		doc.add(fields.hitField);
//		fields.pointWeightField.setIntValue(poi.getPointWeight());
//		doc.add(fields.pointWeightField);
//		fields.pointWeightOfCityField.setIntValue(poi.getPointWeightOfCity());
//		doc.add(fields.pointWeightOfCityField);
		
	/**
		if(poi.getCtraffic() != null)
			fields.trafficField.setStringValue(poi.getCtraffic());
		if(poi.getCdes() != null)
			fields.cinemaDesField.setStringValue(poi.getCdes());
		if(poi.getCspeicalDes()!= null)
			fields.specialField.setStringValue(poi.getCspeicalDes());
		
	**/
		if(poi.getWebUrl()!=null){
			fields.webUrlField.setStringValue(poi.getWebUrl());
			doc.add(fields.webUrlField);
		}
		if(poi.getPhoto()!=null){
			fields.photoField.setStringValue(poi.getPhoto());
			doc.add(fields.photoField);
		}
		doc.add(new TextField("lable", lable.toString(), Store.NO));
		
		if(poi instanceof POIFood)
		{
			POIFood food = (POIFood)poi;
			if(food.getAvgPerCost()!= null)
				doc.add(new StoredField("avePerCost",food.getAvgPerCost()));
			if(food.getTrafficRoute() != null)
				doc.add(new StoredField("trafficRoute",food.getTrafficRoute()));
			if(food.getIntro() != null)
				doc.add(new StoredField("intro",food.getIntro()));
			if(food.getHeadPic() != null)
				doc.add(new StoredField("headPic",food.getHeadPic()));
			
			if(food.getSpecialty() != null)
				doc.add(new StoredField("specialty",food.getSpecialty()));
			
			if(food.getComment() != null)
				doc.add(new StoredField("comment",food.getComment()));
			if(food.getIsPark() != null)
				doc.add(new StoredField("isPark",food.getIsPark()));
			if(food.getIspack() != null)
				doc.add(new StoredField("ispack",food.getIspack()));
			if(food.getSource() != null)
				doc.add(new StoredField("source",food.getSource()));
			if(food.getIsBookingStatus() != null)
				doc.add(new StoredField("isBookingStatus",food.getIsBookingStatus()));
			
			if(food.getRecommendValue() != null)
				doc.add(new StoredField("recommendValue",food.getRecommendValue()));
			if(food.getZcDisount() != null)
				doc.add(new StoredField("zcDiscount",food.getZcDisount()));
			if(food.getFtDiscount() != null)
				doc.add(new StoredField("ftDiscount",food.getFtDiscount()));
			if(food.getAres() != null)
				doc.add(new StoredField("area",food.getAres()));
			if(food.getMsId() != null)
				doc.add(new StoredField("msId",food.getMsId()));
			
			if(food.getMsDiscount() != null)
				doc.add(new StoredField("msDiscount",food.getMsDiscount()));
			if(food.getMsIsFree() != null)
				doc.add(new StoredField("msIsfree",food.getMsIsFree()));
			if(food.getIsSupportCard() != null)
				doc.add(new StoredField("isSupportCard", food.getIsSupportCard()));
		}
		try {
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	
	}
	
	public void finish(){
		try {
			this.writer.forceMerge(1);
			this.writer.commit();
			this.writer.close();
			
			U.log.info(this.indexPath+" allready indexed..");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
