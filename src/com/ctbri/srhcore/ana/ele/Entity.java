package com.ctbri.srhcore.ana.ele;

import org.apache.lucene.search.Query;


public abstract class Entity {
	


	protected String key;
	protected String value;
	protected int order = 0;
	protected String sign = "+";
	
	protected float title_weight = 1;//7;	
	protected float address_weight = 1;//2;
	protected float title_addition_weight = 1;//2;
	protected float content_weight = 1;
	protected float localArea_weight = 0.005f;
	protected float brand_weight = 1;//10;
	
	protected float type_weight = 1;//2;
	protected float tag_weight = 1;//2;	
	protected float title_whole = 15;
	protected float address_whole = 15;
	
	protected int factor = 1;
	
	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
		this.title_weight = title_weight * factor == 0? 1: title_weight * factor;
		this.address_weight = address_weight * factor == 0? 1: address_weight * factor;
		this.title_addition_weight = title_addition_weight * factor == 0? 1: title_addition_weight * factor;
		this.type_weight = type_weight * factor == 0? 1: type_weight * factor;
		this.tag_weight = tag_weight * factor == 0? 1: tag_weight * factor;
		this.title_whole = title_whole * factor == 0? 1: title_whole * factor;
		this.address_whole = address_whole * factor == 0? 1: address_whole * factor;
	}

	
	public abstract Query toQuery();
	


	public String getKey() {
		return key;
	}

	/**
	 * 原始的输入
	 * 2011-3-10
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign.trim();
	}
	


}
