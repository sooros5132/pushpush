package com.team.app.miniGame2.canvas;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.team.app.entity.SoundEffect;
import com.team.app.entity.UpgradeCanvas;
import com.team.app.miniGame1.entity.GameEventListener;
import com.team.app.miniGame2.entity.Background;
import com.team.app.miniGame2.entity.Border;
import com.team.app.miniGame2.entity.Character;
import com.team.app.miniGame2.entity.CountDown;
import com.team.app.miniGame2.entity.CountListener;
import com.team.app.miniGame2.entity.EdgeBorder;
import com.team.app.miniGame2.entity.Entity;
import com.team.app.miniGame2.entity.Quiz;
import com.team.app.miniGame2.entity.QuizListener;
import com.team.app.miniGame2.entity.SpaceBar;
import com.team.app.miniGame2.entity.SpaceBarListener;
import com.team.app.miniGame2.entity.TimeEndListener;
import com.team.app.miniGame2.entity.Timer;

public class MiniGame2Canvas extends UpgradeCanvas {

	public static Canvas instance;
	public int playerCount = 3;
	public static int fps = 60;

//	초기화면
//	배경,시계,캐릭터,카운트
	private Background[] background = new Background[playerCount];
	private Timer[] timer = new Timer[playerCount];
	private Character[] taebo = new Character[playerCount];
	private CountDown[] countDown = new CountDown[playerCount];
//	private BGM[] bgm = new BGM[playerCount];
//	private SoundEffect[] soundEffect = new SoundEffect[playerCount];
	private SpaceBar[] spacebar = new SpaceBar[playerCount];
	private EdgeBorder[] edgeBorder = new EdgeBorder[playerCount];
	private Border[] border = new Border[playerCount];
	private Quiz[] quiz = new Quiz[playerCount];

	private int entitySize = 8;
	private Entity[][] players = new Entity[playerCount][entitySize];

	private int[][] playerColorRGB = { { 255, 238, 147 }, { 92, 149, 255 }, { 253, 88, 33 } };
	
	private BufferedImage buf;
	private Graphics bg;
//	
//	카운트 이후 시작할때
//	배경,시계,캐릭터,문제

//	
//	클리어화면
//	캐릭터, 배경, 시계
//	

	public MiniGame2Canvas() {
		instance = this;

		for (int i = 0; i < 3; i++) {
			background[i] = new Background(0, 0, 1500, 300, false);
			timer[i] = new Timer(30);
			taebo[i] = new Character(1100, 0, 330, 265);

			quiz[i] = new Quiz(10, 20, 2, 4);
			// bgm = new BGM("audio/opening.wav");
			spacebar[i] = new SpaceBar(500, 100);
//			soundEffect[i] = new SoundEffect();
			edgeBorder[i] = new EdgeBorder(0, 0, (int) getWidth(), (int) getHeight());
			border[i] = new Border(0, 300 - 39);// 일단 0,300에 3개 만들어
			countDown[i] = new CountDown(3);
			
			players[i][0]=background[i];
			players[i][1]=timer[i];
			players[i][2]=taebo[i];
			players[i][3]=quiz[i];
			players[i][4]=spacebar[i];
			players[i][5]=border[i];
			players[i][6]=edgeBorder[i];
			players[i][7]=countDown[i];

			border[i].setColorRGB(playerColorRGB[i][0], playerColorRGB[i][1], playerColorRGB[i][2]);

			background[i].setActive(true);
			background[i].show();
			timer[i].show();
			taebo[i].show();
			taebo[i].setActive(true);
			border[i].setActive(true);
			border[i].show();
//			서버에서 받아 올 예정
//			quiz[i].show();
//			quiz[i].setActive(true);

			countDown[i].setVisible(true);
			countDown[i].setActive(true);
			
			edgeBorder[i].setColorRGB(
					playerColorRGB[i][0],
					playerColorRGB[i][1],
					playerColorRGB[i][2]);
			
//			System.out.println("aaaaaaaaaaaaa");
		}
		buf = new BufferedImage(1500,900,BufferedImage.TYPE_INT_RGB);
		bg = buf.getGraphics();
	}

//	스레드 시작(초기화면)
	public void start() {
		Runnable sub = new Runnable() {
			
			
			@Override
			public void run() {
				
				while (true) {
					for (int i = 0; i < playerCount; i++) {
						for (int j = 0; j < entitySize; j++) {
							if (players[i][j].isActive())
								players[i][j].update();
						}
					}
					repaint();

					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread th = new Thread(sub);
		th.start();

	}

	@Override
	public void playerSetting(int playerNum) { // 인터페이스를 여기다 몰아
												// 참조변수 만들어서 실수방지,보기편하게
		super.playerSetting(playerNum);
		CountDown countDown = this.countDown[playerNum];// 참조변수 만들기
		Timer timer = this.timer[playerNum];
		Quiz quiz = this.quiz[playerNum];
		SpaceBar spacebar = this.spacebar[playerNum];
		Character taebo = this.taebo[playerNum];
//		EdgeBorder edgeBorder = this.edgeBorder[playerNum];
//		Background background = this.background[playerNum];
//		SoundEffect soundEffect = this.soundEffect[playerNum];
		
		GameEventListener gameEventListener = getGameEventListener();

		countDown.setCountListener(new CountListener() {

			@Override
			public void onEnd() {
				countDownOnEnd(playerNum);
				if( gameEventListener != null )
					gameEventListener.event("countDownOnEnd", "true");
			}

		});

		timer.setTimeListener(new TimeEndListener() {

			@Override
			public void onEnd() {
				timerOnEnd(getPlayerNum());
				if( gameEventListener != null )
					gameEventListener.event("timerOnEnd", "true");
					gameEventListener.event("finish", "0.0");
			}

		});

		quiz.setQuizListener(new QuizListener() {

			@Override
			public void onEnd() {
				quizOnEnd(getPlayerNum());
				if( gameEventListener != null )
					gameEventListener.event("quizOnEnd", "true");
			}

		});

		spacebar.setPressListener(new SpaceBarListener() {

			@Override
			public void onPress() {
				spacebarOnPress(getPlayerNum());
				if( gameEventListener != null ) {
					gameEventListener.event("spacebarOnPress", "true");
					gameEventListener.event("finish", Float.toString(timer.getTime()));
				}
			}

		});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {// 37 38 39 40
				case KeyEvent.VK_SPACE:
					if (spacebar.isVisible() && timer.isActive()) {
						spacebar.spacePush(key);
						break;
					}
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_UP:
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_DOWN:
				default:
					if (!countDown.isActive() && timer.isActive()) {
						// quiz.check(key);// 문제와 키가 맞는지 체크하는 함수 , 생성자 생성할때 문제는 이미 만들어지고 나는 체크하면되
						if (quiz.check(key)) {
							taebo.changeImg(key);
							if( gameEventListener != null ) {
								gameEventListener.event("taebo", Integer.toString(key));
							}
						} else {
							taebo.changeImg(10); // 키값을 10을 보내줘서 항상 default로 넘어가게 했다. default는 틀렸다는 소리와 그림이 나온다.
							if( gameEventListener != null ) {
								gameEventListener.event("taebo", "false");
							}
						}
					}
					break;
				}
			}
		});
		
		countDown.show();
		quiz.setActiveSound(true);
		taebo.setActiveSound(true);
	}
	
	@Override
	public void otherPlayerEvent(int otherPlayer, String key, String value) {
		//다른 플레이어의 이벤트
		switch(key) {
		case "countDownOnEnd":{
			countDownOnEnd(otherPlayer);
			break;
		}
		case "spacebarOnPress":{
			spacebarOnPress(otherPlayer);
			break;
		}
		case "timerOnEnd":{
			timerOnEnd(otherPlayer);
			break;
		} case "quizOnEnd":{
			quizOnEnd(otherPlayer);
			break;
		} case "taebo":{
			if( value.equals("false") ) {
				quiz[otherPlayer].check(-1);
				taebo[otherPlayer].changeImg(-1);
			} else {
				int keyboardKey = Integer.parseInt(value);
				quiz[otherPlayer].check(keyboardKey);
				taebo[otherPlayer].changeImg(keyboardKey);
			}
			break;
		}
		}
	}
	
	private void countDownOnEnd(int playerNum) {
		timer[playerNum].setActive(true);
		quiz[playerNum].setActive(true);
		quiz[playerNum].show();
		countDown[playerNum].hide();
		countDown[playerNum].setActive(false);
	}
	

	private void spacebarOnPress(int playerNum) {
		background[playerNum].setFinish(true);
		// if(플레이어1등 2등 3등)
		SoundEffect.play("audio/yeah.wav", false);
		timer[playerNum].stop();
		taebo[playerNum].setActive(false);
		taebo[playerNum].hide();
		spacebar[playerNum].setVisible(false);
		spacebar[playerNum].setActive(false);
		edgeBorder[playerNum].setActive(false);
		edgeBorder[playerNum].hide();
	}
	

	private void quizOnEnd(int playerNum) {
		spacebar[playerNum].setActive(true);
		spacebar[playerNum].show();
		quiz[playerNum].setActive(false); // onEnd 한번 실행하고 바로 자기를 꺼
		edgeBorder[playerNum].setActive(true);
		edgeBorder[playerNum].show();
	}
	
	private void timerOnEnd(int playerNum) {
		quiz[playerNum].setActive(false);
		timer[playerNum].setActive(false);
		taebo[playerNum].setActive(false);
	}
	
	@Override
	public void paint(Graphics g) {

		int[] screen = {0, 2};
		int w = 1500;
		int h = 900/3;

		int num = 0;
		try {
			for(int j = 0; j < playerCount; j++) {
//			      Graphics g = bufferedImage.getGraphics();
//				Image buf = createImage(getWidth(), getHeight());
					
				for (int i = 0; i < entitySize; i++) {
					Entity[] entitys = players[j];
					if (entitys[i].isVisible())
						entitys[i].paint(bg);
				}
				
				if( j == getPlayerNum() ) {
					g.drawImage(buf, 0, h, w, h+h, 0, 0, w, h, this);
				} else {
					g.drawImage(buf, 0, h*screen[num], w, h+h*screen[num], 0, 0, w, h, this);
					num++;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void setQuiz(String line) {
		
		for(int i = 0; i<playerCount; i++) {
			quiz[i].setQuiz(line);
//			quiz[i].setActive(true);
//			quiz[i].show();
		}
		
	}

	public void setFinishImage(String token) {
		String[] ImageSrc = {"res/game2/champion.png", "res/game2/champion2.png", "res/game2/belt3-removebg-preview.png"};
		
		String[] datas = token.split(":");
		int key = Integer.parseInt(datas[0]);
		int value = Integer.parseInt(datas[1]);
			
		background[key].setFinishImage(ImageSrc[value]);
		
		
	}

//	시계.병수님꺼
//	캐릭터.태보활성화(),비지블()
//	카운트.활성화(),비지블()
//	
//	스레드 중간(카운트 이후 시작할때)
//	문제.랜덤하게10문제-차례대로출력-점점한문제씩늘어나게()
//	문제는 3명다 똑같이 ,틀리면 부저소리,X이미지, 처음부터 다시
//	캐릭터.방향키에맞게출력()
//	
//	스레드 마무리(클리어화면)
//	캐릭터.승리이미지()
//	시계.멈춰()
//	
//	나중에는 점수화면으로 넘어가()

	// audience2.jpg 누끼
	// 효과음
	//

}
