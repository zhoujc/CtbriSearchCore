package com.ctbri.srhcore.parsedata.movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.parsedata.Cinema;
import com.ctbri.srhcore.parsedata.CinemaC;
import com.ctbri.srhcore.parsedata.MovieWanZhu;
import com.ctbri.srhcore.parsedata.zhizhu.MovieZhizhu;
import com.ctbri.srhcore.parsedata.zhizhu.ZhizhuCinema;

public class MergeMovie {
	
	
	
	public static String filepath = CinemaC.Fetch_cinema_dir+"/resource/movielist/";
	public static Map<String,String> movie_list ;
	public static String movie_tag = "cinema_last";
	
	
	public static void main(String[] args) {
		System.out.println(getMovieList("110000"));
		System.out.println(getMovieList("340100"));
		System.out.println(getMovieList("310000"));
		Set<String > set = movie_list.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
//			U.log.info(string);
		}
	}
	public static String getMovieList(String citycode)	
	{
		String path = ZhizhuCinema.class.getResource("/").getPath();
		File lockfile = new File(path+"/"+"cinemalock.index");
		String content = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lockfile)));
			content = br.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br!= null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(movie_list == null || !content.equals(movie_tag))
		{
			movie_tag = content;
			loadMoveList();
			U.log.info("change movie tag "+ movie_tag);
		}
		String re = movie_list.get(citycode);
		if(re == null) return "[]";
		else return re;
//		return movie_list.get(citycode);
	}
	
	
	public static boolean loadMoveList()
	{
		movie_list = new HashMap<String,String>();
		String areafilepath = filepath+ "merge_movielist";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(areafilepath)),"GBK"));

			String line = "";
			while((line = br.readLine()) != null)
			{
				int index = line.indexOf("$");
				String citycode = line.substring(0,index);
//				System.out.println("process=============================== "+ citycode);
				String co = line.substring(index+1);
				StringBuffer sb1 = new StringBuffer();
				
				String[] items = co.split(";");
				U.log.debug(co);
				
				for (int i = 0; i < items.length; i++) {
					StringBuffer sb = new StringBuffer();
					if(items[i].trim().length()<1) continue;
					int s_index = items[i].indexOf("|");	
					
					int m_index = items[i].indexOf("-");
					int e_index = 0;
					if(items[i].indexOf(",")<0)
						e_index = items[i].length();
					else
						e_index = items[i].indexOf(",");
					
					String refer = items[i].substring(s_index+1, m_index);
					String id = items[i].substring(m_index+1, e_index);
					U.log.debug("refer: "+ refer+" id: "+id);
				
					String url = "";
					String name = items[i].substring(0, s_index);
					if(refer.equals("2"))
					{
						MovieWanZhu m = Cinema.getMovieWanzhu(id);
						if(m == null) 
						{
//							U.log.error(citycode +"   2-"+ id);
							continue;
						}
						if(m.picture.indexOf("|")>0)
							url = m.picture.substring(0, m.picture.indexOf("|"));
						else
							url = m.picture;
//						System.out.println("before ===   "+name);
//						name += " #"+m.type!=null?m.type:""+m.language!=null?m.language:"";
//						System.out.println("change ===   "+name);
					}
					else if(refer.equals("3"))
					{
						MovieZhizhu m = ZhizhuCinema.getMovieZhizhu(id);
						if(m == null) 
						{
							U.log.error(citycode +"   3-"+ id);
							continue;
						}
						if(m.picture.indexOf("|")>0)
							url = m.picture.substring(0, m.picture.indexOf("|"));
						else
							url = m.picture;
					}
					sb.append("{");
					sb.append("\"name\":");		
					sb.append("\"").append(name).append("\"").append(",");
					sb.append("\"id\":");
					sb.append("\"").append(items[i].substring(s_index+1,items[i].length())).append("\"").append(",");
					sb.append("\"picture\":");
					sb.append("\"");
					sb.append(url);
					sb.append("\"");
					sb.append("},");
					sb1.append(sb);
				}
				String re = "";
				if(sb1.length()>0)
					re = "["+sb1.substring(0,sb1.length()-1)+"]";
				else
					re = "[]";

//				U.log.info(re);
				movie_list.put(citycode,re);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br !=null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		U.log.info("load file sucess "+ areafilepath);
		return true;

	}

}
