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
 * ������������⣬ԭ�����StringTokenizer ����ֿ�ָ���쳣��
 * ����С�����¸�д������࣬ȥ����StringTokenizer��ʹ��
 *
 * @date 2013-5-22 ����3:29:25
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
			sb.append(items[i]+" ");//ת��ǰ��ԭ���ļ�������
			String s=WordToNumber.operate(items[i]);//ת��
			if(StringUtil.isValidNumber(s)){
				sb.append(s+" ");//ת��������Ч����ֱ�Ӽ���
			}else{//ת��������Ч����
				if(StringUtil.isIncludeNumber(s)){//������Ч���֣����ǰ������֣�����������Ҳ��������
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
