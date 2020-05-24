package gpsoft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dimension {
	
	private boolean isErr;
	private String reCondDimension;
	private String reDimension;
	Pattern rePattern;
	
	String calcDimension(String strToMatch) {
		 String strToRet = "";
		 int iInd=0;
	
		 if ( strToMatch.matches(reCondDimension) ) {
		    Matcher m = rePattern.matcher(strToMatch); 
//		 System.out.println("\nDEBUG Expression Dimension <"+reDimension+"> on strToMatch <"+ strToMatch + ">");
		    while(m.find()){
			    if ( strToRet.length() > 0 ) strToRet+=";";
//		        System.out.println("\nDEBUG Get " + iInd + " <"+m.group(iInd)+">");
			    strToRet+=m.group(iInd);
		        break;
		    }
		 }
		 return strToRet;
	}

	public Dimension(String reCondDimension, String reDimension) {
		super();
		isErr = false;
		this.reCondDimension = reCondDimension;
		this.reDimension = reDimension;
		try 
		{
		  rePattern = Pattern.compile(reDimension);
	    }
		catch(Exception e) {
			System.out.println("\n Error in compilation expression <" + reDimension + "> Error : " + e.toString());
			isErr = true;
	    }
	}
  
}
