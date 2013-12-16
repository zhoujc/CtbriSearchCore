package com.ctbri.srhcore.ana.analysis.ctbri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.ctbri.base.util.StringUtil;
import com.ctbri.base.util.WordToNumber;
import com.ctbri.segment.CtbriSegment;

public final class CtbriAllSegTokenizer extends TokenFilter  {

	private int start = 0,end = 0;
//	private StringTokenizer segmentedFieldContent;
	
	private Iterator<SegToken> tokenIter;
	private List<SegToken> tokenBuffer;
	private final CharTermAttribute termAtt=addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt=addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt=addAttribute(TypeAttribute.class);
	private boolean hasIllegalOffsets;
	protected CtbriAllSegTokenizer(String fieldName, TokenStream in) {
		super(in);
//		this.segmentedFieldContent=new StringTokenizer(allSegmentation(toStringFieldContent(this.input)),null);
	}

	
	class SegToken{
		public int startOffset;
		public int endOffset;
		public String term;
		public SegToken(String term,int startOffset,int endOffset){
			this.term=term;
			this.startOffset=startOffset;
			this.endOffset=endOffset;
		}
	}
	
	private List<SegToken> allSegmentation(String str) {
		String rt=CtbriSegment.getInstance().Segment4Index(str);
//		System.out.println(rt);
		List<SegToken> allSeg = new ArrayList<SegToken>();
		String items[] = rt.split("\\s+");
		for (int i = 0; i < items.length; i++) {
			String s = WordToNumber.operate(items[i]);// ×ª»»
			if (StringUtil.isValidNumber(s)||StringUtil.isIncludeNumber(s)) {
				this.end=this.start+s.length();
				allSeg.add(new SegToken(s, this.start, this.end));
				this.start=this.end+1;
			}
			this.end=this.start+items[i].length();
			allSeg.add(new SegToken(items[i],this.start,this.end));
			if(items.length-1!=i)
				this.start=this.end+1;
		}
		return allSeg;
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
		if (tokenIter == null || !tokenIter.hasNext()) {
			// there are no remaining tokens from the current sentence... are
			// there more sentences?
			if (input.incrementToken()) {
				start = offsetAtt.startOffset();
				end = offsetAtt.endOffset();
				 hasIllegalOffsets = (start + termAtt.length()) != end;
				tokenBuffer = allSegmentation(termAtt.toString());
				tokenIter = tokenBuffer.iterator();
				if (!tokenIter.hasNext())
					return false;
			} else {
				return false; // no more sentences, end of stream!
			}
		}
	    // There are remaining tokens from the current sentence, return the next one. 
	    SegToken nextWord = tokenIter.next();
	    termAtt.copyBuffer(nextWord.term.toCharArray(), 0, nextWord.term.toCharArray().length);
	    if (hasIllegalOffsets) {
	      offsetAtt.setOffset(start, end);
	    } else {
	      offsetAtt.setOffset(nextWord.startOffset, nextWord.endOffset);
	    }
	    typeAtt.setType("word");
	    return true;
	}
	



	@Override
	public void reset() throws IOException {
		super.reset();
		tokenIter = null;
	}

}
