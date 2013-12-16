package com.ctbri.srhcore.ana.analysis;

import com.ctbri.base.util.ChineseCoding;
import com.ctbri.base.util.StringUtil;
import com.ctbri.base.util.WordToNumber;
import com.ctbri.segment.CtbriSegment;
import com.ctbri.segment.unit.SegResult;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;

public class WordAnalyzer  extends Analyzer {

	@Override
	public String parse(LocalQuery lq, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String analyzer(String strQuery,TokenizerModel tokenModel) {
        String result = strQuery;
        result = ChineseCoding.TraditionalToSimplify(result);
        result = ChineseCoding.DBC2SBC(result);
        result = result.toLowerCase();


        switch(tokenModel)
        {
        	case ALL_SEG:
        		result = CtbriSegment.getInstance().Segment4Index(result);
        		break;
        	case COMMON:
        		String str = result;
        		StringBuffer sb=new StringBuffer();
        		SegResult sr = CtbriSegment.getInstance().segment(result);
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
        		result =  sb.toString().toLowerCase();
        		break;
        	case SEG2:
        		result = CtbriSegment.getInstance().segment2(result);
        		break;
        }

        return result;
    }
	
	public static void main(String[] args) {
		String source = "乔丹是个伟大的运动员";
		System.out.println(CtbriSegment.getInstance().segment2("乔丹是个伟大的运动员"));
		System.out.println(WordAnalyzer.analyzer(source, TokenizerModel.COMMON));
		System.out.println(WordAnalyzer.analyzer(source, TokenizerModel.ALL_SEG));
		System.out.println(WordAnalyzer.analyzer(source, TokenizerModel.SEG2));
	}
}
