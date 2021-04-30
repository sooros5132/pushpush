package com.team.app.miniGame3.entity;

import java.awt.Graphics;

public abstract class Entity {
	
	private double x;
	private double y;
	private double width;
	private double height;
	
	// active가 true 되면 활성화??
	private boolean active = false;
	
	// visible이 true가 되면 보여짐
	private boolean visible = false;
	
	public abstract void update();
	
	public abstract void paint(Graphics g);
//	{	
//		if(active)
//			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
//	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
	}

	public double getX() {
		return x;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
