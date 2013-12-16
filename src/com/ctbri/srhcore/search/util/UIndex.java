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
	
	/** ���������Ѿ����ص�IndexReader	 */
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
	 * �жϸ������ַ���·���Ƿ���ڣ���ת��Ϊ����·�������μ���
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
				U.log.error("�������·��["+indexPath[i]+"]�����ڣ�������");
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
	 * ���������Ŀ¼�������ж��Ƿ��Ѿ����棬������棬ֱ��ȡ���������������
	 * @param dirPath
	 * @return
	 */
	public static synchronized IndexReader getSingleIndexReader(File dirPath,boolean cache)
	{
		//һ������ֵ��������Index_BY_Path.containsKey(dirPath.getAbsolutePath())
		//��ʹ����file����һ���ģ����ص�ֵҲ��false
		//����ÿ�ζ������¼����������������ֻ����String���ȴ���һ�¡�
		
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
			U.log.error("��������["+dirPath.getAbsolutePath()+"]ʧ��");
			return null;
		}
		U.log.debug("�ɹ���������["+dirPath.getAbsolutePath()+"]");
		if(cache)
			Index_BY_Path.put(name, reader);
		return reader;
	}

}
