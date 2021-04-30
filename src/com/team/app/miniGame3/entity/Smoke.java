package com.team.app.miniGame3.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.miniGame3.canvas.MiniGame3Canvas;

public class Smoke extends Entity {

	private Image img;

	public Smoke() {

		setX(0);
		setY(0);

		try {
			img = ImageIO.read(new File("res/game3/안개.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void update() {

	}

	@Override
	public void paint(Graphics g) {
		int x = (int) getX();
		int y = (int) getY();
		int width = MiniGame3Canvas.instance.getWidth();
		
		int height = MiniGame3Canvas.instance.getHeight();
		
		g.drawImage(img, x, y, width, height, MiniGame3Canvas.instance);

	}

}
