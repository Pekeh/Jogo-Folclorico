package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class FireBallShoot extends Entity{
	private double dx;
	private double dy;
	private double spd = 5;
	
	private int life = 100, curLife = 0;
	
	public FireBallShoot(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx=dx;
		this.dy=dy;
	}
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(curLife == life) {
			Game.fireBallShoot.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 6, 6);
	}
}
