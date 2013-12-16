package com.ctbri.srhcore.index.maker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;
import com.ctbri.srhcore.index.maker.IndexInfo.IndexMode;
import com.ctbri.srhcore.parsedata.Cinema;

public class TGIndexMaker {
	

	
	private ExecutorService service =Executors.newFixedThreadPool(4);
	private ExecutorService serviceStore =Executors.newFixedThreadPool(4);
	private IndexInfo indexInfo;
	private String indexcsvPath;
	private String storecsvPath;
	
	/**
	 * @param indexInfo
	 * @param csvPath csv源文件的存储的路径
	 */
	public TGIndexMaker(IndexInfo indexInfo,String indexcsvPath,String storecsvPath)
	{
		this.indexInfo = indexInfo;
		this.indexcsvPath = indexcsvPath;
		this.storecsvPath = storecsvPath;
		this.delExitsfiles(new File(this.indexInfo.getIndexRootPath()));
		this.delExitsfiles(new File(this.indexInfo.getIndexStorePath()));
	}
	
	public void index()
	{
		long time = System.currentTimeMillis();
		File csvDir = new File(this.indexcsvPath);
		if(!csvDir.exists() || !csvDir.isDirectory())
		{
			U.log.error(this.indexcsvPath+" is not a dir, or not exits");
			return;
		}
		File[] csvs = csvDir.listFiles();
		U.log.info("start index from csv NUM:"+ csvs.length);
		
		int i = 0;
		for(File csv: csvs)
		{
			if(!csv.isFile())
			{
				U.log.error(csv.getName()+ " is not a csv file");
			}
			else
			{
				U.log.info("index the file: "+ csv.getAbsoluteFile());
				this.service.execute(new TGIndexJob(csv));
				i++;
			}
		}
		this.service.shutdown();
		
		try {
			this.service.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
			U.log.info("Successfully indexed "+i+" citys. Time taken by program:"+getTime(time));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		String indexpath = "./index/tuangou";
		String indexstorepath = "./indexStore/tuangou";
		String indexcsvdirpath = "tuangou/index";
		String storecsvdirpaht = "tuangou/store";
		if(args!= null && args.length>0)
		{
			indexpath = args[0];
			indexstorepath = args[1];
			indexcsvdirpath = args[2];
			storecsvdirpaht = args[3];
		}
		IndexInfo indexInfo=new IndexInfo(indexpath,indexstorepath,IndexMode.PROVINCE,TokenizerModel.COMMON);
		TGIndexMaker tgindex=new TGIndexMaker(indexInfo,indexcsvdirpath,storecsvdirpaht);
		tgindex.index();
		tgindex.indexStorePOI();
		
		
//		Calendar cal1 = Calendar.getInstance();
//		
//		cal1.add(Calendar.DATE,1);
//		
//		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
//		String a1=dateformat1.format(cal1.getTime());
//		System.out.println(a1);
//		try {
//			Date s = dateformat1.parse("2013-07-26");
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	public void indexStorePOI()
	{
		long time = System.currentTimeMillis();
		File csvDir = new File(this.storecsvPath);
		if(!csvDir.exists() || !csvDir.isDirectory())
		{
			U.log.error(this.storecsvPath+" is not a dir, or not exits");
			return;
		}
		File[] csvs = csvDir.listFiles();
		U.log.info("start index from csv NUM:"+ csvs.length);
		
		int i = 0;
		for(File csv: csvs)
		{
			if(!csv.isFile())
			{
				U.log.error(csv.getName()+ " is not a csv file");
			}
			else
			{
				this.serviceStore.execute(new TGIndexStoreJob(csv));
				i++;
			}
		}
		this.serviceStore.shutdown();
		
		try {
			this.serviceStore.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
			U.log.info("Successfully indexed "+i+" citys. Time taken by program:"+getTime(time));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class TGIndexStoreJob implements Runnable
	{

		String indexPath;
		CtbriIndexWriter writer;
		File cityCSVFile;
		public TGIndexStoreJob(File f)
		{
			this.cityCSVFile = f;
			String name = f.getName();
			String[] nameInfos = name.split("\\.");
			
			switch(indexInfo.getIndexMode()){
			case CITY:
				this.indexPath=indexInfo.getIndexStorePath()+"/"+nameInfos[1];//p.getValue0() GBCitycode
				break;
			case PROVINCE_CITY:
				this.indexPath=indexInfo.getIndexStorePath()+"/"+nameInfos[0]+"/"+nameInfos[1];
				break;
			case PROVINCE:
				this.indexPath=indexInfo.getIndexStorePath()+"/"+nameInfos[0];//暂不支持
				break;
			}
			this.writer=new CtbriIndexWriter(this.indexPath,indexInfo.getSegModle());
		}

		@Override
		public void run() {
			CSVReader cr=null;
			BufferedReader br = null;
			try {
//				cr = new CSVReader(new FileReader(this.cityCSVFile), ',');
				cr = new CSVReader(new InputStreamReader(new FileInputStream(this.cityCSVFile),"GBK"));
//				br = new BufferedReader(new InputStreamReader(new FileInputStream(this.cityCSVFile),"GBK"));
				String[] poiFields ;
				while((poiFields = cr.readNext()) != null)
				{

					if(poiFields.length <9) 
					{
						U.log.info(poiFields[0]+" len is "+ poiFields.length);
						continue;
					}
					if(poiFields.length< 25)
					{
						
						POIInfo poi=new POIInfo();
						poi.setNid(poiFields[0]);
						if(!"null".equals(poiFields[1])&&!"".equals(poiFields[1])){
							poi.setTitle(poiFields[1]);
						}
						if(!"null".equals(poiFields[2])&&!"".equals(poiFields[2])){
							poi.setInvol(poiFields[2]);
						}
						SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
						Date now = new Date();
						Date s = new Date();
						Date e = new Date();
						try {
							s = dateformat1.parse(poiFields[3]);
							
						} catch (ParseException se) {
							// TODO Auto-generated catch block
							s = new Date();
						}
						try {
							e = dateformat1.parse(poiFields[4]);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e = new Date();
						}
						if(s.after(now) || e.before(now))
						{
							continue;
						}
						if(!"null".equals(poiFields[3])&&!"".equals(poiFields[3]))
							poi.setTbegin(poiFields[3]);
						if(!"null".equals(poiFields[4])&&!"".equals(poiFields[4]))
							poi.setTend(poiFields[4]);
						if(!"null".equals(poiFields[5])&&!"".equals(poiFields[5])){
							poi.setSelf_info(poiFields[5]);
						}
						if(!"null".equals(poiFields[6])&&!"".equals(poiFields[6])){
							poi.setPhoto(poiFields[6]);
						}
						if(!"null".equals(poiFields[7])&&!"".equals(poiFields[7])){
							poi.setWebUrl(poiFields[7]);
						}
						if(!"null".equals(poiFields[8])&&!"".equals(poiFields[8])){
							poi.setWebUrl(poi.getWebUrl()+","+poiFields[8]);
						}
						
						/**
						if(!"null".equals(poiFields[1])&&!"".equals(poiFields[1])){
							poi.setCid(poiFields[1]);
						}
						poi.setCityName(poiFields[2]);
						if(poiFields[3].length() ==6 && !poiFields[3].endsWith("00"))
							poiFields[3] = poiFields[3].substring(0,4)+"00";
						poi.setCityCode(poiFields[3]);
						poi.setTitle(poiFields[4]);
						if(!"null".equals(poiFields[5])&&!"".equals(poiFields[5])){
							poi.setAddress(poiFields[5]);
						}
						if(!"null".equals(poiFields[6])&&!"".equals(poiFields[6])){
							poi.setPhone(poiFields[6]);
						}
						if(!"null".equals(poiFields[7])&&!"".equals(poiFields[7])){
							poi.setTypeName(poiFields[7]);
						}
						if(!"null".equals(poiFields[8])&&!"".equals(poiFields[8])){
							poi.setTypeCode(poiFields[8]);
						}
						poi.setLongitude(Double.parseDouble(poiFields[10]));
						poi.setLatitude(Double.parseDouble(poiFields[9]));
						if(!"null".equals(poiFields[11])&&!"".equals(poiFields[11])){
							poi.setDistrict(poiFields[11]);
						}
						if(!"null".equals(poiFields[12])&&!"".equals(poiFields[12])){
							poi.setTag(poiFields[12]);
						}
						if(!"null".equals(poiFields[13])&&!"".equals(poiFields[13])){
							poi.setInvol(poiFields[13]);
						}
						if(!"null".equals(poiFields[14])&&!"".equals(poiFields[14])){
							poi.setBrand(poiFields[14]);
						}
						if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){
							poi.setConfidenceLevel(poiFields[15]);
						}
						if(!"null".equals(poiFields[16])&&!"".equals(poiFields[16])){
//							poi.setStrtimeoflastupdate(poiFields[16]);
							poi.setCtraffic(poiFields[16]);
						}
						if(!"null".equals(poiFields[17])&&!"".equals(poiFields[17])){
//							poi.setSelf_info(poiFields[17]);
							poi.setCdes(poiFields[17]);
						}
						if(!"null".equals(poiFields[18])&&!"".equals(poiFields[18])){
//							poi.setProvince(poiFields[18]);
							poi.setCspeicalDes(poiFields[18]);
						}
						if(!"null".equals(poiFields[19])&&!"".equals(poiFields[19])){
//							poi.setNation(poiFields[19]);
							poi.setPhoto(poiFields[19]);
						}
						
						if(!"null".equals(poiFields[20])&& !"".equals(poiFields[20]))
							poi.setWebUrl(poiFields[20]);
						**/
												

						this.writer.addPOIStore(poi);
					}
			
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			finally
			{
				this.writer.finish();
				if(cr != null)
					try {
						cr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	
	}
	
	
	public class TGIndexJob implements Runnable
	{
		String indexPath;
		CtbriIndexWriter writer;
		File cityCSVFile;
		public TGIndexJob(File f)
		{
			this.cityCSVFile = f;
			String name = f.getName();
			String[] nameInfos = name.split("\\.");
			
			switch(indexInfo.getIndexMode()){
			case CITY:
				this.indexPath=indexInfo.getIndexRootPath()+"/"+nameInfos[1];//p.getValue0() GBCitycode
				break;
			case PROVINCE_CITY:
				this.indexPath=indexInfo.getIndexRootPath()+"/"+nameInfos[0]+"/"+nameInfos[1];
				break;
			case PROVINCE:
				this.indexPath=indexInfo.getIndexRootPath()+"/"+nameInfos[0];//暂不支持
				break;
			}
			this.writer=new CtbriIndexWriter(this.indexPath,indexInfo.getSegModle());
		}

		@Override
		public void run() {
			CSVReader cr=null;
			try {
//				cr = new CSVReader(new FileReader(this.cityCSVFile), ',');
				cr = new CSVReader(new InputStreamReader(new FileInputStream(this.cityCSVFile),"GBK"));
				String[] poiFields ;
				while((poiFields = cr.readNext()) != null)
				{

					if(poiFields.length <= 19)
					{
						U.log.error("error:"+ poiFields[0]);
						continue;
					}
					if(poiFields.length <= 21)
					{
						if(!isNumeric(poiFields[10]) || !isNumeric(poiFields[9]))
						{
							U.log.info(poiFields[0] +" lon or lat is not num");
							continue;
						}
						POIInfo poi=new POIInfo();
						poi.setNid(poiFields[0]);
						if(!"null".equals(poiFields[1])&&!"".equals(poiFields[1])){
							poi.setCid(poiFields[1]);
						}
						poi.setCityName(poiFields[2]);
						if(poiFields[3].length() ==6 && !poiFields[3].endsWith("00"))
							poiFields[3] = poiFields[3].substring(0,4)+"00";
						
						poi.setCityCode(poiFields[3]);
						poi.setTitle(poiFields[4]);
						if(!"null".equals(poiFields[5])&&!"".equals(poiFields[5])){
							poi.setAddress(poiFields[5]);
						}
						if(!"null".equals(poiFields[6])&&!"".equals(poiFields[6])){
							poi.setPhone(poiFields[6]);
						}
						if(!"null".equals(poiFields[7])&&!"".equals(poiFields[7])){
							poi.setTypeName(poiFields[7]);
						}
						if(!"null".equals(poiFields[8])&&!"".equals(poiFields[8])){
							poi.setTypeCode(poiFields[8]);
						}
						poi.setLongitude(Double.parseDouble(poiFields[10]));
						poi.setLatitude(Double.parseDouble(poiFields[9]));
						if(!"null".equals(poiFields[11])&&!"".equals(poiFields[11])){
							String district = Cinema.regbcode.get(poiFields[11]);
							if(district!=null && district.length()>6)
							{
								int index = district.indexOf(poiFields[3].substring(0,4));
								if(index >-1)
									district = district.substring(index,index+6);
								else
								{
									index = district.indexOf(poiFields[3].substring(0,2));
									if(index >-1)
										district = district.substring(index,index+6);
									else
									{
										U.log.error("id:"+poiFields[0] +" district code 解析错误  distric:"+ district +" citycode:"+poiFields[3]);
										continue;
									}
								}
								
							}
							poi.setDistrict(district);
						}
						if(!"null".equals(poiFields[12])&&!"".equals(poiFields[12])){
							poi.setTag(poiFields[12]);
						}
						if(!"null".equals(poiFields[13])&&!"".equals(poiFields[13])){
							poi.setInvol(poiFields[13]);
						}
						if(!"null".equals(poiFields[14])&&!"".equals(poiFields[14])){
							poi.setBrand(poiFields[14]);
						}
						if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){

							poi.setConfidenceLevel(poiFields[15]);
						}
						if(!"null".equals(poiFields[16])&&!"".equals(poiFields[16])){
//							poi.setStrtimeoflastupdate(poiFields[16]);
							poi.setCtraffic(poiFields[16]);
						}
						if(!"null".equals(poiFields[17])&&!"".equals(poiFields[17])){
							poi.setSelf_info(poiFields[17]);
//							poi.setCdes(poiFields[17]);
						}
						if(!"null".equals(poiFields[18])&&!"".equals(poiFields[18])){
							poi.setDescription(poiFields[18]);

						}
						if(!"null".equals(poiFields[19])&&!"".equals(poiFields[19])){
//							poi.setNation(poiFields[19]);
							poi.setPhoto(poiFields[19]);
						}
						
						if("null".equals(poiFields[20])|| "".equals(poiFields[20]))
							poi.setWebUrl(poiFields[20]);
						
						this.writer.addPOI(poi);
					}
						
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				this.writer.finish();
				if(cr != null)
					try {
						cr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

			
		}
	}
	
	private String getTime(long time) {
		long takenTime=System.currentTimeMillis()-time;
		String processedTime="";
		if(3600000<takenTime){
			processedTime=(((float)takenTime)/3600000.0f)+"hours";
		}else if(60000<takenTime){
			processedTime=(((float)takenTime)/60000.0f)+"min";
		}else if(1000<takenTime){
			processedTime=(((float)takenTime)/1000.0f)+"sec";
		}else{
			processedTime=takenTime+" ms";
		}
		
		return processedTime;
	}
	
	private void delExitsfiles(File file)
	{
		if(!file.exists())
			return;
		if(file.isDirectory())
		{
			for(File f:file.listFiles())
			{
				this.delExitsfiles(f);
			}
			file.delete();
		}
		else
		{
			file.delete();
		}
	}
	
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	



}
