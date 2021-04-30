package com.team.app.miniGame2.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Border extends Entity{
	private Image img;
	private Color color = Color.black;

	public void setColorRGB(int r, int g, int b) {
		color = new Color(r,g,b);
	}

	public Border(double x, double y) {
		setX(x);
		setY(y);
		setWidth(1500);
		setHeight(39);
		
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {

		int x =(int)getX();
		int y =(int)getY();
		
		int width = (int)getWidth();
		int height = (int)getHeight();
		
		
		
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

}
