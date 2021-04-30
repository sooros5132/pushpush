package com.team.app.miniGame2.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.miniGame2.canvas.MiniGame2Canvas;

public class Timer extends Entity {

	// 60초부터 시작해서 1초씩 깎아내려감
	// 0초가 되는 순간 Timeout
	// 필요한 것 : for문(반복해서 시간감소),스레드, sleep함수
	// 캔버스,프레임 만들어서 띄워보기(우선은 띄우기만)
	// 60에서 숫자 내려갈때마다 기존숫자를 지우고 그 위에 새로운 숫자 출력
	// repaint 같은 메서드 사용

	private float time = 0;
	private double minus;
	private Image img;
	private Image dotImg;
	
	private TimeEndListener timeListener;

	public void setTimeListener(TimeEndListener timeListener) {
		this.timeListener = timeListener;
	}

	public Timer() {
		this(60);
	}

	public Timer(int setTime) {
		time = setTime;
		minus = 1.0 / MiniGame2Canvas.fps;

		try {
			img = ImageIO.read(new File("res/number1.png"));
			dotImg = ImageIO.read(new File("res/number_dot2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setWidth(img.getWidth(null) / 11);
		setHeight(img.getHeight(null));
		
		setX(10);
		setY(10);
	}

	public void paint(Graphics g) {
		int w = (int)getWidth();
		int h = (int)getHeight();
		
		int tens = (int)(time / 10);
		int ones = (int)time % 10;
		int tenths = (int)(time*10) % 10;
		int hundredths = (int)(time*100) % 10;

		int viewX = 50;
		int viewY = 60;
		int dotW = 10;
		int dotH = 10;
		
		int x = (int)getX();
		int y = (int)getY();
		int dx2 = x + viewX;
		int dy2 = y + viewY;
		
		int correction = 15;
		
//		g.setColor(new Color(255, 255, 255));
//		g.fillRect(x, y, viewX*4+dotW+correction, viewY);
		
//		10의 자리
		if(tens != 0) 
			g.drawImage(img, x, y, dx2, dy2, 
					tens*w, 0, (tens+1)*w, h,
					MiniGame2Canvas.instance);
//		1의 자리
		g.drawImage(img, viewY*1+x-correction, y, viewY*1+dx2-correction, dy2, 
				ones*w, 0, (ones+1)*w, h, 
				MiniGame2Canvas.instance);
// 		소수점
		g.drawImage(dotImg, viewY*2+x-correction*2, dy2-dotH-3, viewY*2+x+dotW-correction*2, dy2-3,
				w * 10, 0, w * 10 + 25, 25,
				MiniGame2Canvas.instance);
//		0.1
		g.drawImage(dotImg, viewY*2+x+dotW-correction*2, y, viewY*2+dx2+dotW-correction*2, dy2, 
				tenths*w, 0, (tenths+1)*w, h, 
				MiniGame2Canvas.instance);
//		0.01
		g.drawImage(dotImg, viewY*3+x+dotW-correction*3, y, viewY*3+dx2+dotW-correction*3, dy2, 
				hundredths*w, 0, (hundredths+1)*w, h, 
				MiniGame2Canvas.instance);

//		테스트
//		System.out.printf("%d, %d, %d, %d, %d, %d, %d, %d\n", dx1, dy1, dx2, dy2, tens*w, 0, (tens+1)*w, h);
	}

	@Override
	public void update() {
		if (isActive())
			time -= minus;

		if (time <= 0) {
			if (timeListener != null) {
				timeListener.onEnd();
			}
			setActive(false);
//			System.out.println("end");
		}
	}

	public float getTime() {
		return this.time;
	}

	public void setTime(int times) {
		this.time = times;
	}

	public void stop() {
		setActive(false);
	}
	
	

}