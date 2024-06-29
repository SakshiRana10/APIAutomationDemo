package bright.api.alaya.pages.configServices;


import java.awt.SystemColor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import bright.api.alaya.utils.Constants;
import bright.api.alaya.utils.FileUtility;
import bright.api.alaya.utils.JsonUtility;

public class CreateThreadLogicalAttributeMappingPage {

	public void divideIntoThreads() throws InterruptedException, IOException
    {
    	
    	ArrayList<String> result = new ArrayList<>();
    	FileWriter file = new FileWriter(Constants.LOVMapping+"lclaMApping.json",false);
    	List<String> businessViewFiles = FileUtility.GetAllFilesFromDirectory(Constants.configBusinessView);
    	ArrayList<String> lovIds=new ArrayList();
		for (String businessViewFile : businessViewFiles) {
			if(businessViewFile.contains("DS_Store")) {

				continue;
			}
			JSONObject jsonObject = JsonUtility.GetJsonFileData(businessViewFile);
			lovIds.add(jsonObject.get("id").toString());
			
			}
		file.write(lovIds.toString());
		file.close();
		
		Scanner s = new Scanner(new FileReader(Constants.LOVMapping+"lclaMApping.json")); 
		while (s.hasNext()) {
			String ruleUsage=(s.next()).replaceAll(",", "").replaceAll("]", "").replaceAll("\\[", "");
			result.add(ruleUsage);
		}
		
		int chunkSize = 40;
        AtomicInteger counter = new AtomicInteger();
        final Collection<List<String>> partitionedList = 
        		result.stream().collect(Collectors.groupingBy(i -> counter.getAndIncrement() / chunkSize))
                            .values(); 
        List<String>[] al = new List[partitionedList.size()];
        
        int i=0;
        
        for(List<String> subList : partitionedList ) {
        	if( i< partitionedList.size()) {
        		al[i]=subList;
        		
        	}
        	i++;
        }
   
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(partitionedList.size());
        
        for (int x = 0; x < partitionedList.size(); x++) {
            Runnable worker = new LogicalAttributeRuleThreadPage(al[x]);
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
