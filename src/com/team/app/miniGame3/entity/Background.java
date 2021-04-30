package com.team.app.miniGame3.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.GameFrame;
import com.team.app.miniGame3.canvas.MiniGame3Canvas;

public class Background extends Entity {

	private Image img;
	private int index;
	private double tempo;
	private double speedUp;
	private double speed;
//
//	public void setSpeedUp(double speedUp) {
//		this.speedUp = speedUp * 100;
//		System.out.println(this.speedUp);
//	}

	public Background() {
		
		try {
			img = ImageIO.read(new File("res/game3/backGround.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/// 諛깃렇�씪�슫�뱶 �뒪�뵾�뱶瑜� 罹붾쾭�뒪�뿉�꽌

		setWidth(6000 / 4);
		setHeight(900);
		tempo = 10;
		speedUp = 7;

	}

	public void setSpeed(double speed) {
		if (speed <= 3)
			this.speed = speed;
		if(speed ==0.5) {
			speedUp =10;
//			System.out.println("speedReset");
		}
	}

	// Active �븷�뻹 �떎�뻾. 罹붾쾭�뒪�뿉�꽌 �떎�뻾 x
	@Override
	public void update() {
		//System.out.println(speed);
		if (speed < 1.99 ||speed!=0)
			speedUp -= 0.01;
		if (tempo <= 0) {
			index++;
			tempo = speedUp - speed;
		}
//		System.out.println(tempo);

		if (index == 4)
			index = 0;
		tempo--;



	}

	@Override
	public void setActive(boolean active) {
		
		super.setActive(active);
		if(active = false) {
			speedUp=0;
		}
	}

	@Override
	public void paint(Graphics g) {

		int canvasW = GameFrame.frameWidth;
		int canvasH = GameFrame.frameHeight;
		int w = (int) getWidth();
		int h = (int) getHeight();

		g.drawImage(img, 0, 0, canvasW, canvasH, w * index, 0, w + w * index, h, MiniGame3Canvas.instance);

		double rectX = 1300;
		double rectY = 0;
		double rectW =200;
		double rectH = 600;
		g.setColor(Color.black);
		g.fillRect((int) rectX, (int) rectY, (int) rectW, (int) rectH);
//		g.fillRect(x, y, width, height);
	}

}
