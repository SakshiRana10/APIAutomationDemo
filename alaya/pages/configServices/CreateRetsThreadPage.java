package bright.api.alaya.pages.configServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class CreateRetsThreadPage {
	
	public void divideIntoThreads(String locale) throws InterruptedException, IOException
    {
	 ArrayList<String> typesList = new ArrayList<String>(Arrays.asList("METADATA-LOOKUP_TYPE","METADATA-TABLE","METADATA-SEARCH_HELP","METADATA-CLASS"));
	 ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
     for (int x = 0; x < 4; x++) {	
         Runnable worker = new MainRetsThreadPage(typesList.get(x),locale);
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

