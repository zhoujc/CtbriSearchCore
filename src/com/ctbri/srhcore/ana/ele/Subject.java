package com.ctbri.srhcore.ana.ele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.SubjectAnalyzer;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer;

/**
 * 分类成分，对应着数据库中的表<br>
 * Subject的query语句转化，原始版本里德变化比较多，这个部分需要重新整理。<br>
 *
 */
public class Subject extends Entity {

	private String type;
	private String titleSign;
	private String contentSign;
	private String strTitleSubject;
	private String strContentSubject;
	
	
	
	public Subject()
	{
		this.order = 70;
		this.title_weight = 1;//7;
		this.address_weight = 1;//2;
		this.content_weight = 0.1f;
		this.type_weight = 7;
		
	}

//	public Query toQuery() {
//		// TODO Auto-generated method stub
//
//		TermQuery type = new TermQuery(new Term("type",Subject.getTypeCode(this.getType())));
//		type.setBoost(this.type_weight);
//		TermQuery tag = new TermQuery(new Term("tag",this.strTitleSubject));
//		tag.setBoost(this.tag_weight);
//		BooleanQuery bq = new BooleanQuery();
//		bq.add(type,BooleanClause.Occur.SHOULD);
//		bq.add(tag,BooleanClause.Occur.SHOULD);
//
//		return bq;
//	}

	public Query toTypeQuery()
	{		
		return toNormalQuery(false,0);
	}
    
    public Query toQuery()
	{
		return toNormalQuery(false, 5);
	}
	
    
    public Query toNQuery()
	{
		return toNormalQuery2(false, 5);
	}
	
    public Query toPureTypeQuery(){
    	return toNormalQuery(true,0);
    }
    
	
	/**当输入为 XX饭店 类似这样的结构时，这个时候不会进行subject搜索
	 * @return
	 */
	public Query toOtherkeyQuery()
	{
		BooleanQuery bq = new BooleanQuery();
		TermQuery term = new TermQuery(new Term(U.Field_title,this.key));
		term.setBoost(this.title_weight);	
		bq.add(term,BooleanClause.Occur.SHOULD);

		return bq;
	}
	
	private Query toNormalQuery(boolean typeonly, int weight_reduce)
	{
		
		QueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"name", new CtbriAnalyzer(C.segMode));
		String strQuery1 = "";
		String strQuery2 = "";
		
	//	System.out.println(this.getType());
		String noKeyword = null;
		String strType = this.getType();
		if(strType.indexOf("-") !=-1)
		{
			noKeyword = strType.split("-")[1];
			strType = strType.split("-")[0];		
		}
		if(strType.equals("宾馆饭店") && !this.getKey().equals("") && !typeonly){
			if(weight_reduce != 0)
			strQuery1 = "name:" + this.getKey() + "^" + this.title_weight/5;
			else
				strQuery1 = "name:" + this.getKey() + "^" + this.address_weight/5;
			
		}
		
		if(noKeyword != null){
			String[] items = noKeyword.split(",");
			strQuery1 = "";//"title:" + noKeyword;
			for(int i = 0; i < items.length; i++){
				strQuery1 += " title:" + items[i];
			}
		}
		Query query1 = null;
		Query query2 = null;
		Query query3 = null;
		if(!strQuery1.equals("")){
			try {
				query1 = qp.parse(strQuery1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!strQuery2.equals("")){
			try {
				query2 = qp.parse(strQuery2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		String typecode = null;
		typecode = getTypeCode(strType);
		
		if(typecode == null){
			Subject s = SubjectAnalyzer.parse(strType);
			if(s != null){
			typecode = getTypeCode(s.getType());
			if(typecode == null){
				typecode = strType;
			}
			}
			
		}
		if(typecode == null)
			typecode = strType;
		BooleanQuery bq = new BooleanQuery();
		if(typecode.indexOf(",") == -1){
			 Term aTerm = new Term("typeCode", typecode);
	         query3 = new TermQuery(aTerm);
	         query3.setBoost(this.type_weight - weight_reduce);

	         
//	         Term bTerm = new Term("title", strType);
//	         query2 = new TermQuery(bTerm);
	         if(query1 != null && noKeyword == null)
		            bq.add(query1, BooleanClause.Occur.SHOULD);
				else if(query1 != null)
					bq.add(query1, BooleanClause.Occur.MUST_NOT);
	          if(query2 != null)
	            bq.add(query2, BooleanClause.Occur.SHOULD);
	          if(query3 != null)
	            bq.add(query3, BooleanClause.Occur.SHOULD);
		}

		return bq;
	}
	
	
	private Query toNormalQuery2(boolean typeonly, int weight_reduce)
	{
		
		QueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"name", new CtbriAnalyzer(C.segMode));
		String strQuery1 = "";
		String strQuery2 = "";
		
	//	System.out.println(this.getType());
		String noKeyword = null;
		String strType = this.getType();
		if(strType.indexOf("-") !=-1)
		{
			noKeyword = strType.split("-")[1];
			strType = strType.split("-")[0];
		//	this.setType(strType);
			
			
		}
		if(!this.getKey().equals("") && !typeonly){
			strQuery1 = "title:" + this.getKey() + "^" + this.title_weight;
		//	strQuery2 = " title_addition:" + this.getKey() + "^" + LocalQuery.type_inaddress_weight;
			strQuery2 = " address:" + this.getKey() + "^" + this.address_weight/5;
		}
		
		if(noKeyword != null){
			String[] items = noKeyword.split(",");
			strQuery1 = "";//"title:" + noKeyword;
			for(int i = 0; i < items.length; i++){
				strQuery1 += " title:" + items[i];
			}
		}
		Query query1 = null;
		Query query2 = null;
		Query query3 = null;
		if(!strQuery1.equals("")){
			try {
				query1 = qp.parse(strQuery1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!strQuery2.equals("")){
			try {
				query2 = qp.parse(strQuery2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		String typecode = null;
		typecode = getTypeCode(strType);
		if(typecode == null){
			Subject s = SubjectAnalyzer.parse(strType);
			if(s != null){
			typecode = getTypeCode(s.getType());
			if(typecode == null){
				typecode = strType;
			}
			}
			
//			this.userTypecode = typecode;
			//typecode = this.getType();
		}
		if(typecode == null)
			typecode = strType;
		BooleanQuery bq = new BooleanQuery();
		if(typecode.indexOf(",") == -1){
			 Term aTerm = new Term("type", typecode);
	         query3 = new TermQuery(aTerm);
	         query3.setBoost(this.type_weight - weight_reduce + 3);
	        
	         if(query1 != null && noKeyword == null)
		            bq.add(query1, BooleanClause.Occur.SHOULD);
				else if(query1 != null)
					bq.add(query1, BooleanClause.Occur.MUST_NOT);
	          if(query2 != null)
	            bq.add(query2, BooleanClause.Occur.SHOULD);
	          if(query3 != null)
	            bq.add(query3, BooleanClause.Occur.SHOULD);
			}else{
				String[] items = typecode.split(",");
				for(int i = 0; i < items.length; i++){
					 Term aTerm = new Term("type", getTypeCode(items[i]));
			//		 System.out.println(items[i]+" "+getTypeCode(items[i]));
			         query3 = new TermQuery(aTerm);
			         query3.setBoost(this.type_weight -weight_reduce);
			         bq.add(query3, BooleanClause.Occur.SHOULD);
				}
				if(query1 != null && noKeyword == null)
		            bq.add(query1, BooleanClause.Occur.SHOULD);
				else if(query1 != null)
					bq.add(query1, BooleanClause.Occur.MUST_NOT);
		         if(query2 != null)
		            bq.add(query2, BooleanClause.Occur.SHOULD);
		}

		return bq;
	}
	
	public void setSubject(String strSubject)
	{
		if(strSubject != null)
		{
			if(strSubject.startsWith("+") || strSubject.startsWith("-"))
			{
				this.titleSign = Character.toString(strSubject.charAt(0));
				this.contentSign = Character.toString(strSubject.charAt(0));
				this.strTitleSubject = strSubject.substring(1);
				this.strContentSubject =  strSubject.substring(1);
			}
			else
			{
				this.titleSign = "";
				this.contentSign = "";
				this.strTitleSubject = strSubject;
				this.strContentSubject = strSubject;
			}
		}
	}
	public String getContentSign() {
		return contentSign;
	}
	public String getStrContentSubject() {
		return strContentSubject;
	}
	public String getStrTitleSubject() {
		return strTitleSubject;
	}
	public String getTitleSign() {
		return titleSign;
	}
	public String getType() {
		return type;
	}
	public void setContentSign(String contentSign) {
		this.contentSign = contentSign;
	}
	public void setStrContentSubject(String strContentSubject) {
		this.strContentSubject = strContentSubject;
	}
	public void setStrTitleSubject(String strTitleSubject) {
		this.strTitleSubject = strTitleSubject;
	}
	public void setTitleSign(String titleSign) {
		this.titleSign = titleSign;
	}
	public void setType(String type) {
		this.type = type;
	}

	public static String getTypeCode(String typeName){
    	
    	String code = null;
    	if(typeName != null)
    	code = ht_typecode.get(typeName);
    	return code;
    }
	
	public static String getTypeName(String typeCode)
	{
		String typename ="";
		if(typeCode != null)
			typename = re_typecode.get(typeCode);
		return typename;
	}
	
	private static String SUBJ_FILE_PATH = "type";
	private static String subjectcode_file = C.DATA_PATH + File.separator + SUBJ_FILE_PATH+ File.separator + "type_table";
	private static String inverse_subjectcode_file = C.DATA_PATH + File.separator 	+ SUBJ_FILE_PATH+ File.separator + "inverse_type";

	private static HashMap<String, String> ht_priority = null;
	private static HashMap<String, String> ht_typecode = null;
	private static HashMap<String,String> re_typecode = new HashMap<String,String>();
	private static ArrayList<String> inversetype_list = null;
	private static ArrayList<String> unuseinversetype_list = null;
	
	static{
    	//从配置文件中读取各类别的权重
    	ht_typecode = getSubjectDic(subjectcode_file);
	}
	
	private static Hashtable<String,String> getSubjectPriority(String subjectPrio_file){
    	Hashtable<String, String> ht = null;
    	BufferedReader br = null;
    	try{
    		br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(subjectPrio_file)),"GBK"));
//    	  br = new BufferedReader(new FileReader(subjectPrio_file));
    	  String line = "";
    	  while((line = br.readLine())!= null){
    		 
    		  if(ht == null) ht = new Hashtable<String, String>();
    		  if(line.trim().equals("")) continue;
    		  if(line.startsWith("#")) continue;
    		  String[] items = line.split("=");
    		  ht.put(items[0], items[1]);
    		  
    	  }
    	  br.close();
    	  U.log.debug("subject priortity文件加载成功。");
    	}catch(Exception e){
    		try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	return ht;
    }
	private static HashMap<String,String> getSubjectDic(String subject_file){
		HashMap<String, String> ht = null;
    	BufferedReader br = null;
    	try{
//    	  br = new BufferedReader(new FileReader(subject_file));
    	  br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(subject_file)),"GBK"));
    	  String line = "";
    	  while((line = br.readLine())!= null){
    		  if(line.indexOf("\"")>-1)
    		  {
    			  System.out.println(line);
    			  line = line.replaceAll("\"", "\\\\\"");
    			  System.out.println(line);
    		  }
    		  if(ht == null) ht = new HashMap<String, String>();
    		  if(line.trim().equals("")) continue;
    		  if(line.startsWith("#")) continue;
    		  String[] items = line.split("=");
    		  ht.put(items[1], items[0]); //geting the type code by type name  
    		  re_typecode.put(items[0],items[1]);
    	  }
    	  br.close();
    	  U.log.debug("SubjectDic文件加载成功 "+subject_file);
    	}catch(Exception e){
    		try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	return ht;
    }
	public static void main(String[] args) {
//		System.out.println(Subject.getTypeCode("电影院"));
		System.out.println(Subject.getTypeName("250"));
		String ss = "sfsf\"sfs\"f";
		System.out.println(ss.replaceAll("\"", "\\\""));
		
	}
	
}
