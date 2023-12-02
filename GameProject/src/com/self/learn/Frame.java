package com.self.learn;

import javax.swing.JFrame;


public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;

	// create game window
	public Frame(Panel panel) {
		this.setTitle("CORRIDA de TOROS");
		this.setSize(348, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
