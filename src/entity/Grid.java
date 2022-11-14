package entity;

public class Grid {
	
	private Cell[][] grid;
	private int height;
	private int width;
	
	public Grid(int height, int width, int[][][] coords) {
		super();
		this.height = height;
		this.width = width;	
		
		grid = new Cell[width][height];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Cell(0, i, j);
			}
		}
		int nb = 1;
		for (int[][] coord : coords) {
			for (int[] point : coord) {
				grid[point[0]][point[1]] = new Cell(nb, point[0], point[1]);
			}
			nb++;
		}
	}
	
	public int[] getGridSize() {
		int[] size = new int[] {height, width};
		return size;
	}
	
	public Cell[][] fetchGrid() {
		return grid;
	}
}
