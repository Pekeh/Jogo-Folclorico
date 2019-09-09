package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class Entity {
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(5,295,22,19);
	public static BufferedImage MANA_EN = Game.spritesheet.getSprite(104,296,16,16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(181,142,10,5);
	public static BufferedImage FIRE_BALL_EN = Game.spritesheet.getSprite(203,147,7,10);
	public static BufferedImage LANCA_EN = Game.spritesheet.getSprite(226,116,27,5);
	public static BufferedImage TRIDENTE_EN = Game.spritesheet.getSprite(32,145,32,11);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(41,295,14,24);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(0,96,32,32);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(256,288,32,32);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(160, 128, 32, 32);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(128, 128, 32, 32);
	public static BufferedImage GUN_UP = Game.spritesheet.getSprite(96, 128, 32, 32);
	public static BufferedImage GUN_DOWN = Game.spritesheet.getSprite(64, 128, 32, 32);
	public static BufferedImage LANCA_RIGHT = Game.spritesheet.getSprite(224, 96, 32, 32);
	public static BufferedImage LANCA_LEFT = Game.spritesheet.getSprite(192, 96, 32, 32);
	public static BufferedImage LANCA_UP = Game.spritesheet.getSprite(160, 96, 32, 32);
	public static BufferedImage LANCA_DOWN = Game.spritesheet.getSprite(128, 96, 32, 32);
	public static BufferedImage TRIDENTE_RIGHT = Game.spritesheet.getSprite(32, 128, 32, 32);
	public static BufferedImage TRIDENTE_LEFT = Game.spritesheet.getSprite(0, 128, 32, 32);
	public static BufferedImage TRIDENTE_UP = Game.spritesheet.getSprite(288, 96, 32, 32);
	public static BufferedImage TRIDENTE_DOWN = Game.spritesheet.getSprite(256, 96, 32, 32);
	public static BufferedImage FIRE_BALL_RIGHT = Game.spritesheet.getSprite(288, 128, 32, 32);
	public static BufferedImage FIRE_BALL_LEFT = Game.spritesheet.getSprite(256, 128, 32, 32);
	public static BufferedImage FIRE_BALL_UP = Game.spritesheet.getSprite(224, 128, 32, 32);
	public static BufferedImage FIRE_BALL_DOWN = Game.spritesheet.getSprite(192, 128, 32, 32);
	public static BufferedImage LANCAMED_UP = Game.spritesheet.getSprite(192, 288, 32, 32);
	public static BufferedImage LANCAMED_DOWN = Game.spritesheet.getSprite(224, 288, 32, 32);
	public static BufferedImage TRIDENTEMED_UP = Game.spritesheet.getSprite(128, 288, 32, 32);
	public static BufferedImage TRIDENTEMED_DOWN = Game.spritesheet.getSprite(160, 288, 32, 32);
	public static BufferedImage IARA_FRONT = Game.spritesheet.getSprite(96, 1248, 32, 32);
	public static BufferedImage IARA_RIGHT = Game.spritesheet.getSprite(32, 1248, 32, 32);
	public static BufferedImage IARA_LEFT = Game.spritesheet.getSprite(64, 1248, 32, 32);
	public static BufferedImage IARA_BACK = Game.spritesheet.getSprite(0, 1248, 32, 32);
	public static BufferedImage IARA_FEEDBACK = Game.spritesheet.getSprite(128,1248, 32, 32);
	public static BufferedImage IARA_DEAD = Game.spritesheet.getSprite(160,1248, 32, 32);
	public static BufferedImage MULA_EN = Game.spritesheet.getSprite(0,1184, 32, 32);
	
	public int x, y, width, height;
	private BufferedImage sprite;
	
	private int maskx, masky, mwidth, mheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); 
	}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky,e2.mwidth,e2.mheight);
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX()+maskx-Camera.x,this.getY()+masky-Camera.y, mwidth, mheight);
	}
	
}
