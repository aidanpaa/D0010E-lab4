package lab4.gui;

import java.awt.Color;
import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer {

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	private GomokuGameState gameState;

	/**
	 * The constructor
	 * 
	 * @param grid
	 *            The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid) {
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize() * UNIT_SIZE + 1, grid.getSize() * UNIT_SIZE + 1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates of the panel
	 * 
	 * @param x
	 *            the x coordinates
	 * @param y
	 *            the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y) {
		double a = (x / UNIT_SIZE);
		double b = (y / UNIT_SIZE);

		int[] intArray = { (int) Math.floor(a), (int) Math.floor(b) };
		return intArray;

	}
	/**
	 *  args0 The object that calls for notifyObervers()
	 *  args1 If an argument comes with notifyObservers()
	 */

	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	/**
	 * Paints out the whole grid, boxes. Also draws the X and O symbols for each player.
	 */

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = grid.getSize() * UNIT_SIZE;
		for (int x = 0, i = 0; x < size; x += UNIT_SIZE, i++) {
			for (int y = 0, j = 0; y < size; y += UNIT_SIZE, j++) {
				
				g.setColor(Color.BLACK);
				g.drawRect(x, y, UNIT_SIZE, UNIT_SIZE);


				if (grid.getLocation(i, j) == GameGrid.ME) {
					g.drawLine(x + 3, y + 3, x + UNIT_SIZE - 3, y + UNIT_SIZE - 3);
					g.drawLine(x + 3, y + UNIT_SIZE - 3, x + UNIT_SIZE - 3, y + 3);

				}

				else if (grid.getLocation(i, j) == GameGrid.OTHER) {
					g.setColor(Color.RED);
					g.drawOval(x+1, y+1, UNIT_SIZE-2, UNIT_SIZE-2);
				}

			}

		}
	}

}