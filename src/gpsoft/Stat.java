package gpsoft;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stat {

	private String nameStat;
    private List<Cleaner> lCleans; 	
    private List<Condition> lConds; 	
    private List<Dimension> lDims; 	
    private List<Metric> lMetrics;
    private List<Increment> lIncrement;
    private String currentKey;
    private HashMap<String, double[]> hashStat;
    
    public void displayKeys(int decDig) {
    	double[] metrics;
    	double[] totMetrics;
    	
    	String formatMetrics = new String();
    	
    	formatMetrics = String.format("\t%%s : %%.%df\n",decDig);
    	
    	totMetrics = new double[lMetrics.size()];
		for(int iInd=0;iInd<totMetrics.length;iInd++) {
			totMetrics[iInd] = 0;
		}
    	
		for (Map.Entry<String, double[]> entry : hashStat.entrySet()) {
			System.out.println("KEY : " + entry.getKey() +"\n");
			metrics = entry.getValue();
			for(int iInd=0;iInd<metrics.length;iInd++) {
				System.out.printf(formatMetrics, lMetrics.get(iInd).getMentricName(), metrics[iInd]);	
			    totMetrics[iInd] += metrics[iInd];
			}
			System.out.println("");
		}
		if ( lMetrics.size() > 0 ) {
		  System.out.println("\nTOTALS METRICS\n");
		  for(int iInd=0;iInd<totMetrics.length;iInd++) {
		  		System.out.printf(formatMetrics,lMetrics.get(iInd).getMentricName(), totMetrics[iInd]);	
		  }
		}
		
		if ( lIncrement.size() > 0 ) {
		  System.out.println("\nCOUNTERS\n");
    	  for (Increment increment : lIncrement) {
      		  System.out.println("\t" + increment.getIncrementName() + " : " + increment.currentValue);
      	  }
		}
    }
    
    public void addKey() {
    	if ( currentKey.length() == 0 ) return;
    	double[] retMetrics = hashStat.get(currentKey);
    	if ( retMetrics == null ) {
    		retMetrics = new double[lMetrics.size()];
    		for(int iInd=0;iInd<lMetrics.size();iInd++) {
    			retMetrics[iInd] = lMetrics.get(iInd).getCurrentValue();
    			lMetrics.get(iInd).setCurrentValue(0L);
    		}
    		hashStat.put(currentKey, retMetrics);
    	} else {
    		for(int iInd=0;iInd<lMetrics.size();iInd++) {
    			retMetrics[iInd] += lMetrics.get(iInd).getCurrentValue();
    			lMetrics.get(iInd).setCurrentValue(0L);
    		}    		
    	}
    }
   
    protected String getCurrentKey() {
		return currentKey;
	}

	protected void setCurrentKey(String currentKey) {
		this.currentKey = currentKey;
	}

	protected String getNameStat() {
		return nameStat;
	}
    
	protected void setNameStat(String nameStat) {
		this.nameStat = nameStat;
	}

	public void addDimension(Dimension newDim) {
    	lDims.add(newDim);
    }

    public void addCleaner(Cleaner newCle) {
    	lCleans.add(newCle);
    }

    public void addCondition(Condition newCond) {
    	lConds.add(newCond);
    }

    public void addMetric(Metric newMetr) {
    	lMetrics.add(newMetr);
    }

    public void addIncrement(Increment newIncr) {
    	lIncrement.add(newIncr);
    }
    
    public boolean conditionsAreAllOk() {
    	boolean bRet = true;
    	if ( lConds.size() == 0 ) return bRet;
    	for (Condition cond : lConds) {
    		if ( cond.isbCond() == false ) {
    		  bRet = false;	
    		  break;
    		}
    	}
        return bRet;	
    }

    public boolean cleanersAreAllOk() {
    	boolean bRet = false;
    	if ( lCleans.size() == 0 ) return bRet;
    	bRet = true;
    	for (Cleaner clean : lCleans) {
    		if ( clean.isbCLean() == false ) {
    		  bRet = false;	
    		  break;
    		}
    	}
        return bRet;	
    }
    
    public void initializeConds() {
    	for (Condition cond : lConds) {
    		cond.setbCond(false);;
    	}
    }    
    
    public void checkConditions(String record) {
    	for (Condition cond : lConds) {
    		cond.checkCond(record);
    	}
    }

    public void checkCleaner(String record) {
    	for (Cleaner clean : lCleans) {
    		clean.checkCond(record);
    	}
    }
    
    public void calcMetrics(String record) {
    	for (Metric metric : lMetrics) {
    		metric.calcValue(record);
    	}
    }

    public void setIncrements(String record) {
    	for (Increment increment : lIncrement) {
    		increment.incrValue(record);
    	}
    }
    
    public void setDimensions(String record) {
    	String dimValue;
    	for (Dimension dimension : lDims) {
    		dimValue = dimension.calcDimension(record);
    		if ( dimValue.length() > 0 ) {
    		   if ( currentKey.length() > 0 ) currentKey += ";";
    		   currentKey += dimValue;
    		}
    	}
    }

	public Stat(String nameStat) {
		this.nameStat = nameStat; 
		lConds = new ArrayList<Condition>();
		lMetrics = new ArrayList<Metric>();
		lCleans = new ArrayList<Cleaner>();
		lDims = new ArrayList<Dimension>();
		lIncrement = new ArrayList<Increment>();
        currentKey = "";
		hashStat = new HashMap<String, double[]>();
	}
}
