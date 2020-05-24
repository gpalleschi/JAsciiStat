package gpsoft;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileStat {

	private boolean bError;

	protected boolean isbError() {
		return bError;
	}

	protected void setbError(boolean bError) {
		this.bError = bError;
	}
	
	public FileStat(String fileStat, List<Stat> lStat, String sepFileStat) throws IOException {
          String labelMetric;
          int    iMetric = 0;
          String labelIncrement;
          int    iIncrement = 0;
  	      setbError(false);
  	      lStat.add(new Stat(fileStat));
 		  try
		  {
		    BufferedReader reader = new BufferedReader(new FileReader(fileStat));
		    String line;
		    while ((line = reader.readLine()) != null)
		    {
		      String[] parts = line.split(sepFileStat);
		      if ( parts.length < 2 ) {
		    	  System.out.println("\n Error on file <" + fileStat + "> record <" + line + "> not in correct format."); 
		    	  setbError(true);
		    	  break;
		      }
		      // Dimensions
		      if ( parts[0].compareTo("D") == 0 ) {
		    	 if ( parts.length < 3 ) {
		    	  System.out.println("\n Error on file <" + fileStat + "> record <" + line + "> not in correct format for type D needs 3 values."); 
		    	  setbError(true);
		    	  break;
		    	 }
		    	 lStat.get(lStat.size()-1).addDimension(new Dimension(parts[1], parts[2])); 
		      }
		      // Cleaners
		      if ( parts[0].compareTo("Z") == 0 ) {
		    	 lStat.get(lStat.size()-1).addCleaner(new Cleaner(parts[1])); 
		      }
		      // Conditions
		      if ( parts[0].compareTo("C") == 0 ) {
		    	 lStat.get(lStat.size()-1).addCondition(new Condition(parts[1])); 
		      }
		      // Counters
		      if ( parts[0].compareTo("I") == 0 ) {
		    	 if ( parts.length > 2 ) {
			           if ( parts[2].length() > 0 ) {
			        	   labelIncrement = parts[2];  
			           } else {
			        	   labelIncrement = "INCREMENT_" + String.valueOf(iIncrement++);  
			           }
			    	 } else {
		        	   labelIncrement = "INCREMENT_" + String.valueOf(iIncrement++);  
		    	 }
		    	 lStat.get(lStat.size()-1).addIncrement(new Increment(labelIncrement,parts[1]));
		      }
		      
		      // Metrics
		      if ( parts[0].compareTo("M") == 0 ) {
		    	 if ( parts.length < 3 ) {
		    	  System.out.println("\n Error on file <" + fileStat + "> record <" + line + "> not in correct format for type M needs at least 3 values."); 
		    	  setbError(true);
		    	  break;
		    	 }
		    	 if ( parts.length > 3 ) {
		           if ( parts[3].length() > 0 ) {
		        	   labelMetric = parts[3];  
		           } else {
		        	   labelMetric = "METRIC_" + String.valueOf(iMetric++);  
		           }
		    	 } else {
	        	   labelMetric = "METRIC_" + String.valueOf(iMetric++);  
		    	 }
		    	 lStat.get(lStat.size()-1).addMetric(new Metric(labelMetric,parts[1],parts[2])); 
		      }
		      // Title
		      if ( parts[0].compareTo("T") == 0 ) {
		    	 if ( parts[1].length() > 0 ) {
                    lStat.get(lStat.size()-1).setNameStat(parts[1]);
		    	 }
		      }
		      
		    }
		    reader.close();
		  }
		  catch (Exception e)
		  {
		    System.err.format("\nException occurred trying to read '%s' error : " + e.toString(), fileStat);
		    e.printStackTrace();
            setbError(true);
		    return;
		  }
	}
}
