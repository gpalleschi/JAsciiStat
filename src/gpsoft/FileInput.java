package gpsoft;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileInput {
  String fileName;
  List<Stat> lStat;
  
  protected String getFileName() {
	return fileName;
  }

  protected void setFileName(String fileName) {
	this.fileName = fileName;
  }

  void Elab() throws IOException {
	  String line;
	  BufferedReader reader;
	  try
	  {	    
	    reader = new BufferedReader(new FileReader(fileName));
	    while ((line = reader.readLine()) != null)
	    {
//	    	System.out.println("DEBUG : record <" + line + ">");
	    	// Loop for Stats
	    	for (Stat stat : lStat) {
	    		// All Cleaner
	    		stat.checkCleaner(line);
	    		if ( stat.cleanersAreAllOk() ) {
//	    	        System.out.println("DEBUG : Cleaners All");
	    			stat.addKey();
//	    	        System.out.println("DEBUG : AddKey <" + stat.getCurrentKey() + ">");
	    			stat.setCurrentKey("");
	    			stat.initializeConds();
	    		}
	    		// All Increment
	    	    stat.setIncrements(line);	
	    		// All Condition
	    		stat.checkConditions(line);
//    	        System.out.println("DEBUG : Check Conditions");
	    		// If All Condition are true calculate dimensions
	    		if ( stat.conditionsAreAllOk() ) {
//    	        System.out.println("DEBUG : All Conditions OK ");
	    		   stat.setDimensions(line);	
//    	        System.out.println("DEBUG : Set Dimensions ");
	    		   if ( stat.getCurrentKey().length() > 0 ) {
//    	               System.out.println("DEBUG : CalcMetrics");
	    			   stat.calcMetrics(line);
	    		   }
	    		}
	    		
	    	}	    	
	    }
	    reader.close();
	    // Loop for Stats last valorizations
	    for (Stat stat : lStat) {
	    	stat.addKey();	
	    }
	    
	  }  
	  catch (Exception e)
	  {
	    System.err.format("\nException occurred trying to read '%s' error : " + e.toString(), fileName);
	    e.printStackTrace();
	    return;
	  }
  }

  public FileInput(String fileName, List<Stat> lStat) {
	super();
	this.fileName = fileName;
	this.lStat = lStat;
  }
  
  
}
