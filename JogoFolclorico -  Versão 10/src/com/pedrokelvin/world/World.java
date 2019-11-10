package com.pedrokelvin.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.pedrokelvin.entities.Bullet;
import com.pedrokelvin.entities.Enemy;
import com.pedrokelvin.entities.Entity;
import com.pedrokelvin.entities.FireBall;
import com.pedrokelvin.entities.Iara;
import com.pedrokelvin.entities.Lanca;
import com.pedrokelvin.entities.Lifepack;
import com.pedrokelvin.entities.Mana;
import com.pedrokelvin.entities.Mula;
import com.pedrokelvin.entities.Particle;
import com.pedrokelvin.entities.Player;
import com.pedrokelvin.entities.Tridente;
import com.pedrokelvin.entities.Weapon;
import com.pedrokelvin.graficos.Spritesheet;
import com.pedrokelvin.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	public int passX;
	public int passY;
	public int passageX;
	public int passageY;
	
	private static int saveLife;
	private static int saveMana;
	private static int saveAmmo;
	private static boolean saveTridente;
	private static boolean saveFireBall;
	private static boolean saveLanca;
	private static boolean saveZarabatana;
	
	public World(String path) {
		try {
			System.out.println(path);
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0,0,map.getWidth(),map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx<map.getWidth(); xx++) {
				for(int yy = 0; yy<map.getWidth(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.PLAN_TILE);
					else if(Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.TILE_FLOOR);	
					else if(Game.CUR_LEVEL==5)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.SWAMP_TILE);
					if(pixelAtual == 0xFF000000) {
						//Floor
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2)
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.PLAN_TILE);
						else if(Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4)
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.TILE_FLOOR);
						else if(Game.CUR_LEVEL==5)
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.SWAMP_TILE);
					}
					else if(pixelAtual == 0xFFFFFFFF){
						//Parede
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==5)
							tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32, Tile.PEDRA_TILE);
						else if(Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4)
							tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32, Tile.TILE_WALL);
					}
					
					else if(pixelAtual == 0xFFB5B5B5){
						//Cerca
						tiles[xx + (yy*WIDTH)] = new CercaIara(xx*32,yy*32, Tile.TILE_CERCA);
					}
					else if(pixelAtual == 0xFF177A40){
						//Tronco
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TRONCO_TILE1);
					}
					else if(pixelAtual == 0xFF177A41){
						//Tronco
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TRONCO_TILE2);
					}
					else if(pixelAtual == 0xFF177A42) {
						//Flor Grande
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.BIG_FLOWER_TILE);
					}
					else if(pixelAtual == 0xFF177A43) {
						//Rocha
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.ROCHA_TILE);
					}
					else if(pixelAtual == 0xFF177A44){
						//Tronco2
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TRONCO2_TILE1);
					}
					else if(pixelAtual == 0xFF177A45){
						//Tronco2
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TRONCO2_TILE2);
					}
					else if(pixelAtual == 0xFF177A46) {
						//Rocha com musgo
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.ROCHA_COM_MUSGO_TILE);
					}
					else if(pixelAtual == 0xFF177A47) {
						//Rocha com musgo2
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.ROCHA_COM_MUSGO2_TILE);
					}
					else if(pixelAtual == 0xFF177A48) {
						//Rocha com musgo3
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.ROCHA_COM_MUSGO3_TILE);
					}
					else if(pixelAtual == 0xFF177A49){
						//Raizes
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.RAIZES_TILE1);
					}
					else if(pixelAtual == 0xFF177A50){
						//Raizes
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.RAIZES_TILE2);
					}
					
					
					else if(pixelAtual == 0xFF00FF00) {
						//Fogueira
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.FOGUEIRA_TILE);
					}
					else if(pixelAtual == 0xFF00FF01) {
						//Mato
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.MATO1_TILE);
					}
					else if(pixelAtual == 0xFF00FF02) {
						//Mato2
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.MATO2_TILE);
					}
					else if(pixelAtual == 0xFF00FF03) {
						//Planta
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.PLANTA_TILE);
					}
					else if(pixelAtual == 0xFF00FF04) {
						//Cogu1
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.COGU1_TILE);
					}
					else if(pixelAtual == 0xFF00FF05) {
						//Cogu2
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.COGU2_TILE);
					}
					else if(pixelAtual == 0xFF00FF06){
						//Grama Alta
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_GRASSUP);
					}
					else if(pixelAtual == 0xFF00FF07){
						//Milho
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.MILHO_TILE);
					}
					else if(pixelAtual == 0xFF00FF07){
						//Milho
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.MILHO_TILE);
					}
					else if(pixelAtual == 0xFF00FF08){
						//Ninho
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.NINHO_TILE);
					}
					else if(pixelAtual == 0xFF00FF09){
						//Ninho
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.FIRE_FLOWER_TILE);
					}
					else if(pixelAtual == 0xFF00FF10){
						//Ninho
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.ROSA_TILE);
					}
					else if(pixelAtual == 0xFF00FF11){
						//Ninho
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.SAMAMBAIA_TILE);
					}
					else if(pixelAtual == 0xFF00FF12){
						//Florzinha
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.FLORZINHA_TILE);
					}
				
					
					else if(pixelAtual == 0xFF001DB0){
						//Agua
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_WATER);
					}
					else if(pixelAtual == 0xFF001DB1){
						//Vitoria Regia1
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.VITORIA_REGIA1_TILE);
					}
					else if(pixelAtual == 0xFF001DB2){
						//Vitoria Regia2
						
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.VITORIA_REGIA2_TILE);
					}
					
					else if(pixelAtual == 0xFFBEB391){
						//Passagem
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2)
							tiles[xx + (yy*WIDTH)] = new PassTile(xx*32,yy*32, Tile.TILE_PASS3);
						else if(Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4)
							tiles[xx + (yy*WIDTH)] = new PassTile(xx*32,yy*32, Tile.TILE_PASS);
						else if(Game.CUR_LEVEL==5)
							tiles[xx + (yy*WIDTH)] = new PassTile(xx*32,yy*32, Tile.TILE_PASS2);
						passageX = xx;
						passageY = yy;
						passX = xx*32;
						passY = yy*32;
					}
					

					//LAGO FLOOR TILES	
					else if(pixelAtual == 0xFFFF0069){
						//Lago Diagonal Cima Direita
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_DCD);
					}
					else if(pixelAtual == 0xFFFF0068){
						//Lago Cima
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_C);
					}
					else if(pixelAtual == 0xFFFF0067){
						//Lago Diagonal Cima Esquerda
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_DCE);
					}
					else if(pixelAtual == 0xFFFF0066){
						//Lago Lateral Direita
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_LD);
					}
					else if(pixelAtual == 0xFFFF0064){
						//Lago Lateral Esquerda
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_LE);
					}
					else if(pixelAtual == 0xFFFF0063){
						//Lago Diagonal Baixo Direita
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_DBD);
					}
					else if(pixelAtual == 0xFFFF0062){
						//Lago Baixo
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_B);
					}
					else if(pixelAtual == 0xFFFF0061){
						//Lago Diagonal Baixo Esquerda
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_LAGO_DBE);
					}
				
					
					//ENTIDADES
					else if(pixelAtual == 0xFF0026FF) {
						//Player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
					}
					else if(pixelAtual == 0xFFFF0000) {
						Enemy en = new Enemy(xx*32, yy*32, 32, 32, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}
					else if(pixelAtual == 0xFFFF0065) {
						Iara iara = new Iara(xx*32, yy*32, 32, 32, Entity.IARA_FRONT);
						Game.entities.add(iara);
						Game.iara.add(iara);
					}
					else if(pixelAtual == 0xFFFF0050) {
						Mula mula = new Mula(xx*32, yy*32, 32, 32, Entity.MULA_EN);
						Game.entities.add(mula);
						Game.mula.add(mula);
					}
					
					
					//COLETAVEIS
					else if(pixelAtual== 0xFFFF6A00){
						Game.entities.add(new Weapon(xx*32, yy*32, 10,5, Entity.WEAPON_EN));
					}
					else if(pixelAtual== 0xFFA84300){
						Game.entities.add(new Tridente(xx*32, yy*32, 32, 32, Entity.TRIDENTE_EN));
					}
					else if(pixelAtual== 0xFFA88400){
						Game.entities.add(new FireBall(xx*32, yy*32, 32, 11, Entity.FIRE_BALL_EN));
					}
					else if(pixelAtual== 0xFFA8A800){
						Game.entities.add(new Lanca(xx*32, yy*32, 27, 5, Entity.LANCA_EN));
					}
					else if(pixelAtual == 0xFFFF9170) {
						Lifepack pack = new Lifepack(xx*32, yy*32, 19, 16, Entity.LIFEPACK_EN);
						pack.setMask(8,8,16,8);
						Game.entities.add(pack);
					}
					else if(pixelAtual == 0xFFFFD800) {
						Game.entities.add(new Bullet(xx*32, yy*32, 10, 29, Entity.BULLET_EN));
					}
					else if(pixelAtual == 0xFFF23400) {
						Game.entities.add(new Mana(xx*32, yy*32, 10, 29, Entity.MANA_EN));
					}
				}
			}
		}
				catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateParticles(int amount, int x, int y) {
		for(int i=0; i<amount; i++) {
			Game.entities.add(new Particle(x, y, 1, 1, null));
		}
		
	}
	
	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + width) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height) / TILE_SIZE;
		
		int x4 = (xnext + width) / TILE_SIZE;
		int y4 = (ynext + height) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)	||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof CenarioColTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof CenarioColTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof CenarioColTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof CenarioColTile)||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof PassTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof PassTile)
				 );
		}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = (xnext+8) / TILE_SIZE;
		int y1 = (ynext+8) / TILE_SIZE;
		
		int x2 = ((xnext+8) + TILE_SIZE - 20) / TILE_SIZE;
		int y2 = (ynext+8) / TILE_SIZE;
		
		int x3 = (xnext+8) / TILE_SIZE;
		int y3 = ((ynext+8) + TILE_SIZE - 16) / TILE_SIZE;
		
		int x4 = ((xnext+8) + TILE_SIZE - 20) / TILE_SIZE;
		int y4 = ((ynext+8) + TILE_SIZE - 16) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)	||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof CenarioColTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof CenarioColTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof CenarioColTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof CenarioColTile) ||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof CercaIara) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof CercaIara) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof CercaIara) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof CercaIara)
				);
		}
	
	public static void restartGame(String level) {
		saveLife = (int) Game.player.life;
		saveAmmo = (int) Game.player.ammo;
		saveMana = (int) Game.player.mana;
		saveTridente = Game.player.tridente;
		saveFireBall = Game.player.fireBall;
		saveLanca = Game.player.lanca;
		saveZarabatana = Game.player.zarabatana;
		
		Game.fireTrail.clear();
		Game.fireBallShoot.clear();
		Game.bullets.clear();
		Game.bulletIara.clear();
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		try {
			Game.spritesheet = new Spritesheet("/spritesheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Game.player = new Player(0,0,32,32,Game.spritesheet.getSprite(0, 0, 32, 32));
		Game.entities.add(Game.player);
		if(Game.levelup) {
			Game.player.life=saveLife;
			Game.player.ammo=saveAmmo;
			Game.player.mana=saveMana;
			Game.player.fireBall = saveFireBall;
			Game.player.lanca = saveLanca;
			Game.player.tridente = saveTridente;
			Game.player.zarabatana = saveZarabatana;
			Game.levelup = false;
		}
		Game.world = new World("/"+level);
		return;
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
		if(Game.enemies.size()!=0){
			if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==5)
				tiles[passageX + (passageY*WIDTH)] = new WallTile(passX,passY, Tile.PEDRA_TILE);
			else if(Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4)
				tiles[passageX + (passageY*WIDTH)] = new WallTile(passX,passY, Tile.TILE_WALL);
		}
		else {
			if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2)
				tiles[passageX + (passageY*WIDTH)] = new PassTile(passX,passY, Tile.TILE_PASS3);
			else if(Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4)
				tiles[passageX + (passageY*WIDTH)] = new PassTile(passX,passY, Tile.TILE_PASS);
			else if(Game.CUR_LEVEL==5)
				tiles[passageX + (passageY*WIDTH)] = new PassTile(passX,passY, Tile.TILE_PASS3);
		}
	}
	public static void renderMiniMap() {
		for(int i = 0; i<Game.minimapaPixels.length; i++) {
			Game.minimapaPixels[i] = 0xFF008000;
			
		}
		for(int xx = 0; xx < World.WIDTH; xx++) {
			for(int yy = 0; yy < World.HEIGHT; yy++) {
				if(tiles [xx + (yy*WIDTH)] instanceof WallTile) {
					Game.minimapaPixels[xx+(yy*WIDTH)]= 0x7F3300;
				}
				else if(tiles [xx + (yy*WIDTH)] instanceof CenarioColTile) {
					Game.minimapaPixels[xx+(yy*WIDTH)]= 0x808080;
				}
				else if(tiles [xx + (yy*WIDTH)] instanceof CercaIara) {
					Game.minimapaPixels[xx+(yy*WIDTH)]= 0x808080;
				}
				else if(tiles [xx + (yy*WIDTH)] instanceof Water) {
					Game.minimapaPixels[xx+(yy*WIDTH)]= 0x0052FF;
				}
			}
		}
		int xPlayer = Game.player.getX()/32;
		int yPlayer = Game.player.getY()/32;
		Game.minimapaPixels[xPlayer+(yPlayer*WIDTH)]= 0xFFFFFF;
	}
}
