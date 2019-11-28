package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class FireBallShoot extends Entity{
	private double dx;
	private double dy;
	private double spd = 5;
	
	private int life = 100, curLife = 0;
	
	private BufferedImage fireBallR;
	private BufferedImage fireBallL;
	private BufferedImage fireBallU;
	private BufferedImage fireBallD;
	
	public FireBallShoot(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx=dx;
		this.dy=dy;
	}
	
	public void tick() {
		if(World.isFreeDynamic((int)(x+(dx*spd)), (int)(y+(dy*spd)), 3, 3)) {
			x+=dx*spd;
			y+=dy*spd;
		}else {
			Game.fireBallShoot.remove(this);
			World.generateParticles(100, (int) x, (int) y);
			return;
		}
		curLife++;
		if(curLife == life) {
			Game.fireBallShoot.remove(this);
			return;
		}
	}
	
	
	public void render(Graphics g) {
		fireBallR= Game.spritesheet.getSprite(305, 141, 10, 7);
		fireBallL = Game.spritesheet.getSprite(262, 141, 10, 7);
		fireBallU = Game.spritesheet.getSprite(242,135, 7, 10);
		fireBallD = Game.spritesheet.getSprite(203,147, 7, 10);
		g.setColor(Color.RED);
		Graphics2D g2 = (Graphics2D) g;
		if(Game.player.dir == Game.player.left_dir) {
			g2.drawImage(fireBallL, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Game.player.dir == Game.player.right_dir) {
			g2.drawImage(fireBallR, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Game.player.dir == Game.player.up_dir) {
			g2.drawImage(fireBallU, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Game.player.dir == Game.player.down_dir) {
			g2.drawImage(fireBallD, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}

	