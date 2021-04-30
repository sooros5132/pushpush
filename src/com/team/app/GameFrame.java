package com.team.app;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.team.app.entity.BGM;
import com.team.app.entity.ClientListener;
import com.team.app.entity.EventMsgListener;
import com.team.app.entity.GameClient;
import com.team.app.entity.SoundEffect;
import com.team.app.entity.UpgradeCanvas;
import com.team.app.main.canvas.MainCanvas;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;
import com.team.app.miniGame1.entity.GameEventListener;
import com.team.app.miniGame2.canvas.MiniGame2Canvas;
import com.team.app.miniGame3.canvas.MiniGame3Canvas;
import com.team.app.result.canvas.ResultCanvas;

public class GameFrame extends JFrame {
	
//	public Player[] players;
	public static final int frameWidth = 1500;
	public static final int frameHeight = 900;
	public static final int totalPlayerNum = 3;
	public static final int MINIGAME1 = 1;
	public static final int MINIGAME2 = 2;
	public static final int MINIGAME3 = 3;
	public static final int RESULTSCREEN = 4;
	public static final int FPS = 60;
	public static GameFrame instance;
	private UpgradeCanvas nowCanvas;
	private GameClient client;
	private BGM bgm;
	private Set<Integer> players = new HashSet<>();
	
	public int playerNum;
	
	public GameFrame() {
		instance = this;
		setTitle("PUSHPUSH ver1.0");
		setLayout(new BorderLayout());
		setVisible(true);
		setSize(frameWidth + 16, frameHeight + 39); // 16, 39 만큼 보정
		
		// 데스크탑 사이즈 구해오고 창을 가운데로 옮김
		Dimension desktop = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation(desktop.width/2 - frameWidth/2, desktop.height/2 - frameHeight/2 - 39);
		
		
		MainCanvas mainCanvas = new MainCanvas();
		add(mainCanvas);
		nowCanvas = mainCanvas;

		mainCanvas.setActiveCanvas(true);
		mainCanvas.setFocusable(true);
		mainCanvas.requestFocus();
		mainCanvas.start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(client != null && client.isConnect())
					client.send("#DISCONNECT");
				System.exit(0);
			}
		});
		
		mainCanvas.setGameStartListener(()->{
			if( playerNum == 0 && client!=null )
				// =============================================================
				// =============================================================
				// =============================================================
				// =============================================================
				// 캔버스 스위칭, 서버인척 메시지 뿌리기 방법임 오류많음
				// 테스트용 지우기, 정상적인건 아래방법
//				client.send("switchCanvas:1");
				client.send("#GAMESTART");
				// =============================================================
				// =============================================================
				// =============================================================
				// =============================================================
			});

		mainCanvas.setClientCreatListener(new ClientListener() {
			
			@Override
			public void onCreat(GameClient client) {
				GameFrame.this.client = client;
				
				client.setMsgListener(new EventMsgListener() {
					
					@Override
					public void onMsg(String line) {
						try {
							ClientOnMsg(line);
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		mainCanvas.setBgmListener((volume)->{bgm.setVolume(volume);SoundEffect.setVolume(volume);});
		bgm = new BGM("audio/We_Ride.wav");
		bgm.setVolume(-(25*0.4));
		bgm.play();
	}
	public void switchCanvas(UpgradeCanvas oldCanvas, Class newCanvas) throws InstantiationException, IllegalAccessException {
		
		UpgradeCanvas canvas = (UpgradeCanvas)newCanvas.newInstance();

		if(canvas instanceof MainCanvas) {
			MainCanvas obj = ((MainCanvas)canvas);
			
			bgm.setAudioSrc("audio/We_Ride.wav");
			nowCanvas = obj;
		} else if(canvas instanceof MiniGame1Canvas) {
			MiniGame1Canvas obj = ((MiniGame1Canvas)canvas);
			bgm.setAudioSrc("audio/miniGame1_2.wav");
			switchMiniGame1(obj);
			
		} else if(canvas instanceof MiniGame2Canvas) {
			MiniGame2Canvas obj = ((MiniGame2Canvas)canvas);
			bgm.setAudioSrc("audio/hi_bgm2.wav");
			switchMiniGame2(obj);
			
		} else if(canvas instanceof MiniGame3Canvas) {
			MiniGame3Canvas obj = ((MiniGame3Canvas)canvas);
			bgm.setAudioSrc("audio/miniGame3.wav");
			switchMiniGame3(obj);
			
		} else if(canvas instanceof ResultCanvas) {
			ResultCanvas obj = ((ResultCanvas)canvas);
			bgm.setAudioSrc("audio/크루원_승리.wav");
			bgm.setLoop(false);
			nowCanvas = obj;
		}

		bgm.play();
		
		nowCanvas.playerSetting(playerNum);
		nowCanvas.start();
		
		add(canvas);

		revalidate();
		oldCanvas.setVisible(false);
		remove(oldCanvas);
		
		canvas.setFocusable(true);
		canvas.requestFocus();
		
	}
	private void switchMiniGame1(MiniGame1Canvas obj) {
		obj.setGameEventListener(new GameEventListener() {
			
			@Override
			public void event(String key, String value) {
				if ( client != null ) {
					String msg = String.format("otherPlayer:%s,miniGame:1,%s:%s", playerNum ,key ,value);
					client.send(msg);
				}
				
			}
		});

		obj.setting(frameWidth, frameHeight);
		nowCanvas = obj;
	}
	
	private void switchMiniGame2(MiniGame2Canvas obj) {
		obj.setGameEventListener(new GameEventListener() {
			
			@Override
			public void event(String key, String value) {
				if ( client != null ) {
					String msg = String.format("otherPlayer:%s,miniGame:2,%s:%s", playerNum ,key ,value);
					client.send(msg);
				}
			}
		});

		nowCanvas = obj;
	}
	
	private void switchMiniGame3(MiniGame3Canvas obj) {
		obj.setGameEventListener(new GameEventListener() {
			
			@Override
			public void event(String key, String value) {
				if ( client != null ) {
					String msg = String.format("otherPlayer:%s,miniGame:3,%s:%s", playerNum ,key ,value);
					client.send(msg);
				}
				
			}
		});

		obj.setPlayerN(players);
		nowCanvas = obj;
	}
	
	private void ClientOnMsg(String line) throws InstantiationException, IllegalAccessException {
		if( line.trim().isEmpty() )
			return;

		int otherPlayer = 0;
		int nimiGameNum = 0;
		
//		List<String> datas = Arrays.asList(line.split(","));
//		{"otherPlayer":0,"miniGame":1,"countDownOnEnd":true}
		
		
//		otherPlayer:0,miniGame:3,gauge:97.75717726170046

//		System.out.println(line);
		String[] tokens = line.split(",");
		String[] token = tokens[0].split(":");

		String key = token[0];
		String value = token[1];
		
		switch (key) {
		case "otherPlayer":{ 					// 1. 플레이어의 번호
			otherPlayer = Integer.parseInt(value);
			
			token = tokens[1].split(":");
			key = token[0];
			value = token[1];
			
			if( key.equals("miniGame") && 
				otherPlayer != playerNum ) { 	// 2. miniGame: 미니게임 번호
				nimiGameNum = Integer.parseInt(value);
				
				token = tokens[2].split(":");
				key = token[0];
				value = token[1];

				switch(nimiGameNum) {			// 3. 이벤트, 데이터
				case MINIGAME1:{
					((MiniGame1Canvas)nowCanvas).otherPlayerEvent(otherPlayer, key, value);
					break;
				}
				case MINIGAME2:{ 
					((MiniGame2Canvas)nowCanvas).otherPlayerEvent(otherPlayer, key, value);
					break;
				}
				case MINIGAME3:{
					((MiniGame3Canvas)nowCanvas).otherPlayerEvent(otherPlayer, key, value);
					break;
				}
				}
			}
			break;
		}
		case "switchCanvas":{ // 게임 스위칭
			switch (Integer.parseInt(value)) {
			case MINIGAME1:{ 
				nowCanvas.setActiveCanvas(false);
				switchCanvas(nowCanvas, MiniGame1Canvas.class);
				break;
			}
			case MINIGAME2:{ 
				nowCanvas.setActiveCanvas(false);
				switchCanvas(nowCanvas, MiniGame2Canvas.class);
				break;
			}
			case MINIGAME3:{
				nowCanvas.setActiveCanvas(false);
				switchCanvas(nowCanvas, MiniGame3Canvas.class);
				break;
			}
			case RESULTSCREEN:{
				nowCanvas.setActiveCanvas(false);
				switchCanvas(nowCanvas, ResultCanvas.class);
				// ====================== 최종 승리 테스트용 ==========================
				// ====================== 최종 승리 테스트용 ==========================
				// ====================== 최종 승리 테스트용 ==========================
				// ====================== 최종 승리 테스트용 ==========================
//				((ResultCanvas)nowCanvas).setPlayerResult("60&30&70.84846");
				// ====================== 최종 승리 테스트용 ==========================
				break;
			}
			}
			break;
		}
		case "youerNum":{
			playerNum = Integer.parseInt(value);
			
			if(nowCanvas instanceof MainCanvas) {
				nowCanvas.setPlayerNum(playerNum);

				if( playerNum == 0 ) {
					JOptionPane.showMessageDialog(GameFrame.this,
							"접속 성공\n나는 첫번째 캐릭터 노랑이\n방장이니까 준비되면 게임시작 누르기~");
				} else {
					String[] playerColor = {"노랑이", "파랑이", "주황이"};
					String str = String.format("접속 성공\n나는 %s\n%d번째에 있는 캐릭터", playerColor[playerNum], playerNum + 1);
					JOptionPane.showMessageDialog(GameFrame.this, str);
				}
			}
			break;
		}
		case "connectedPlayer":{
			int playerNum = Integer.parseInt(value);
			players.add(playerNum);
			
			SoundEffect.play("audio/플레이어_입장.wav", false);
			
			if( nowCanvas instanceof MainCanvas ) {
				if(playerNum == this.playerNum)
					((MainCanvas)nowCanvas).connectPlayer(playerNum, true);
				else
					((MainCanvas)nowCanvas).connectPlayer(playerNum, false);
			}
			
			break;
		}
		case "disconnectPlayer":{
			int playerNum = Integer.parseInt(value);
			players.remove(playerNum);
			
			SoundEffect.play("audio/플레이어_퇴장.wav", false);
			
			if( nowCanvas instanceof MainCanvas )
				((MainCanvas)nowCanvas).disconnectPlayer(playerNum);
			break;
		}
		case "miniGame2Quiz":{
			if( nowCanvas instanceof MiniGame2Canvas )
				((MiniGame2Canvas)nowCanvas).setQuiz(tokens[1]);
			break;
		}
		case "gameResult":{
			if( nowCanvas instanceof ResultCanvas ) {
				((ResultCanvas)nowCanvas).setPlayerResult(tokens[1]);
				SoundEffect.play("audio/크루원_승리.wav", false);
			}
			break;
		}
		case "miniGame2Rank":{
			if( nowCanvas instanceof MiniGame2Canvas )
				((MiniGame2Canvas)nowCanvas).setFinishImage(tokens[1]);
			break;
		}
		case "overPlayer":{
			if( nowCanvas instanceof MainCanvas )
				((MainCanvas)nowCanvas).overPlayer();
			break;
		}
		
		}
	}
	
}
