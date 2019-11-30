package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class BulletBoitata extends Entity{
	private double dx;
	private double dy;
	private double spd = 4;
	
	private int life = 100, curLife = 0;
	
	private BufferedImage fireBallR;
	private BufferedImage fireBallL;
	private BufferedImage fireBallU;
	private BufferedImage fireBallD;
	
	public BulletBoitata(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx=dx;
		this.dy=dy;
	}
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(curLife == life) {
			Game.bulletBoitata.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		fireBallR= Game.spritesheet.getSprite(305, 141, 10, 7);
		fireBallL = Game.spritesheet.getSprite(262, 141, 10, 7);
		fireBallU = Game.spritesheet.getSprite(242,135, 7, 10);
		fireBallD = Game.spritesheet.getSprite(203,147, 7, 10);
		g.setColor(Color.BLUE);
		Graphics2D g2 = (Graphics2D) g;
		if(Boitata.indexBoitata == 2) {
			g2.drawImage(fireBallL, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Boitata.indexBoitata == 1) {
			g2.drawImage(fireBallR, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Boitata.indexBoitata == 0) {
			g2.drawImage(fireBallU, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Boitata.indexBoitata == 3) {
			g2.drawImage(fireBallD, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
