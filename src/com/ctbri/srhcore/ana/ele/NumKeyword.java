package com.ctbri.srhcore.ana.ele;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer;




/**
 * 数字类
 *
 */
public class NumKeyword extends Entity {

	
	public NumKeyword()
	{
		this.title_weight = 7;
		this.address_weight = 2;
		this.content_weight = 0.1f;
	}

	public static void main(String[] args) {
		AnalyzingQueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"brand", new CtbriAnalyzer(C.segMode));
		
		//	qp.setAllowLeadingWildcard(true);
			Query q = null;
			try {
				q = qp.parse("+(brand:中国合伙人) +(brand:中国合伙人)");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
			System.out.println("sdfs "+q.toString());
			
			TermQuery q2 = new TermQuery(new Term(U.Field_brand, "中国合伙人"));
	}

	public Query toQuery(){
		
		BooleanQuery bq = new BooleanQuery();
		
		if(this.key.endsWith("*"))
		{
			WildcardQuery tq_title = new WildcardQuery(new Term(U.Field_title, this.key));
			U.log.debug("***********  "+this.key);

			bq.add(tq_title,BooleanClause.Occur.SHOULD);
			return bq;
		}
		TermQuery term = new TermQuery(new Term(U.Field_title,this.key));
		term.setBoost(2);
		
//		TermQuery term2 = new TermQuery(new Term(U.Field_title_addition,this.key));
//		term2.setBoost(this.title_weight/5);
		
		bq.add(term,BooleanClause.Occur.SHOULD);	
//		bq.add(term2,BooleanClause.Occur.SHOULD);
//		U.log.info("[otherkeyword/toQuery]: "+ bq.toString());
		return bq;
//		return q;
	}
	
	
	public Query toVagueQuery()
	{
		StringBuilder sb = new StringBuilder();
		if(this.getValue().length() >= 3){
		sb.append(" (");
		sb.append(" title:").append(this.getValue()).append("^").append(2);
		sb.append(" title_addition:").append(this.getValue()).append("^").append(1);
		sb.append(" address:").append(this.getValue()).append("^").append(0.1);
		sb.append(")");
		
		}else{
			sb.append(" (");
			sb.append(" title:").append(this.getValue()).append("^").append(2);
			sb.append(" title_addition:").append(this.getValue()).append("^").append(1);
			sb.append(" address:").append(this.getValue()).append("^").append(0.1);
			sb.append(" content:").append(this.getValue()).append("^").append(0.1);
			sb.append(")");
			
		}
		
		QueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"name", new CtbriAnalyzer(C.segMode));
	//	qp.setAllowLeadingWildcard(true);
		Query q = null;
		try {
			q = qp.parse(sb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		return q;
	}
	
	public Query toNumQuery(){
		StringBuilder sb = new StringBuilder();
		/*if(this.getKey().length() >= 4){
		sb.append(" +(");
		sb.append(" title:*").append(this.getKey()).append("*").append("^").append(LocalQuery.title_weight);
		sb.append(" address:*").append(this.getKey()).append("*").append("^").append(LocalQuery.address_weight);
		sb.append(" content:*").append(this.getKey()).append("*").append("^").append(LocalQuery.content_weight);
		sb.append(")");
		
		}else*/
		{
			sb.append(" +(");
			sb.append(" title:").append(this.getValue()).append("^").append(this.title_weight);
			sb.append(" address:").append(this.getValue()).append("^").append(this.address_weight);
			sb.append(" content:").append(this.getValue()).append("^").append(this.content_weight);
			sb.append(")");	
		}
		
		QueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"name", new CtbriAnalyzer(C.segMode));
        qp.setAllowLeadingWildcard(true);
		
		Query q = null;
		try {
			q = qp.parse(sb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		return q;
	}
	
	public Query toNumVagueQuery(){
		StringBuilder sb = new StringBuilder();
		if(this.getValue().length() >= 4){
		sb.append(" +(");
		sb.append(" title:*").append(this.getValue()).append("*").append("^").append(this.title_weight);
		sb.append(" address:*").append(this.getValue()).append("*").append("^").append(this.address_weight);
		sb.append(" content:*").append(this.getValue()).append("*").append("^").append(this.content_weight);
		sb.append(")");
		
		}else
		{
			sb.append(" +(");
			sb.append(" title:").append(this.getValue()).append("^").append(this.title_weight);
			sb.append(" address:").append(this.getValue()).append("^").append(this.address_weight);
			sb.append(" content:").append(this.getValue()).append("^").append(this.content_weight);
			sb.append(")");	
		}
		
		QueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"name", new CtbriAnalyzer(C.segMode));
        qp.setAllowLeadingWildcard(true);
		
		Query q = null;
		try {
			q = qp.parse(sb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		return q;
	}



}
