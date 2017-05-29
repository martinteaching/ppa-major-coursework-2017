package model;

/**
 * Generated.
 * 
 * @author Martin
 *
 */
public class StatisticNameAndValue implements Comparable<StatisticNameAndValue> {
	
	private String title;
	private String value;
	private boolean selectedByUser;
	
	@Override
	public int compareTo(StatisticNameAndValue o) {
		
		if ( selectedByUser && !o.selectedByUser ) return -1;
		
		if ( !selectedByUser && o.selectedByUser ) return 1;
		
		return title.compareTo(o.title);
		
	}
	
	public StatisticNameAndValue(String title, String value, boolean selectedByUser) {
		this.title = title;
		this.value = value;
		this.selectedByUser = selectedByUser;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public boolean isSelectedByUser() {
		return selectedByUser;
	}
	
	public void setSelectedByUser() {
		selectedByUser = true;
	}

	
	
}
