package gpsoft;

public class Cleaner {

	boolean bClean;
	String cleanRE;
	
	protected boolean isbCLean() {
		return bClean;
	}

	protected void setbCond(boolean bCLean) {
		this.bClean = bClean;
	}

	public void checkCond(String sInput) {
		bClean = sInput.matches(cleanRE);
	}

	public Cleaner(String cleanRE) {
		super();
		this.cleanRE = cleanRE;
		bClean = false;
	}
}
