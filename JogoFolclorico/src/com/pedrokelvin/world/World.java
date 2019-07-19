package com.pedrokelvin.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pedrokelvin.entities.Bullet;
import com.pedrokelvin.entities.Enemy;
import com.pedrokelvin.entities.Entity;
import com.pedrokelvin.entities.LifePack;
import com.pedrokelvin.entities.Weapon;
import com.pedrokelvin.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0,0,map.getWidth(),map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx<map.getWidth(); xx++) {
				for(int yy = 0; yy<map.getWidth(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						//Floor
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.TILE_FLOOR);
					}
					else if(pixelAtual == 0xFFFFFFFF){
						//Parede
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32, Tile.TILE_WALL);
					}
					else if(pixelAtual == 0xFF0026FF) {
						//Player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
					}
					else if(pixelAtual == 0xFFFF0000) {
						Game.entities.add(new Enemy(xx*32, yy*32, 32, 32, Entity.ENEMY_EN));
					}
					else if(pixelAtual== 0xFFFF6A00){
						Game.entities.add(new Weapon(xx*32, yy*32, 32, 32, Entity.WEAPON_EN));
					}
					else if(pixelAtual == 0xFFFF9170) {
						Game.entities.add(new LifePack(xx*32, yy*32, 32, 32, Entity.LIFEPACK_EN));
					}
					else if(pixelAtual == 0xFFFFD800) {
						Game.entities.add(new Bullet(xx*32, yy*32, 32, 32, Entity.BULLET_EN));
					}
					
				}
			}
		}
				catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 8) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 4) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE - 8) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 4) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public void render (Graphics g) {
		int xstart = Camera.x / 32;
		int ystart = Camera.y / 32;
		
		int xfinal = xstart + (Game.WIDTH / 32);
		int yfinal = ystart + (Game.HEIGHT / 32);

		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy>=HEIGHT )
					continue;
				Tile tile = tiles [xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
