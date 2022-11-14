package entity;

import java.util.ArrayList;
import java.util.List;

public class Path {
	
	private Tag tag;
	private List<Cell> path = new ArrayList<>();
	private boolean isCompleted = false;
	
	public Path(Tag tag) {
		super();
		this.tag = tag;
	}
	
	public Tag getTag() {
		return tag;
	}
	
	public List<Cell> getPath() {
		return path;
	}
	
	public int getPathLength() {
		return path.size();
	}
	
	public void addCell(Cell cell) {
		path.add(cell);
	}
	
	public void removeLastCell() {
		path.remove(path.size() - 1);
	}
	
	public boolean isCompleted() {
		return isCompleted;
	}
	
	public void setCompleted(boolean isOrNot) {
		isCompleted = isOrNot;
	}
}
