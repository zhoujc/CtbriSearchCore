package com.ctbri.srhcore.ana.analysis;

import com.ctbri.srhcore.ana.LocalQuery;


public abstract class Analyzer {
	
	/**
	 * 用来记录每个分析的order
	 */
	protected int order;
	protected boolean isReplace = true;
	abstract public String parse(LocalQuery lq,String key);

}
