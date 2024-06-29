package bright.api.alaya.utils;

import io.restassured.specification.RequestSpecification;


public class MainDocNamesThreads extends MainClassAlaya implements Runnable {
	
	private RequestSpecification HttpRequest;
	private String doctype;
	private String contentObj;
	private int Limit;
	
	 public MainDocNamesThreads(RequestSpecification httpRequest,String docType, String contentObject,int limit)
	    {
		 HttpRequest = httpRequest;
		 doctype = docType;
		 contentObj = contentObject; 
		 Limit = limit;
	    }
	  
	 public void run() {
			CommonUtilityMethods.fetchDocuments(this.HttpRequest, this.doctype, this.Limit,this.contentObj);
	  }
}
