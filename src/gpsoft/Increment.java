package gpsoft;

import java.util.regex.Pattern;

public class Increment {
	String incrementName;
	String reCondIncrement;
	long currentValue;
	
	protected long getCurrentValue() {
		return currentValue;
	}

	protected void setCurrentValue(long currentValue) {
		this.currentValue = currentValue;
	}

	protected String getIncrementName() {
		return incrementName;
	}

	protected void setIncrementName(String incrementName) {
		this.incrementName = incrementName;
	}

	protected String getReCondIncrement() {
		return reCondIncrement;
	}

	protected void setReCondIncrement(String reCondIncrement) {
		this.reCondIncrement = reCondIncrement;
	}
	
	void incrValue(String record) {
		if ( record.matches(reCondIncrement) ) {
		   this.currentValue++;
		}
	}

	public Increment(String incrementName, String reCondIncrement) {
		super();
		this.incrementName = incrementName;
		this.reCondIncrement = reCondIncrement;
		this.currentValue = 0;
	}


}
