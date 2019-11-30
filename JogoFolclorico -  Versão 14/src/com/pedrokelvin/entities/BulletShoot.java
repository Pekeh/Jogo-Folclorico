package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class BulletShoot  extends Entity{
	
	private int dx;
	private int dy;
	private double spd = 5;
	
	private int life = 100, curLife = 0;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx=dx;
		this.dy=dy;
	}
	public void tick() {
		if(World.isFreeDynamic((int)(x+(dx*spd)), (int)(y+(dy*spd)), 3, 3) && Game.CUR_LEVEL!=17) {
			x+=dx*spd;
			y+=dy*spd;
		}else if(World.isFreeDynamicXp((int)(x+(dx*spd)), (int)(y+(dy*spd)), 3, 3) && Game.CUR_LEVEL==17) {
			x+=dx*spd;
			y+=dy*spd;
		}
		else {
			Game.bullets.remove(this);
			World.generateParticles(100, (int) x, (int) y);
			return;
		}
		curLife++;
		if(curLife == life) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
	}
}
