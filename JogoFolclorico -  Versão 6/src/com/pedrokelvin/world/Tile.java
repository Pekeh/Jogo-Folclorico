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
	public static BufferedImage TILE_TREE = Game.spritesheet.getSprite(192,480,128,160);

	
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
