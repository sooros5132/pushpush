package com.team.app.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.main.canvas.MainCanvas;

public class DrawImg extends Entity {

	private Image img;
	private int imgW;
	private int imgH;
	private String src;

	public DrawImg() {
		this(0, 0, 0, 0, null);
	}

	public DrawImg(String src) {
		this(0,0,0,0,src);
	}
	
	public DrawImg(double x, double y, double w, double h, String src) {
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		this.src = src;
		try {
			img = ImageIO.read(new File(this.src));
			imgW = img.getWidth(null);
			imgH = img.getHeight(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		int x = (int) getX();
		int y = (int) getY();
		int w = (int) getWidth();
		int h = (int) getHeight();
		
//		g.setColor(Color.white);
//		g.fillRect(0, 0, 1500, 900);
		
		g.drawImage(img, x, y, x+w, y+h, 0, 0, imgW, imgH, MainCanvas.instance);
	}

	public int getImgW() {
		return imgW;
	}

	public int getImgH() {
		return imgH;
	}

	public String getSrc() {
		return src;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	public void changeImg(String src) {
		this.src = src;
		try {
			img = ImageIO.read(new File(src));
			imgW = img.getWidth(null);
			imgH = img.getHeight(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
