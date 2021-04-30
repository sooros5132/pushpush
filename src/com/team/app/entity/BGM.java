package com.team.app.entity;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JSlider;

public class BGM {

	private Clip clip;
	private boolean loop;
	private String audioSrc;
	private FloatControl control;
	private static float volume = 0;
	
	public BGM() {
		this("");
	}

	public BGM(String audioSrc) {
		this.audioSrc = audioSrc;
		loop = true;
		setAudioSrc(audioSrc);
		// 볼륨 컨트롤
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public void play() {
		this.start();
	}

	public void start() {
		try {
			clip.start();
			if (loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("오디오 시작 에러");
		}
	}

	public void stop() {
		clip.stop();
	}

	public void setVolume(double volume) {

		try {
			BGM.volume = (float)volume;
			control.setValue(BGM.volume);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("오디오 볼륨 조절 에러");
		}
	}
	
	public float getVolume() {
//		System.out.println(control.getValue());
		return control.getValue();
	}

	public String getAudioSrc() {
		return audioSrc;
	}

	public void setAudioSrc(String audioSrc) {
		this.audioSrc = audioSrc;
		if( clip != null )
			clip.stop();
	
		try {
			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(audioSrc));
			clip.open(ais);
			control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			setVolume(volume);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("오디오 에러");
		}
	}
	
	
	
}
