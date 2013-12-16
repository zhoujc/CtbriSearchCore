package com.ctbri.srhcore.ana.ele;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.ctbri.srhcore.U;



public class BrandWord extends Entity {
	




	private String brand;
	
	
	public Query toQuery()
	{
		BooleanQuery bq = new BooleanQuery();
//		TermQuery q1 = new TermQuery(new Term(U.Field_title, this.key));q1.setBoost(this.title_weight/2);
		TermQuery q2 = new TermQuery(new Term(U.Field_brand, this.brand));q2.setBoost(this.tag_weight);
//		if(this.brand.endsWith("ÒøÐÐ"))
//		{
//			String type = Subject.getTypeCode(this.brand);
//			if(type != null && !type.equals(null))
//			{
//				TermQuery q3 = new TermQuery(new Term(U.Field_type, type));q3.setBoost(this.tag_weight);
//				bq.add(q3,BooleanClause.Occur.SHOULD);
//			}			
//		}
		bq.add(q2,BooleanClause.Occur.SHOULD);

		
		U.log.debug("[BrandWord/toQuery]:"+ bq.toString());
		return bq;		
	}
	
	public Query toVagueQuery()
	{
		BooleanQuery bq = new BooleanQuery();
		TermQuery q1 = new TermQuery(new Term(U.Field_title, this.key));q1.setBoost(this.title_weight);
		TermQuery q2 = new TermQuery(new Term(U.Field_brand, this.brand));q2.setBoost(this.tag_weight);
		
//		TermQuery q3 = new TermQuery(new Term(U.Field_type, Subject.getTypeCode(this.tag)));q3.setBoost(this.type_weight);
		bq.add(q1,BooleanClause.Occur.SHOULD);
		bq.add(q2,BooleanClause.Occur.SHOULD);
//		bq.add(q3,BooleanClause.Occur.SHOULD);
		U.log.debug("[BrandWord/toVagueQuery]:"+ bq.toString());
		return bq;	

	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}
