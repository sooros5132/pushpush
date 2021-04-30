package com.team.app.miniGame2.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.miniGame2.canvas.MiniGame2Canvas;

public class SpaceBar extends Entity {
	private static final int SPACEBARKEY = KeyEvent.VK_SPACE;
	
	private Image spaceImg; //스페이스 이미지
	private int imgTempo = 30; //이미지 변경 속도조절 인덱스 
	private int imgIndex; //이미지 변경을 위한 인덱스
	
	private SpaceBarListener spaceBarListener;
	
	//인터페이스
	public void setPressListener(SpaceBarListener spaceBarListener) {
		this.spaceBarListener = spaceBarListener;
	}
	
	//생성자
	public SpaceBar() {
		this(130, 550);
	}
	
	
	public SpaceBar(int x, int y) {
		this.setX(x);
		this.setY(y);
		
		try {
			spaceImg = ImageIO.read(new File("res/spaceBar.png"));//동기식 이미지 파일읽기
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setWidth(spaceImg.getWidth(MiniGame2Canvas.instance));
		this.setHeight(spaceImg.getHeight(MiniGame2Canvas.instance));
	
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
		} else {
			imgTempo--;
		}
				
		
		int imgW = (int)this.getWidth()/2;
		int imgH = (int)this.getHeight();
		
		
	
		int dx1 = (int)this.getX();
		int dy1 = (int)this.getY();

		
		int dx2 = (int)this.getX() + imgW/2;
		int dy2 = (int)this.getY() + imgH/2;
		
		int sx1 = imgW * imgIndex;
		int sx2 = imgW * imgIndex + imgW;
		
		if(this.isActive() && this.isVisible()) {
			g.drawImage(spaceImg, dx1, dy1, dx2, dy2,
					sx1, 0, sx2, imgH,
					MiniGame2Canvas.instance);
			
			//g.fillRect(dx1, dy1, (int)imgW, (int)imgH);
			/*
			 dx1, dy1: 출력화면 시작점, dx2, dy2: 출력화면 마지막점
			 sx1, sy1: 원본 파일의 시작점, sx2, sy2: 원본 파일의 마지막점
				
			 */
		}
	}

	
	public void spacePush(int key) {
		if(key==SPACEBARKEY && this.isActive() && this.isVisible()) {
			spaceBarListener.onPress();
			//this.setActive(false);
			//this.setVisible(false);
			//발동
		}

	}
}