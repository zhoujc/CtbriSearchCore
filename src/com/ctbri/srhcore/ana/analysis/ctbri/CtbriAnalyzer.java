package com.ctbri.srhcore.ana.analysis.ctbri;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

import com.ctbri.srhcore.U;



public class CtbriAnalyzer extends Analyzer {

	TokenizerModel tokenModel;
	HashSet<String> keepAlls = new HashSet<String>();
	public CtbriAnalyzer(TokenizerModel model)
	{
		super();
		this.tokenModel = model;
	}
	

	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		// TODO Auto-generated method stub
		boolean keepAll = false;
		if(this.keepAlls.contains(fieldName))
			keepAll = true;
		
		Tokenizer source = null;
		TokenStream result = null;
		switch(this.tokenModel)
		{
			case ALL_SEG:
				source =new SentenceTokenizer(reader); 
				result = new CtbriAllSegTokenizer(fieldName, source);
				break;
			case COMMON:
				source = new CtbriCommonTokenizer(fieldName,reader,keepAll);
				result = source;
				break;
			case SEG2:
				source = new CtbriSeg2Tokenizer(fieldName,reader);
				result = source;
				break;
			default:
				U.log.error("Please specify a seg modle..[COMMON,ALL_SEG,SEG2]");
		}
		
		return new TokenStreamComponents(source, result);
	}
	
	public static void main(String[] args) throws IOException{
//		MapbarAnalyzer ma=new MapbarAnalyzer(TokenizerModle.COMMON);
//		StringReader sr=new StringReader("5G0");
//		TokenStream ts=ma.tokenStream("typeCode", sr);
////		ts=ma.tokenStream("name", sr);
////		ts.reset();
//		while(ts.incrementToken()){
//			System.out.println(ts.reflectAsString(true));
//		}
		
//		CtbriAnalyzer ma=new CtbriAnalyzer(TokenizerModel.ALL_SEG);
//		CtbriAnalyzer ma=new CtbriAnalyzer(TokenizerModel.COMMON);
		CtbriAnalyzer ma=new CtbriAnalyzer(TokenizerModel.SEG2);
		StringReader sr=new StringReader("自信人生二千年");
		TokenStream ts=ma.tokenStream("typeCode", sr);
//		ts=ma.tokenStream("name", sr);
//		ts.reset();
		while(ts.incrementToken()){
			System.out.println(ts.reflectAsString(true));
		}

	}
	
	public enum TokenizerModel{
		COMMON,ALL_SEG,SEG2;
	}

}
