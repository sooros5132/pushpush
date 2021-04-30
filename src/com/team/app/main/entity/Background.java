package com.team.app.main.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.GameFrame;
import com.team.app.entity.Entity;
import com.team.app.main.canvas.MainCanvas;

public class Background extends Entity{

	private static Image img;
	private int width;
	private int height;
	
	static {
		try {
			img = ImageIO.read(new File("res/main/mainBg01.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Background() {
		this(0, 0, "res/main/mainBg01.png");
	}

	public Background(double x, double y, String imgSrc) {

		try {
			img = ImageIO.read(new File(imgSrc));
			width = img.getWidth(null);
			height = img.getHeight(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update() {
//		double x = getX();
//		double y = getY();
//		setY(y);
//		setX(x);
//		System.out.println(vy);
//		System.out.println(y);
	}

	@Override
	public void paint(Graphics g) {
		int x = (int)getX();
		int y = (int)getY();

		g.drawImage(img, x, y, GameFrame.frameWidth, GameFrame.frameHeight, MainCanvas.instance);
	}
}
