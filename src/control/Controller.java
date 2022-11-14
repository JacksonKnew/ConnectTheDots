package control;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entity.Cell;
import entity.End;
import entity.Grid;
import entity.Path;

public class Controller {
	
	//game state attributes
	private Grid grid;
	private End currentEnd;
	private ArrayList<Path> paths = new ArrayList<Path>();
	private Path currentPath;
	private boolean isWon = false;
	
	//graphical customization attributes
	private int scale;
	private int wallThickness;
	private int endSize;
	private int pathSize;
	private int highlightEndSize;
	private int offset;
	
	//constructor
	public Controller(Grid grid, int scale, int wallThickness, int endSize, int pathSize, int highlightEndSize, int offset) {
		super();
		this.grid = grid;
		this.scale = scale;
		this.wallThickness = wallThickness;
		this.endSize = endSize;
		this.pathSize = pathSize;
		this.highlightEndSize = highlightEndSize;
		this.offset = offset;
	}
	
	//get functions for all the customization
		public int getScale() {
			return scale;
		}
		public int getWallThickness() {
			return wallThickness;
		}
		public int getEndSize() {
			return endSize;
		}
		public int getPathSize() {
			return pathSize;
		}
		public int getHighlightEndSize() {
			return highlightEndSize;
		}
		public int getOffset() {
			return offset;
		}
	
	//get functions for the state of the game
	public int[] getGridSize() {
		return grid.getGridSize();
	}
	public Cell[][] fetchGrid() {
		return grid.fetchGrid();
	}
	public List<Path> getPaths() {
		return paths;
	}
	public boolean IsWon() {
		return isWon;
	}
	
	
	//The following functions are local functions that determine if a move is legal
	
	//checks if the predicted next Cell is usable for the current path
	private boolean _isUsableCell(int coord_1, int coord_2, Cell[][] layout, int tag, List<Cell> currentPathList) {
		int[] size = this.getGridSize();
		//checks if it is in the Grid
		if(coord_1 >= 0 & coord_2 >= 0 & coord_1 < size[0] & coord_2 < size[1]) {
			//verifies it is not another color end
			if (layout[coord_1][coord_2].isEnd()) {
				if (layout[coord_1][coord_2].getEnd().getTag().getTagNb() != tag) {
					return false;
				}
			}
		// verifies it is not already in the current path
		for (Cell cell : currentPathList) {
			int[] coords = cell.getCoords();
			if (coords[0] == coord_1 & coords[1] == coord_2) {
				return false;
				}
			}
			return true;
		}
		return false;
	}
	
	//checks if the predicted next cell is actually the previous one, which means the last cell needs to be deleted
	private boolean _isPreviousCell(int coord_1, int coord_2, List<Cell> currentPathList) {
		if (currentPathList.size() > 1) {
			Cell previousCell = currentPathList.get(currentPathList.size() - 2);
			int[] coords = previousCell.getCoords();
			if (coords[0] == coord_1 & coords[1] == coord_2) {
				return true;
			}
		}
		return false;
	}
	
	//checks if another path is in the way, in which case it needs to be deleted
	private int _isOtherPath(int coord_1, int coord_2) {
		for (int i = 0; i < paths.size(); i++) {
			Path path = paths.get(i);
			List<Cell> cellList = path.getPath();
			for (Cell cell : cellList) {
				int[] coords = cell.getCoords();
				if (coords[0] == coord_1 & coords[1] == coord_2) {
					return i;
				}	
			}
		}
		return -1;
	}
	
	//checks if the path is completed with this cell
	private boolean _isLastCell(int coord_1, int coord_2, Cell[][] layout, int tag) {
		if (layout[coord_1][coord_2].isEnd()) {
			if (layout[coord_1][coord_2].getEnd().getTag().getTagNb() == tag) {
				return true;
			}
		}
		return false;
	}
	
	
	public void keyPressed(KeyEvent e) {
		
		if(currentPath != null) {
		//Getting current cell
		Cell[][] layout = grid.fetchGrid();
		int tag = currentPath.getTag().getTagNb();
		List<Cell> currentPathList = currentPath.getPath();
		Cell currentCell = currentPathList.get(currentPathList.size() - 1);
		int[] coords = currentCell.getCoords();
		
		//Determining next cell
		switch(e.getKeyCode()) {
			case KeyEvent.VK_UP : 
				if (currentPath != null) {
					if (this._isPreviousCell(coords[0], coords[1] - 1, currentPathList)) {
						currentPath.removeLastCell();
					}
					else {
						if (this._isUsableCell(coords[0], coords[1] - 1, layout, tag, currentPathList)) {
							int otherPath = this._isOtherPath(coords[0], coords[1] - 1);
							if (otherPath != -1) {
								paths.remove(otherPath);
							}
							Cell nextCell = layout[coords[0]][coords[1]-1];
							currentPath.addCell(nextCell);
						if (this._isLastCell(coords[0], coords[1] - 1, layout, tag)) {
							currentEnd.setCurrentEnd(false);
							currentPath.setCompleted(true);
							currentEnd = null;
							currentPath = null;
						}
					}
				}
			}
			break;
			case KeyEvent.VK_DOWN : 
				if (currentPath != null) {
					if (this._isPreviousCell(coords[0], coords[1] + 1, currentPathList)) {
						currentPath.removeLastCell();
					}
					else {
						if (this._isUsableCell(coords[0], coords[1] + 1, layout, tag, currentPathList)) {
							int otherPath = this._isOtherPath(coords[0], coords[1] + 1);
							if (otherPath != -1) {
								paths.remove(otherPath);
							}
							Cell nextCell = layout[coords[0]][coords[1]+1];
							currentPath.addCell(nextCell);
							if (this._isLastCell(coords[0], coords[1] + 1, layout, tag)) {
								currentEnd.setCurrentEnd(false);
								currentPath.setCompleted(true);
								currentEnd = null;
								currentPath = null;
							}
					}
				}
			}
			break;
			case KeyEvent.VK_RIGHT : 
				if (currentPath != null) {
					if (this._isPreviousCell(coords[0] + 1, coords[1], currentPathList)) {
						currentPath.removeLastCell();
					}
					else {
						if (this._isUsableCell(coords[0] + 1, coords[1], layout, tag, currentPathList)) {
							int otherPath = this._isOtherPath(coords[0] + 1, coords[1]);
							if (otherPath != -1) {
								paths.remove(otherPath);
							}
							Cell nextCell = layout[coords[0]+1][coords[1]];
							currentPath.addCell(nextCell);
							if (this._isLastCell(coords[0] + 1, coords[1], layout, tag)) {
								currentEnd.setCurrentEnd(false);
								currentPath.setCompleted(true);
								currentEnd = null;
								currentPath = null;
							}
					}
				}
			}
			break;
			case KeyEvent.VK_LEFT : 
				if (currentPath != null) {
					if (this._isPreviousCell(coords[0] - 1, coords[1], currentPathList)) {
						currentPath.removeLastCell();
					}
					else {
						if (this._isUsableCell(coords[0] - 1, coords[1], layout, tag, currentPathList)) {
							int otherPath = this._isOtherPath(coords[0] - 1, coords[1]);
							if (otherPath != -1) {
								paths.remove(otherPath);
							}
							Cell nextCell = layout[coords[0]-1][coords[1]];
							currentPath.addCell(nextCell);
							if (this._isLastCell(coords[0] - 1, coords[1], layout, tag)) {
								currentEnd.setCurrentEnd(false);
								currentPath.setCompleted(true);
								currentEnd = null;
								currentPath = null;
							}
					}
				}
			}
		}
	}
		
		//determining if game is won
		int totalLength = 0;
		for (Path path : paths) {
			if (path.isCompleted()) {
				totalLength += path.getPathLength();
			}
		}
		int[] size = grid.getGridSize();
		if (totalLength == size[0] * size[1]) {
			isWon = true;
		}
	}
	
	public void mouseClicked(int x, int y) {
		Cell[][] layout = grid.fetchGrid();
		if (layout[x][y].isEnd()) {
			if (currentEnd != null) {
				currentEnd.setCurrentEnd(false);
			}
			currentEnd = layout[x][y].getEnd();
			currentEnd.setCurrentEnd(true);
			currentPath = new Path(currentEnd.getTag());
			currentPath.addCell(layout[x][y]);
			for (int i = 0; i< paths.size(); i++) {
				if (paths.get(i).getTag().getTagNb() == currentPath.getTag().getTagNb()) {
					paths.remove(i);
				}
			}
			paths.add(currentPath);
		}
	}
}
