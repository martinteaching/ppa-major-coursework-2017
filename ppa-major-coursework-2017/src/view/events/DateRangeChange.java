package view.events;

public class DateRangeChange {

	public static final int UPDATING = 0;
	public static final int UPDATED = 1;
	
	private int event;
	private String dateRange;
	private String grabTime;
	
	public DateRangeChange() {
		
		this.event = -1;
		
	}
	
	public DateRangeChange(int event) {
		
		this.event = event;
		
	}
	
	public DateRangeChange(int event, String data) {
		
		this.event = event;
		
		if ( event == UPDATING ) {
			
			dateRange = data;
			
		} else if ( event == UPDATED ) {
			
			grabTime = data;
			
		}
		
	}
	
	public String getDateRange() {
		
		return dateRange;
		
	}
	
	public String getGrabTime() {
		
		return grabTime;
		
	}
	
	public int getEvent() {
		
		return event;
		
	}	

}
