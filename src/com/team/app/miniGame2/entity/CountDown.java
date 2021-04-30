package com.team.app.miniGame2.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.team.app.entity.SoundEffect;
import com.team.app.miniGame2.canvas.MiniGame2Canvas;


public class CountDown extends Entity {
	private Clip clip; //사운드 클립

	private Image numImg;
	private Image startImg;
	private int x;
	private int y;
	private int size;

	private double numIngW;
	private double numIngH;
	private double startIngW;
	private double startIngH;
	
	private int index;
	private int countSleep;
	
	private CountListener countListener;

	public CountDown() {
		this(3);
	}

	public CountDown(int i) {
		try {
			numImg = ImageIO.read(new File("res/count.png"));
			startImg = ImageIO.read(new File("res/start.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		numIngW = 255 / 3;
		numIngH = 118;
		startIngW = 451;
		startIngH = 320;
		size = 2;

		index = 3;
		countSleep=0;
	}

	public void update() {
		if(countSleep <= 0 ) {
			index--;
//			countSleep = ActionCanvas.fps;
			countSleep = 55;
		}
		countSleep--;
	}

	public void paint(Graphics g) {
		int canvasW = MiniGame2Canvas.instance.getWidth();
		int canvasH = MiniGame2Canvas.instance.getHeight()/3;
		if (index >= 0) {
			x = canvasW/2-(int)numIngW*size/2;
			y = canvasH/2-(int)numIngH*size/2;
			int dx1 = x;
			int dy1 = y;
			int dx2 = x + (int) (numIngW * size);
			int dy2 = y + (int) (numIngH * size);
			int sx1 = (int) (numIngW * index - 1);
			int sy1 = 0;
			int sx2 = (int) (numIngW *(index+1));
			int sy2 = (int) numIngH;
			g.drawImage(numImg, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame2Canvas.instance);
		} else if(index == -1) {
			size = 1;
			
			x = canvasW/2-(int)startIngW*size/2;
			y = canvasH/2-(int)startIngH*size/2;
			int dx1 = x;
			int dy1 = y;
			int dx2 = x + (int) (startIngW * size);
			int dy2 = y + (int) (startIngH * size);
			int sx1 = 0;
			int sy1 = 0;
			int sx2 = (int) startIngW;
			int sy2 = (int) startIngH;
			g.drawImage(startImg, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame2Canvas.instance);
		} else if (index <= -2) {
			if(countListener != null)
				countListener.onEnd(); 
		}
	}
	
	@Override
	public void show() {
		SoundEffect.play("audio/countDown.wav",false);
		super.show();
	}
	
	//효과음
//   public void playSound(String pathName, boolean isLoop) {
//      try {
//         clip = AudioSystem.getClip();
//         File audioFile = new File(pathName);
//         AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
//         clip.open(ais);
//         clip.start();
//         
//      } catch (IOException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      } catch (LineUnavailableException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      } catch (UnsupportedAudioFileException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//      
//	}

		public void setCountListener(CountListener countListener) {
			this.countListener = countListener;
		}

	}
