package com.team.app.miniGame3.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Cars extends Entity {
	
	
	private ArrayList<Car> cars;
	private int carCount;
	private double speed = 0.5;
	private boolean startN ;//시작변수
	private boolean carLoop;
	public Cars() {
		cars = new ArrayList<Car>();	
		startN = true;
		carLoop = true;
	}
	
	
	public void start() {
		Runnable sub2 = new Runnable() {
			@Override
			public void run() {
				while(isActive() && isVisible()) {
					int newCarN;
					int oldCarN = 0;
					int i = 0;
					while(carLoop) {
						Random random1 = new Random();
						Random random2 = new Random();
	
						int rand1 = random1.nextInt(3) + 1; // 1 2 3 램덤값(자동차 등장하는 라인 변수)
						int rand2 = random2.nextInt(4) + 1; // 1 2 3 4램덤값 (자동차 이미지 변수)
	
						newCarN = rand1;
						if (newCarN != oldCarN) {
							new Car(rand1, rand2);
							cars.add(new Car(rand1, rand2)); // rand1:위치,  rand2:차 종류
							cars.get(i).setActive(true);
							cars.get(i).setVisible(true);
							// ==============================
							cars.get(i).setSpeed(speed);
							// ==============================
							carCount++;
							Random threadRand = new Random();
							try {
								// ============================== 
								// 차 생성 스레드 (값조절로 생성 시간 조절...)
								Thread.sleep(threadRand.nextInt((int)(1000)) + 500);
								// ============================== 
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							oldCarN = newCarN;
							
							cars.get(i).setListener(new CarListener() {
								
								@Override
								public void onOut(Car car) {
									// TODO Auto-generated method stub
									for(int i = 0; i < cars.size(); i++) {
										if(cars.get(i) ==  car) {
											cars.get(i).remove();
										}
									}
								}
							});
							i++;
						} else {
							oldCarN = 0;
							continue;
	
						}
					}
				}
			}
		};
		Thread th2 = new Thread(sub2);
		th2.start();
	}


	
	public boolean isCollision(int manX2, int manY2){
		int manX = manX2;
		int manY = manY2;
		
		for(int i=0; i<cars.size(); i++) {
			if (cars.get(i) != null) {
				int carX = (int)cars.get(i).getX();
				int carY = (int)cars.get(i).getY();
				
				if(carX-100 <=manX && manX <= carX+100  &&
						carY-100 <= manY &&  manY <= carY+100 ) {					
						//자동차 사라짐
						cars.get(i).setActive(false);
						cars.get(i).setVisible(false);
						cars.get(i).remove();	
						return true;
				}
			}
				
		}
		return false;
	}
	
	public void setActive(boolean active) {
		super.setActive(active);
		if(active == false) {
			this.carLoop= false;
		}
		if(active == true) {
			start();
		}
		
	}

	@Override
	public void update() {
		
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i) != null) {
				cars.get(i).update();
			}
		}
		
	}


	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i) != null) {
				cars.get(i).paint(g);
			}
		}
	}
	

	public int getCarCount() {
		return carCount;
	}


	public void setCarCount(int carCount) {
		this.carCount = carCount;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public boolean isStartN() {
		return startN;
	}


	public void setStartN(boolean startN) {
		this.startN = startN;
	}
	
	
}
