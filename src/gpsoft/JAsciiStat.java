package gpsoft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title: JAsciiStat</p>
 * <p>Description: A simple File Ascii Statistical Tool using Regular Expressions</p> 
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: GPSoft.</p>
 * @author Giovanni Palleschi
 * @version 1.0
 */

public class JAsciiStat {

	    static String version = "1.0";
	    static String years="2020";
	    static String creator="GPSoft By GNNK71";
	    
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
	        System.out.println("Use: java -jar JAsciiStat.jar <File Ascii> [-p<separator char File Statistic>] -s<File Name Statistic> \n");
	        System.out.println("[...] are optional parameters\n");
	        System.out.println("[-s<File Name Statistic>] : Is a csv file with separator char |\n");
	        System.out.println("                            Each record has this format <Type>|<Regular Expression Condition>|<Regular Expression Extraction>[|<Label>]\n");
	        System.out.println("                            Values for <Type> : Z for Clean Statistic Key formed by Dimension and conditions");
	        System.out.println("                                                C for condition");
	        System.out.println("                                                D for dimension");
	        System.out.println("                                                I for Counters");
	        System.out.println("                                                M for metrics to sums (for metrics you can specify third field (Label)");
	        System.out.println("                                                T for statistic title\n\n");
	        System.out.println("Example of regular expression : \n\n");
	        System.out.println("(?<=\\[)([^\\]]+)(?=\\]) : Extract string value between square brackets [...].\n");
	        System.out.println("(?<=\\()([^)]+)(?=\\))   : Extract string value between round brackets (...).\n");
	        System.out.println("(?<=\\{)([^}]+)(?=\\})   : Extract string value between curly brackets {...}.\n");
	        System.out.println("(?<=.{4})(.{3})          : Extract string from byte 5 for 3 bytes.\n");
	        return;
		}
		
		private static void diplayStat(String fileInput, List<Stat> lStat) {
    		System.out.println("STATISTICS ON FILE " + fileInput + "\n");
	    	for (Stat stat : lStat) {
       		  System.out.println("STAT NAME  " + stat.getNameStat() + "\n");
       		  stat.displayKeys();
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

	    
        if ( args.length == 0 )
        {
            bHelp = true;
        	displayHelp();
        }
        
	    for(String arg : args) 
    	{	    	  
	      // Optional Parameter Separator for csv File Statistic	
          if ( arg.substring(0, 2).compareTo("-p") == 0 ) {
             if ( arg.substring(2).length() > 0 ) { 
	            sepFileStat = arg.substring(2);
	         }	    	
	   	     continue;
	      }	    	
	    	
          // File Statistics	
          if ( arg.substring(0, 2).compareTo("-s") == 0 ) {
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
