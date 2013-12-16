package com.ctbri.srhcore.ana.analysis.ctbri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.ctbri.segment.CtbriSegment;


public class CtbriSeg2Tokenizer  extends Tokenizer{
	
	private int start=0,end=0;
	private StringTokenizer segmentedFieldContent;
	private final CharTermAttribute termAtt=addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offAtt=addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt=addAttribute(TypeAttribute.class);
	
	
	protected CtbriSeg2Tokenizer(String fieldName, Reader input) {
		super(input);
		this.segmentedFieldContent=new StringTokenizer(CtbriSegment.getInstance().segment2(toStringFieldContent(this.input)));
	}

	private String toStringFieldContent(Reader input) {
		StringBuffer sb=new StringBuffer();
		BufferedReader br=new BufferedReader(input);
		String line=null;
		try {
			while((line=br.readLine())!=null){
				sb.append(line+" ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		if(this.segmentedFieldContent==null){
			return false;
		}
		if(this.segmentedFieldContent.hasMoreTokens()){
			String token=this.segmentedFieldContent.nextToken();
			this.end=this.start+token.length();
			this.termAtt.setEmpty().append(token);
			this.offAtt.setOffset(correctOffset(this.start), correctOffset(this.end));
			this.typeAtt.setType("word");
		}else{
			return false;
		}
		this.start=this.end+1;
		return true;
	}

	@Override
	public void end() throws IOException {
		int finalEndOffset=correctOffset(this.end);
		offAtt.setOffset(finalEndOffset, finalEndOffset);
	}

	@Override
	public void reset() throws IOException {
		this.start=this.end=0;
		this.segmentedFieldContent=new StringTokenizer(CtbriSegment.getInstance().segment2(toStringFieldContent(this.input)));
	}
	
	

}
