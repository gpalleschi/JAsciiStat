package gpsoft;

public class Condition {
	
	boolean bCond;
	String condRE;
	
	protected boolean isbCond() {
		return bCond;
	}

	protected void setbCond(boolean bCond) {
		this.bCond = bCond;
	}

	public void checkCond(String sInput) {
		if ( bCond == false ) {
//		System.out.println("\nDEBUG Check Condition <"+condRE+"> is "+ String.valueOf(bCond));
		   bCond = sInput.matches(condRE);
		}
	}

	public Condition(String condRE) {
		super();
		this.condRE = condRE;
		bCond = false;
	}
}
