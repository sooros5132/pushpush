package com.team.app.miniGame1.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.team.app.entity.Entity;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;

public class Background extends Entity {

	private Image img;
	private Image[] twinkles = new Image[3];
	
//	private BackgroundListener backgroundListener;
//
//	public void setFinishImageListener(BackgroundListener backgroundListener) {
//		this.backgroundListener = backgroundListener;
//	}

	// 筌뤴뫀諭? 占쎌뵠沃섎챷占쏙옙?벥 占쎈뻻占쎌삂,占쎄국 占쎌맄燁삼옙
//	private int dx1;
//	private int dy1;
//	private int dx2;
//	private int dy2;

	// 椰꾧퀣占쏙쭪占?
//	private static int sWidth = 255;
//	private static int sHeight = 255;

	// 繹먮뗀嫄ワ쭪占? 野껓옙 dx,dy
//	private static int fWidth = 700;// 331;//占쎈돗占쎈빍占쎈룴 占쎄텢筌욊쑴?벥 占쎄섐?뜮袁⑹뵠占쎌쁽 筌뤴뫀諭? dx2 ?넫?슦紐?
//	private static int fHeight = 700;// 510;//占쎈돗占쎈빍占쎈룴 占쎄텢筌욊쑴?벥 占쎈꼥占쎌뵠占쎌뵠占쎌쁽 筌뤴뫀諭? dy2 ?넫?슦紐?
	// 700,700 占쎌몵嚥∽옙 dx,dy 占쎌삜?⑨옙 占쎌뵠沃섎챷占쏙옙?뮉 ?넫?슦紐댐옙毓억옙?뵠 占쎌궎?뵳?딉옙占쎄섯嚥∽옙 揶쏉옙占쎌죬占쎈뼄占쎈쓠占쎈뮉占쎈쑓 占쎌넞 筌욁끇?봺筌욑옙?

	private Boolean finish = false;

	public Boolean getFinish() {
		return finish;
	}

	public void setFinish(Boolean finish) {
		this.finish = finish;
	}

	public Background() {
		this(0, 0, 0, 0, false);
	}

	public Background(double x, double y, double w, double h, Boolean finish) {
		this.finish = finish;
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		// 占쎈뻻占쎌삂占쎌뵠沃섎챷占?
		try {
			img = ImageIO.read(new File("res/game1/startBackground.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {

		// 占쎈あ 占쎌뵠沃섎챷占쏙옙?벥 ?넫?슦紐닷첎占? 揶쏆늾苑?? 占쎈퓦占쎈뼒占쎈뼒占쎌벥 x,y?몴占? 占쎌뵠占쎌뒠占쎈릭占쎌쁽
		// 占쎌뵠沃섎챷占쏙옙諭억옙?벥 ?넫?슦紐?
		int dx = (int) getX();// 0
		int dy = (int) getY();// 0

		int w = (int) getWidth();
		int h = (int) getHeight();

		if (finish) {
			changeImage();
			// 占쎌뵠沃섎챷占?. x?넫?슦紐?,y?넫?슦紐?, 占쎄섐?뜮占?,占쎈꼥占쎌뵠
			g.drawImage(img, dx, dy, w, h, MiniGame1Canvas.instance);
		} else
			g.drawImage(img, dx, dy, w, h, MiniGame1Canvas.instance);
	}

	public void changeImage() {
		try {
			img = ImageIO.read(new File("res/game1/finishBackground.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
