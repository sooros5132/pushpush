package com.team.app.miniGame1.entity;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.team.app.GameFrame;
import com.team.app.entity.Entity;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;

public class EdgeBorder extends Entity {
	
	private double speed = 14;
	private boolean fadeIn = false;
	private int red = 217;
	private int green = 217;
	private int blue = 37;
	private int alpha = 255;
	private int borderSize = 40;
	
	public EdgeBorder(){
		this(0, 0, 0, 0);
	}
	
	public EdgeBorder(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void update() {
		if( fadeIn ) {
			alpha += speed;
			if( alpha > 230) {
				fadeIn = false;
				alpha = 230;
			}
		} else {
			alpha -= speed;
			if( alpha <= 0) {
				fadeIn = true;
				alpha = 0; 
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		Color color1 = new Color(red,green,blue,alpha);
		Color color2 = new Color(red,green,blue,0);
		
		int x = (int)getX();
		int y = (int)getY();
		int w = (int)getWidth();
		int h = (int)getHeight();

		Graphics2D g2 = (Graphics2D)g;
//		상단, 하단, 왼쪽, 오른쪽
        g2.setPaint(new GradientPaint(x, y, color1, x, y+borderSize, color2, false));
        g2.fillRect(x, y, w, h);
		g2.setPaint(new GradientPaint(x, y+h-borderSize, color2, x, y+h, color1, false));
        g2.fillRect(x, y, w, h);
        g2.setPaint(new GradientPaint(x, y, color1, x+borderSize, y, color2, false));
        g2.fillRect(x, y, w, h);
        g2.setPaint(new GradientPaint(x+w-borderSize, y, color2, x+w, y, color1, false));
        g2.fillRect(x, y, w, h);
        
        
//        System.out.printf("%d, %d, %d, %d", red,green,blue,alpha);
//        System.out.println("paint");
	}

	public void setColorRGB(int red,  int green, int blue/*, int alpha*/) {
//		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
		
	@Override
	public void show() {
		this.setVisible(true);
	}
	
	@Override
	public void setVisible(boolean visible) {
		setWidth(GameFrame.frameWidth/3);
		setHeight(GameFrame.frameHeight);
		alpha = 0;
		super.setVisible(visible);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}
}
