package bright.api.alaya.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphQlQueries extends MainClassAlaya {
	
	public static HashMap<String, String> graphQlMethods = new HashMap<String,String>(){{
        put("listing", "getListings");
        put("office","getOffices");
        put("member", "getMembers");
        put("countyrate","getCountyRates");
        put("taxrecord","getTaxRecords");
        put("city","getCities");
        put("subdivision","getSubdivisions");
        put("builder_model","getBuilderModels");
        put("building_name","getBuildingNames");
        put("team","getTeams");
        put("wrong","wrong");
    }};
    
    
    public static String graphQlQuery(String queryParam, String value, ArrayList<String> returnFields, String documentType) {
        
        String queries = "";
        
        String graphQlMethod = graphQlMethods.get(documentType);
        
        queries += String.format("  findExact: { %s: { value:\\\"%s\\\" } }  ", queryParam, value );
            
        String fields = "";
        
        for (int field = 0 ; field<returnFields.size() ; field++) {
            fields += returnFields.get(field) + " ";
        }
        
        
        return "{\"query\":\"{ "
                + graphQlMethod+"( "
                + "query: { "
                + queries
                + " } "
                + ") { "
                + "returnFields { "
                + fields
                + "} "
                + "}"
                + "}\",\"variables\":{}}";
    }
    
 public static String graphQlRangeQuery(ArrayList<String> returnFields, String documentType, String gt, String lt) {
        
        String queries = "";
        
        String graphQlMethod = graphQlMethods.get(documentType);
        
        queries += String.format("  range: { listingId: {  gt: \\\"%s\\\", lt: \\\"%s\\\" } }  ", gt,lt );
        
        String fields = "";
        
        for (int field = 0 ; field<returnFields.size() ; field++) {
            fields += returnFields.get(field) + " ";
        }
        
        return "{\"query\":\"{ "
        + graphQlMethod+"( "
        + "query: { "
        + queries
        + " } "
        + ") { "
        + "returnFields { "
        + fields
        + "} "
        + "}"
        + "}\",\"variables\":{}}";
      
    }
 
    public static String graphQlConditinalQuery(ArrayList<String> returnFields, String documentType, String value1, String value2) {
     
     String queries = "";
     
     String graphQlMethod = graphQlMethods.get(documentType);
     
     queries += String.format( " conditions: { or: [{find: {officeMlsId: { query: \\\"%s\\\" }}}{find: {officeMlsId: { query: \\\"%s\\\" }}}]}",value1,value2);
     
     String fields = "";
     
     for (int field = 0 ; field<returnFields.size() ; field++) {
         fields += returnFields.get(field) + " ";
     }
     
     return "{\"query\":\"{ "
     + graphQlMethod+"( "
     + "query: { "
     + queries
     + " } "
     + ") { "
     + "returnFields { "
     + fields
     + "} "
     + "}"
     + "}\",\"variables\":{}}";
     
    }
    
    public static String graphQlNestedQuery(String value, ArrayList<String> returnFields, String documentType) {
        
        String queries = "";
        
        String graphQlMethod = graphQlMethods.get(documentType);
        
        queries += String.format( " nested:{path: \\\"permissions\\\", query: {findExact:{permissions__partyPermPermissionType__key: {value:\\\"%s\\\"}}}}", value);
        
        String fields = "";
        
        for (int field = 0 ; field<returnFields.size() ; field++) {
            fields += returnFields.get(field) + " ";
        }
        
        return "{\"query\":\"{ "
        + graphQlMethod+"( "
        + "query: { "
        + queries
        + " } "
        + ") { "
        + "returnFields { "
        + fields
        + "} "
        + "}"
        + "}\",\"variables\":{}}";
        
       }
    
    
    public static String getListingKeyFromStatusAndPropertyType( ArrayList<String> returnFields, String documentType,String status, String propertyType) {
	    
	    String queries = "";
	    
	    String graphQlMethod = graphQlMethods.get(documentType);
	    
	    queries = String.format("conditions: {and: [{find: {mlsStatus__key: {query: \\\"%s\\\"}}}, {find: {propertyType__key: {query: \\\"%s\\\"}}}]}"
	    		,status,propertyType);
	        
	
	    	
     
	    String fields = "";
	    
	    for (int field = 0 ; field<returnFields.size() ; field++) {
	        fields += returnFields.get(field) + " ";
	    }
		
	    
	    return "{\"query\":\"{ "
	            + graphQlMethod+"( "
	            + "query: { "
	            + queries
	            + " } "
	            + ") { "
	            + "returnFields { "
	            + fields
	            + "} "
	            + "}"
	            + "}\",\"variables\":{}}";
	}



     public static String getDocTypeKeys(ArrayList<String> returnFields,String documentType) {
	 String graphQlMethod = graphQlMethods.get(documentType);
	    
	  String fields = "";
	    
	    for (int field = 0 ; field<returnFields.size() ; field++) {
	        fields += returnFields.get(field) + " ";
	    }
	
	    return "{\"query\":\"{ "
        + graphQlMethod
        + " { "
        + "returnFields { "
        + fields
        + "} "
        + "}"
        + "}\",\"variables\":{}}";
}

     public static String getCitykeyUsingCountyValue( ArrayList<String> returnFields, String documentType,String city, String county) {
 	    
 	    String queries = "";
 	    
 	    String graphQlMethod = graphQlMethods.get(documentType);
 	    
 	   queries=String.format("conditions: {and: [{find: {ctyCityName: {query: \\\"%s\\\"}}}, {find: {ctyCityCounty__key: {query: \\\"%s\\\"}}}]}  "
 	    		,city,county);
 	    	
      
 	    String fields = "";
 	    
 	    for (int field = 0 ; field<returnFields.size() ; field++) {
 	        fields += returnFields.get(field) + " ";
 	    }
 		
 	    
 	    return "{\"query\":\"{ "
 	            + graphQlMethod+"( "
 	            + "query: { "
 	            + queries
 	            + " } "
 	            + ") { "
 	            + "returnFields { "
 	            + fields
 	            + "} "
 	            + "}"
 	            + "}\",\"variables\":{}}";
 	}
     
     public static String getMemberKeyUsingMlsId(String value, ArrayList<String> returnFields, String documentType) {
         
         String queries = "";
         
         String graphQlMethod = graphQlMethods.get(documentType);
         
         queries += String.format("  find: { memberMlsId: { query:\\\"%s\\\" } }  ",value );
             
         String fields = "";
         
         for (int field = 0 ; field<returnFields.size() ; field++) {
             fields += returnFields.get(field) + " ";
         }
         
         
         return "{\"query\":\"{ "
                 + graphQlMethod+"( "
                 + "query: { "
                 + queries
                 + " } "
                 + ") { "
                 + "returnFields { "
                 + fields
                 + "} "
                 + "}"
                 + "}\",\"variables\":{}}";
     }
	
 public static String getListingsForTrueOfficeExclusiveYN(ArrayList<String> returnFields, String documentType) {
         
         String queries = "";
         
         String graphQlMethod = graphQlMethods.get(documentType);
         
         queries += String.format("   find: { officeExclusiveYN: { query: \\\"true\\\" } }  ");
             
         String fields = "";
         
         for (int field = 0 ; field<returnFields.size() ; field++) {
             fields += returnFields.get(field) + " ";
         }
         
         
         return "{\"query\":\"{ "
                 + graphQlMethod+"( "
                 + "query: { "
                 + queries
                 + " } "
                 + ") { "
                 + "returnFields { "
                 + fields
                 + "} "
                 + "}"
                 + "}\",\"variables\":{}}";
     }
 
 public static String nestedGraphQlQueryForLAE(String queryParam,String nestedKey, String value, String secondAttribute, ArrayList<String> returnFields, String documentType) {
	 String queries = "";
     
     String graphQlMethod = graphQlMethods.get(documentType);
     
     queries=String.format("conditions: {and: [{find: { %s : {query: \\\"%s\\\"}}}, {find: { %s : {query: \\\"%s\\\"}}}]}  "
	    		,queryParam,value,nestedKey,secondAttribute);
         
     String fields = "";
     
     for (int field = 0 ; field<returnFields.size() ; field++) {
         fields += returnFields.get(field) + " ";
     }
     
     
     return "{\"query\":\"{ "
             + graphQlMethod+"( "
             + "query: { "
             + queries
             + " } "
             + ") { "
             + "returnFields { "
             + fields
             + "} "
             + "}"
             + "}\",\"variables\":{}}";
 
	 
 }
	
}
    
