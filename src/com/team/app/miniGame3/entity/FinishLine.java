package com.team.app.miniGame3.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.miniGame3.canvas.MiniGame3Canvas;

public class FinishLine extends Entity {
	private Image img;
	private double size;
	
	private double dx; //도착지점
	private double dy;
	
	private double vx; //움직이는 속도
	private double vy;
	private double d;
	
	private double speed = 5; //속도조절
	
	private double w;
	private double h;
	
	//멈출때 인터페이스
	private LineStopListener lineStopListenr;
	public void setLineStopListenr(LineStopListener lineStopListenr) {
		this.lineStopListenr = lineStopListenr;
	}
	
		
	public FinishLine() {
		
		try {
			img = ImageIO.read(new File("res/game3/finish-line.png"));//동기식 이미지 파일읽기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		this.setWidth(img.getWidth(MiniGame3Canvas.instance)/3);
		this.setHeight(img.getHeight(MiniGame3Canvas.instance)/3);
		//시작점
		this.setX(500);
		this.setY(-200);
		
		
		this.dx = 340;
		this.dy = 80;
		
		
	     w = Math.ceil(this.dx - getX());
		 h = Math.ceil(this.dy - getY());
		 d = Math.sqrt(w * w + h * h);
	}	
	
	@Override
	public void update() {
		
		if(this.isVisible() && this.isActive()) {
			
			
			//속도 점점느리게
			if(speed >= 1) {
				speed-=0.02;
			}
			
			double x = getX();
			double y = getY();
			this.vx = w /d * speed;
			this.vy = h / d * speed;
		
			
			if(this.getX() >= this.dx) {
				this.setX(x= x+ vx);
			}
			if(this.getY() <= this.dy) {
				
				this.setY(y= y+ vy);
			}
			
			
			if(this.getY() >= dy) {
				this.setActive(false);
				lineStopListenr.onStop();
				
			}else {
				this.size +=4.2;
			}
			
		}
		
	}
	
	
	
	
	
	

	@Override
	public void paint(Graphics g) {
		
			int imgW = (int)getWidth();
			int imgH = (int)getHeight();

			int x1 = (int)getX();
			int y1 = (int)(getY() + imgH); 
		
			int x2 = x1 + imgW +(int)size;;
			int y2 = y1 + imgH/2 + (int)size/2;
			g.drawImage(img, x1, y1 , x2 , y2,
					0, 0, imgW*3, imgH*3, MiniGame3Canvas.instance);
			
	}
	
}
