package com.team.app.miniGame2.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import com.team.app.entity.SoundEffect;
import com.team.app.miniGame2.canvas.MiniGame2Canvas;

public class Character extends Entity {

	private static Image img;
	private static int imgW;
	private static int imgH;

	private SoundEffect se;

	private int index;
	
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private Clip clip; // 사운드 클립
	
	private boolean activeSound = false;

	static {
		try {
			img = ImageIO.read(new File("res/game2/TB_40_2.png"));
			imgW = img.getWidth(null) / 6;
			imgH = img.getHeight(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Character() {
		this(0, 0, 100, 100);
	}

	public Character(double x, double y) {
//		setX(x);
//		setY(y);
		this(x, y, 0, 0);
	}

	public Character(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		index = 4;
		se = new SoundEffect();
	}

	public void changeImg(int key) {
//		System.out.println(key);
		switch (key) {
		case KeyEvent.VK_LEFT:
			index = 0;
			if(activeSound)
				SoundEffect.play("audio/jap.wav", false);
			break;
		case KeyEvent.VK_UP:
			index = 1;
			if(activeSound)
				SoundEffect.play("audio/hook.wav", false);
			break;
		case KeyEvent.VK_RIGHT:
			index = 2;
			if(activeSound)
				SoundEffect.play("audio/jap.wav", false);
			break;
		case KeyEvent.VK_DOWN:
			index = 3;
			if(activeSound)
				SoundEffect.play("audio/down.wav", false);
			break;
		default:
			index=5;
			if(activeSound)
				SoundEffect.play("audio/Beeee_1.wav", false);
		}
	}

	@Override
	public void update() {

	}

	@Override
	public void paint(Graphics g) {

		int width = (int) getWidth();
		int height = (int) getHeight();
		int x = (int) getX();
		int y = (int) getY();

		int dx1 = x;
		int dy1 = y;
		int dx2 = x + width;
		int dy2 = y + height;

		int sx1 = index * imgW;
		int sy1 = 0;
		int sx2 = index * imgW + imgW;
		int sy2 = imgH;

		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame2Canvas.instance);

	}

	public boolean isActiveSound() {
		return activeSound;
	}

	public void setActiveSound(boolean activeSound) {
		this.activeSound = activeSound;
	}

	
	
}
