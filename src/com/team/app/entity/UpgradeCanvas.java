package com.team.app.entity;

import java.awt.Canvas;
import java.awt.Graphics;

import com.team.app.miniGame1.entity.GameEventListener;

public class UpgradeCanvas extends Canvas{

	private boolean activeCanvas = false;
	private int finishPlayers = 0;
	private int playerNum = -1;
	
	private GameEventListener gameEventListener;
	
	public void setGameEventListener(GameEventListener gameEventListener) {
		this.gameEventListener = gameEventListener;
	}
	
	public GameEventListener getGameEventListener() {
		return gameEventListener;
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	public void start() {
		// Frame에서 객체형변환 없이 공통적인 호출을 필요로 만든 메소드
	}
	
	public void playerSetting(int playerNum) {
		// 캔버스 스위칭 하고 바로 호출될 메소드
		// 시작 하기 전에 Frame에서 호출할 플레이어 기본 세팅 메소드
		activeCanvas = true;
		this.playerNum = playerNum;
	}
	
	public void otherPlayerEvent(int otherPlayer, String key, String value) {
		// 다른 플레이어의 이벤트를 받았을 때 호출될 메소드
	}
	
//	public abstract void start();
//	public abstract void otherPlayerEvent(int otherPlayer, String key, String value);
//	public abstract void playerSetting(int playerNum);
	
	public boolean isActiveCanvas() {
		return activeCanvas;
	}

	public void setActiveCanvas(boolean activeCanvas) {
		this.activeCanvas = activeCanvas;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public int getFinishPlayers() {
		return finishPlayers;
	}

	public void setFinishPlayers(int finishPlayers) {
		this.finishPlayers = finishPlayers;
	}
	
}

