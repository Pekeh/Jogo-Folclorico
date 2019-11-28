package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class Particle extends Entity{

	public int lifeTime = 4;
	public int curLife = 0;
	
	public int spd = 1;
	public double dx = 0;
	public double dy = 0;
	
	
	public Particle(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}
	
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(lifeTime == curLife) {
			Game.entities.remove(this);
		}
	}
	
	public void render(Graphics g) {
		if(Game.player.fireBallActive) {
			g.setColor(Color.RED);
			g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
		}
		else if(Game.player.zarabatanaActive) {
			g.setColor(Color.YELLOW);
			g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
		}
			
	}
	
}
