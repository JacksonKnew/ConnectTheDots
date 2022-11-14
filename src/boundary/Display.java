package boundary;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import control.Controller;
import entity.Cell;
import entity.Path;

@SuppressWarnings("serial")
public class Display extends JPanel {
	
	private Controller controller;
	
	public Display(Controller controller) {
		super();
		this.controller = controller;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		//Getting the configuration from the controller
		int[] size = controller.getGridSize();
		int scale = controller.getScale();
		int wallThickness = controller.getWallThickness();
		int endSize = controller.getEndSize();
		int pathSize = controller.getPathSize();
		int highlightEndSize = controller.getHighlightEndSize();
		int offset = controller.getOffset();
		
		//black square background
		g.setColor(Color.BLACK);
		g.fillRect(offset ,offset , size[1] * scale + scale*wallThickness/100, size[0] * scale+ scale*wallThickness/100);
		
		//white cells
		g.setColor(Color.WHITE);
		for (int i = 0; i < size[1]; i++) {
			for (int j = 0; j < size[0]; j++) {
				g.fillRect( i * scale + offset + scale*wallThickness/100, j * scale + offset + scale*wallThickness/100, 
						    scale * (100/wallThickness-1)*wallThickness/100, scale * (100/wallThickness-1)*wallThickness/100);
			}
		}
		
		//Colored ends
		Cell[][] grid = controller.fetchGrid();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].isEnd()) {
					//black highlight circle for the current end
					if (grid[i][j].getEnd().isCurrentEnd()) {
						g.setColor(Color.BLACK);
						g.fillOval(i * scale + offset + scale * (100 - (endSize+highlightEndSize))/200 + scale*wallThickness/200, 
								j * scale + offset + scale * (100 - (endSize+highlightEndSize))/200 + scale*wallThickness/200, 
								scale * (endSize+highlightEndSize)/100, 
								scale * (endSize+highlightEndSize)/100);
					}
					//the colored circle in itself
					g.setColor(grid[i][j].getEnd().getTag().getColor());
					g.fillOval(i * scale + offset + scale * (100 - endSize)/200 + scale*wallThickness/200, 
							j * scale + offset + scale * (100 - endSize)/200 + scale*wallThickness/200, 
							scale * endSize/100, 
							scale * endSize/100);
				}
			}
		}
		
		//paths
		List<Path> paths = controller.getPaths();
		for (Path path : paths) {
			List<Cell> cellList = path.getPath();
			if (cellList.size() > 0) {
				g.setColor(path.getTag().getColor());
				for (int i = 0; i < cellList.size() - 1; i++) {
					int[] coords_1 = cellList.get(i).getCoords();
					int[] coords_2 = cellList.get(i+1).getCoords();
					g.fillOval(coords_2[0] * scale + offset + scale * (100-pathSize)/200 + scale*wallThickness/200, 
							coords_2[1] * scale + offset + scale * (100-pathSize)/200 + scale*wallThickness/200, 
							scale * pathSize/100, 
							scale * pathSize/100);
					if (coords_1[0] < coords_2[0]) {
						g.fillRect(coords_1[0] * scale + offset + scale/2 + scale*wallThickness/200, 
								coords_1[1] * scale + offset + scale * (100-pathSize)/200 + scale*wallThickness/200, 
								scale, 
								scale * pathSize/100);
					}
					if (coords_1[0] > coords_2[0]) {
						g.fillRect(coords_2[0] * scale + offset + scale/2 + scale*wallThickness/200, 
								coords_2[1] * scale + offset + scale * (100-pathSize)/200 + scale*wallThickness/200, 
								scale, 
								scale * pathSize/100);
					}
					if (coords_1[1] < coords_2[1]) {
						g.fillRect(coords_1[0] * scale + offset + scale * (100-pathSize)/200 + scale*wallThickness/200, 
								coords_1[1] * scale + offset + scale/2 + scale*wallThickness/200, 
								scale * pathSize/100, 
								scale);
					}
					if (coords_1[1] > coords_2[1]) {
						g.fillRect(coords_2[0] * scale + offset + scale * (100-pathSize)/200 + scale*wallThickness/200, 
								coords_2[1] * scale + offset + scale/2 + scale*wallThickness/200, 
								scale * pathSize/100, 
								scale);
					}
				}
			}
		}
	}
}