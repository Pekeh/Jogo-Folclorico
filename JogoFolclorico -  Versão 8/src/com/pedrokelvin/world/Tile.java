package com.pedrokelvin.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;

public class Tile {
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0,320,32,32);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(32,320,32,32);
	public static BufferedImage TILE_GRASSUP = Game.spritesheet.getSprite(64,320,32,32);
	public static BufferedImage TILE_CERCA = Game.spritesheet.getSprite(96,320,32,32);
	public static BufferedImage TILE_WATER = Game.spritesheet.getSprite(128,320,32,32);
	public static BufferedImage TILE_PASS = Game.spritesheet.getSprite(160,384,32,32);
	public static BufferedImage PLAN_TILE = Game.spritesheet.getSprite(0,384,32,32);
	public static BufferedImage TRONCO_TILE1 = Game.spritesheet.getSprite(224,384,32,32);
	public static BufferedImage TRONCO_TILE2 = Game.spritesheet.getSprite(224,384+32,32,32);
	
	//TILE LAGO
	public static BufferedImage TILE_LAGO_DCD = Game.spritesheet.getSprite(256,320,32,32);
	public static BufferedImage TILE_LAGO_C = Game.spritesheet.getSprite(224,320,32,32);
	public static BufferedImage TILE_LAGO_DCE = Game.spritesheet.getSprite(192,320,32,32);
	public static BufferedImage TILE_LAGO_LD = Game.spritesheet.getSprite(288,352,32,32);
	public static BufferedImage TILE_LAGO_LE = Game.spritesheet.getSprite(288,320,32,32);
	public static BufferedImage TILE_LAGO_DBD = Game.spritesheet.getSprite(256,352,32,32);
	public static BufferedImage TILE_LAGO_B = Game.spritesheet.getSprite(224,352,32,32);
	public static BufferedImage TILE_LAGO_DBE = Game.spritesheet.getSprite(192,352,32,32);
	
	
	
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x=x;
		this.y=y;
		this.sprite=sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);	
	}
}
