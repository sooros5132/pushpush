package com.team.app.miniGame3.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.team.app.miniGame1.entity.TimeEndListener;

public class Timer extends Entity {
	private int time = 0;
	private Color fontColor = Color.WHITE;
	private boolean timerLoop;
	private TimeEndListener timeListener;
	// 60초부터 시작해서 1초씩 깎아내려감
	// 0초가 되는 순간 Timeout
	// 필요한 것 : for문(반복해서 시간감소),스레드, sleep함수
	// 캔버스,프레임 만들어서 띄워보기(우선은 띄우기만)
	// 60에서 숫자 내려갈때마다 기존숫자를 지우고 그 위에 새로운 숫자 출력
	// repaint 같은 메서드 사용

	public void setTimeListener(TimeEndListener timeListener) {
		this.timeListener = timeListener;
	}
	
	public Timer(int times) {
		this.time = times;
		timerLoop = true;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int times) {
		this.time = times;
	}

	public void startTimer() {
		int times = this.time;

		Runnable sub = new Runnable() {

			@Override
			public void run() {
				int i = 0;
				while (timerLoop) {
					// int v = 0;
					// System.out.println(time-i);

					if ((times - i) <= 10) {
						fontColor = Color.red;
					}

					setTime(times - i);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					i++;
					if (time == 0) {
						if (timeListener != null) {
						}
						timeListener.onEnd();

						timerLoop = false;
						setActive(false);
					}
				}

			}
		};
		Thread th = new Thread(sub);
		th.start();
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("bold", 1, 70));
		g.drawString("Time", 40, 100);

		g.setColor(fontColor);
		g.setFont(new Font("bold", 1, 70));
		g.drawString(String.valueOf(this.time), 80, 185);

	}

	@Override
	public void update() {
		
	}

	public boolean isTimerLoop() {
		return timerLoop;
	}

	public void setTimerLoop(boolean timerLoop) {
		this.timerLoop = timerLoop;
	}

}