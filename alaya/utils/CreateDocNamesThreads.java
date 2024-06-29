package bright.api.alaya.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import io.restassured.specification.RequestSpecification;

public class CreateDocNamesThreads {
	
	public void divideIntoThreads(String contentObj) throws InterruptedException, IOException
    {
     RequestSpecification httpRequest = null;
     int limit = 10;
	 if(contentObj.equalsIgnoreCase("lmsObject")) 
	 {
		 ArrayList<String> docTypeList = new ArrayList<String>(Arrays.asList("listing","member","office","city","subdivision","builder_model","building_name","team"));	 
		 ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(7);
	     for (int x = 0; x < 8; x++) {	
	         Runnable worker = new MainDocNamesThreads(httpRequest,docTypeList.get(x),contentObj,limit);
	         executor.execute(worker);
	     }
	     
	     while(executor.getActiveCount()!=0)
	     {
	         try {
	             Thread.sleep(100);
	         } catch (InterruptedException e) {
	             e.printStackTrace();
	         }
	     }
	 executor.shutdown(); 
	 }
	 else if(contentObj.equalsIgnoreCase("items")) 
	 {
		 ArrayList<String> docTypeList = new ArrayList<String>(Arrays.asList("countyrate","taxrecord"));	 
		 ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
	     for (int x = 0; x < 2; x++) {	
	         Runnable worker = new MainDocNamesThreads(httpRequest,docTypeList.get(x),contentObj,limit);
	         executor.execute(worker);
	     }
	     
	     while(executor.getActiveCount()!=0)
	     {
	         try {
	             Thread.sleep(100);
	         } catch (InterruptedException e) {
	             e.printStackTrace();
	         }
	     }
	 executor.shutdown();
	 }
}
}