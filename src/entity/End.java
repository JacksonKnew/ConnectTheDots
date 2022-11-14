package entity;

public class End {
	
	private Tag tag;
	private boolean currentEnd = false;
	
	public End(Tag tag) {
		super();
		this.tag = tag;
	}
	
	public Tag getTag() {
		return tag;
	}
	
	public void setCurrentEnd(boolean isOrNot) {
		currentEnd = isOrNot;
	}
	
	public boolean isCurrentEnd() {
		return currentEnd;
	}
}
