package com.team.app.miniGame2.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import com.team.app.miniGame2.canvas.MiniGame2Canvas;

public class Background extends Entity {

	private Image img;
	private Image img_audience;
	private Image img_finish;
	
	private Clip clip;

	private Boolean finish = false;

	int fHeight = 125;

	private int dyOffset;
	private int syOffset;
	private int tempo = 10;

	public Boolean getFinish() {
		return finish;
	}

	public void setFinish(Boolean finish) {
		this.finish = finish;
	}

	public Background() {
		this(0, 0, 100, 100, false);
	}

	public Background(double x, double y, double w, double h, Boolean finish) {
		this.finish = finish;
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		try {
			img = ImageIO.read(new File("res/game2/background_yg1.png"));
			img_audience = ImageIO.read(new File("res/game2/audience2-removebg-preview2.png"));
			img_finish=ImageIO.read(new File("res/game2/champion.png"));
//			img_finish=ImageIO.read(new File("res/game2/champion2.png"));
//			img_finish=ImageIO.read(new File("res/game2/champion3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {

		int dx = (int) getX();// 0
		int dy = (int) getY();// 0

		int dx2 = (int) getWidth();// 1500
		int dy2 = (int) getHeight();// 300

		g.drawImage(img, dx, dy, dx2, dy2, MiniGame2Canvas.instance);
		if (finish) {
			int fWidth = 572;
			fHeight = 125; // �뼐�룄 0�뿉�꽌遺��꽣 萸� �뼱�뼸寃� �빐以섏빞�맆嫄곌컳���뜲

			int fdx = 0;
			int fdy = 300 - dyOffset;
			int fdx2 = 1500;
			int fdy2 = 300;

			int sx1 = 0;
			int sy1 = 0;
			int sx2 = fWidth;
			int sy2 = syOffset;
			g.drawImage(img_audience, fdx, fdy, fdx2, fdy2, sx1, sy1, sx2, sy2, MiniGame2Canvas.instance);
			g.drawImage(img_finish, 500, 0, 1000, 300, 0, 0, 345, 250, MiniGame2Canvas.instance);
			
		}
		// �닾紐낅룄 �꽕�젙
//		g.setColor(new Color(0,0,0,0));  //0,0,0�씠 寃����깋�쑝濡� �꽕�젙�맂嫄곗빞 setColor(Color.black)�씠�옉 媛숈�嫄곗� �뿬湲곗뿉 �닾紐낅룄留� 異붽��맂寃�
//		g.fillRect(0, 0, 1500, 300);
	}
	public void setFinishImage(String src) {
		try {
			img_finish=ImageIO.read(new File(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public void changeImage() {
//		try {
//			img = ImageIO.read(new File("res/game2/tbh1.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public void update() {
		if (getFinish()) {

			// dyOffset�씠 syOffset�씠�옉 媛숈씠 利앷��븯�떎媛� syOffset�씠利앷� �븞�븯硫� dy�룄 利앷��븯吏��븡寃� 留뚮벉
			if (syOffset != fHeight) {
				syOffset++;
				if (dyOffset != 300)
					dyOffset = dyOffset + 2;
			}
		}
	}
	
}
