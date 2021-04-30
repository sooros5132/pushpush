package com.team.app.result.canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.team.app.entity.DrawImg;
import com.team.app.entity.UpgradeCanvas;
import com.team.app.main.entity.Button;
import com.team.app.result.entity.PlayerImg;
import com.team.app.result.entity.Text;

public class ResultCanvas extends UpgradeCanvas {

	private DrawImg background = new DrawImg(0, 0, 1500, 900,"res/result/fg_finish1.png");
	private PlayerImg[] players = new PlayerImg[3];
	private Button title = new Button(750 - 150, 150, 300, 120, "승리");
//	private List<Float> playerResult = new ArrayList<>();
	private float[] playerResult = new float[3];
	private float first = 0;
	private String[] playerImgSrc = {"res/result/player1.png","res/result/player2.png","res/result/player3.png"};
	private String[] playerImg2Src = {"res/result/player1_1.png","res/result/player2_1.png","res/result/player3_1.png"};
	private int[][] playerColorRGB = {{255,238,147},{92,149,255},{253,88,33}};
	private Button[] playerTime = new Button[3]; 

	private BufferedImage buf;
	private Graphics bg;
	private Color canvasBgColor = Color.black;
	
	public ResultCanvas() {
		players[0] = new PlayerImg(0,0,160,210,"res/result/player1.png");
		players[1] = new PlayerImg(0,0,160,210,"res/result/player2.png");
		players[2] = new PlayerImg(0,0,160,210,"res/result/player3.png");
		playerTime[0] = new Button(0,0,160,210,"0.0");
		playerTime[1] = new Button(0,0,160,210,"0.0");
		playerTime[2] = new Button(0,0,160,210,"0.0");

		title.setFontSize(90);
		title.textMove(59, 57);
		title.setBgColor(new Color(0,0,0,0));
		
		buf = new BufferedImage(1500,900,BufferedImage.TYPE_INT_RGB);
		bg = buf.getGraphics();
	}
	
	public void start() {
		
		new Thread(()->{
			while (true) {
//				System.out.println("update");
				for(int i = 0; i < 3; i++)
					players[i].update();
				
				repaint();
				
				try {
					Thread.sleep(1000 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
	
	@Override
	public void paint(Graphics g) {
//		Image buf = this.createImage(this.getWidth(), this.getHeight());
//		Graphics bg = buf.getGraphics();

		//.setColor(Color.white);
		//.fillRect(, y, width, height);
		
		bg.setColor(canvasBgColor);
		bg.fillRect(0, 0, 1500, 900);
		
		background.paint(bg);
		
		for(int i = 0; i < 3; i++)
			if(players[i] != null) {
				players[i].paint(bg);
				playerTime[i].paint(bg);
			}
		
		title.paint(bg);
		
		g.drawImage(buf, 0, 0, this);
	}

	public void setPlayerResult(String result) {
		String[] temp = result.split("&");
		System.out.println(result);
		
		for(int i = 0; i < temp.length; i++) {
			float time = Float.parseFloat(temp[i]);
//			if( time >= 0 )
//			playerResult.add(Float.parseFloat(temp[i]));
			playerResult[i] = time;

			if( time > first )
				first = time;
		}
			
		int[] screen = {-270, 260};
		int[] textScreen = {-300, 250};
		int x = 1500 / 2 - 160 / 2;
		int y = 900 / 2 - 210 / 2;
		int num = 0;
		
		for( int i = 0; i < 3; i++ ) {
//			if( playerResult.get(i) == first ) {
			if( playerResult[i] == first ) {
				players[i].setSize(players[i].getWidth()+74, players[i].getHeight()+100);
				players[i].move(x-37, y-50);
				players[i].setFirst(true);
				
				canvasBgColor  = new Color(
						playerColorRGB[i][0],
						playerColorRGB[i][1],
						playerColorRGB[i][2]);
				
				title.setFontColor(new Color(
						playerColorRGB[i][0],
						playerColorRGB[i][1],
						playerColorRGB[i][2]));
				playerTime[i].move(x-25,y+260);
//				players[i] = new PlayerImg(0,0,180,210,playerImgSrc[i]);
//				playerTime[i].setText(String.format("%4.1f", playerResult[i]));
			} else {
				players[i] = new PlayerImg(0,0,180,210,playerImg2Src[i]);
				players[i].move(x+screen[num], y);
				players[i].setSize(players[i].getImgW()/1.5, players[i].getWidth()/1.5);
				playerTime[i].move(x+textScreen[num],y+210);
				num++;
			}
			playerTime[i].setFontSize(60);
			playerTime[i].textMove(59, 57);
			playerTime[i].setBgColor(new Color(0,0,0,0));
			playerTime[i].setFontColor(new Color(0xaeaeae));
//			playerTime[i].setText(Float.toString(playerResult[i]));
			playerTime[i].setText("");
		}
		
		System.out.println(Arrays.toString(playerResult));
		System.out.println(first);
//		playerResult.stream().filter(predicate)
			
	}
}