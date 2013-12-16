package com.ctbri.srhcore.ana.analysis;

import com.ctbri.base.util.StringUtil;
import com.ctbri.base.util.WordToNumber;
import com.ctbri.srhcore.ana.LocalQuery;
import com.ctbri.srhcore.ana.ele.EngWord;
import com.ctbri.srhcore.ana.ele.NumKeyword;
import com.ctbri.srhcore.ana.ele.OtherKeyword;


public class OtherKeywordAnalyzer extends Analyzer {

	
	public String parse(LocalQuery lq,String key)
	{
		
		if(key.trim().length() ==0 )return key;
		String[] keys = key.split(" ");
		for (int i = 0; i < keys.length; i++) {
//			if(StringUtil.isValidNumber(keys[i]))
//			{
//				NumKeyword num = new NumKeyword();
//				num.setKey(keys[i]);
//				
//				int index = lq.getSegStrIndex(keys[i],num.getOrder());
//                if(index > -1)
//                {
//                	lq.getTokenEntities()[index] = num;
//                    key = key.replaceFirst(keys[i], "");
//                }                	
//			}
//			else 
			if(StringUtil.isValidCharacter(keys[i]))
			{
				EngWord eng = new EngWord();
				eng.setKey(keys[i]);
				int index = lq.getSegStrIndex(keys[i],eng.getOrder());
                if(index > -1)
                {
                	lq.getTokenEntities()[index] = eng;
                    key = key.replaceFirst(keys[i], "");
                }    
			}
			else if(!WordToNumber.operate(keys[i]).equals(keys[i]))
			{
				String tempNum = WordToNumber.operate(keys[i]);
				if(StringUtil.isValidNumber(tempNum))
				{
					NumKeyword num = new NumKeyword();
					num.setKey(keys[i] );
					num.setValue(tempNum);
					
					int index = lq.getSegStrIndex(keys[i],num.getOrder());
	                if(index > -1)
	                {
	                	lq.getTokenEntities()[index] = num;
	                    key = key.replaceFirst(keys[i], "");
	                }
				}
				else
				{
					OtherKeyword other = new OtherKeyword();
					other.setKey(keys[i]);
					int index = lq.getSegStrIndex(keys[i],other.getOrder());
	                if(index > -1)
	                {
	                	lq.getTokenEntities()[index] = other;
	                    key = key.replaceFirst(keys[i], "");
	                }
				}
			}
			else
			{
				OtherKeyword other = new OtherKeyword();
				other.setKey(keys[i]);
				int index = lq.getSegStrIndex(keys[i],other.getOrder());
                if(index > -1)
                {
                	lq.getTokenEntities()[index] = other;
                    key = key.replaceFirst(keys[i], "");
                }
			}
		}
		
		
		key = key.replaceAll(" +", " ").trim();
		return key;
	}}
