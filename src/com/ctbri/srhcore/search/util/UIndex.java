package com.ctbri.srhcore.search.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

import com.ctbri.srhcore.C;
import com.ctbri.srhcore.U;
import com.ctbri.srhcore.loadindex.CinemaIndexContext;
import com.ctbri.srhcore.pojo.MapPoint;


public class UIndex {
	
	/** 用来缓存已经加载的IndexReader	 */
	public static HashMap<String,IndexReader> Index_BY_Path = new HashMap<String,IndexReader>();
	
	public static CinemaIndexContext cic = new CinemaIndexContext();
	
	public static Query getLatLonRange(double range,MapPoint p)
	{
//		/s
		return null;
	}
	
	public static IndexReader readCinemaIndex()
	{
		String dir = C.INDEX_PATH+File.separator+ C.Cinema_Index;
		IndexReader temp = getSingleIndexReader(new File(dir),false);
		cic.reader = temp;
		cic.indexFilePath = dir;
		return temp;
	}
	
	public static IndexReader readFoodIndex()
	{
		String dir = C.INDEX_PATH+File.separator+ C.Food_Index;
		return getSingleIndexReader(new File(dir));
	}
	
	public static IndexReader readTuangouIndex()
	{
		String dir = C.INDEX_PATH+File.separator+ C.Tuangou_Index;
		return getSingleIndexReader(new File(dir));
		
	}
	
	public static IndexSearcher getPOIIndexSearcher()
	{
		String p_dir = C.INDEX_PATH;
		File par = new File(p_dir);
		File[] files = par.listFiles();
		return UIndex.getIndexSearcher(files);
	}
	
	/**
	 * 判断给出的字符串路径是否存在，并转化为绝对路径，依次加载
	 * @param indexPath
	 * @return
	 */
	public static synchronized IndexSearcher getIndexSearcher(File[] indexPath)
	{
		IndexSearcher searcher = null;
		int len = indexPath.length;
		ArrayList<File> indexFiles = new ArrayList<File>();
		for (int i = 0; i < len; i++) {
			if(!indexPath[i].exists())
			{
				U.log.error("您输入的路径["+indexPath[i]+"]不存在，将跳过");
				continue;
			}
			if(indexPath[i].isDirectory())
				indexFiles.add(indexPath[i]);
		}
		ArrayList<IndexReader> listIndex = new ArrayList<IndexReader>();
		for (int i = 0; i < indexFiles.size(); i++) {
			IndexReader tempreder = getSingleIndexReader(indexFiles.get(i));	
			if(tempreder != null)
				listIndex.add(tempreder);
		}
		IndexReader[] mul_search = new IndexReader[listIndex.size()];
		mul_search = listIndex.toArray(mul_search);		
		searcher = new IndexSearcher(new MultiReader(mul_search));		
		return searcher;
	}
	
	public static synchronized IndexReader getSingleIndexReader(File dirPath)
	{
		return getSingleIndexReader(dirPath, true);
	}
	
	/**
	 * 根据输入的目录，首先判断是否已经缓存，如果缓存，直接取。否则加载索引。
	 * @param dirPath
	 * @return
	 */
	public static synchronized IndexReader getSingleIndexReader(File dirPath,boolean cache)
	{
		//一个很奇怪的现象，如果Index_BY_Path.containsKey(dirPath.getAbsolutePath())
		//即使两个file都是一样的，返回的值也是false
		//导致每次都是重新加载这个索引，所以只好用String事先存贮一下。
		
//		IndexReader reader = IndexReader.open(NIOFSDirectory.open(new File(strFile)), true);
//	    IndexSearcher searcher = new IndexSearcher(reader);
		String name = dirPath.getAbsolutePath();
		if(cache && Index_BY_Path.containsKey(name))
		{
			return Index_BY_Path.get(name);
		}	
		IndexReader reader = null;
		try {
//			reader = IndexReader.open(dirPath);
			reader = DirectoryReader.open(FSDirectory.open(dirPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			U.log.error("加载索引["+dirPath.getAbsolutePath()+"]失败");
			return null;
		}
		U.log.debug("成功加载索引["+dirPath.getAbsolutePath()+"]");
		if(cache)
			Index_BY_Path.put(name, reader);
		return reader;
	}

}
