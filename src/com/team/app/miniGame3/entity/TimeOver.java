package com.team.app.miniGame3.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.miniGame3.canvas.MiniGame3Canvas;

public class TimeOver extends Entity {

	private Image img;
	private int size=2;
	public TimeOver() {
		try {
			img = ImageIO.read(new File("res/game3/TimeOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setWidth(579);
		setHeight(135);
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		int canvasW= MiniGame3Canvas.instance.getWidth();
		int canvasH = MiniGame3Canvas.instance.getHeight();
		int w = (int)getWidth();
		int h = (int)getHeight();
		
		
		int dx1 = canvasW/2-(w*size)/2;
		int dy1 = canvasH/2 -(h*size)/2-100;
		int dx2 = canvasW/2+(w*size)/2;
		int dy2 = canvasH/2 +(h*size)/2-100;
		int sx1 =0;
		int sy1 = 0;
		int sx2 = w;
		int sy2 = h;
//		System.out.printf("%d  %d  %d  %d  \n",dx1,dy1,dx2,dy2);
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame3Canvas.instance);
		
	}
	

}
