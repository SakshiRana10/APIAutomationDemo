package bright.api.alaya.pages.configServices;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import bright.api.alaya.utils.Constants;

public class CreateThreadPage {
	
	    public void divideIntoThreads() throws FileNotFoundException, InterruptedException
	    {
	    	
	    	ArrayList<String> result = new ArrayList<>();
			
			Scanner s = new Scanner(new FileReader(Constants.BrightRuleConfigMapping)); 
			while (s.hasNext()) {
				String ruleUsage=(s.next()).replaceAll(",", "").replaceAll("]", "").replaceAll("\\[", "");
				result.add(ruleUsage);
			}
			
			int chunkSize = 500;
	        AtomicInteger counter = new AtomicInteger();
	        final Collection<List<String>> partitionedList = 
	        		result.stream().collect(Collectors.groupingBy(i -> counter.getAndIncrement() / chunkSize))
	                            .values(); 
	        List<String>[] al = new List[partitionedList.size()];
	        
	        int i=0;
	        System.out.println(partitionedList.size());
	        for(List<String> subList : partitionedList ) {
	        	if( i< partitionedList.size()) {
	        		al[i]=subList;
	        	}
	        	i++;
	        }
	   
	        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(partitionedList.size());
	        
	        for (int x = 0; x < partitionedList.size(); x++) {
	            Runnable worker = new MainRuleThreadPage(al[x]);
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