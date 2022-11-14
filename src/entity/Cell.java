package entity;

public class Cell {
	
	private End end;
	@SuppressWarnings("unused")
	private int nb;
	private int x;
	private int y;
	
	public Cell(int nb, int x, int y) {
		super();
		this.nb = nb;
		this.x = x;
		this.y = y;
		if (nb == 0) {
			this.end = null;
		}
		else {
			this.end = new End(new Tag(nb));
		}
	}
	
	public int[] getCoords() {
		return new int[] {x, y};
	}
	
	public boolean isEnd() {
		if (end == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public End getEnd() {
		return end;
	}
}
