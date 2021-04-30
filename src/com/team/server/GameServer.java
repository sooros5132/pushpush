package com.team.server;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.team.app.entity.Entity;
import com.team.app.entity.SoundEffect;

public class GameServer {

	private ServerSocket svrSock;
	
	private boolean running = false;
	private final int MAX_PLAYERS;
	
//	private List<Socket> sockets = new ArrayList<Socket>();
	private static Socket[] sockets;
	private static int socketsSize = 0;
	
	private static int brokenPlayerNum = -1;
	private static int finishPlayers = 0;
	private static int currentMinigame = 1;
	
//	private float[][] playTime = {{36.11703F,35.15038F,26.700508F},{58.200027F,56.250057F,53.81676F},{54.0F,52.0F,49.0F}};
	private float[][] playTime = new float[3][3];

	public GameServer() {
		this.MAX_PLAYERS = 0;
		System.out.println("플레이어 수를 입력해주세요.");
	}

	public GameServer(int MAX_PLAYERS, int Port) throws IOException {
		this.MAX_PLAYERS = MAX_PLAYERS;
		sockets = new Socket[MAX_PLAYERS + 1];
		svrSock = new ServerSocket(Port);
		running = true;
		System.out.println("Server Started...");

		// 처음부터 쓰레드가 있어야 플레이어 받는 while에서 멈추지 않는다.
//		new Thread(() -> {
//			try {
				while (running) {

					Socket sock = svrSock.accept();
					
					new Thread(() -> {
						try {
							System.out.println("클라이언트 연결: " + sock.getRemoteSocketAddress());
							int playerNum = -1;

							// 인원 초과시
							if (socketsSize >= MAX_PLAYERS) {
								System.out.println("인원초과");
								PrintStream nout = new PrintStream(sock.getOutputStream(), true, "UTF-8");
								nout.println("overPlayer:true");
								
								nout.close();
								sock.close();
								
								return;
							}

							// 팅긴 플레이어를 생각해서 빈 자리에 들어감 
							// 다른 플레이어가 연결시 방장이 될 수 있음
							// 빈자리 찾아서 넣기
							for(int i = 0; i < MAX_PLAYERS + 1; i++) {
								if(sockets[i] == null) {
									playerNum = i;
									break;
								}
							}
							
							sockets[playerNum] = sock;
							socketsSize++;
							
							PrintStream nout = new PrintStream(sock.getOutputStream(), true, "UTF-8");
							Scanner nscan = new Scanner(sock.getInputStream(), "UTF-8");

							// 내 번호와 이미 연결된 플레이어를 보냄
							nout.println("youerNum:" + playerNum);
							send("connectedPlayer:" + playerNum);
							
							for(int i = 0; i < sockets.length; i++) { 
								if (sockets[i] != null && i != playerNum) {
									nout.println("connectedPlayer:" + i);
								}
							}
							
							
							try {
								String line;
								while (true) {
									line = nscan.nextLine();

									if(line.equals("#DISCONNECT"))
										break;

									String[] tokens = line.split(",");
									String[] data = tokens[0].split(":");
									
									// 게임 시작 메시지일 경우 초기화
									if(data[0].equals("#GAMESTART")){
										currentMinigame = 1;
										finishPlayers = 0;
										playTime = new float[3][3];
										send("switchCanvas:1");
										continue;
									}
									
									for (int i = 0; i < sockets.length; i++) {
										if (sockets[i] != null)
											new PrintStream(sockets[i].getOutputStream(), true, "UTF-8").println(line);
									}

									{ // 게임 스위칭 블럭
										if( tokens.length >= 3) {
											data = tokens[2].split(":");
											String key = data[0];
											String value = data[1];
											
											switch (key) {
											case "finish":{
												if( currentMinigame == 2 ) {
													send(String.format("miniGame2Rank:true,%s:%s", playerNum, finishPlayers));
												}
												float timeRemaining = Float.parseFloat(value);
												playTime[playerNum][currentMinigame-1] = timeRemaining;
												System.out.println("finishPlayers: " + finishPlayers + " player: " + playerNum + " timeRemaining: " + timeRemaining);
												finishPlayers++;
												break;
											}
//											case "finish1":{
//											}
											}
											
											// 끝난 플레이어 수랑 소켓 사이즈가 같아지면
											if( finishPlayers == socketsSize ) {
												
												// 3초 카운트새고 캔버스 스위칭
												new Thread(() -> {
													finishPlayers = 0;
													
													int delay = 0;
													int END = 5;
													
													if(currentMinigame == 3) {
														// 변경하려는게 3번재 게임인 경우 15초 기다림
														END = 13;
													}

													// 테슽트 ============ 
//													END = 1;
													
													
													// sleep 1000임
													while( delay < END ) {
														try {
															Thread.sleep(1000); // 1초
														} catch (InterruptedException e) {
															e.printStackTrace();
														}
														delay++;
														System.out.println(delay);
													}

													currentMinigame++;
													try {
														send("switchCanvas:" + currentMinigame);
														System.out.println("캔버스스위칭"+ currentMinigame);
														
														if( currentMinigame == 2 ) {
															// ==================================
															// quiz(라운드, 최소문제)
															// String quiz = quiz(8,4);
															String quiz = quiz(8,4);
															// ==================================
															send("miniGame2Quiz:true,"+quiz);
															
														} else if ( currentMinigame == 4) {
															send("gameResult:true," + result());
															System.out.println(result());
															
														}
													} catch (UnsupportedEncodingException e) {
														e.printStackTrace();
													} catch (IOException e) {
														e.printStackTrace();
													}
												}).start();
											}
										}
									}
								} // while (!line.equals("#DISCONNECT"));
								System.out.println("정상 종료: " + sock.getRemoteSocketAddress());
							} catch (Exception e) {
								System.out.println("강제 종료: " + sock.getRemoteSocketAddress());
								System.out.println(e);
							} finally {
								nscan.close();
								nscan.close();
								nout.close();
								sock.close();

								//플레이어가 튕겼을때
								for (int i = 0; i < sockets.length; i++) {
									if (sockets[i] != null) {
										if(sockets[i].equals(sock)) {
											
											brokenPlayerNum = i;
											sockets[i] = null;
											socketsSize--;
											break;
										}
									}
								}
								
								if( socketsSize == 0 ) {
									System.out.println("서버 초기화");
									currentMinigame = 1;
									finishPlayers = 0;
									playTime = new float[3][3];
								}

								send("disconnectPlayer:" + playerNum);
								
							}
						} catch (Exception e1) {
							System.out.println("뭔가 오류가 났음");
//							e1.printStackTrace();
						}
					}).start();
					

				}
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}).start();

	}

	private String result() {
		
		List<String> temp = new ArrayList<>();
		
		for(int i = 0; i < 3; i++) {
			String time = Float.toString(playTime[i][0] + playTime[i][1] + playTime[i][2]); 
			temp.add(time);
		}
		
		String resultCSV = String.join("&", temp);
		return resultCSV;
		
	}

	public void send(String line) throws UnsupportedEncodingException, IOException {
		for(int i = 0; i < sockets.length; i++)
			if (sockets[i] != null)
				new PrintStream(sockets[i].getOutputStream(), true, "UTF-8")
				.println(line);
	}
	
	public String quiz(int round, int min) {
		
		List<String> temp = new ArrayList<>();
		String[][] quiz = new String[round][];
		Random rand = new Random();
		for (int i = 0; i < round; i++) {
			quiz[i] = new String[min];
			for (int j = 0; j < min; j++) {
				quiz[i][j] = Integer.toString(37 + rand.nextInt(4)); // [37][38][39][37]
													// [38][40][39][38][38]
			}
			temp.add(String.join(":", quiz[i]));
			min++;
		}
		
		String quizCSV = String.join("&", temp);
		return quizCSV;
	}
	
//	public void send(String msg) throws UnsupportedEncodingException, IOException {
//		for (int i = 0; i < sockets.size(); i++) {
//			new PrintStream(sockets.get(i).getOutputStream(), true, "UTF-8").println(msg);
//		}
//	}
//
//	public void send(int playerNum, String msg) throws UnsupportedEncodingException, IOException {
//
//		for (int i = 0; i < sockets.size(); i++) {
////			System.out.println(msg);
////			if (i == playerNum)
////				continue;
//
//			send(msg);
//		}
//	}

//	public Socket[] getSockets() {
//		return sockets;
//	}

//	public boolean isRunning() {
//		return running;
//	}
//
//	public ServerSocket getSvrSock() {
//		return svrSock;
//	}

}
