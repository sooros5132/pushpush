package com.team.app.main.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.team.app.GameFrame;
import com.team.app.entity.BGM;
import com.team.app.entity.BgmListener;
import com.team.app.entity.ClientListener;
import com.team.app.entity.DrawImg;
import com.team.app.entity.GameClient;
import com.team.app.entity.GameStartListener;
import com.team.app.entity.Gauge;
import com.team.app.entity.SoundEffect;
import com.team.app.entity.UpgradeCanvas;
import com.team.app.main.entity.Button;
import com.team.app.main.entity.TextInput;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;
import com.team.app.result.entity.PlayerImg;

public class MainCanvas extends UpgradeCanvas {

	public static MainCanvas instance;
	private Button gameStartBtn;
	private Button creatServerBtn;
	private Button ingressBtn;
	private DrawImg[] players = new DrawImg[3];
	private Button[] playersBtn = new Button[3];
	private Button[] buttons = new Button[6];
	private DrawImg background;
	private DrawImg title;
	private DrawImg speaker;
	private TextInput textInput;
//	private BGM bgm;
	private Gauge gauge;
	private GameClient client;
	private int FPS = GameFrame.FPS;
	private int frameW = GameFrame.frameWidth;
	private int frameH = GameFrame.frameHeight;
	
	private String[] playerImgSrc = {"res/result/player1.png","res/result/player2.png","res/result/player3.png"};
	private String[] playerImg2Src = {"res/result/player1_1.png","res/result/player2_1.png","res/result/player3_1.png"};
	private int[][] playerColorRGB = {{255,238,147},{92,149,255},{253,88,33}};

	private boolean connected = false;
	private boolean started = false;
	private final int port = 10005;
	private BufferedImage buf;
	private Graphics bg;
	
	private GameStartListener gameStartListener;
	
	public void setGameStartListener(GameStartListener gameStartListener) {
		this.gameStartListener = gameStartListener;
	}
	
	private ClientListener clientCreatListener;
	private BgmListener bgmListener;
	public void setClientCreatListener(ClientListener clientCreatListener) {
		this.clientCreatListener = clientCreatListener;
	}
	
	public void setBgmListener(BgmListener bgmListener) {
		this.bgmListener = bgmListener;
	}
	
	public MainCanvas() {
		instance = this;

//		bgm = new BGM("audio/We_Ride.wav");
		
		background = new DrawImg(0,0,frameW,frameH,"res/main/mainBg02.png");
		
		title = new DrawImg("res/main/mainTitle.png");
		title.setSize(title.getImgW(), title.getImgH());
		title.move(frameW/2 - title.getImgW() / 2, 100);
		
		speaker = new DrawImg(50, frameH - 75, 25, 25, "res/main/speaker.png");
		gauge = new Gauge(100, frameH - 75, 300, 25, 300, 25);
		textInput = new TextInput(540, frameH - 75, 300, 25);
		textInput.setText("127.0.0.1");
		
		{ // 버튼 생성
			int btnW = 300;
			int btnH = 120;
			int btnX = frameW/2-btnW/2;
			gameStartBtn = new Button(btnX, 640, btnW, btnH, "게임 시작");
			gameStartBtn.textMove(btnW-260, btnH-40);
			
			creatServerBtn = new Button(btnX, 600, btnW, btnH, "서버 개설");
			creatServerBtn.setBgColor(new Color(0x17EAD9), new Color(0x6078EA));
			creatServerBtn.textMove(btnW-257, btnH-40);
			
			int playerbtnW = 200;
			int playerbtnH = 90;

			ingressBtn = new Button(840, frameH - 75, 100, 25, "서버 참가");
			ingressBtn.setBgColor(Color.orange);
			ingressBtn.textMove(2, (int)(25*0.9));
			ingressBtn.setFontSize(22);
			ingressBtn.setBoxRadius(0);
			
			buttons[0] = gameStartBtn;
			buttons[1] = creatServerBtn;
			buttons[2] = ingressBtn;

			players[0] = new DrawImg(frameW/2-playerbtnW/2 - 220 ,280,180,210,"res/result/player1_1.png");
			players[1] = new DrawImg(frameW/2-playerbtnW/2 ,280 ,180,210,"res/result/player2_1.png");
			players[2] = new DrawImg(frameW/2-playerbtnW/2 + 220 ,280,180,210,"res/result/player3_1.png");
			playersBtn[0] = new Button(frameW/2-playerbtnW/2 - 220, 500, btnW/1.5, btnH/1.5, "연결 안됨");
			playersBtn[1] = new Button(frameW/2-playerbtnW/2, 500, btnW/1.5, btnH/1.5, "연결 안됨");
			playersBtn[2] = new Button(frameW/2-playerbtnW/2 + 220, 500, btnW/1.5, btnH/1.5, "연결 안됨");
			for(int i = 0; i < playersBtn.length; i++) {
				playersBtn[i].setFontSize(34);
				playersBtn[i].textMove(26, 56);
				playersBtn[i].setBoxRadius(0);
			}
		}
		
		// 볼륨 세팅
		gauge.setSize(300*0.6,gauge.getHeight());
//		bgm.setVolume(-(25*0.4));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				if( gameStartBtn.isSelected(x, y) ) {
					if( connected && !started && getPlayerNum() == 0 ) {
						if(gameStartListener != null) {
							started = true;
							System.out.println("게임시작");
							gameStartListener.onStart();
						}
						
						// 더블클릭 방지 3초 기다리고 풀음
						new Thread(()->{
							try {
								Thread.sleep(3000);
								started = false;
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}).start();
					} else if( started ){
						System.out.println("빠르게 클릭하지마세요");
					} else {
						JOptionPane.showMessageDialog(MainCanvas.this, "방장이 시작 누르는걸로~");
					}
					
				}

				if( gauge.contains(x, y) ) {
					int setX = (int)(x - gauge.getX());
					gauge.setSize(setX,gauge.getY());
//					bgm.setVolume(-25+(gauge.getWidth()/gauge.getMAX_WIDTH()*25));
					if( bgmListener != null )
						bgmListener.onChange((float)(-25+(gauge.getWidth()/gauge.getMAX_WIDTH()*25)));
				}

				// 클릭시 무조건 선택취소 후 다시 검사
				textInput.setActive(false);
				if( textInput.isSelected(x, y) ) {
					textInput.setActive(true);
					String ip = JOptionPane.showInputDialog("호스트의 아이피를 입력해주세요");
					if( ip != null && ip != "") {
						textInput.setText(ip);
					}
					
				}
				
				// 클라이언트 생성, 접속
				if( !connected && ingressBtn.isSelected(x, y)   ) {
					if ( !textInput.getText().isEmpty() && clientCreatListener != null ) {
						ingressBtn.setText("접속 중");
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								try {
									client = new GameClient(textInput.getText(), port);
									if( client.isConnect() ) {
										connected = true;
										clientCreatListener.onCreat(client);
										ingressBtn.setText("연결 끊기");
										ingressBtn.setBgColor(new Color(0xF74F39));
									}
								} catch (Exception e1) {
//									e1.printStackTrace();
									connected = false;
									ingressBtn.setText("접속 실패");
								}
							}

						}).start();
					} else {
						System.out.println("MainCanvas: 아이피를 입력해주세요");
					}
				} else if( connected && ingressBtn.isSelected(x, y) ) {
					try {
						client.send("#DISCONNECT");
					} finally {
						SoundEffect.play("audio/플레이어_퇴장.wav", false);
						ingressBtn.setText("서버 참가");
						ingressBtn.setBgColor(Color.orange);
						gameStartBtn.setBgColor(gameStartBtn.getDefaultColor());
						disconnectPlayer(0);
						disconnectPlayer(1);
						disconnectPlayer(2);
						connected = false;
						setPlayerNum(-1);
					}
				}
			}
		});
		

		addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if( gauge.contains(x, y) ) {
					int setX = (int)(x - gauge.getX());
					gauge.setSize(setX,gauge.getY());
					if( bgmListener != null )
						bgmListener.onChange((float)(-25+(gauge.getWidth()/gauge.getMAX_WIDTH()*25)));
					System.out.printf("volume: %5.2f \n",-25+(gauge.getWidth()/gauge.getMAX_WIDTH()*25));
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				textInput.append(e.getKeyChar());
			}
		});
		
//		bgm.play();
		buf = new BufferedImage(1500,900,BufferedImage.TYPE_INT_RGB);
		bg = buf.getGraphics();
	}
	
	@Override
	public void start() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				float i = 0;
				while( isActiveCanvas() /*GameFrame.instance.getNowCanvas() instanceof MainCanvas*/ ) {
					repaint();
//					i -= 0.5f;
//					bgm.setVolume(i);
//					bgm.getVolume();
					if( getPlayerNum() == 0)
						gameStartBtn.setBgColor(new Color(112, 245, 112, 200), new Color(73,198,40,200));
					try {
						Thread.sleep(1000/60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
//				bgm.stop();
			}
		}).start();
	}
	
	@Override
	public void paint(Graphics g) {
//		Image buf = createImage(getWidth(), getHeight());
//		Graphics bg = buf.getGraphics();

		background.paint(bg);
		gameStartBtn.paint(bg);
		ingressBtn.paint(bg);
//		creatServerBtn.paint(bg);
		title.paint(bg);
		gauge.paint(bg);
		speaker.paint(bg);
		textInput.paint(bg);
		for(int i = 0; i < playersBtn.length; i++) {
			players[i].paint(bg);
			playersBtn[i].paint(bg);
		}
		
		g.drawImage(buf, 0, 0, this);
		
	}

	public void connectPlayer(int num, boolean isMyNum) {
		
		playersBtn[num].setBgColor(new Color(
				playerColorRGB[num][0],
				playerColorRGB[num][1],
				playerColorRGB[num][2])
				);
		
		playersBtn[num].setText("얜 누구지");
		if( isMyNum ) {
			playersBtn[num].setText("내꺼 색깔");
		}
		players[num].changeImg(playerImgSrc[num]);
	}

	public void disconnectPlayer(int num) {
		playersBtn[num].setText("연결 끊김");
		players[num].changeImg(playerImg2Src[num]);
		playersBtn[num].setBgColor(playersBtn[num].getDefaultColor());
	}

	public boolean isConnected() {
		return connected;
	}

	public void overPlayer() {
		ingressBtn.setText("인원 초과");
	}
	
}
