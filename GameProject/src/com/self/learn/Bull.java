package com.self.learn;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Bull {
	private int xPos;
	private int yPos = -20;
	private int lato = 45; // for size of bull

	Image bull = new ImageIcon("bull.png").getImage();

	// Constructor
	public Bull(int xPos) {
		super();
		this.xPos = xPos;
	}

	// Getters and Setters
	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getLato() {
		return lato;
	}

	public void setLato(int lato) {
		this.lato = lato;
	}

	public void moveDown() {
		yPos += 40;
	}

	// Draw bull
	public void drawBull(Graphics g) {
		g.drawImage(bull, xPos, yPos, lato, lato, null);
	}
}
