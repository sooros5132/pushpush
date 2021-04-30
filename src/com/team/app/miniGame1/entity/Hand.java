package com.team.app.miniGame1.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.team.app.GameFrame;
import com.team.app.entity.Entity;
import com.team.app.entity.SoundEffect;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;

public class Hand extends Entity{
	private static final int LEFT = KeyEvent.VK_LEFT;
	private static final int RIGHT = KeyEvent.VK_RIGHT;
	
	private Image img; //손이미지
	private Clip clip; //사운드 클립
	private int keyTempo;
	
	boolean direction = true; //손움직임반향
	private double per;
	private HandMoveListener handMoveListener;
	
	
	
	//인터페이스
	public void setMoveListener(HandMoveListener handMoveListener) {
		this.handMoveListener = handMoveListener;
	}
	
	//생성자(초기화)
	public Hand() {
		this(200, 350);
		
	}
	//생성자2(초기화)
	public Hand(int x, int y) {
		
		this.setX(x);
		this.setY(y);
		
		try {
			img = ImageIO.read(new File("res/game1/hand.png"));//동기식 이미지 파일읽기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setWidth(img.getWidth(MiniGame1Canvas.instance));
		this.setHeight(img.getHeight(MiniGame1Canvas.instance));
		
//		this.setVisible(true);//활성화
//		this.setActive(true);//보여짐;
	}
	

	
//	//효과음
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
	
	//키누르기
	public void moveKey(int key) {
		
		if(key == LEFT ) {
			direction = false; //왼쪽반향으로 변경
		}else if(key == RIGHT) {
			direction = true; //오른쪽반향으로 변경
		}
			
		moveHand();
	}
	
	//조건맞을시 움직임
	public void moveHand() {
		int MinX = 50; //위치 x가 가질 수 있는 최소값
		int MaxX = GameFrame.frameWidth / GameFrame.totalPlayerNum - 150; //위치 x가 가질 수 있는 최댓값
		
		if(this.isActive()) {
			if(direction == true) {
				if(getX()+ 50 > MaxX) {
					return;
				}
				
				if(handMoveListener != null)
					handMoveListener.onMove("right");
				//오른쪽 반향으로
				setX(getX()+50);
				//setY(getY()-10);
		

			}
			
			if(direction == false) {
				if(getX()-50 < MinX) {
					return;
				}

				if(handMoveListener != null)
					handMoveListener.onMove("left");
				//왼쪽반향으로
				setX(getX()-50);
				//setY(getY()+10);
			}
			SoundEffect.play("audio/handSound.wav", false); //오디오 파일
		}
		if(per >= MiniGame1Canvas.MAX_PUSH) {
			this.setActive(false); //안보임
			this.setActive(false); //활성화 중지	
		}
		 
	}
	
	
	public void update() {
	}
	
	public void paint(Graphics g) {
		if(this.isActive() && this.isVisible()) {
			g.drawImage(img, (int)getX(), (int)getY(), MiniGame1Canvas.instance);
		}
	}
}