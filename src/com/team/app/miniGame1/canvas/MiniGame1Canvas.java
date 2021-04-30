package com.team.app.miniGame1.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.team.app.GameFrame;
import com.team.app.entity.Entity;
import com.team.app.entity.UpgradeCanvas;
import com.team.app.miniGame1.entity.Background;
import com.team.app.miniGame1.entity.CountDown;
import com.team.app.miniGame1.entity.CountListener;
import com.team.app.miniGame1.entity.EdgeBorder;
import com.team.app.miniGame1.entity.Effect;
import com.team.app.miniGame1.entity.GameEventListener;
import com.team.app.miniGame1.entity.Gauge;
import com.team.app.miniGame1.entity.GaugeFillListener;
import com.team.app.miniGame1.entity.Hand;
import com.team.app.miniGame1.entity.HandMoveListener;
import com.team.app.miniGame1.entity.SpaceBar;
import com.team.app.miniGame1.entity.SpaceBarListener;
import com.team.app.miniGame1.entity.TimeEndListener;
import com.team.app.miniGame1.entity.Timer;
import com.team.app.miniGame1.entity.Twinkle;

public class MiniGame1Canvas extends UpgradeCanvas {

	public static Canvas instance;
	public int playerCount = 3;
	
	// 첫 화면부터
	private Hand[] hand = new Hand[playerCount];
	private CountDown[] countDown = new CountDown[playerCount];
	private Timer[] timer = new Timer[playerCount];
	private Gauge[] gauge = new Gauge[playerCount];
	private Background[] background = new Background[playerCount];

	// 게이지 다 차고
	private Twinkle[][] twinkles = new Twinkle[playerCount][4];
	private Effect[] effect = new Effect[playerCount];
	private SpaceBar[] spaceBar = new SpaceBar[playerCount];
	private EdgeBorder[] border = new EdgeBorder[playerCount];
	
	private Entity[][] players = new Entity[playerCount][12];
	private int entitySize;
	
	private int frameW;
	private int frameH;
	private int FPS = GameFrame.FPS;
	public final static int MAX_PUSH = 200;

	private BufferedImage buf;
	private Graphics bg;
	
//	private BGM bgm;

	// hand, countDown, time, gauge, background,
	// twinkle, effect, spaceBar, border
	// 9개

	// bg, border, time, hand, gauge,
	// twinkle, spaceBar, effect, countDown
	

	
	public MiniGame1Canvas() {
		instance = this;

//		bgm = new BGM("audio/miniGame1_2.wav");

		int[][] playerColorRGB = {{255,238,147},{92,149,255},{253,88,33}};
		for(int i = 0; i < playerCount; i++) {
			countDown[i] = new CountDown(3);

			timer[i] = new Timer(20);
			hand[i] = new Hand();								// 좌표 미정
			gauge[i] = new Gauge(50, 200, 25, 0);
			background[i] = new Background(0,0,500,900,false);	// 캔버스 사이즈
			
			border[i] = new EdgeBorder();
			twinkles[i][0] = new Twinkle(10, 120, 200, 200, 1);
			twinkles[i][1] = new Twinkle(230, 0, 300, 300, 1);
			twinkles[i][2] = new Twinkle(220, 220, 400, 400, 1);
			twinkles[i][3] = new Twinkle(/*20, 350, 400, 400, 2*/);// 하얀색 반짝이 였지만 안씀
			spaceBar[i] = new SpaceBar();						// 좌표 미정
			effect[i] = new Effect();
			
			twinkles[i][1].setTempo(20);
			twinkles[i][3].setTempo(20);
			gauge[i].drawFace(true);
			border[i].setColorRGB(
					playerColorRGB[i][0],
					playerColorRGB[i][1],
					playerColorRGB[i][2]);
			
			gauge[i].setGaugeColor(new Color(
					playerColorRGB[i][0],
					playerColorRGB[i][1],
					playerColorRGB[i][2]));
		}
		

		
//		플레이어들한테 담기
		for(int i = 0; i < playerCount; i++) {
			players[i][0] = background[i];
			players[i][1] = border[i];
			players[i][2] = timer[i];
			players[i][3] = hand[i];
			players[i][4] = gauge[i];
			players[i][5] = twinkles[i][0];
			players[i][6] = twinkles[i][1];
			players[i][7] = twinkles[i][2];
			players[i][8] = twinkles[i][3];
			players[i][9] = spaceBar[i];
			players[i][10] = effect[i];
			players[i][11] = countDown[i];
			
			// 게임시작
			countDown[i].setVisible(true);
			background[i].show();
			timer[i].show();
			gauge[i].show();
			hand[i].show();
			countDown[i].setActive(true);
			background[i].setActive(true);
			border[i].show();
			border[i].setActive(true);
		}
		entitySize = 12;
		
//		bgm.play();
		buf = new BufferedImage(1500,900,BufferedImage.TYPE_INT_RGB);
		bg = buf.getGraphics();
	}

	public void start() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				miniGame:
				while( isActiveCanvas() /*GameFrame.instance.getNowCanvas() instanceof MiniGame1Canvas*/ ) {
					for(int j = 0; j < playerCount; j++) {
						for (int i = 0; i < entitySize; i++) {
							Entity[] entitys = players[j];
							if(entitys[i].isActive())
								entitys[i].update();
						}
					}
					repaint();
					
					try {
						Thread.sleep(1000/FPS); // 16.666666
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					

				}
			}
		}).start();
	}

	@Override
	public void paint(Graphics g) {

		int[] screen = {0, 2};
		int w = frameW / playerCount;
		int h = frameH;

		int num = 0;
		for(int j = 0; j < playerCount; j++) {
//			Image buf = createImage(getWidth(), getHeight());
//			Graphics bg = buf.getGraphics();

			for (int i = 0; i < entitySize; i++) {
				Entity[] entitys = players[j];
				if (entitys[i].isVisible())
					entitys[i].paint(bg);
			}
			
			if( j == getPlayerNum() ) {
				g.drawImage(buf, w, 0, w+w, h, 0, 0, w, h, this);
			} else {
				g.drawImage(buf, w*screen[num], 0, w+w*screen[num], h, 0, 0, w, h, this);
				num++;
			}
		}
	}

	@Override
	public void otherPlayerEvent(int otherPlayer, String key, String value) {
		//다른 플레이어의 이벤트
		switch(key) {
		case "countDownOnEnd":{
			countDownOnEnd(otherPlayer);
			break;
		}
		case "handOnMove":{
			Hand hand = this.hand[otherPlayer];
			if( value.equals("right") ) {
				hand.setX(hand.getX()+50);
			} else if ( value.equals("left") ) {
				hand.setX(hand.getX()-50);
			}
			handOnMove(otherPlayer);
			break;
		}
		case "gaugeOnFilld":{
			gaugeOnFilld(otherPlayer);
			break;
		}
		case "spaceBarOnPress":{
			spaceBarOnPress(otherPlayer);
			timer[otherPlayer].setTime(Float.parseFloat(value));
			break;
		}
		case "timerOnEnd":{
			timerOnEnd(otherPlayer);
			break;
		}
		}
	}

	@Override
	public void playerSetting(int playerNum) {
		setPlayerNum(playerNum);
		setActiveCanvas(true);

		Timer timer = this.timer[playerNum];
		Hand hand = this.hand[playerNum];
		Gauge gauge = this.gauge[playerNum];
		SpaceBar spaceBar = this.spaceBar[playerNum];
		CountDown countDown = this.countDown[playerNum];
		GameEventListener gameEventListener = getGameEventListener();
		
		// 이벤트 리스너 --------------------------------------------------
		countDown.setCountListener(new CountListener() {

			@Override
			public void onEnd() {

//				timer[n].setActive(true);
//				gauge[n].setActive(true);
//				hand[n].setActive(true);
//				countDown[n].hide();
//				countDown[n].setActive(false);
//				for(int i = 0; i < playerCount; i++) {
//					border[i].hide();
//					border[i].setActive(false);	
//				}
				countDownOnEnd(playerNum);
				if( gameEventListener != null) {
					gameEventListener.event("countDownOnEnd", "true");
				}
			}
		});

		hand.setMoveListener(new HandMoveListener() {

			@Override
			public void onMove(String str) {
				handOnMove(playerNum);
				if( gameEventListener != null) {
					gameEventListener.event("handOnMove", str);
				}
			}

		});
		
		gauge.setFillListener(new GaugeFillListener() {

			@Override
			public void onFilld() {
				gaugeOnFilld(playerNum);
//				System.out.println("onFilld");
				if( gameEventListener != null) {
					gameEventListener.event("gaugeOnFilld", "true");
				}
			}
		});
		
		spaceBar.setPressListener(new SpaceBarListener(){
			
			@Override
			public void onPress() {
				spaceBarOnPress(playerNum);
				if( gameEventListener != null) {
					gameEventListener.event("spaceBarOnPress", Float.toString(timer.getTime()));
					gameEventListener.event("finish", Float.toString(timer.getTime()));
				}
//				System.out.println("spaceBarOnPress");
			}
			
		});
		
		timer.setTimeListener(new TimeEndListener() {
			
			@Override
			public void onEnd() {
				timerOnEnd(playerNum);
				if( gameEventListener != null) {
					gameEventListener.event("timerOnEnd", "true");
					gameEventListener.event("finish", "0.0");
				}
//				System.out.println("timerOnEnd");
			}
		});
		
		// 키보드 이벤트 --------------------------------------------------
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				switch (key) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_RIGHT:
					if( !countDown.isActive() )
						hand.moveKey(key);
					break;
				case KeyEvent.VK_SPACE:
					spaceBar.spacePush(key);
					break;
				}
			}
		});
		countDown.show();
	}
	
	private void countDownOnEnd(int n) {
		timer[n].setActive(true);
		gauge[n].setActive(true);
		hand[n].setActive(true);
		countDown[n].hide();
		countDown[n].setActive(false);
		for(int i = 0; i < playerCount; i++) {
			border[i].hide();
			border[i].setActive(false);	
		}
	}

	private void handOnMove(int n) {
		Gauge gauge = this.gauge[n];
		
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
		// 테스트 마지막 * 10 (speed) 제거 =================================
//		double plus = gauge.getMAX_HEIGHT() / MAX_PUSH * 50;
		double plus = gauge.getMAX_HEIGHT() / MAX_PUSH;
		gauge.setSize(gauge.getWidth(), gauge.getHeight() + plus);
	}

	private void gaugeOnFilld(int n){
		border[n].show();
		border[n].setActive(true);
		spaceBar[n].show();
		spaceBar[n].setActive(true);
		hand[n].setActive(false);
		gauge[n].changeImage("res/game1/gaugeImg2.png");
	}
	
	private void spaceBarOnPress(int n){
		effect[n].show();
		effect[n].setActive(true);
		border[n].hide();
		spaceBar[n].hide();
		hand[n].hide();
		background[n].setFinish(true);
		gauge[n].hide();
		gauge[n].setActive(false);
		for(int j = 0; j < 4; j++) {
			twinkles[n][j].setActive(true);
			twinkles[n][j].show();
		}
		timer[n].stop();
	}
	
	private void timerOnEnd(int n){
		for (int i = 0; i < entitySize; i++) {
			players[n][i].setActive(false);
		}
	}

	public void setting(int frameW, int frameH) {
		this.frameW = frameW;
		this.frameH = frameH;
	}
	
}

// 캔버스 작성, 오류 수정 추가 사항 설명하기

// 황병준, 허민강 - 방향키 누르기
// 최은희, 황영걸 - 차 피하기
// 주말포함 이번주까지

// 1. 남은거 메인화면
// 2. 최종점수판 먼저 - 시간나면 스테이지 점수판 만들기
// 3. 미니게임 만들기
// 게임소개
