package com.ctbri.srhcore.ana.analysis.ctbri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.ctbri.base.util.ChineseCoding;
import com.ctbri.base.util.StringUtil;
import com.ctbri.base.util.WordToNumber;
import com.ctbri.segment.CtbriSegment;
import com.ctbri.segment.unit.SegResult;


public class CtbriCommonTokenizer extends Tokenizer {

	private int start=0,end=0;
	private StringTokenizer segmentedFieldContent;
	private final CharTermAttribute termAtt=addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offAtt=addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt=addAttribute(TypeAttribute.class);
	private boolean keepAll;
	
	
	protected CtbriCommonTokenizer(String fieldName, Reader input,boolean keepAll) {
		super(input);
		this.keepAll=keepAll;
		this.segmentedFieldContent=new StringTokenizer(toStringFieldContent(this.input));
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
		String str=ChineseCoding.TraditionalToSimplify(sb.toString());
		str = ChineseCoding.DBC2SBC(str);
		sb=new StringBuffer();
		if(this.keepAll){
			sb.append(str).append(" ");
		}
		SegResult sr = CtbriSegment.getInstance().segment(str);
		for (int i = 0; i < sr.phrase.cnt; i++) {
			int thi = sr.phrase.seg[i].offset;
			int nex = sr.phrase.seg[i].len + thi;
			String key = str.substring(thi, nex);
			String subkey = WordToNumber.operate(key);
			if (StringUtil.isValidNumber(subkey)) {
				key = subkey;
			}
			if (i != 0) {
				sb.append(" ");
			}
			sb.append(key);
		}
		sb.append(" ");
		
		for (int i = 0; i < sr.subPhrase.cnt; i++) {
			int thi = sr.subPhrase.seg[i].offset;
			int nex = sr.subPhrase.seg[i].len + thi;
			String key = str.substring(thi, nex);
			String subkey = WordToNumber.operate(key);
			if (StringUtil.isValidNumber(subkey)) {
				key = subkey;
			}
			if (i != 0) {
				sb.append(" ");
			}
			sb.append(key);
		}
		return sb.toString().toLowerCase();
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
//		this.segmentedFieldContent=null;
	}

}
