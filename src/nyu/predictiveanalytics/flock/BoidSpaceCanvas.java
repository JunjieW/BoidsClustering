package nyu.predictiveanalytics.flock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

public class BoidSpaceCanvas extends JPanel {
	
	private Vector<int[]> vect_positions = new Vector<int[]>();
	
    public BoidSpaceCanvas() {

    }
    
    public void setDotsAndRepaint(Vector<int[]> vect_pos) {
    	vect_positions = vect_pos;
    	this.repaint();
    }
    
    private void doDrawing(Graphics g) {
		if (vect_positions.size() != 0) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(Color.blue);

			int w = getWidth();
			int h = getHeight();

			for (int i = 0; i < vect_positions.size(); i++) {
				int x = vect_positions.get(i)[0];
				int y = vect_positions.get(i)[1];
				//g2d.drawLine(x, y, x, y);
				g2d.drawOval(x, y, 3, 3);
			}
		}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

}
