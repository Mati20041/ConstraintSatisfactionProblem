package com.lds.mati.CSP;

import java.awt.EventQueue;

import com.lds.mati.CSP.GUI.GUI;

public class Main {
	/**
	 * @param args
	 */
	static int boardSize = 100;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("koniec");
	}
}