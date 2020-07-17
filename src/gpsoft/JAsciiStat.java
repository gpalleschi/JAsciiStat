package gpsoft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * <p>Title: JAsciiStat</p>
 * <p>Description: A simple File Ascii Statistical Tool using Regular Expressions</p> 
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: GPSoft.</p>
 * @author Giovanni Palleschi
 * @version 1.0
 */

public class JAsciiStat {

	    static String version = "1.2";
	    static String years="2020";
	    static String creator="GPSoft By GNNK71";
	    static int decimalDigits = 2;
	    
	    static FileStat fileStat = null;
	    static FileInput fileInput = null;
	
	    public static boolean isFile(String filePathString) {
	    File f = new File(filePathString);
	    if(f.exists() && !f.isDirectory()) { 
	    // do something
	       return true;		 
	      } else {
	           return false;		 
	      }
	    }

	    public static String getVersion() {
		   return version;
		}

		public static String getYears() {
			return years;
		}

		public static String getCreator() {
			return creator;
		}
		
		private static void displayHelp() {
	        System.out.println("\nJAsciiStat version " + getVersion() + " (" + getYears()   + ") " + getCreator() + "\n");
	        System.out.println("Use: java -jar JAsciiStat.jar <File Ascii> [-p<separator char File Statistic>] [-d<decimal digits for metrics>] -s<File Name Statistic> \n");
	        System.out.println("[...] are optional parameters\n");
	        System.out.println("[-s<File Name Statistic>] : Is a csv file with separator char |\n");
	        System.out.println("                            Each record in Statistic File has a csv format like this <Type>|<Regular Expression Condition>|<Regular Expression Extraction>[|<Label>]\n");
	        System.out.println("                            Values for <Type> : Z for Clean Statistic Key formed by Dimension and conditions (format : 'Z|<Regular Expression Condition>')");
	        System.out.println("                                                C for condition (format : 'C|<Regular Expression Condition>')");
	        System.out.println("                                                D for dimension (format : 'D|<Regular Expression Condition>|<Regular Expression Extraction>')");
	        System.out.println("                                                I for Counters (format : 'I|<Regular Expression Condition>|<Label to show in resume>')");
	        System.out.println("                                                M for metrics to sums (format : 'M|<Regular Expression Condition>|<Regular Expression Extraction>|<Label to show in resume>')");
	        System.out.println("                                                T for statistic title (format : 'T|<Label to show in resume>')\n\n");
	        System.out.println("\nWorkflow\n");
	        System.out.println("For every record reads from **File Ascii** and for every **Statistic File**"); 
	       	System.out.println("		1. Execute all **Cleaner** expressio");
	    	System.out.println("		2. If all **Cleaner** expressions are true ");
	    	System.out.println("		    - Add Key");
	    	System.out.println("		    - Reinitialize Key");
	    	System.out.println("		    - Reinitialize Conditions");
	    	System.out.println("		3. Execute **Incremental** expressions");
	    	System.out.println("		4. Check all **Condition** expressions");
	    	System.out.println(" 		5. If all **Condition** expressions are true");
	    	System.out.println("		    - Execute **Dimension** expressions");
	    	System.out.println("		    - If length of key calculated is more than 0");
	    	System.out.println("		        * Execute **Metric** expressions\n\n");
	        System.out.println("Example of regular expression : \n\n");
	        System.out.println("(?<=\\[)([^\\]]+)(?=\\]) : Extract string value between square brackets [...].\n");
	        System.out.println("(?<=\\()([^)]+)(?=\\))   : Extract string value between round brackets (...).\n");
	        System.out.println("(?<=\\{)([^}]+)(?=\\})   : Extract string value between curly brackets {...}.\n");
	        System.out.println("(?<=.{4})(.{3})          : Extract string from byte 5 for 3 bytes.\n");
	        System.out.println("^.*$                     : Condition Always True.\n");
	        System.out.println("^.{3}abc.*$              : Condition to check if at byte 4 is present string abc.\n");
	        return;
		}
		
		private static void diplayStat(String fileInput, List<Stat> lStat) {
    		System.out.println("STATISTICS ON FILE " + fileInput + "\n");
	    	for (Stat stat : lStat) {
       		  System.out.println("STAT NAME  " + stat.getNameStat() + "\n");
       		  stat.displayKeys(decimalDigits);
	    	}
		}
	
	    public static void main(String[] args) throws IOException {

	    boolean bErr = true;
	    boolean bFileStat = false;
	    boolean bFileInput = false;
	    boolean bHelp = false;
	    String sepFileStat = "\\|";
	    FileInput fileInput = null;
	    List<Stat> lStat = new ArrayList<Stat>();
	    Pattern pattern = Pattern.compile("\\d*");
	    
        if ( args.length == 0 )
        {
            bHelp = true;
        	displayHelp();
        }
        
	    for(String arg : args) 
    	{	    	  
	      // Optional Parameter Separator for csv File Statistic	
          if ( arg.length() > 2 && arg.substring(0, 2).compareTo("-p") == 0 ) {
             if ( arg.substring(2).length() > 0 ) { 
	            sepFileStat = arg.substring(2);
	         }	    	
	   	     continue;
	      }	    	
	    	
          // File Statistics	
          if ( arg.length() > 2 && arg.substring(0, 2).compareTo("-s") == 0 ) {
             if ( arg.substring(2).length() > 0 && isFile(arg.substring(2)) ) { 
            	bFileStat = true;
            	
            	fileStat = new FileStat(arg.substring(2),lStat,sepFileStat);
	    	    continue;
             } else {
	    		bErr = true;  
                System.out.println("\n\nStatistic File specified <" +  arg.substring(2) + "> not exists or not is a correct file.\n");
                break;
	    	 }
	      }	    	
          // Decimal Points
          if ( arg.length() > 2 && arg.substring(0, 2).compareTo("-d") == 0 ) {
             if ( !pattern.matcher(arg.substring(2)).matches() ) {
                 System.out.println("\n\nDecimal digits not numeric for -d parameter <" +  arg.substring(2) + ">.\n");
                 break;           	 
             }
             decimalDigits = Integer.valueOf(arg.substring(2)).intValue();
             if ( decimalDigits > 10 ) {
                 System.out.println("\n\nToo many Decimal digits for -d parameter <" +  arg.substring(2) + "> max value admitted is 10.\n");
                 break;           	 
             }
             continue;
          }
          
   	      if ( arg.length() > 4 && arg.substring(0, 5).compareTo("-help") == 0 ) {
		     displayHelp();
             bHelp = true;
		     break;
	      }
   	   // The last control is the input file 
		  if ( arg.length() > 0 && isFile(arg) ) { 
			 bErr = false;
			 bFileInput = true;
	         fileInput = new FileInput(arg,lStat);
		  } else {
			bErr = true;  
            System.out.println("\n\nInput File specified <" +  arg + "> not exists or not is a correct file.\n");
            break;
         }   	      
    	}
	    // If there are no errors
        if ( !bErr ) {
           if ( !bFileStat ) {
               System.out.println("\n\nStatistic File no specified.\n");
           }
           else {
	         fileInput.Elab();
	         diplayStat(fileInput.getFileName(), lStat);
           }
        }
	    else {
           if ( bHelp == false ) {
        	  if ( !bFileInput ) System.out.println("\n\nInput Ascii File no specified.\n\n");
           }
        }
	}
}
