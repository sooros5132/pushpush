package com.team.app.miniGame2.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.team.app.entity.SoundEffect;
import com.team.app.miniGame2.canvas.MiniGame2Canvas;

public class Quiz extends Entity {

	private Random rand;

	// 초기 배열
	private int round;
	private int min;// 첫 배열의 크기
	private int max = 7;
	private int[][] quiz;
	private SoundEffect se;
	
	
	

	// check 할떄 이용할 변수
	private int quizRound = 0;
	private int quizNum = 0;
	private int keyCode; // 얘를 이용해서 폭발 이미지 출력할거야

	private static Image img_punch;
	private static Image img_boom;

	private final int LEFT = 37;
	private final int UP = 38;
	private final int RIGHT = 39;
	private final int DOWN = 40;
	
	private boolean half;
	private boolean end;

	private boolean activeSound = false;
	

	private QuizListener quizListener;

	public void setQuizListener(QuizListener quizListener) {
		this.quizListener = quizListener;
	}

	static {
		try {
			img_punch = ImageIO.read(new File("res/game2/box_40.png"));
			img_boom = ImageIO.read(new File("res/game2/boom.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Quiz() {
		this(10, 10, 7, 4);
	}

	public Quiz(double x, double y, int round, int min) {
		setX(x);
		setY(y);
		this.round = round; // 라운드 2 2
		this.min = min; // 시작 문제 수 4 4
		

		quiz = new int[round][];
		se = new SoundEffect();
		rand = new Random();
		for (int i = 0; i < round; i++) {
			quiz[i] = new int[min];
			for (int j = 0; j < min; j++) {

				quiz[i][j] = 37 + rand.nextInt(4); // [37][38][39][37]
													// [38][40][39][38][38]
			}
			min++;
		}
	}

	@Override
	public void update() {
		if (round == quizRound) {
			if(quizListener != null)
				quizListener.onEnd();
		}
	}

	@Override
	public void paint(Graphics g) {
		int width = 180;
		int height = 180;

		int dx1;
		int dy1 = 45+(int)getY();
		int dx2;
		int dy2 = height * 4/3+(int)getY();

		int sx1;
		int sx2;
		int sy1 = 0;
		int sy2 = height;

		//퀴즈라운드가 round/2 면 힘내혀과음
		//퀴즈라운드가 round-1 이면 라스트원
		if (round != quizRound) {
			for (int j = 0; j < quiz[quizRound].length; j++) {// 이미 배열은 만들어져있고 값도 넣어져있어 출력만 하면되
				int key = quiz[quizRound][j];

				dx1 = j * width /2+(int)getX(); //원하는 주먹의 크기 + 원하는 위치
				dx2 = (j + 1) * width /2+(int)getX();

				sx1 = width * (key - 37);
				sx2 = width * (key - 37) + width;


				switch (key) {
				case LEFT: // key-37 = 0,1,2,3
				case UP:
				case RIGHT:
				case DOWN:
					//System.out.printf("현재문제 배열 : %d,%d,%d,%d\n", round, quizRound, quizNum, quiz[quizRound].length - 1);

					if (quizNum > j) {
						g.drawImage(img_boom, dx1, dy1, dx2, dy2, 0, 0, 700, 700, MiniGame2Canvas.instance);
					}
					else g.drawImage(img_punch, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame2Canvas.instance);

					break;
				}
			}
		}
	}

	public boolean check(int keyCode) {
		// 한사이클이 돌때마다 quizNum값을 증가시켜주고
		// 더이상 배열이 없다면 quizRound값 증가
		this.keyCode = keyCode;
		if (quizRound < round) {
			if (quiz[quizRound][quizNum] == keyCode) { // 37 38 39 40

				quizNum++;// 체크해서 맞으면 다음으로 얘 위로 올렸으니까 아래에 길이에서 -1은 없애
				if (quizNum == quiz[quizRound].length) {// 지금 문제가 마지막 문제면
					if (round != quizRound) {// 마지막 라운드가 아니면
						quizRound++;
						if(activeSound) {
							if(quizRound == 1)
								SoundEffect.play("audio/round1.wav", false);
							if(quizRound == round/2)
								SoundEffect.play("audio/icandoit.wav", false);
							if(quizRound == round-1)
								SoundEffect.play("audio/lastone.wav",false) ;
//							if(quizRound == round)
//								SoundEffect.play("audio/end.wav", false);
						}
						quizNum = 0;
					} // 마지막 라운드면(round==quizRound) 아무것도 없어 이건 페인트에서 그리자
				}

				return true;
			}

			quizNum = 0;// 틀리면 처음부터
		}
		return false;
	}

	public void setQuiz(String line) {
		String[] temps = line.split("&");

		List<Integer> quizs = new ArrayList<>();

		quiz = new int[temps.length][];
		
		for ( int i = 0; i < temps.length; i++) {
			String[] temp = temps[i].split(":");
			quiz[i] = new int[temp.length];
			for( int j = 0; j < temp.length; j++) {
				quiz[i][j] = Integer.parseInt(temp[j]);
			}
			
		}

		round = temps.length;
		
	}

	public boolean isHalf() {
		return half;
	}

	public void setHalf(boolean half) {
		this.half = half;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public boolean isActiveSound() {
		return activeSound;
	}

	public void setActiveSound(boolean activeSound) {
		this.activeSound = activeSound;
	}
	
	
}

//package com.team.mini2.app.entity;
//
//import java.awt.Graphics;
//import java.awt.Image;
//import java.io.File;
//import java.io.IOException;
//import java.util.Random;
//
//import javax.imageio.ImageIO;
//
//import com.team.mini2.app.canvas.ActionCanvas;
//
//public class Quiz extends Entity {
//
//   private Random rand;
//
//   // 초기 배열
//   private int num;
//   private int max;
//   private int min;// 첫 배열의 크기
//   private int[][] quiz;
//   private int i = 0;
//   private int j = 0;
//
//   // check 할떄 이용할 변수
//   private int quizRound = 0;
//   private int quizNum = 0;
//
//   private static Image img_box;
//   private static Image img_boom;
//
//   final int LEFT = 37;
//   final int UP = 38;
//   final int RIGHT = 39;
//   final int DOWN = 40;
//
//   static {
//      try {
//         img_box = ImageIO.read(new File("res/box.png"));
//         img_boom = ImageIO.read(new File("res/boom.png"));
//      } catch (IOException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//   }
//
//   public Quiz() {
//      this(0, 0, 4, 2, 10);
//   }
//
//   public Quiz(double x, double y, int num, int min, int max) {
//      setX(x);
//      setY(y);
//      this.num = num;
//      this.min = min;
//      this.max = max;
//      quiz = new int[num][];
//      rand = new Random();
//      for (int i = 0; i < num; i++) {
//         quiz[i]=new int[min];
//         for (int j = 0; j < min; j++) {
//            quiz[i][j] = 37 + rand.nextInt(4); // [37][38][39][37]
//                                       // [38][40][39][38][38]
//         }
//         min++;
//      }
//
//   }
//
//   @Override
//   public void update() {
//
//   }
//
//   @Override
//   public void paint(Graphics g) {
//      int weight = 180;
//      int height = 180;
//
//      int dx1;
//      int dy1 = height * 0;
//      int dx2;
//      int dy2 = height * 1;
//
//      int sx1;
//      int sx2;
//      int sy1 = 0;
//      int sy2 = height;
//
//      if( quizRound != num) {
//         for (int j = 0; j < quiz[quizRound].length; j++) {// 이미 배열은 만들어져있고 값도 넣어져있어 출력만 하면되
//            int key = quiz[quizRound][j];
//   
//            dx1 = j * weight / 3;
//            dx2 = (j + 1) * weight / 3;
//   
//            sx1 = weight * (key - 37);
//            sx2 = weight * (key - 37) + weight;
//   
//            switch (key) {
//            case LEFT: // key-37 = 0,1,2,3
//            case UP:
//            case RIGHT:
//            case DOWN:
//   //            if (check(key)) {
//   //               
//   //               g.drawImage(img_boom, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, ActionCanvas.instance);
//   //            }
//               g.drawImage(img_box, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, ActionCanvas.instance);
//            }
//         }
//      } else {
//         System.out.println("끝");
//      }
//   }
//
//   public boolean check(int keyCode) {
//      // 한사이클이 돌때마다 j값을 증가시켜주고
//      // 더이상 배열이 없다면 i값 증가
//
//      if (quiz[quizRound][quizNum] == keyCode) { // 37 38 39 40
////         System.out.printf("현재문제 배열 : %d,%d,%d\n", quizRound, quizNum,quiz[quizRound].length - 1);
////         일단 증가
//         quizNum++;// 체크해서 맞으면 다음으로
//         int len = quiz[quizRound].length;
//         if (quizNum == len) {
//            if( quizRound < num) { // 마지막 문제가 아니라면 증가시킴
//               quizRound++;// 지금 문제가 마지막 문제면
//            }
//            System.out.println("증가");
//            quizNum = 0;
//         }
//         return true;
//      }
//      quizNum = 0;// 틀리면 처음부터
//      return false;
//   }
//
//}
