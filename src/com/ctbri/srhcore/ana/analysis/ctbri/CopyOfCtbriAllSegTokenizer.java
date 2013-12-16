package com.ctbri.srhcore.ana.analysis.ctbri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.ctbri.base.util.StringUtil;
import com.ctbri.base.util.WordToNumber;
import com.ctbri.segment.CtbriSegment;

/**
 * 这个类会出现问题，原因就是StringTokenizer 会出现空指针异常。
 * 所以小陶重新改写了这个类，去掉了StringTokenizer的使用
 *
 * @date 2013-5-22 下午3:29:25
 * @author zhoujc
 *
 */
public final class CopyOfCtbriAllSegTokenizer extends Tokenizer  {

	private int start = 0,end = 0;
	private StringTokenizer segmentedFieldContent;
	private final CharTermAttribute termAtt=addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offAtt=addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt=addAttribute(TypeAttribute.class);
	protected CopyOfCtbriAllSegTokenizer(String fieldName, Reader input) {
		super(input);
		this.segmentedFieldContent=new StringTokenizer(allSegmentation(toStringFieldContent(this.input)),null);
	}

	
	private String allSegmentation(String str) {
		String rt=CtbriSegment.getInstance().Segment4Index(str);
//		System.out.println(rt);
		String items[] = rt.split("\\s+");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<items.length;i++){
			sb.append(items[i]+" ");//转换前将原来的加入索引
			String s=WordToNumber.operate(items[i]);//转换
			if(StringUtil.isValidNumber(s)){
				sb.append(s+" ");//转换后是有效数字直接加入
			}else{//转换后不是有效数字
				if(StringUtil.isIncludeNumber(s)){//不是有效数字，但是包含数字，则把这种情况也加入索引
					sb.append(s+" ");
				}
			}
		}
		String result=sb.toString().trim();
//		System.out.println(result);
		return result;
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
		return sb.toString().trim();
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
		this.segmentedFieldContent=null;
	}

}
