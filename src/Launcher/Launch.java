package Launcher;

import javax.swing.SwingUtilities;

import boundary.Window;
import control.Controller;
import entity.Grid;

public class Launch implements Runnable{
	
	//game parameters, both sets of end coordinates can be put into the grid constructor
	private int size = 7;
	@SuppressWarnings("unused")
	private int[][][] coords_1 = new int[][][] {
		{{4, 2}, {2, 5}},
		{{4, 1}, {0, 6}},
		{{3, 2}, {1, 1}},
		{{3, 0}, {4, 6}},
		{{5, 1}, {3, 3}},
	};
	@SuppressWarnings("unused")
	private int[][][] coords_2 = new int[][][] {
		{{1, 0}, {0, 3}},
		{{6, 0}, {4, 6}},
		{{4, 2}, {1, 3}},
		{{4, 1}, {4, 4}},
		{{4, 3}, {4, 5}},
		{{2, 2}, {2, 6}},
		{{1, 5}, {3, 6}},
	};
	
	//Graphical parameters for the game
	//all following values are in pixels
	private int scale = 100;
	private int offset = 10;
	//all following values are in percentages
	private int wallThickness = 8;
	private int endSize = 70;
	private int pathSize = 30;
	private int highlightEndSize = 10;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Launch());
	}

	@Override
	public void run() {
		Grid grid = new Grid(size, size, coords_1);
		Controller controller = new Controller(grid, scale, wallThickness, endSize, pathSize, highlightEndSize, offset);
		@SuppressWarnings("unused")
		Window window = new Window(controller);
	}
}
