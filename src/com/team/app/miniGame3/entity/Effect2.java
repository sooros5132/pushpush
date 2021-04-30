package com.team.app.miniGame3.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import com.team.app.entity.SoundEffect;
import com.team.app.miniGame3.canvas.MiniGame3Canvas;

public class Effect2 extends Entity {
	private Clip clip;

	private Image img;
	private int index;
	private int endPoint;
	private int size;

	public Effect2() {
		setX(0);
		setY(0);
		setWidth(2500/10);
		setHeight(600/4);
		index = 0;
		size=1;
		endPoint = 39;
		try {
			img = ImageIO.read(new File("res/game3/finish.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setLocation(double x,double y){
		setX(x);
		setY(y);
	}

	@Override
	public void update() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(index!=endPoint) {
					index++;
					try {
						Thread.sleep(70); // 16.666666
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(index);
				}
				Effect2.this.hide();
				Effect2.this.setActive(false);
				index=0;
			}
			
		}).start();
		
//		index++;
//			if(index==endPoint)
//				this.hide();
	}

	@Override
	public void paint(Graphics g) {
		int w =(int)getWidth();
		int h = (int)getHeight();
		int canvasW = MiniGame3Canvas.instance.getWidth();
		int canvasH = MiniGame3Canvas.instance.getHeight();
		
		int dx1 =0;
		int dy1 =0;
		int dx2 = canvasW;
		int dy2 = canvasH;

		int sx1 =w*(index%10);
		int sy1 =h*(index/10);
		int sx2 =w+ w*(index%10);
		int sy2 =h+h*(index/10);
		
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame3Canvas.instance);

	}
	
	@Override
	public void show() {
//		SoundEffect.play("audio/effect2.wav", false);
		this.update();
		super.show();
	}
	
//	@Override
//	public void setActive(boolean active) {
//
//		super.setActive(active);
//	}

	

	
//	//효과음
//	   public void playSound(String pathName, boolean isLoop) {
//	      try {
//	         clip = AudioSystem.getClip();
//	         File audioFile = new File(pathName);
//	         AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
//	         clip.open(ais);
//	         clip.start();
//	         
//	      } catch (IOException e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	      } catch (LineUnavailableException e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	      } catch (UnsupportedAudioFileException e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	      }
//	      
//		}

}
