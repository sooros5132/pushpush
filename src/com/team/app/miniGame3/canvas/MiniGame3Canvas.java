package com.team.app.miniGame3.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.TreeSet;

import com.team.app.entity.UpgradeCanvas;
import com.team.app.miniGame1.entity.GameEventListener;
import com.team.app.miniGame1.entity.TimeEndListener;
import com.team.app.miniGame3.entity.Background;
import com.team.app.miniGame3.entity.Cars;
import com.team.app.miniGame3.entity.CountDown;
import com.team.app.miniGame3.entity.CountListener;
import com.team.app.miniGame3.entity.EdgeBorder;
import com.team.app.miniGame3.entity.Effect;
import com.team.app.miniGame3.entity.Effect2;
import com.team.app.miniGame3.entity.Entity;
import com.team.app.miniGame3.entity.FinishLine;
import com.team.app.miniGame3.entity.Gauge;
import com.team.app.miniGame3.entity.GaugeFillListener;
import com.team.app.miniGame3.entity.LineStopListener;
import com.team.app.miniGame3.entity.Man;
import com.team.app.miniGame3.entity.ManMoveListener;
import com.team.app.miniGame3.entity.Smoke;
import com.team.app.miniGame3.entity.SpaceBar;
import com.team.app.miniGame3.entity.SpaceBarListener;
import com.team.app.miniGame3.entity.SpeedGauge;
import com.team.app.miniGame3.entity.TimeOver;
import com.team.app.miniGame3.entity.Timer;

public class MiniGame3Canvas extends UpgradeCanvas {

	public static Canvas instance;
	public int playerCount = 3;
	// private Set<Integer> players = new HashSet<>();
	private int[][] playerColorRGB = { { 255, 238, 147 }, { 92, 149, 255 }, { 253, 88, 33 } };

	private final int entitySize = 16;
	private Entity[] entities = new Entity[entitySize];

	private Background background;
	private Man man;
	private SpeedGauge speedGauge;
	private Effect effect;
	private CountDown countDown;
	private Timer timer;
	private Gauge[] gauges = new Gauge[3];
//	private Gauge temp;
	private Smoke smoke;
	// ============================//
	private SpaceBar spaceBar;
	private EdgeBorder edge;
	private Effect2 finishEffect;
	private FinishLine finishLine;
	private TimeOver timeOver;


	private boolean start = false;

	// 게이지
	private final double END_POINT = 130000;// 130000;
	private double score = 20;
	private double currentLocation = 0;

	public final static int fps = 60;
	public final static int MAX_PUSH = 100;

	private Cars cars;

	private boolean isCollision;

	// ==============================
	private double defaultSpeed = 5;
	private double speed = defaultSpeed;
	// ==============================
	private Set<Integer> players = new TreeSet<>();
	private BufferedImage buf;
	private Graphics bg;
	
	public MiniGame3Canvas() {

		instance = this;

		background = new Background();
		man = new Man();
		cars = new Cars();
		speedGauge = new SpeedGauge();
		effect = new Effect();
		countDown = new CountDown();
		timer = new Timer(80);
		
		
		gauges[0] = new Gauge(1405, 60, 35, 0, "res/game3/고경표1.png");
		gauges[1] = new Gauge(1405, 60, 35, 0, "res/game3/고경표1.png");
		gauges[2] = new Gauge(1405, 60, 35, 0, "res/game3/고경표1.png");
		smoke = new Smoke();
		smoke = new Smoke();

		// ====================================//
		spaceBar = new SpaceBar();
		edge = new EdgeBorder();
		finishEffect = new Effect2();
		finishLine = new FinishLine();
		timeOver = new TimeOver();

//		>>배경 깃발 자동차 안개  게이지  얼굴 시계 스피드 충돌효과  스페이스바 , 테두리 , 사람 카운트 꽃가루
		entities[0] = background;
		entities[1] = cars;
		entities[2] = smoke;
		entities[3] = gauges[0];
		entities[4] = gauges[1];
		entities[5] = gauges[2];
		entities[6] = timer;
		entities[7] = speedGauge;
		entities[8] = effect;
		entities[9] = man;
		entities[10] = finishLine;
		entities[11] = countDown;
		entities[12] = spaceBar;
		entities[13] = edge;
		entities[14] = finishEffect;
		entities[15] = timeOver;

		// 이벤트 리스너 --------------------------------------------------
		countDown.setCountListener(new CountListener() {

			@Override
			public void onEnd() {
				background.setActive(true);
				man.setActive(true);
				speedGauge.setActive(true);
				cars.show();
				cars.setActive(true);
				gauges[getPlayerNum()].setActive(true);
				timer.startTimer();
				countDown.hide();

				for (int i = 0; i < 3; i++)
					if (players.contains(i))
						gauges[i].setActive(true);
				

				start = true;

			}
		});

		finishLine.setLineStopListenr(new LineStopListener() {

			@Override
			public void onStop() {
				// 깃발멈춘 후
				spaceBar.show();
				spaceBar.setActive(true);
				edge.show();
				edge.setActive(true);
				gauges[getPlayerNum()].setActive(false);
				man.setFinish(true);

			}
		});

		man.setManMoveListenr(new ManMoveListener() {

			@Override
			public void goMan() {
				finishEffect.show();
				finishEffect.setActive(true);
				background.setActive(false);
				speedGauge.reset();
				speedGauge.setActive(false);
				start = false;
			}
		});
		
		

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (start) {
					int code = e.getKeyCode();
					switch (code) {
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_RIGHT:
						man.move(code);
						break;
					case KeyEvent.VK_SPACE:
						if( !timeOver.isActive() ) {
							spaceBar.spacePush();
						}
						
						break;
					}

				}

			}
		});

		background.show();
		man.show();
		speedGauge.show();
		timer.show();
//		System.out.println(getPlayerNum());
//		gauges[1].show(); // 나중에 자기 게이지바로 변경
		countDown.show();
		countDown.setActive(true);
		smoke.show();

		// start() 안의 while문에서 쓸 상태 변수
		setActiveCanvas(true);
		buf = new BufferedImage(1500,900,BufferedImage.TYPE_INT_RGB);
		bg = buf.getGraphics();
	}

	public void start() {
		
		Runnable sub = new Runnable() {
			@Override
			public void run() {
//				int delay = 0;
				GameEventListener gameEventListener = getGameEventListener();
				miniGame: while (isActiveCanvas()) {

					for (int i = 0; i < entitySize; i++) {
						if (entities[i].isActive())
							entities[i].update();
					}

					int manX = (int) man.getX();
					int manY = (int) man.getY();

					if (cars.isCollision(manX, manY)) {
						speed = defaultSpeed;
						speedGauge.reset();
						man.setCollision(true);
						effect.setLocation(manX, manY);
						effect.show();
						gauges[getPlayerNum()].setCollision(true);
						
						if (gameEventListener != null) {
							gameEventListener.event("Collision","true");
						}
						
						repaint();
						isCollision = false;
					}

					// ==============================
					// 스피드 조절

					if (start) {
						if (speed < 30) {
							speed *= 1.0025;
						}
						// ==============================
						cars.setSpeed(speed);
//							cars.update();

						// 게이지 채우기 ===================================================

						if (currentLocation <= END_POINT) {
							currentLocation += score + speed;
							gauges[getPlayerNum()].per(currentLocation / END_POINT * 100);
//							System.out.println(speed);
//							 if (fillListener != null)
//					             fillListener.rogress();
							// 바뀌는 곳에 넣기

							if (gameEventListener != null) {
								gameEventListener.event("gauge",
										Double.toString((currentLocation / END_POINT * 100)));
							}
						} else {
							speed = 0;
							background.setSpeed(speed);
						}
					}

					repaint();
					try {
						Thread.sleep(1000 / fps); // 16.666666
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		};

		Thread th = new Thread(sub);
		th.start();
	}

	@Override
	public void playerSetting(int playerNum) {
		

		// 캔버스 스위칭 하고 바로 호출될 메소드
		// 시작 하기 전에 Frame에서 호출할 플레이어 기본 세팅 메소드
		
		setActiveCanvas(true);
		setPlayerNum(playerNum);
//		gauges[playerNum].show();
		// 다른 플레이어 있는지 확인하고 . shoiw();
		
//		switch (playerNum) {
//		case 0:
//			gauges[0].setImages("res/game3/고경표.png", "res/game3/고경표1.png")
//			break;
//		case 1:
//			gauges[1].setImages("res/game3/고경표.png", "res/game3/고경표1.png")
//			break;
//		case 2:
//			gauges[2].setImages("res/game3/고경표.png", "res/game3/고경표1.png")
//			break;
//		}
		

		
		String[][] playerFaceImg = 
			{{"res/game3/서예지.png", "res/game3/서예지1.png"},
			{ "res/game3/손담비.png", "res/game3/손담비1.png" }};
		
		int imgNum = 0;
		for (int i = 0; i < 3; i++) {
			if (players.contains(i)) {
				Color gaugeColor = new Color (
					playerColorRGB[i][0],
					playerColorRGB[i][1],
					playerColorRGB[i][2]	
				);
				gauges[i].setGaugeColor(gaugeColor);
				gauges[i].show();
			}
			
//			//자기 게이지를 맨 위로 올리기
			if(playerNum != 2) {
				Entity temp;
				temp = entities[5];
				entities[5] = entities[3+playerNum];
				entities[3+playerNum]= temp;
			}

			// ==============================================
			if(playerNum != i) {
				gauges[i].setImages(playerFaceImg[imgNum][0], playerFaceImg[imgNum][1]);
				imgNum++;
			}
			
		}
		
		
		

		GameEventListener gameEventListener = getGameEventListener();

		gauges[playerNum].setFillListener(new GaugeFillListener() {

			@Override
			public void onFilld() {
				cars.hide();
				cars.setActive(false);

				finishLine.show();
				finishLine.setActive(true);

				// 게이지 다찼는지

				if (gameEventListener != null) {
					gameEventListener.event("gaugeOnFill", "true");
				}

			}
		});
		spaceBar.setPressListener(new SpaceBarListener() {

			@Override
			public void onPress() {
				edge.hide();
				edge.setActive(false);
				man.go();
				timer.setActive(false);
				timer.setTimerLoop(false);

				// 게임끝났을때 시간 보내기
				if (gameEventListener != null) {
					gameEventListener.event("speceBarPress", Integer.toString(timer.getTime()));
					gameEventListener.event("finish", Integer.toString(timer.getTime()));

//					System.out.println(timer.getTime());
				}
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

		edge.setColorRGB(
				playerColorRGB[playerNum][0],
				playerColorRGB[playerNum][1],
				playerColorRGB[playerNum][2]);
	}

	@Override
	public void otherPlayerEvent(int otherPlayer, String key, String value) {
		// 다른 플레이어의 이벤트를 받았을 때 호출될 메소드

		switch (key) {
		case "speceBarPress":
			break;
//		case "finish":
//			break;
//		case "gaugeOnFill":
//			break;
		case "gauge":
			gauges[otherPlayer].per(Double.parseDouble(value));
			break;
		case "Collision":
			gauges[otherPlayer].setCollision(true);
		break;
		case "timerOnEnd":{
//			timerOnEnd(otherPlayer);
			break;
		}
			
		
		
		}

	}

	@Override
	public void paint(Graphics g) {
//		Image buf = this.createImage(this.getWidth(), this.getHeight());
//		Graphics bg = buf.getGraphics();

		for (int i = 0; i < entitySize; i++) {
			if (entities[i].isVisible())
				entities[i].paint(bg);
		}

		g.drawImage(buf, 0, 0, this);

	}

	public void setPlayerN(Set<Integer> players) {

		this.players = players;
	}
	
	private void timerOnEnd(int n){
		for (int i = 0; i < gauges.length; i++) {
			gauges[i].setActive(false);
			cars.setActive(false);
			speedGauge.setActive(false);
			timeOver.show();
			timeOver.setActive(true);
		}
	}

}
