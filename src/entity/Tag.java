package entity;

import java.awt.Color;

public class Tag {
	
	private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.LIGHT_GRAY, Color.CYAN, Color.MAGENTA};
	private Color color;
	private int nb;
	
	public Tag(int nb) {
		super();
		this.nb = nb;
		this.color = colors[nb-1];
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getTagNb() {
		return nb;
	}

}
