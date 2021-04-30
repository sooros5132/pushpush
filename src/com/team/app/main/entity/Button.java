package com.team.app.main.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Button {

	private double x;
	private double y;
	private double width;
	private double height;
	
	private static Color defaultColor = new Color(0xa6a6a6);
	
	private Color bgColor = new Color(0xa6a6a6);
//	private Color bgGradient2 = new Color(217,217,37,255);
	private Color bgGradient1 = new Color(233,233,233,255);
	private Color bgGradient2 = new Color(233,233,233,255);
	private Color fontColor = Color.black;
	private Boolean isGradient = false;
	
	private String text;
	private double textX;
	private double textY;
	private int fontSize = 50;
	private int boxRadius = 30;
	
	public Button() {
		this(10, 10, 150, 50, "Click");
	}
	
	public Button(String text) {
		this(10, 10, 150, 50, text);
	}
	
	public Button(double x, double y, double width, double height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}

	public void textMove(double x, double y) {
		textX = x;
		textY = y;
	}
	public void paint(Graphics g) {
//		g.fillRoundRect(10, 10, 110, 110, arcWidth, arcHeight);
		int x = (int)this.x;
		int y = (int)this.y;
		int w = (int)this.width;
		int h = (int)this.height;

		if( isGradient ) {
			Graphics2D g2 = (Graphics2D)g;
	        g2.setPaint(new GradientPaint(x, y, bgGradient1, x+w, y+h, bgGradient2));
	        g2.fillRoundRect(x, y, w, h, boxRadius, boxRadius);
		} else {
			g.setColor(bgColor);
			g.fillRoundRect(x, y, w, h, boxRadius, boxRadius);
		}
		
		g.setColor(fontColor);
		g.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
		g.drawString(text, (int)(x+textX), (int)(y+textY));
	}
	
	public void setBgColor(Color color) {
		this.isGradient = false;
		this.bgColor = color;
	}
	
	public void setBgColor(Color gradient1, Color gradient2) {
		this.isGradient = true;
		this.bgGradient1 = gradient1;
		this.bgGradient2 = gradient2;
	}
	
	public void setFontColor(Color color) {
		this.fontColor = color;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isSelected(int x, int y) {
		if( this.x <= x && x <= this.x + this.width &&
			this.y <= y && y <= this.y + this.height)
			return true;
			
		return false;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setBoxRadius(int boxRadius) {
		this.boxRadius = boxRadius;
	}

	public Boolean IsGradient() {
		return isGradient;
	}

	public void setIsGradient(Boolean isGradient) {
		this.isGradient = isGradient;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void move(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	
}
