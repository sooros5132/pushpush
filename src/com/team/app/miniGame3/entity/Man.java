package com.team.app.miniGame3.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.team.app.entity.SoundEffect;
import com.team.app.miniGame3.canvas.MiniGame3Canvas;

public class Man extends Entity {
	private Clip clip; //사운드 클립
	
	private static final int LEFT = 37;
	private static final int RIGHT = 39;
//	private static final int SPACE = 32;
	
	private double FinishX;
	private double FinishY;
	private double vx;
	private double vy;

	private static final int wd = 500;

	private Image img;
	private int index;
	private boolean isCollision;
	private boolean isLast;
	private boolean isFinish;

	private int collisionTime = 10;

	private int tempo;
	private int size;
	
	//인터페이스
	private ManMoveListener manMoveListener;
	public void setManMoveListenr(ManMoveListener manMoveListener) {
		this.manMoveListener = manMoveListener;
	}
	
	public Man() {
		this(750, 700, 756 / 4, 195);

	}
	
	//효과음
//	public void playSound(String pathName, boolean isLoop) {
//		try {
//			clip = AudioSystem.getClip();
//			File audioFile = new File(pathName);
//			AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
//			clip.open(ais);
//			clip.start();
//			if(isLoop)
//				clip.loop(Clip.LOOP_CONTINUOUSLY); //소리 무한반복
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	

	// 충돌>
	public void setCollision(boolean isCollision) {
		this.isCollision = true;
		SoundEffect.play("audio/collision.wav", false); //오디오 파일
	}

	public Man(double x, double y, double w, double h) {
		try {
			img = ImageIO.read(new File("res/game3/man.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		size = 2;

		index = 0;
		tempo = 5;
		isCollision = false;
		isLast =false;
		isFinish = false;


	}

	@Override
	public void update() {
		// 걷기 속도 조절
		if (tempo == 0) {
			if (isCollision)
				// 충돌 이미지 유지시간
				if (collisionTime == 0) {
					collisionTime = 10;
					index = 0;
					isCollision = false;
				} else {
					index = 3;
					collisionTime--;
				}
			else
				index = 1 - index;
			tempo = 5;
		}
		tempo--;
		
		if(FinishY-1 <= getY() && getY() <= FinishY+1  && FinishX -1 <=getX() && getX()  <=FinishX +1)
			index =2;
		else {
			if(isLast) {
				double x = getX();
				double y = getY();
				double w = FinishX - x;
				double h = FinishY - y;
				double d = Math.sqrt(w*w + h*h);
				vx = w/d;
				vy = h/d;
				setX(x+vx);
				setY(y+vy);
			}
			
		}
		
		
		if(this.getY() <= (int)FinishY+20) {
			
			manMoveListener.goMan();
			
		}
			

	}

	@Override
	public void paint(Graphics g) {
		int x = (int) getX();
		int y = (int) getY();
		int w = (int) getWidth();
		int h = (int) getHeight();

		int dx1 = x - w * size / 2; // 캐릭터 중앙으로 포인트 옮김
		int dy1 = y - h * size / 2;
		int dx2 = dx1 + w * size;
		int dy2 = dy1 + h * size;
		int sx1 = w * index;
		int sy1 = 0;
		int sx2 = w + w * index;
		int sy2 = h;
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame3Canvas.instance);
	}
	
	public void go() {
		if (isFinish) {
			isLast =true;
			FinishX = 750;
			FinishY = 400;
		}
	}
	
	
	public void move(int key) {

		double x = getX();
		double y = getY();
		
		switch (key) {
		case LEFT:
			if (x - wd >= 0  && !isLast)
				setX(x - wd);
			SoundEffect.play("audio/moveMan.wav", false); //오디오 파일
			break;
		case RIGHT:
			if (x + wd <= 1500 && !isLast)
				setX(x + wd);
			SoundEffect.play("audio/moveMan.wav", false); //오디오 파일
			break;
		
//		case SPACE:
//			if (isFinish) {
//				isLast =true;
//				FinishX = 750;
//				FinishY = 400;
//			}
//			break;
		}
		

	}


	public void setFinish(boolean isFinish) {
		this.isFinish = true;
	}

	public boolean isFinish() {
		return isFinish;
	}
	
	
	
}
