package com.ctbri.srhcore.parsedata.himovie;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ctbri.srhcore.U;
import com.ctbri.srhcore.parsedata.CinemaC;

public class HimovieCinema {
	
	public static String filepath = CinemaC.Fetch_cinema_dir+"/resource/himoviecsv/";
	
	public static String url_himovie = "http://ticket.api.beta.himovie.com/Ticket_API.asmx";
	
	public static String movie_tag = "cinema_last";
	
	public static String secret_himovie = "123456";
	public static String key_himovie = "Test";
	
	public static void getCityList()
	{
		long time = System.currentTimeMillis();
		String para = "apiCode="+key_himovie+"&networkID=0&time="+time ;
		String url = url_himovie+"cityList.html?";
		String sig = U.getMD5(key_himovie+0+time+secret_himovie);
		
		url+= para+"&cryptograph="+sig;
		U.log.debug(url);
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";
	}
	
	public static void main(String[] args) {
		getCityList();
	}

}
