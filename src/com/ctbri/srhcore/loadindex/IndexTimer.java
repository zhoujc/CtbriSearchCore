package com.ctbri.srhcore.loadindex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.ctbri.segment.U;
import com.ctbri.srhcore.C;

public class IndexTimer {
	
	public static String lockIndexFileName = "cinemalock.index";
	
	
	public static void checkCinemaIndex(long peroid)
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{

			@Override
			public void run() {
				String path = this.getClass().getResource("/").getPath();
//		                .getLocation().getPath();  
				// TODO Auto-generated method stub
				File lockfile = new File(path+"/"+lockIndexFileName);
				if(!lockfile.exists())
				{
					try {
						lockfile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block

					}
					
				}
//				U.log.debug(lockfile.getAbsoluteFile());
//				if()
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(lockfile)));
					String content = br.readLine();
					if(content!= null && content.length()>0 && !content.equals(C.Cinema_Index))
					{
						U.log.warn("cinema index change to: "+ content);
						String oldIndexPath = C.INDEX_PATH+File.separator+ C.Cinema_Index;
						String oldStorePath = C.Index_Store_Path+File.separator+ C.Cinema_Index;
						
						C.Cinema_Index = content;
						File oldIndex = new File(oldIndexPath);
						while(oldIndex.exists())
						{
							U.log.info("try delete dir "+ oldIndexPath );
							delExistsFiles(oldIndex);
						}
						File storeIndex = new File(oldStorePath);
						while(storeIndex.exists())
						{
							U.log.info("try delete dir "+ oldStorePath );
							delExistsFiles(storeIndex);
						}
						U.log.info("delete dir "+ oldIndexPath +" success!");
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if(br != null)
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
				
			}
			
		}, 1000*3,peroid);// the last para is peroid
		
	}
	
	private static void delExistsFiles(File file) {
		if(!file.exists()){
			return;
		}
		if(file.isDirectory()){
			for(File f:file.listFiles()){
				delExistsFiles(f);
			}
			file.delete();
		}else{
			file.delete();
		}
	}
	
	public static void main(String[] args) {
		final String lockFileName = "cinemaLock";
		final String usingIndex = "aaa";
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				U.log.info(new Date());
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(lockFileName))));
					String content = br.readLine();
					if(content!= null && content.length()>0 && !content.equals(C.Cinema_Index))
					{
						System.out.println(content);
						C.Cinema_Index = content;
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		}, 1000*3,1000*1);// the last para is peroid
	}
	


}
