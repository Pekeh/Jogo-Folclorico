package com.pedrokelvin.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	
	private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("/sound/music.wav");
	public static final Sound damageEffect = new Sound("/sound/damage.wav");
	public static final Sound hurtEffect = new Sound("/sound/hurt.wav");
	public static final Sound shootEffect = new Sound("/sound/shoot.wav");
	public static final Sound collectEffect = new Sound("/sound/collect.wav");
	
	private Sound (String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {}
	}
	
	
	public void play() {
		try {
			new Thread () {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread () {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}
}
