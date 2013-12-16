package com.ctbri.srhcore.parsedata;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.ctbri.srhcore.U;

public class City {

	public static void parseCity() {
		

		String secret = "9EAECAA87375C808D4741003ADC4B448";
		String url = "http://qa.fun-guide.mobi:7005/v2/cities?";//城市列表
		String para = "key=LBS189CN";
		String sig = U.getMD5(para+secret);
		url +=para+"&sig="+sig;
		System.out.println(url);
		
		
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse hr = null;
		String result = "";

		try {

			hr = hc.execute(get);
			result = EntityUtils.toString(hr.getEntity());
			JSONObject srcobj = new JSONObject(result);
			System.err.println(srcobj.get("code"));
//			JSONArray arr = srcobj.getJSONArray("cinemas");
//			System.out.println(arr.length());
//			// url返回的查询结果，System.out.println(result);

			System.out.println(result);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
		}



	
	}
}
