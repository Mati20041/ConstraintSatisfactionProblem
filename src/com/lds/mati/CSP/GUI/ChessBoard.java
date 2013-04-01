package com.lds.mati.CSP.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ChessBoard extends JPanel {
	private ArrayList<Integer> positions;

	public void setPositions(List<Integer> result) {
		positions = (ArrayList<Integer>) result;

		repaint();
	}

	public List<Integer> getPositions() {
		if (positions != null)
			return (List<Integer>) positions.clone();
		else
			return null;
	}
	
	public void paintImage(int width, int height, Graphics g){
		g.fillRect(0, 0, width, height);
		if (positions != null) {
			double boxWidth = 1. * width / (1. * positions.size());
			double boxHeight = 1. * height / (1. * positions.size());
			int bwpx = (int) boxWidth;
			int bhpx = (int) boxHeight;
			for (int i = 0; i < positions.size(); ++i) {
				for (int j = 0; j < positions.size(); ++j) {
					if ((i + j) % 2 == 0)
						g.setColor(Color.white);
					else
						g.setColor(Color.gray);
					int x = (int) (i * boxWidth);
					int y = (int) (j * boxHeight);
					g.fillRect(x, y, bwpx, bhpx);
					if (positions.get(i) != null && positions.get(i) == j) {
						g.setColor(Color.black);
						g.fillOval(x, y, bwpx, bhpx);
					}
				}
			}
		}
	}
		

	@Override
	public void paint(Graphics g) {
		paintImage(getWidth(), getHeight(), g);
	}

}
