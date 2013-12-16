package com.ctbri.srhcore.index.maker;

import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;

public class POIFields {

	public Field nidField=new StringField("nid", "", Store.YES);
//	public Field cidField=new StringField("cid","",Store.YES);
	public Field cidField=new StoredField("cid","");
	public Field nameField=new TextField("name","",Store.YES);
	public Field addressField=new TextField("address","",Store.YES);
	public Field phoneField=new StringField("phone","",Store.YES);
	public Field longitudeField=new DoubleField("longitude", 0.0, Store.YES);
	public Field latitudeField=new DoubleField("latitude",0,Store.YES);
	public Field typeCodeField=new TextField("typeCode","",Store.YES);
	public Field typeNameField=new StoredField("typeName", "");
	public Field cityCodeField=new StringField("cityCode","",Store.YES);
	public Field cityNameField=new StringField("cityName","",Store.YES);
	public Field districtField=new StringField("district","",Store.YES);
	public Field tagField=new TextField("tag","",Store.YES);
	public Field rankField=new StringField("rank","",Store.YES);
	public Field brandField=new TextField("brand","",Store.YES);
	public Field trafficField = new StoredField("traffic",""); //16
	public Field cinemaDesField = new StoredField("des",""); //17
	public Field specialField = new StoredField("spicalDes","");//18
	public Field confidencelevelField=new StringField("confidenceLevel","",Store.YES);
	public Field lastUpdateTimeField=new StringField("lastUpdateTime","",Store.YES);
	public Field descriptionField=new TextField("description","",Store.YES);
	public Field infoField = new TextField("info","",Store.YES);
//	public Field provinceField=new StringField("province","",Store.YES);
//	public Field hitField=new IntField("hit",0,Store.YES);
//	public Field pointWeightField=new IntField("pointWeight",0,Store.YES);
//	public Field pointWeightOfCityField=new IntField("pointWeightOfCity",0,Store.YES);
	public Field webUrlField=new StoredField("webUrl", ""); //20
	public Field photoField=new StoredField("photo", ""); //19
}
