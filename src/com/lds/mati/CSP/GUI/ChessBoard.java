package com.lds.mati.CSP.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ChessBoard extends JPanel{
	private ArrayList<Integer> positions;
	

	public void setPositions(List<Integer> result){
		positions = (ArrayList<Integer>) result;
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		g.fillRect(0, 0, getWidth(), getHeight());
		if(positions != null){
			int boxWidth = getWidth()/positions.size();
			int boxHeight = getHeight()/positions.size();
			for(int i = 0 ; i < positions.size() ;++i){
				for(int j = 0 ; j < positions.size() ;++j){
					if((i+j)%2==0)
						g.setColor(Color.white);
					else
						g.setColor(Color.gray);
					g.fillRect(i*boxWidth, j*boxHeight, boxWidth, boxHeight);
					if(positions.get(i)!=null && positions.get(i)==j){
						g.setColor(Color.black);
						g.fillOval(i*boxWidth, j*boxHeight, boxWidth, boxHeight);
					}
				}
			}
		}
	}

}
