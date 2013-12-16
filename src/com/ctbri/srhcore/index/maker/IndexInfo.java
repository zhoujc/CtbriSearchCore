package com.ctbri.srhcore.index.maker;

import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;


public class IndexInfo {
	
	
	private String indexRootPath;
	private String indexStorePath;
	private IndexMode indexMode;
	private TokenizerModel segModel;
	public IndexInfo(String indexRootPath,String indexStorePath,IndexMode indexMode,TokenizerModel segModle){
		this.indexRootPath=indexRootPath;
		this.indexStorePath = indexStorePath;
		this.indexMode=indexMode;
		this.segModel=segModle;
	}
	
	
	public TokenizerModel getSegModle() {
		return segModel;
	}


	public void setSegModle(TokenizerModel segModle) {
		this.segModel = segModle;
	}


	public String getIndexRootPath() {
		return indexRootPath;
	}


	public void setIndexRootPath(String indexRootPath) {
		this.indexRootPath = indexRootPath;
	}


	public IndexMode getIndexMode() {
		return indexMode;
	}


	public void setIndexMode(IndexMode indexMode) {
		this.indexMode = indexMode;
	}

	

	public String getIndexStorePath() {
		return indexStorePath;
	}


	public void setIndexStorePath(String indexStorePath) {
		this.indexStorePath = indexStorePath;
	}



	public enum IndexMode{
		CITY,PROVINCE,PROVINCE_CITY;
	}
	


}
