package com.team.app.result.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.util.Random;

import com.team.app.entity.DrawImg;
import com.team.app.main.canvas.MainCanvas;

public class PlayerImg extends DrawImg{

	private int speed = 1;
	private boolean first = false;
	private AffineTransform identity = new AffineTransform();
	private Random rand = new Random();
	private int rotate = 0;

	public PlayerImg() {
		this(0, 0, 0, 0, null);
	}

	public PlayerImg(String src) {
		this(0,0,0,0,src);
	}
	
	public PlayerImg(double x, double y, double w, double h, String src) {
		super(x, y, w, h, src);
	}
	
	@Override
	public void update(){

		int canvasW = 1500;
		int canvasH = 900;
		double imgW = getImgW();
		double imgH = getImgH();
		double x = getX();
		double y = getY();
		double w = getWidth();
		double h = getHeight();
		
		if( !first ) {
			setX( getX()+1*speed);
//			setY( getY()+1*speed);
			if(rotate > 360)
				rotate = 0;
			rotate++;
		
		
			if( x < 0 || canvasW - imgW < x || 
				y < imgH || canvasH - imgH < y) {
				setY(rand.nextInt((int)(canvasH-imgH))+imgH);
				setX(-(rand.nextInt((int)(imgW*2))));
				setX(1);
				speed = rand.nextInt(4)+1;
//				super.setW(getImgW()/(rand.nextFloat()*2), getImgH()/(rand.nextFloat()*2));
			}

		}
	}
	
//	@Override
//	public void paint(Graphics g) {
//		Graphics2D g2d = (Graphics2D)g;
//		AffineTransform trans = new AffineTransform();
//		
//
//		trans.translate(
//			    (getImgW()),
//			    (getImgH())
//			);
////		trans.scale(0.5,0.5);
//		g2d.setTransform(trans);
//		trans.
//		trans.setTransform(identity);
//		trans.rotate( Math.toRadians(rotate) );
//		System.out.println(rotate);
//		
//		int x = (int) getX();
//		int y = (int) getY();
//		int w = (int) getWidth();
//		int h = (int) getHeight();
//		
////		RenderedImage image = new Null
////		g2d.drawImage(getImg(), x, y, x+w, y+h, 0, 0, getImgW(), getImgH(), MainCanvas.instance);
//		g2d.drawImage(getImg(), trans, MainCanvas.instance);
////		g2d.drawRenderedImage(getImg(), trans);
//	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
	
}
