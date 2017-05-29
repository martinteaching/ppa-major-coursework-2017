package view.events;

/**
 * Auto-gen
 * 
 * @author Martin
 *
 */
public class AddAlienHead {
	
	private int xPosition;
	private int yPosition;
	private int size;
	private String state;
	
	public AddAlienHead(int xPosition, int yPosition, int size, String state) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.size = size;
		this.state = state;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}