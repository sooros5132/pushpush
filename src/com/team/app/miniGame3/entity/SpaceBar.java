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

public class SpaceBar extends Entity {
	private static final int SPACEBARKEY = 32;
	
	private Image spaceImg; //스페이스 이미지
	private int imgTempo = 30; //이미지 변경 속도조절 인덱스 
	private int imgIndex; //이미지 변경을 위한 인덱스
	
	private Clip clip; //사운드 클립
	
	private SpaceBarListener spaceBarListener;
	
	//인터페이스
	public void setPressListener(SpaceBarListener spaceBarListener) {
		this.spaceBarListener = spaceBarListener;
	}
	
	
	//효과음
	public void playSound(String pathName, boolean isLoop) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
			clip.open(ais);
			clip.start();
			if(isLoop)
				clip.loop(Clip.LOOP_CONTINUOUSLY); //소리 무한반복
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//생성자
	public SpaceBar() {
		this(540, 600);
	}
	
	
	public SpaceBar(int x, int y) {
		this.setX(x);
		this.setY(y);
		
		try {
			spaceImg = ImageIO.read(new File("res/spaceBar.png"));//동기식 이미지 파일읽기
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setWidth(spaceImg.getWidth(MiniGame3Canvas.instance));
		this.setHeight(spaceImg.getHeight(MiniGame3Canvas.instance));
	
	}


	@Override
	public void update() {
	}

	@Override
	public void paint(Graphics g) {
		if(imgTempo == 0) {
			imgIndex++;
			imgIndex = imgIndex % 2;
			
			imgTempo = 20;
		}
		else {
			imgTempo--;
		}
				
		
		int imgW = (int)this.getWidth()/2;
		int imgH = (int)this.getHeight();
		
		
	
		int dx1 = (int)this.getX();
		int dy1 = (int)this.getY();

		
		int dx2 = (int)this.getX() + imgW;
		int dy2 = (int)this.getY() + imgH;
		
		int sx1 = imgW * imgIndex;
		int sx2 = imgW * imgIndex + imgW;
		
		if(this.isActive() && this.isVisible()) {
			g.drawImage(spaceImg, dx1, dy1, dx2, dy2,
					sx1, 0, sx2, imgH,
					MiniGame3Canvas.instance);
			
			//g.fillRect(dx1, dy1, (int)imgW, (int)imgH);
			/*
			 dx1, dy1: 출력화면 시작점, dx2, dy2: 출력화면 마지막점
			 sx1, sy1: 원본 파일의 시작점, sx2, sy2: 원본 파일의 마지막점
				
			 */
		}
	}
	
	
	public void spacePush() {
		if(this.isActive() && this.isVisible()) {
			spaceBarListener.onPress();
			SoundEffect.play("audio/finish.wav", false); //오디오 파일
			this.setActive(false);
			this.setVisible(false);
		}

	}
}
