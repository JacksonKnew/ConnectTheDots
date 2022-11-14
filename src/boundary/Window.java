package boundary;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.Controller;

@SuppressWarnings("serial")
public class Window extends JFrame implements KeyListener, MouseListener{
	
	private Controller controller;
	private int[] size;
	
	public Window(Controller controller) {
		super();
		this.controller = controller;
		Display display = new Display(this.controller);
		size = controller.getGridSize();
		
		//initializing the window
		setPreferredSize(new Dimension(size[1] * controller.getScale() + 40, size[0] * controller.getScale() + 60));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		//telling it what to show
		add(display);
		
		//adding mouse and key listeners
		addKeyListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controller.keyPressed(e);
		repaint();
		if (controller.IsWon()) {
			Window.infoBox("You won", "You won");
		}
	}
	
	//this method converts the x and y into integers that represent the cell that was clicked on then gives it to the controller
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int scale = controller.getScale();
		int wallThickness = controller.getWallThickness();
		for (int i = 0; i < size[0]; i++) {
			for (int j = 0; j < size[1]; j++) {
				if (x > i * scale + 10 + scale*wallThickness/100 & x < (i+1) * scale + 10 + scale*wallThickness/100 ) {
					if (y > j * scale + 10 + scale*wallThickness/100 & y < (j+1) * scale + 10 + scale*wallThickness/100 ) {
						controller.mouseClicked(i, j);
						repaint();
					}
				}
			}
		}
	}
	
	//static method to output an infobox
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	
	//unused key and mouse listener methods
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
