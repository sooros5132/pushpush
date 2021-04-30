package com.team.app.miniGame1.entity;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.entity.Entity;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;


public class Gauge extends Entity {

	// 빨간바 왼쪽 상단 기준으로 그려진다  x, y에 0, 0 넣으면 테두리가 짤려 보일 수 있음
//	private double x;
//	private double y;

	// 빨간 바의 너비, 현재 채워진 높이
//	private double width;
//	private double height;
	private final double MAX_HEIGHT;

	private int imgW;
	private int imgH;
	private Image img;
	private Color gaugeColor = Color.red;

	private GaugeFillListener fillListener;

	private boolean drawFace;
	
	public void setFillListener(GaugeFillListener fillListener) {
		this.fillListener = fillListener;
	}

	public Gauge() {
		this(100, 100, 25, 0);
	}
	
	public Gauge(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.MAX_HEIGHT = 500;
		drawFace = false;
		
		try {
//			img = ImageIO.read(new File("res/red.png"));
			img = ImageIO.read(new File("res/game1/gaugeImg.png"));
//			 이미지의 w, h 불러오기
			imgW = img.getWidth(MiniGame1Canvas.instance);
			imgH = img.getHeight(MiniGame1Canvas.instance);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void move(double x, double y) {
//		this.x = x;
//		this.y = y;
//	}
//	
//	public void setSize(double width, double height) {
//		this.width = width;
//		this.height = height;
//	}
	
	public void per(double setPer) {
		double per = MAX_HEIGHT / 100;
		
		setHeight(setPer * per);
	}
	
	public void setSize(double newWidth, double newHeight) {
		setWidth(newWidth);
		
		if (newHeight < 0)
			setHeight(0);
		else if (MAX_HEIGHT < newHeight)
			setHeight(MAX_HEIGHT);
		else
			setHeight(newHeight);

		double height = getHeight();
		
		if( height == MAX_HEIGHT ) {
			if( fillListener != null )
				fillListener.onFilld();
		}
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void paint(Graphics g) {
		
		double height = getHeight();
		double width = getWidth();
		double x = getX();
		double y = getY();
		double dx1 = x;
		// Math.ceil: 소수점 아래 올림
		double dy1 = Math.ceil(y + MAX_HEIGHT - height);
//		double dy1 = y + MAX_HEIGHT - height;
		
		g.setColor(gaugeColor);
		g.fillRect((int)dx1, (int)dy1, (int)width, (int)height);

//		Graphics2D g2 = (Graphics2D)g;
//        g2.setPaint(new GradientPaint((int)x, (int)y, new Color(0xF8D800),
//        		(int)(x+width), (int)(y+height), new Color(0xFDEB71)));
//        g2.fillRect((int)dx1, (int)dy1, (int)width, (int)height);
		
		double rectX = x - width / 2;
		double rectY = y - width / 2;
		double rectW = width * 2;
		double rectH = MAX_HEIGHT + width;
		
		g.setColor(Color.black);
		g.drawRect((int)rectX, (int)rectY, (int)rectW, (int)rectH);
		
		int iDx1 = (int)(x-width);
		int iDy1 = (int)(dy1-width);
		int iDx2 = (int)(x+width*1.5);
		int iDy2 = (int)(dy1+width*1.5);
		
		if ( drawFace ) {
			g.drawImage(img, iDx1, iDy1, iDx2, iDy2,
					0,    0, imgW, imgH, MiniGame1Canvas.instance);
		}
		
		
//		double dx1 = x;
//		// 빨간바의 아래부분 - 현재값 ( 밑에서부터 위로 여기까지 빨간색을 칠하겠다 )
//		double dy1 = y + MAX_HEIGHT - height;
//		// 빨간색바의 너비
//		double dx2 = x + width;
//		// 빨간바의 아래
//		double dy2 = y + MAX_HEIGHT;
//
//		g.drawImage(img, (int)dx1, (int)dy1, (int)dx2, (int)dy2,
//								0, 		  0, 		1,		  1,
//																ActionCanvas.instance);
	}

	public void changeImage(String src) {
		try {
//			img = ImageIO.read(new File("res/red.png"));
			img = ImageIO.read(new File(src));
//			 이미지의 w, h 불러오기
			imgW = img.getWidth(MiniGame1Canvas.instance);
			imgH = img.getHeight(MiniGame1Canvas.instance);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double getMAX_HEIGHT() {
		return MAX_HEIGHT;
	}
	
	public void setGaugeColor(Color gaugeColor) {
		this.gaugeColor = gaugeColor;
	}

	public void drawFace(boolean face) {
		this.drawFace = face;
	}
}
