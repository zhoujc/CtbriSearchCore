package com.ctbri.srhcore.index.maker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.ana.analysis.ctbri.CtbriAnalyzer.TokenizerModel;
import com.ctbri.srhcore.index.maker.IndexInfo.IndexMode;
import com.ctbri.srhcore.parsedata.Cinema;

public class IndexMaker {
	
	private ExecutorService service =Executors.newFixedThreadPool(4);
	private ExecutorService serviceStore =Executors.newFixedThreadPool(4);
	private IndexInfo indexInfo;
	private String csvPath;
	
	/**
	 * @param indexInfo
	 * @param csvPath csv源文件的存储的路径
	 */
	public IndexMaker(IndexInfo indexInfo,String csvPath)
	{
		this.indexInfo = indexInfo;
		this.csvPath = csvPath;
		this.delExitsfiles(new File(this.indexInfo.getIndexRootPath()));
		this.delExitsfiles(new File(this.indexInfo.getIndexStorePath()));
	}
	
	public void index()
	{
		long time = System.currentTimeMillis();
		File csvDir = new File(this.csvPath);
		if(!csvDir.exists() || !csvDir.isDirectory())
		{
			U.log.error(this.csvPath+" is not a dir, or not exits");
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
				this.service.execute(new IndexJob(csv));
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
		String indexpath = "./index";
		String indexstorepath = "./indexStore";
		String csvdirpath = "poi";
		if(args!= null && args.length>0)
		{
			indexpath = args[0];
			indexstorepath = args[1];
			csvdirpath = args[2];
		}
//		System.out.println(isNumeric("-12.45"));
//		IndexInfo indexInfo=new IndexInfo("./index",IndexMode.CITY,TokenizerModel.COMMON);
		IndexInfo indexInfo=new IndexInfo(indexpath,indexstorepath,IndexMode.PROVINCE,TokenizerModel.COMMON);
		IndexMaker index=new IndexMaker(indexInfo,csvdirpath);
		index.index();
		index.indexStorePOI();

	}
	
	public void indexStorePOI()
	{
		long time = System.currentTimeMillis();
		File csvDir = new File(this.csvPath);
		if(!csvDir.exists() || !csvDir.isDirectory())
		{
			U.log.error(this.csvPath+" is not a dir, or not exits");
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
				this.serviceStore.execute(new IndexStoreJob(csv));
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
	
	public class IndexStoreJob implements Runnable
	{

		String indexPath;
		CtbriIndexWriter writer;
		File cityCSVFile;
		public IndexStoreJob(File f)
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

					if(poiFields.length <20) 
					{
						U.log.info(poiFields[0]+" len is "+ poiFields.length);
						continue;
					}

					if(poiFields.length< 25)
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
						
						//
						if(poiFields.length>21)
						{
							try {
								if(!"null".equals(poiFields[21])&&!"".equals(poiFields[21]))
									poi.setFfavorite(poiFields[21]);
								
								if(!"null".equals(poiFields[22])&&!"".equals(poiFields[22]))
									poi.setFreviews(poiFields[22]);
								
								if(!"null".equals(poiFields[23])&&!"".equals(poiFields[23]))
									poi.setFpark(poiFields[23]);
								if(!"null".equals(poiFields[24])&&!"".equals(poiFields[24]))
									poi.setFroom(poiFields[24]);
								if(!"null".equals(poiFields[25])&&!"".equals(poiFields[25]))
									poi.setFrefer(poiFields[25]);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println(poiFields[0] +"  ssssssssssssss "+poiFields.length);
							}							
							
						}
						
//						if("null".equals(poiFields[20])|| "".equals(poiFields[20]))
//							poi.setHit(0);
//						else
//							poi.setHit(Integer.parseInt(poiFields[20]));
//						
//						if("null".equals(poiFields[21]) || "".equals(poiFields[21]))
//							poi.setPointWeight(0);
//						else
//							poi.setPointWeight(Integer.parseInt(poiFields[21]));
//						
//						if("null".equals(poiFields[22]) || "".equals(poiFields[22]))
//							poi.setPointWeightOfCity(0);
//						else
//							poi.setPointWeightOfCity(Integer.parseInt(poiFields[22]));
						this.writer.addPOIStore(poi);
					}
					else if(poiFields.length >= 35)
					{
						if(!isNumeric(poiFields[10]) || !isNumeric(poiFields[9]))
						{
							U.log.info(poiFields[0] +" lon or lat is not num");
							continue;
						}
						POIFood poi = new POIFood();
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
							poi.setDistrict(poiFields[11]);
						}
						if(!"null".equals(poiFields[12])&&!"".equals(poiFields[12])){
							poi.setTag(poiFields[12]);//01
						}
						
						if(!"null".equals(poiFields[13])&&!"".equals(poiFields[13])){
							poi.setInvol(poiFields[13]);
						}
						if(!"null".equals(poiFields[14])&&!"".equals(poiFields[14])){
							poi.setBrand(poiFields[14]);
						}
						
						if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){
							poi.setAvgPerCost(poiFields[15]);
						}
						
						
						if(!"null".equals(poiFields[16])&&!"".equals(poiFields[16])){
							poi.setTrafficRoute(poiFields[16]);
						}
						if(!"null".equals(poiFields[17])&&!"".equals(poiFields[17])){
							poi.setIntro(poiFields[17]);
						}
						if(!"null".equals(poiFields[19])&&!"".equals(poiFields[19])){
							poi.setHeadPic(poiFields[19]);
						}
						
						if(!"null".equals(poiFields[21])&&!"".equals(poiFields[21])){
							poi.setSpecialty(poiFields[21]);
						}
						if(!"null".equals(poiFields[22])&&!"".equals(poiFields[22])){
							poi.setComment(poiFields[22]);
						}
						if(!"null".equals(poiFields[23])&&!"".equals(poiFields[23])){
							poi.setIsPark(poiFields[23]);
						}
						if(!"null".equals(poiFields[24])&&!"".equals(poiFields[24])){
							poi.setIspack(poiFields[24]);
						}
						if(!"null".equals(poiFields[25])&&!"".equals(poiFields[25])){
							poi.setSource(poiFields[25]);
						}
						if(!"null".equals(poiFields[26])&&!"".equals(poiFields[26])){
							poi.setIsBookingStatus(poiFields[26]);
						}
						
						if(!"null".equals(poiFields[28])&&!"".equals(poiFields[28])){
							poi.setRecommendValue(poiFields[28]);
						}
						if(!"null".equals(poiFields[29])&&!"".equals(poiFields[29])){
							poi.setZcDisount(poiFields[29]);
						}
						if(!"null".equals(poiFields[30])&&!"".equals(poiFields[30])){
							poi.setFtDiscount(poiFields[30]);
						}
						if(!"null".equals(poiFields[31])&&!"".equals(poiFields[31])){
							poi.setAres(poiFields[31]);
						}
						if(!"null".equals(poiFields[32])&&!"".equals(poiFields[32])){
							poi.setMsId(poiFields[32]);
						}
						if(!"null".equals(poiFields[33])&&!"".equals(poiFields[33])){
							poi.setMsDiscount(poiFields[33]);
						}
						if(!"null".equals(poiFields[34])&&!"".equals(poiFields[34])){
							poi.setMsIsFree(poiFields[34]);
						}
						if(poiFields.length>35)
						{
							if(!"null".equals(poiFields[35])&&!"".equals(poiFields[35])){
								poi.setIsSupportCard(poiFields[35]);
							}
						}
						
						
						
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
	
	
	public class IndexJob implements Runnable
	{
		String indexPath;
		CtbriIndexWriter writer;
		File cityCSVFile;
		public IndexJob(File f)
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

					
					if(poiFields.length <= 22)
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
						
						if("null".equals(poiFields[20])|| "".equals(poiFields[20]))
							poi.setWebUrl(poiFields[20]);
						
						this.writer.addPOI(poi);
					}
					
					if(poiFields[0].equals("12255761"))
						U.log.info("12255761                "+poiFields.length);
					
//					select  id,bid,city,cityCode,name,address,phone,poiTypeName,poiTypeCodeNew,lat,lon,districtName,"01",points,"",avgPerCost,trafficRoute,intro,"",headPic,"",specialty,comment,isPark,ispack,source,
//					isBookingStatus,"",recommendValue,zcDiscount,ftDiscount,area,msId,msDiscount,msIsfree from canyin_poi
//					tag // index 第12
//					points,
//					"",
//					avgPerCost,
//					trafficRoute,
//					intro,
//					"",
//					headPic,
//					"",
//					specialty,
//					comment,
//					isPark,
//					ispack,
//					source,
//					isBookingStatus,
//					"",
//					recommendValue,
//					zcDiscount,
//					ftDiscount,
//					area,
//					msId,
//					msDiscount,
//					msIsfree

					else if(poiFields.length >= 35)
					{
						if(!isNumeric(poiFields[10]) || !isNumeric(poiFields[9]))
						{
							U.log.info(poiFields[0] +" lon or lat is not num");
							continue;
						}
						POIFood poi = new POIFood();
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
//						if(!"null".equals(poiFields[11])&&!"".equals(poiFields[11])){
//							poi.setDistrict(poiFields[11]);
//						}
						if(!"null".equals(poiFields[11])&&!"".equals(poiFields[11])){
							String district = Cinema.regbcode.get(poiFields[11]);
							if(district != null)
							{
								if(district.length()>6)
								{
									int index = district.indexOf(poiFields[3].substring(0,4));
									if(index >0)
										district = district.substring(index,index+6);
									else
									{
										U.log.error("id:"+poiFields[0] +" district code 解析错误");
										continue;
									}
								}
								poi.setDistrict(district);
							}							
						}
						if(!"null".equals(poiFields[12])&&!"".equals(poiFields[12])){
							poi.setTag(poiFields[12]);//01
						}
						
						if(!"null".equals(poiFields[13])&&!"".equals(poiFields[13])){
							poi.setInvol(poiFields[13]);
						}
						if(!"null".equals(poiFields[14])&&!"".equals(poiFields[14])){
							poi.setBrand(poiFields[14]);
						}
						
						if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){
							poi.setAvgPerCost(poiFields[15]);
						}
						
						if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){
							poi.setAvgPerCost(poiFields[15]);
						}
						if(!"null".equals(poiFields[16])&&!"".equals(poiFields[16])){
							poi.setTrafficRoute(poiFields[16]);
						}
						if(!"null".equals(poiFields[17])&&!"".equals(poiFields[17])){
							poi.setIntro(poiFields[17]);
						}
						if(!"null".equals(poiFields[19])&&!"".equals(poiFields[19])){
							poi.setHeadPic(poiFields[19]);
						}
						
						if(!"null".equals(poiFields[21])&&!"".equals(poiFields[21])){
							poi.setSpecialty(poiFields[21]);
						}
						if(!"null".equals(poiFields[22])&&!"".equals(poiFields[22])){
							poi.setComment(poiFields[22]);
						}
						if(!"null".equals(poiFields[23])&&!"".equals(poiFields[23])){
							poi.setIsPark(poiFields[23]);
						}
						if(!"null".equals(poiFields[24])&&!"".equals(poiFields[24])){
							poi.setIspack(poiFields[24]);
						}
						if(!"null".equals(poiFields[25])&&!"".equals(poiFields[25])){
							poi.setSource(poiFields[25]);
						}
						if(!"null".equals(poiFields[26])&&!"".equals(poiFields[26])){
							poi.setIsBookingStatus(poiFields[26]);
						}
						
						if(!"null".equals(poiFields[28])&&!"".equals(poiFields[28])){
							poi.setRecommendValue(poiFields[28]);
						}
						if(!"null".equals(poiFields[29])&&!"".equals(poiFields[29])){
							poi.setZcDisount(poiFields[29]);
						}
						if(!"null".equals(poiFields[30])&&!"".equals(poiFields[30])){
							poi.setFtDiscount(poiFields[30]);
						}
						if(!"null".equals(poiFields[31])&&!"".equals(poiFields[31])){
							poi.setAres(poiFields[31]);
						}
						if(!"null".equals(poiFields[32])&&!"".equals(poiFields[32])){
							poi.setMsId(poiFields[32]);
						}
						if(!"null".equals(poiFields[33])&&!"".equals(poiFields[33])){
							poi.setMsDiscount(poiFields[33]);
						}
						if(!"null".equals(poiFields[34])&&!"".equals(poiFields[34])){
							poi.setMsIsFree(poiFields[34]);
						}
						if(poiFields.length>35)
						{
							if(!"null".equals(poiFields[35])&&!"".equals(poiFields[35])){
								poi.setIsSupportCard(poiFields[35]);
							}
						}
						
						
						
						
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
//			List<String[]> poiList=null;
//			try {
//				poiList = cr.readAll();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			for(String[] poiFields:poiList){
//				
//				if(poiFields.length <= 22)
//				{
//					if(!isNumeric(poiFields[10]) || !isNumeric(poiFields[9]))
//					{
//						U.log.info(poiFields[0] +" lon or lat is not num");
//						continue;
//					}
//					POIInfo poi=new POIInfo();
//					poi.setNid(poiFields[0]);
//					if(!"null".equals(poiFields[1])&&!"".equals(poiFields[1])){
//						poi.setCid(poiFields[1]);
//					}
//					poi.setCityName(poiFields[2]);
//					poi.setCityCode(poiFields[3]);
//					poi.setTitle(poiFields[4]);
//					if(!"null".equals(poiFields[5])&&!"".equals(poiFields[5])){
//						poi.setAddress(poiFields[5]);
//					}
//					if(!"null".equals(poiFields[6])&&!"".equals(poiFields[6])){
//						poi.setPhone(poiFields[6]);
//					}
//					if(!"null".equals(poiFields[7])&&!"".equals(poiFields[7])){
//						poi.setTypeName(poiFields[7]);
//					}
//					if(!"null".equals(poiFields[8])&&!"".equals(poiFields[8])){
//						poi.setTypeCode(poiFields[8]);
//					}
//					poi.setLongitude(Double.parseDouble(poiFields[10]));
//					poi.setLatitude(Double.parseDouble(poiFields[9]));
//					if(!"null".equals(poiFields[11])&&!"".equals(poiFields[11])){
//						poi.setDistrict(poiFields[11]);
//					}
//					if(!"null".equals(poiFields[12])&&!"".equals(poiFields[12])){
//						poi.setTag(poiFields[12]);
//					}
//					if(!"null".equals(poiFields[13])&&!"".equals(poiFields[13])){
//						poi.setInvol(poiFields[13]);
//					}
//					if(!"null".equals(poiFields[14])&&!"".equals(poiFields[14])){
//						poi.setBrand(poiFields[14]);
//					}
//					if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){
//						poi.setConfidenceLevel(poiFields[15]);
//					}
//					if(!"null".equals(poiFields[16])&&!"".equals(poiFields[16])){
////						poi.setStrtimeoflastupdate(poiFields[16]);
//						poi.setCtraffic(poiFields[16]);
//					}
//					if(!"null".equals(poiFields[17])&&!"".equals(poiFields[17])){
////						poi.setSelf_info(poiFields[17]);
//						poi.setCdes(poiFields[17]);
//					}
//					if(!"null".equals(poiFields[18])&&!"".equals(poiFields[18])){
////						poi.setProvince(poiFields[18]);
//						poi.setCspeicalDes(poiFields[18]);
//					}
//					if(!"null".equals(poiFields[19])&&!"".equals(poiFields[19])){
////						poi.setNation(poiFields[19]);
//						poi.setPhoto(poiFields[19]);
//					}
//					
//					if("null".equals(poiFields[20])|| "".equals(poiFields[20]))
//						poi.setWebUrl(poiFields[20]);
//					
//					this.writer.addPOI(poi);
//				}
//				
////				select  id,bid,city,cityCode,name,address,phone,poiTypeName,poiTypeCodeNew,lat,lon,districtName,"01",points,"",avgPerCost,trafficRoute,intro,"",headPic,"",specialty,comment,isPark,ispack,source,
////				isBookingStatus,"",recommendValue,zcDiscount,ftDiscount,area,msId,msDiscount,msIsfree from canyin_poi
////				tag // index 第12
////				points,
////				"",
////				avgPerCost,
////				trafficRoute,
////				intro,
////				"",
////				headPic,
////				"",
////				specialty,
////				comment,
////				isPark,
////				ispack,
////				source,
////				isBookingStatus,
////				"",
////				recommendValue,
////				zcDiscount,
////				ftDiscount,
////				area,
////				msId,
////				msDiscount,
////				msIsfree
//
//				else if(poiFields.length == 35)
//				{
//					if(!isNumeric(poiFields[10]) || !isNumeric(poiFields[9]))
//					{
//						U.log.info(poiFields[0] +" lon or lat is not num");
//						continue;
//					}
//					POIFood poi = new POIFood();
//					poi.setNid(poiFields[0]);
//					if(!"null".equals(poiFields[1])&&!"".equals(poiFields[1])){
//						poi.setCid(poiFields[1]);
//					}
//					poi.setCityName(poiFields[2]);
//					poi.setCityCode(poiFields[3]);
//					poi.setTitle(poiFields[4]);
//					if(!"null".equals(poiFields[5])&&!"".equals(poiFields[5])){
//						poi.setAddress(poiFields[5]);
//					}
//					if(!"null".equals(poiFields[6])&&!"".equals(poiFields[6])){
//						poi.setPhone(poiFields[6]);
//					}
//					if(!"null".equals(poiFields[7])&&!"".equals(poiFields[7])){
//						poi.setTypeName(poiFields[7]);
//					}
//					if(!"null".equals(poiFields[8])&&!"".equals(poiFields[8])){
//						poi.setTypeCode(poiFields[8]);
//					}
//					poi.setLongitude(Double.parseDouble(poiFields[10]));
//					poi.setLatitude(Double.parseDouble(poiFields[9]));
//					if(!"null".equals(poiFields[11])&&!"".equals(poiFields[11])){
//						poi.setDistrict(poiFields[11]);
//					}
//					if(!"null".equals(poiFields[12])&&!"".equals(poiFields[12])){
//						poi.setTag(poiFields[12]);//01
//					}
//					
//					if(!"null".equals(poiFields[13])&&!"".equals(poiFields[13])){
//						poi.setInvol(poiFields[13]);
//					}
//					if(!"null".equals(poiFields[14])&&!"".equals(poiFields[14])){
//						poi.setBrand(poiFields[14]);
//					}
//					
//					if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){
//						poi.setAvgPerCost(poiFields[15]);
//					}
//					
//					if(!"null".equals(poiFields[15])&&!"".equals(poiFields[15])){
//						poi.setAvgPerCost(poiFields[15]);
//					}
//					if(!"null".equals(poiFields[16])&&!"".equals(poiFields[16])){
//						poi.setTrafficRoute(poiFields[16]);
//					}
//					if(!"null".equals(poiFields[17])&&!"".equals(poiFields[17])){
//						poi.setIntro(poiFields[17]);
//					}
//					if(!"null".equals(poiFields[19])&&!"".equals(poiFields[19])){
//						poi.setHeadPic(poiFields[19]);
//					}
//					
//					if(!"null".equals(poiFields[21])&&!"".equals(poiFields[21])){
//						poi.setSpecialty(poiFields[21]);
//					}
//					if(!"null".equals(poiFields[22])&&!"".equals(poiFields[22])){
//						poi.setComment(poiFields[22]);
//					}
//					if(!"null".equals(poiFields[23])&&!"".equals(poiFields[23])){
//						poi.setIsPark(poiFields[23]);
//					}
//					if(!"null".equals(poiFields[24])&&!"".equals(poiFields[24])){
//						poi.setIspack(poiFields[24]);
//					}
//					if(!"null".equals(poiFields[25])&&!"".equals(poiFields[25])){
//						poi.setSource(poiFields[25]);
//					}
//					if(!"null".equals(poiFields[26])&&!"".equals(poiFields[26])){
//						poi.setIsBookingStatus(poiFields[26]);
//					}
//					
//					if(!"null".equals(poiFields[28])&&!"".equals(poiFields[28])){
//						poi.setRecommendValue(poiFields[28]);
//					}
//					if(!"null".equals(poiFields[29])&&!"".equals(poiFields[29])){
//						poi.setZcDisount(poiFields[29]);
//					}
//					if(!"null".equals(poiFields[30])&&!"".equals(poiFields[30])){
//						poi.setFtDiscount(poiFields[30]);
//					}
//					if(!"null".equals(poiFields[31])&&!"".equals(poiFields[31])){
//						poi.setAres(poiFields[31]);
//					}
//					if(!"null".equals(poiFields[32])&&!"".equals(poiFields[32])){
//						poi.setMsId(poiFields[32]);
//					}
//					if(!"null".equals(poiFields[33])&&!"".equals(poiFields[33])){
//						poi.setMsDiscount(poiFields[33]);
//					}
//					if(!"null".equals(poiFields[34])&&!"".equals(poiFields[34])){
//						poi.setMsIsFree(poiFields[34]);
//					}
//					
//					
//					this.writer.addPOI(poi);
//				
//				}
//					
//					
//					
////					if("null".equals(poiFields[20])|| "".equals(poiFields[20]))
////						poi.setHit(0);
////					else
////						poi.setHit(Integer.parseInt(poiFields[20]));
////					
////					if("null".equals(poiFields[21]) || "".equals(poiFields[21]))
////						poi.setPointWeight(0);
////					else
////						poi.setPointWeight(Integer.parseInt(poiFields[21]));
////					
////					if("null".equals(poiFields[22]) || "".equals(poiFields[22]))
////						poi.setPointWeightOfCity(0);
////					else
////						poi.setPointWeightOfCity(Integer.parseInt(poiFields[22]));
//					
//				}
			
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
