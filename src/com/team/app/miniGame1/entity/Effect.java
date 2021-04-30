package com.team.app.miniGame1.entity;

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

import com.team.app.entity.Entity;
import com.team.app.entity.SoundEffect;
import com.team.app.miniGame1.canvas.MiniGame1Canvas;

public class Effect extends Entity {
	private Clip clip;

	private Image img;
	private int index;
	private int endPoint;
	private int size;

	public Effect() {
		setX(-100);
		setY(0);
		setWidth(2000/10);
		setHeight(445/3);
		index = 0;
		size=6;
		endPoint =29;
		try {
			img = ImageIO.read(new File("res/game1/effect.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update() {
		index++;

		if(index==endPoint) {
			this.hide();
			//뒤에 반짝이 이벤트 인터페이스 작성
			
		}
		
		
	}

	@Override
	public void paint(Graphics g) {
		int x =(int)getX();
		int y = (int)getY();
		int w =(int)getWidth();
		int h = (int)getHeight();
		
		int dx1 =x;
		int dy1 =y;
		int dx2 = (x+w)*size;
		int dy2 = (y+h)*size;

		int sx1 =w*(index%10);
		int sy1 =h*(index/10);
		int sx2 =w+ w*(index%10);
		int sy2 =h+h*(index/10);
		
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, MiniGame1Canvas.instance);

	}
	
	@Override
	public void show() {
		SoundEffect.play("audio/effect2.wav", false);
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
}
