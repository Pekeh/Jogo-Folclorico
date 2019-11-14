package com.pedrokelvin.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;

public class Tile {
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0,320,32,32);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(32,320,32,32);
	public static BufferedImage TILE_GRASSUP = Game.spritesheet.getSprite(128,416,32,32);
	public static BufferedImage TILE_CERCA = Game.spritesheet.getSprite(96,320,32,32);
	public static BufferedImage TILE_WATER = Game.spritesheet.getSprite(160,320,32,32);
	public static BufferedImage TILE_TRILHO1 = Game.spritesheet.getSprite(160,384,32,32);
	public static BufferedImage TILE_TRILHO2 = Game.spritesheet.getSprite(192,384,32,32);
	public static BufferedImage TILE_TRILHO3 = Game.spritesheet.getSprite(224,384,32,32);
	public static BufferedImage ARVORE1_TILE = Game.spritesheet.getSprite(256,512,32,32);
	public static BufferedImage ARVORE2_TILE = Game.spritesheet.getSprite(288,512,32,32);
	public static BufferedImage PLAN_TILE = Game.spritesheet.getSprite(0,384,32,32);
	public static BufferedImage TRONCO_TILE1 = Game.spritesheet.getSprite(224,416,32,32);
	public static BufferedImage TRONCO_TILE2 = Game.spritesheet.getSprite(224,448,32,32);
	public static BufferedImage TRONCO2_TILE1 = Game.spritesheet.getSprite(160,448,32,32);
	public static BufferedImage TRONCO2_TILE2 = Game.spritesheet.getSprite(192,448,32,32);
	public static BufferedImage SWAMP_TILE = Game.spritesheet.getSprite(160,416,32,32);
	public static BufferedImage PEDRA_TILE = Game.spritesheet.getSprite(64,416,32,32);
	public static BufferedImage FOGUEIRA_TILE = Game.spritesheet.getSprite(192,416,32,32);
	public static BufferedImage MATO1_TILE = Game.spritesheet.getSprite(32,384,32,32);
	public static BufferedImage MATO2_TILE = Game.spritesheet.getSprite(96, 416,32,32);
	public static BufferedImage PLANTA_TILE = Game.spritesheet.getSprite(64,384,32,32);
	public static BufferedImage COGU1_TILE = Game.spritesheet.getSprite(96,384,32,32);
	public static BufferedImage COGU2_TILE = Game.spritesheet.getSprite(128,384,32,32);
	public static BufferedImage BIG_FLOWER_TILE = Game.spritesheet.getSprite(0,416,32,32);
	public static BufferedImage BIG_FLOWER2_TILE = Game.spritesheet.getSprite(160,480,32,32);
	public static BufferedImage ROCHA_TILE = Game.spritesheet.getSprite(32,416,32,32);
	public static BufferedImage VITORIA_REGIA1_TILE = Game.spritesheet.getSprite(0,352,32,32);
	public static BufferedImage VITORIA_REGIA2_TILE = Game.spritesheet.getSprite(32,352,32,32);
	public static BufferedImage MILHO_TILE = Game.spritesheet.getSprite(256,384,32,32);
	public static BufferedImage NINHO_TILE = Game.spritesheet.getSprite(288,384,32,32);
	public static BufferedImage VIOLETA_TILE = Game.spritesheet.getSprite(256,416,32,32);
	public static BufferedImage FIRE_FLOWER_TILE = Game.spritesheet.getSprite(288,416,32,32);
	public static BufferedImage ROSA_TILE = Game.spritesheet.getSprite(256,448,32,32);
	public static BufferedImage SAMAMBAIA_TILE = Game.spritesheet.getSprite(288,448,32,32);
	public static BufferedImage ROCHA_COM_MUSGO_TILE = Game.spritesheet.getSprite(256,480,32,32);
	public static BufferedImage ROCHA_COM_MUSGO2_TILE = Game.spritesheet.getSprite(288,480,32,32);
	public static BufferedImage ROCHA_COM_MUSGO3_TILE = Game.spritesheet.getSprite(224,480,32,32);
	public static BufferedImage FLORZINHA_TILE = Game.spritesheet.getSprite(192,480,32,32);
	public static BufferedImage RAIZES_TILE1 = Game.spritesheet.getSprite(160,512,32,32);
	public static BufferedImage RAIZES_TILE2 = Game.spritesheet.getSprite(192,512,32,32);
	//TILE LAGO
	public static BufferedImage TILE_LAGO_DCD = Game.spritesheet.getSprite(256,320,32,32);
	public static BufferedImage TILE_LAGO_C = Game.spritesheet.getSprite(224,320,32,32);
	public static BufferedImage TILE_LAGO_DCE = Game.spritesheet.getSprite(192,320,32,32);
	public static BufferedImage TILE_LAGO_LD = Game.spritesheet.getSprite(288,352,32,32);
	public static BufferedImage TILE_LAGO_LE = Game.spritesheet.getSprite(288,320,32,32);
	public static BufferedImage TILE_LAGO_DBD = Game.spritesheet.getSprite(256,352,32,32);
	public static BufferedImage TILE_LAGO_B = Game.spritesheet.getSprite(224,352,32,32);
	public static BufferedImage TILE_LAGO_DBE = Game.spritesheet.getSprite(192,352,32,32);
	//TILE BORDA
	public static BufferedImage TILE_BORDA_DCE = Game.spritesheet.getSprite(0,448,32,32);
	public static BufferedImage TILE_BORDA_CC = Game.spritesheet.getSprite(32,448,32,32);
	public static BufferedImage TILE_BORDA_DCD = Game.spritesheet.getSprite(64,448,32,32);
	public static BufferedImage TILE_BORDA_ALTA_DCE = Game.spritesheet.getSprite(96,448,32,32);
	public static BufferedImage TILE_BORDA_ALTA_DCD = Game.spritesheet.getSprite(128,448,32,32);
	public static BufferedImage TILE_BORDA_EC = Game.spritesheet.getSprite(0,480,32,32);
	public static BufferedImage TILE_BORDA_C = Game.spritesheet.getSprite(32,480,32,32);
	public static BufferedImage TILE_BORDA_DC = Game.spritesheet.getSprite(64,480,32,32);
	public static BufferedImage TILE_BORDA_BAIXO_DCE = Game.spritesheet.getSprite(96,480,32,32);
	public static BufferedImage TILE_BORDA_BAIXO_DCD = Game.spritesheet.getSprite(128,480,32,32);
	public static BufferedImage TILE_BORDA_DBE = Game.spritesheet.getSprite(0,512,32,32);
	public static BufferedImage TILE_BORDA_CB = Game.spritesheet.getSprite(32,512,32,32);
	public static BufferedImage TILE_BORDA_DBD = Game.spritesheet.getSprite(64,512,32,32);
	public static BufferedImage TILE_BORDA_CENTRO_E = Game.spritesheet.getSprite(96,512,32,32);
	public static BufferedImage TILE_BORDA_CENTRO_D = Game.spritesheet.getSprite(128,512,32,32);
	public static BufferedImage TILE_BORDA_CENTRO_C = Game.spritesheet.getSprite(0,544,32,32);
	public static BufferedImage TILE_BORDA_CENTRO_B = Game.spritesheet.getSprite(96,544,32,32);
	
	
	
	
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
