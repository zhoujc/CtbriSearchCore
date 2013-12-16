package com.ctbri.srhcore.ana.ele;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import com.ctbri.srhcore.U;



/**
 * 默认类，当输入不属于前面所有的成分，就会赋值为这个类型。
 *
 */
public class OtherKeyword extends Entity {

	
	
//	public Query toQuery(Condition con) {
//		// TODO Auto-generated method stub
//		if(!(con instanceof OtherKeywordCondition))
//		{
//			U.log.error("OtherKeyword类型转换错误");
//			return null;
//		}
//		OtherKeywordCondition ocon = (OtherKeywordCondition)con;
//		if(ocon.isVague())
//		{
//			if(ocon.isVaguecity())
//			{
//				return this.toVagueCityQuery();
//			}
//			else
//			{
//				return this.toVagueQuery();
//			}
//		}			
//		else
//			return this.toQuery();
//	}

	
	public Query toQuery()
	{
		BooleanQuery bq = new BooleanQuery();
		
		if(this.key.endsWith("*"))
		{
			WildcardQuery tq_title = new WildcardQuery(new Term(U.Field_title, this.key));
			U.log.debug("***********  "+this.key);

			bq.add(tq_title,BooleanClause.Occur.SHOULD);
			return bq;
		}
		TermQuery term = new TermQuery(new Term(U.Field_title,this.key));
		term.setBoost(this.title_weight);
		
		TermQuery term2 = new TermQuery(new Term(U.Field_title_addition,this.key));
		term2.setBoost(this.title_weight/5);
		
		bq.add(term,BooleanClause.Occur.SHOULD);	
		bq.add(term2,BooleanClause.Occur.SHOULD);
//		U.log.info("[otherkeyword/toQuery]: "+ bq.toString());
		return bq;
	}
	
	public Query toVagueQuery()
	{
		
		BooleanQuery bq = new BooleanQuery();
		TermQuery term = new TermQuery(new Term(U.Field_title,this.key));
		term.setBoost(this.title_weight);
		
		TermQuery term2 = new TermQuery(new Term(U.Field_title_addition,this.key));
		term2.setBoost(this.title_addition_weight/5);
		
		TermQuery term3 = new TermQuery(new Term(U.Field_address,this.key));
		term3.setBoost(this.address_weight/10);
		
		
		bq.add(term,BooleanClause.Occur.SHOULD);
		bq.add(term2,BooleanClause.Occur.SHOULD);
		bq.add(term3,BooleanClause.Occur.SHOULD);

		
		U.log.info("[otherkeyword/toQuery]: "+ bq.toString());
		return bq;
	}
	



}
