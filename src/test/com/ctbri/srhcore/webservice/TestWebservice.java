package test.com.ctbri.srhcore.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.xml.soap.SOAPException;
import javax.xml.ws.Response;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.junit.Test;

public class TestWebservice {

	@Test
	public void test() 
	{
		try {
            String endpoint = "http://localhost:8080/ca3/services/caSynrochnized?wsdl";
            //直接引用远程的wsdl文件
           //以下都是套路 
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("addUser");//WSDL里面描述的接口名称
            call.addParameter("userName", org.apache.axis.encoding.XMLType.XSD_DATE,
                          javax.xml.rpc.ParameterMode.IN);//接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
            String temp = "测试人员";
            String result = (String)call.invoke(new Object[]{temp});
            //给方法传递参数，并且调用方法
            System.out.println("result is "+result);
     }
     catch (Exception e) {
            System.err.println(e.toString());
     }
	}

//	@Test
//	public void testSOAP()
//	{
//		URL url = null;
//	       try {
//	           url=new URL("http://192.168.0.100:8080/ca3/services/caSynrochnized");
//	       } catch (MalformedURLException mue) {
//	          return mue.getMessage();
//	         }
//	             // This is the main SOAP object
//	       Call soapCall = new Call();
//	       // Use SOAP encoding
//	       soapCall.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
//	       // This is the remote object we're asking for the price
//	       soapCall.setTargetObjectURI("urn:xmethods-caSynrochnized");
//	       // This is the name of the method on the above object
//	       soapCall.setMethodName("getUser");
//	       // We need to send the ISBN number as an input parameter to the method
//	       Vector soapParams = new Vector();
//	 
//	       // name, type, value, encoding style
//	       Parameter isbnParam = new Parameter("userName", String.class, user, null);
//	       soapParams.addElement(isbnParam);
//	       soapCall.setParams(soapParams);
//	       try {
//	          // Invoke the remote method on the object
//	          Response soapResponse = soapCall.invoke(url,"");
//	          // Check to see if there is an error, return "N/A"
//	          if (soapResponse.generatedFault()) {
//	              Fault fault = soapResponse.getFault();
//	             String f = fault.getFaultString();
//	             return f;
//	          } else {
//	             // read result
//	             Parameter soapResult = soapResponse.getReturnValue ();
//	             // get a string from the result
//	             return soapResult.getValue().toString();
//	          }
//	       } catch (SOAPException se) {
//	          return se.getMessage();
//	       }
//	}
}
