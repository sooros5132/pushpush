package com.team.app.miniGame1.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.entity.Entity;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;

public class Twinkle extends Entity {

	private Image img;
	private int tempo = 0;
	private int tempoCount = 20;
	private int num = 1;

	public Twinkle() {

	}

	public Twinkle(double x, double y, double w, double h, int num) {
		// 50, 100, 200, 200
		// 400, 50, 300, 300
		// 250, 300, 400, 400
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		this.num = num;
		switch (num) {
		case 1:
			try {
				img = ImageIO.read(new File("res/game1/Twinkle.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				img = ImageIO.read(new File("res/game1/Twinkle2.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			try {
				img = ImageIO.read(new File("res/game1/Twinkle.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

	}

	@Override
	public void update() {
		//

	}

	@Override
	public void paint(Graphics g) {

		int x = (int) getX();
		int y = (int) getY();
		int w = (int) getWidth();
		int h = (int) getHeight();
		if(num != 2) {
			if (tempo == 0) {
				try {
					img = ImageIO.read(new File("res/game1/Twinkle_copy.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tempo = tempoCount * 2;// tempoCount=20
			} else if (tempo == 20) {
				try {
					img = ImageIO.read(new File("res/game1/Twinkle.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		g.drawImage(img, x, y, w, h, MiniGame1Canvas.instance);
		tempo--;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

}
