package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class BulletBoto extends Entity{
	private double dx;
	private double dy;
	private double spd = 5;
	
	private int life = 100, curLife = 0;
	
	private BufferedImage waterBallR;
	private BufferedImage waterBallL;
	private BufferedImage waterBallU;
	private BufferedImage waterBallD;
	
	public BulletBoto(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx=dx;
		this.dy=dy;
	}
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(curLife == life) {
			Game.bulletBoto.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		waterBallR= Game.spritesheet.getSprite(305, 1262, 10, 7);
		waterBallL = Game.spritesheet.getSprite(262,1262, 10, 7);
		waterBallU = Game.spritesheet.getSprite(242,1255, 7, 10);
		waterBallD = Game.spritesheet.getSprite(203,1267, 7, 10);
		g.setColor(Color.BLUE);
		Graphics2D g2 = (Graphics2D) g;
		if(Boto.indexBoto == 2 || Boto.indexBoto == 3) {
			g2.drawImage(waterBallL, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Boto.indexBoto == 0 || Boto.indexBoto == 1) {
			g2.drawImage(waterBallR, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Boto.indexBoto == 2 || Boto.indexBoto == 3) {
			g2.drawImage(waterBallU, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Boto.indexBoto == 0 || Boto.indexBoto == 1) {
			g2.drawImage(waterBallD, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
