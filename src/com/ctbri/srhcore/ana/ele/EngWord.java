package com.ctbri.srhcore.ana.ele;


import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer;

/**
 * 英文词
 *
 */
public class EngWord extends Entity {


	
	public Query toQuery()
	{
		QueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"name", new CtbriAnalyzer(C.segMode));
		StringBuilder sb = new StringBuilder();
		sb.append(" (");
//		sb.append(" title_pinyin:").append(this.getKey());
		sb.append(" title:").append(this.getKey()).append("^").append(this.title_weight);
/*	   if(this.getKey().length() >= 3)
	   {
	   sb.append(" title:*").append(this.getKey()).append("*");
	   sb.append(" content:*").append(this.getKey()).append("*");
	   }*/
//		sb.append(" address:").append(this.getKey()).append("^").append(this.address_weight);
//		sb.append(" content:").append(this.getKey()).append("^").append(this.content_weight);
		sb.append(")");
		
		qp.setAllowLeadingWildcard(true);

		try {
			Query q = qp.parse(sb.toString());
			return q;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			U.log.error("EngWord toQuery解析错误");
			e.printStackTrace();
		}
		//sb.append(" +title_pinyin:").append(this.getKey());
		return null;
	}
	
	/**
	 * 允许模糊匹配，在当前情况仅当出现单独的英文字符的时候允许模糊匹配
	 * @return
	 */
	private Query toVagueQuery()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" (");
//		sb.append(" title_pinyin:").append(this.getKey());
		sb.append(" title:").append(this.getKey()).append("^").append(this.title_weight);
		if(this.getKey().length() >= 3)
		{
			sb.append(" title:*").append(this.getKey()).append("*");
			sb.append(" content:*").append(this.getKey()).append("*");
		}
		sb.append(" address:").append(this.getKey()).append("^").append(this.address_weight);
		sb.append(")");
	   
		QueryParser qp=new AnalyzingQueryParser(Version.LUCENE_40,"name", new CtbriAnalyzer(C.segMode));
		qp.setAllowLeadingWildcard(true);
		

		try {
			Query q = qp.parse(sb.toString());
			return q;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			U.log.error("EngWord toVagueQuery解析错误");
			e.printStackTrace();
		}
		//sb.append(" +title_pinyin:").append(this.getKey());

		return null;
	}
	



}
