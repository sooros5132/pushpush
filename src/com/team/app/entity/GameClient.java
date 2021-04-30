package com.team.app.entity;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

	private Socket socket;
	private Scanner nscan;
	private PrintStream nout;
	private boolean connect = false;

	private EventMsgListener msgListener;

	public void setMsgListener(EventMsgListener msgListener) {
		this.msgListener = msgListener;
	}

	public GameClient() {
		System.out.println("GameClient: 아이피를 입력해주세요");
	}

	public GameClient(String serverIP, int port) throws UnknownHostException, IOException {
		if (serverIP.isEmpty()) {
			System.out.println("GameClient: 아이피를 입력해주세요");
			return;
		}
		if (!serverIP.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			System.out.println("GameClient: 유효한 아이피 형식이 아닙니다");
			System.out.println("GameClient: 예) xxx.xxx.xxx.xxx");
			return;
		}


		try {
			socket = new Socket(serverIP, port);
			connect = socket.isConnected();
		} catch (Exception e) {
			throw e;
		}
		
		
		if (connect) {
			nscan = new Scanner(socket.getInputStream(), "UTF-8");
			nout = new PrintStream(socket.getOutputStream(), true, "UTF-8");
		
			new Thread(new Runnable() {
	
				@Override
				public void run() {
	
					
					while (nscan.hasNextLine()) {
						String msg = nscan.nextLine();
	
						if (msgListener != null) {
//							System.out.println(msg);
							msgListener.onMsg(msg);
						}
	
						if (msg == "#DISCONNET") {
							try {
								nscan.close();
								nout.close();
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
	
					}
				}
	
			}).start();
		}
	}

	public Scanner getNscan() {
		return nscan;
	}

	public PrintStream getNout() {
		return nout;
	}

	public Socket getSocket() {
		return socket;
	}

	public boolean isConnect() {
		return connect;
	}

	public void send(String msg) {
		nout.println(msg);
	}

}
