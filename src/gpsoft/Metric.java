package gpsoft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Metric {
	String mentricName;
	String reCondMetric;
	String reMetric;
	Pattern rePatMetric;
	double currentValue;

	protected String getMentricName() {
		return mentricName;
	}

	protected void setMentricName(String mentricName) {
		this.mentricName = mentricName;
	}

	protected double getCurrentValue() {
		return currentValue;
	}

	protected void setCurrentValue(long currentValue) {
		this.currentValue = currentValue;
	}

	void calcValue(String strToMatch) {
		 int iInd=0;
		 
		 if ( strToMatch.matches(reCondMetric) ) {
    		 Matcher m = rePatMetric.matcher(strToMatch);
//		 System.out.println("\nDEBUG Expression Metrics <"+rePatMetric+"> on strToMatch <"+ strToMatch + ">");
	    	 while(m.find()){
//		    	 System.out.println("\nDEBUG Get " + iInd + " <"+m.group(iInd)+">");
			     currentValue += Double.parseDouble(m.group(iInd));
//			     System.out.println("\nDEBUG Get " + iInd + " <"+m.group(iInd)+"> value " + currentValue);
			     break;
		     }
		 }
	}

	public Metric(String metricName, String reCondMetric, String reMetric) {
      this.mentricName = metricName;
      this.currentValue = 0;
      this.reCondMetric = reCondMetric;
      this.reMetric = reMetric;
	  try 
	  {
        rePatMetric = Pattern.compile(reMetric);
	  }
      catch(Exception e) {
		System.out.println("\n Error in compilation expression <" + reMetric + "> Error : " + e.toString());
	  }      
	}
}
