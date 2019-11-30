package com.pedrokelvin.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.pedrokelvin.entities.Boitata;
import com.pedrokelvin.entities.Boto;
import com.pedrokelvin.entities.Bullet;
import com.pedrokelvin.entities.Capelobo;
import com.pedrokelvin.entities.Cuca;
import com.pedrokelvin.entities.Enemy;
import com.pedrokelvin.entities.Entity;
import com.pedrokelvin.entities.FireBall;
import com.pedrokelvin.entities.Iara;
import com.pedrokelvin.entities.Lanca;
import com.pedrokelvin.entities.Lifepack;
import com.pedrokelvin.entities.Mana;
import com.pedrokelvin.entities.Mapinguari;
import com.pedrokelvin.entities.Mula;
import com.pedrokelvin.entities.Npc;
import com.pedrokelvin.entities.Particle;
import com.pedrokelvin.entities.Pergaminho;
import com.pedrokelvin.entities.Player;
import com.pedrokelvin.entities.Saci;
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
					if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==3  || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.PLAN_TILE);
					else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 || Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11 )
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.SWAMP_TILE);
					else if(Game.CUR_LEVEL==18)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.VITORIA_REGIA1_TILE);
					else if(Game.CUR_LEVEL==20) {
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.CHAO_CAVERNA);
					}
					
					if(pixelAtual == 0xFF000000) {
						//Floor
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==3  || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.PLAN_TILE);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 || Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.SWAMP_TILE);
						else if(Game.CUR_LEVEL==18)
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.VITORIA_REGIA1_TILE);
						else if(Game.CUR_LEVEL==20) {
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32, Tile.CHAO_CAVERNA);
						}
					}
					else if(pixelAtual == 0xFFFFFFFF){
						//Parede
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==3  || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32, Tile.ARVORE1_TILE);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 || Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32, Tile.ARVORE2_TILE);
					}
					else if(pixelAtual == 0xFFFFFFF1){
						//Arvore Resto 1
						tiles[xx + (yy*WIDTH)] = new ArvoreResto(xx*32,yy*32, Tile.ARVORE1_RESTO1_TILE);
					}
					else if(pixelAtual == 0xFFFFFFF2){
						//Arvore Resto 2
						tiles[xx + (yy*WIDTH)] = new ArvoreResto(xx*32,yy*32, Tile.ARVORE1_RESTO2_TILE);
					}
					else if(pixelAtual == 0xFF177A39){
						//Cerca
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==3  || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_CERCA1);
						else  if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 || Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_CERCA3);
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
					else if(pixelAtual == 0xFF177A51){
						//Raizes
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TRONCO_PODRE_TILE);
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
						//Flor Fogo
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.FIRE_FLOWER_TILE);
					}
					else if(pixelAtual == 0xFF00FF10){
						//Rosa
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.ROSA_TILE);
					}
					else if(pixelAtual == 0xFF00FF11){
						//Samambaia
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.SAMAMBAIA_TILE);
					}
					
					else if(pixelAtual == 0xFF00FF13){
							//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_V);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_V);
					}
					else if(pixelAtual == 0xFF00FF14){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_CCE);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_CCE);
					}
					else if(pixelAtual == 0xFF00FF15){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_CBE);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_CBE);
					}
					else if(pixelAtual == 0xFF00FF16){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_H);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_H);
					}
					else if(pixelAtual == 0xFF00FF17){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_CCD);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_CCD);
					}
					else if(pixelAtual == 0xFF00FF18){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_CBD);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_CBD);
					}
					else if(pixelAtual == 0xFF00FF19){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_TE);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_TE);
					}
					else if(pixelAtual == 0xFF00FF20){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_TD);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_TD);
					}
					else if(pixelAtual == 0xFF00FF21){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_TC);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_TC);
							
					}
					else if(pixelAtual == 0xFF00FF22){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_TB);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_TB);
					}
					else if(pixelAtual == 0xFF00FF23){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_FC);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_FC);
					}
					else if(pixelAtual == 0xFF00FF24){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_FD);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_FD);
					}
					else if(pixelAtual == 0xFF00FF25){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_FE);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_FE);
					}	
					else if(pixelAtual == 0xFF00FF26){
						//Trilho
						if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 ||  Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19) 
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO_FB);
						else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 ||  Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
							tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.TILE_TRILHO2_FB);
					}
					
					
					else if(pixelAtual == 0xFF001DB0){
						//Agua
						tiles[xx + (yy*WIDTH)] = new Water(xx*32,yy*32, Tile.TILE_WATER);
					}
					else if(pixelAtual == 0xFF001DB1){
						//Borda Agua EC
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_EC);
					}
					else if(pixelAtual == 0xFF001DB2){
						//Borda Agua DC
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_DC);
					}
					else if(pixelAtual == 0xFF001DB3){
						//Borda Agua C
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_C);
					}
					else if(pixelAtual == 0xFF001DB4){
						//Borda Agua B
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_B);
					}
					else if(pixelAtual == 0xFF001DB5){
						//Borda Agua EB
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_EB);
					}
					else if(pixelAtual == 0xFF001DB6){
						//Borda Agua DB
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_DB);
					}
					else if(pixelAtual == 0xFF001DB7){
						//Borda Agua D
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_D);
					}
					else if(pixelAtual == 0xFF001DB8){
						//Borda Agua E
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_E);
					}
					else if(pixelAtual == 0xFF001DB9){
						//Borda Agua IEC
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_IEC);
					}
					else if(pixelAtual == 0xFF001DBA){
						//Borda Agua IDC
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_IDC);
					}
					else if(pixelAtual == 0xFF001DBB){
						//Borda Agua IEB
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_IEB);
					}
					else if(pixelAtual == 0xFF001DBC){
						//Borda Agua IDB
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_AGUA_IDB);
					}
					
					else if(pixelAtual == 0xFF00AF80){
						//Vitoria Regia1
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.VITORIA_REGIA1_TILE);
					}
					else if(pixelAtual == 0xFF00AF81){
						//Vitoria Regia2
						
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.VITORIA_REGIA2_TILE);
					}
					else if(pixelAtual == 0xFF00AF82){
						//Vitoria Regia CC
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.VITORIA_REGIA_CC_TILE);
					}
					else if(pixelAtual == 0xFF00AF83){
						//Vitoria Regia CB
						
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.VITORIA_REGIA_CB_TILE);
					}
					
					else if(pixelAtual == 0xFFBEB391){
						//Passagem
							tiles[xx + (yy*WIDTH)] = new PassTile(xx*32,yy*32, Tile.TILE_PASS);
						passageX = xx;
						passageY = yy;
						passX = xx*32;
						passY = yy*32;
					}
					
					//BORDA DE LAGO PANTANO
					else if(pixelAtual == 0xFFFF0088){
						//Borda Diagonal Cima Esquerda
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_AGUA_PANTANO);
					}
					else if(pixelAtual == 0xFFFF0087){
						//Borda Diagonal Cima Esquerda
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_AGUA_PANTANO_BOLHA);
					}
					else if(pixelAtual == 0xFFFF0086){
						//Borda Diagonal Cima Esquerda
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_DCE);
					}
					else if(pixelAtual == 0xFFFF0085){
						//Borda Diagonal Cima Centro
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_CC);
					}
					else if(pixelAtual == 0xFFFF0084){
						//Borda Diagonal Cima Direita
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_DCD);
					}
					else if(pixelAtual == 0xFFFF0083){
						//Borda Alta Diagonal Cima Esquerda
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_ALTA_DCE);
					}
					else if(pixelAtual == 0xFFFF0082){
						//Borda Alta Diagonal Cima Direita
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_ALTA_DCD);
					}
					else if(pixelAtual == 0xFFFF0081){
						//Borda Esquerda Centro
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_EC);
					}
					else if(pixelAtual == 0xFFFF0080){
						//Borda C
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_C);
					}
					else if(pixelAtual == 0xFFFF0079){
						//Borda Direita Centro
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_DC);
					}
					else if(pixelAtual == 0xFFFF0078){
						//Borda Baixo Diagonal Cima Esquerda
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_BAIXO_DCE);
					}
					else if(pixelAtual == 0xFFFF0077){
						//Borda Baixo Diagonal Cima Direita
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_BAIXO_DCD);
					}
					else if(pixelAtual == 0xFFFF0076){
						//Borda Diagonal Baixo Esquerda
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_DBE);
					}
					else if(pixelAtual == 0xFFFF0075){
						//Borda Diagonal Centro Baixo
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_CB);
					}
					else if(pixelAtual == 0xFFFF0074){
						//Borda Diagonal Baixo Direito
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_DBD);
					}
					else if(pixelAtual == 0xFFFF0073){
						//Borda Centro Esquerda
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_CENTRO_E);
					}
					else if(pixelAtual == 0xFFFF0072){
						//Borda Centro Direito
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_CENTRO_D);
					}
					else if(pixelAtual == 0xFFFF0071){
						//Borda Centro Cima
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_CENTRO_C);
					}
					else if(pixelAtual == 0xFFFF0070){
						//Borda Centro Baixo
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.TILE_BORDA_CENTRO_B);
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
				
					
					//CAVERNA
					else if(pixelAtual == 0xFFFF8C00) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.PAREDE_CAVERNA);
					}
					else if(pixelAtual == 0xFFFF8C01) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.PORTA_CAVERNA_C);
					}
					else if(pixelAtual == 0xFFFF8C02) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.PORTA_CAVERNA_B);
					}
					else if(pixelAtual == 0xFFFF8C03) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CD);
					}
					else if(pixelAtual == 0xFFFF8C04) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CE);
					}
					else if(pixelAtual == 0xFFFF8C05) {
						tiles[xx + (yy*WIDTH)] = new CenarioTile(xx*32,yy*32, Tile.CHAO_CAVERNA);
					}
					else if(pixelAtual == 0xFFFF8C06) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CRISTAL_CAVERNA);
					}
					else if(pixelAtual == 0xFFFF8C07) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CASTICAL_CAVERNA_C);
					}
					else if(pixelAtual == 0xFFFF8C08) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CASTICAL_CAVERNA_B);
					}
					else if(pixelAtual == 0xFFFF8C09) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CALDEIRAO_CAVERNA_CE);
					}
					else if(pixelAtual == 0xFFFF8C0A) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CALDEIRAO_CAVERNA_CD);
					}
					else if(pixelAtual == 0xFFFF8C0B) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CALDEIRAO_CAVERNA_BE);
					}
					else if(pixelAtual == 0xFFFF80C) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CALDEIRAO_CAVERNA_BD);
					}
					else if(pixelAtual == 0xFFFF8C0D) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CC);
					}
					else if(pixelAtual == 0xFFFF8C0E) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CB);
					}
					else if(pixelAtual == 0xFFFF8C0F) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CCE);
					}
					else if(pixelAtual == 0xFFFF8C10) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CCD);
					}
					else if(pixelAtual == 0xFFFF8C11) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CBE);
					}
					else if(pixelAtual == 0xFFFF8C12) {
						tiles[xx + (yy*WIDTH)] = new CenarioColTile(xx*32,yy*32, Tile.CHAO_CAVERNA_CBD);
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
					else if(pixelAtual == 0xFFFF0001) {
						Mapinguari mapinguari = new Mapinguari(xx*32, yy*32, 32, 32, Entity.MAPINGUARI_EN);
						Game.entities.add(mapinguari);
						Game.mapinguari.add(mapinguari);
					}
					else if(pixelAtual == 0xFFFF0002) {
						Capelobo capelobo = new Capelobo(xx*32, yy*32, 32, 32, Entity.CAPELOBO);
						Game.entities.add(capelobo);
						Game.capelobo.add(capelobo);
					}
					else if(pixelAtual == 0xFFFF0065) {
						Iara iara = new Iara(xx*32, yy*32, 32, 32, Entity.IARA_FRONT);
						Game.entities.add(iara);
						Game.iara.add(iara);
					}
					else if(pixelAtual == 0xFFFF0060) {
						Boto boto = new Boto(xx*32, yy*32, 32, 32, Entity.DOLPHIN_RIGHT);
						Game.entities.add(boto);
						Game.boto.add(boto);
					}
					else if(pixelAtual == 0xFFFF0050) {
						Mula mula = new Mula(xx*32, yy*32, 32, 32, Entity.MULA_EN);
						Game.entities.add(mula);
						Game.mula.add(mula);
					}
					else if(pixelAtual == 0xFFFF0100) {
						Boitata boitata = new Boitata(xx*32, yy*32, 32, 32, Entity.BOITATA);
						Game.entities.add(boitata);
						Game.boitata.add(boitata);
					}
					else if(pixelAtual == 0xFFFF0090) {	
						Npc npc = new Npc(xx*32, yy*32, 32, 32, Entity.NPC);
						Game.entities.add(npc);
					}
					else if(pixelAtual == 0xFFFF0091) {	
						Saci saci = new Saci(xx*32, yy*32, 32, 32, Entity.SACI);
						Game.entities.add(saci);
					}
					else if(pixelAtual == 0xFFFF0110) {	
						Cuca cuca = new Cuca(xx*32, yy*32, 64, 64, Entity.CUCA);
						Game.entities.add(cuca);
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
					else if(pixelAtual == 0xFFFFEB14) {
						Game.entities.add(new Pergaminho(xx*32, yy*32, 25, 25, Entity.PERGAMINHO_EN));
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
				 (tiles[x4 + (y4*World.WIDTH)] instanceof CenarioColTile) ||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof PassTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof ArvoreResto) || 
				 (tiles[x2 + ((y2=(ynext+8) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto) ||
				 (tiles[x3 + ((y3=((ynext+8) + TILE_SIZE - 32) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto) ||
				 (tiles[x4 + ((y4=((ynext+8) + TILE_SIZE - 32) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto)
				 );
		}
	
	public static boolean isFreeDynamicXp(int xnext, int ynext, int width, int height) {
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
				 (tiles[x1 + (y1*World.WIDTH)] instanceof PassTile) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof PassTile) ||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof ArvoreResto) || 
				 (tiles[x2 + ((y2=(ynext+8) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto) ||
				 (tiles[x3 + ((y3=((ynext+8) + TILE_SIZE - 32) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto) ||
				 (tiles[x4 + ((y4=((ynext+8) + TILE_SIZE - 32) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto)
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
				 (tiles[x1 + (y1*World.WIDTH)] instanceof Water) || 
				 (tiles[x2 + (y2*World.WIDTH)] instanceof Water) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof Water) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof Water) ||
				 (tiles[x1 + (y1*World.WIDTH)] instanceof ArvoreResto) || 
				 (tiles[x2 + ((y2=(ynext+8) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto) ||
				 (tiles[x3 + ((y3=((ynext+8) + TILE_SIZE - 32) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto) ||
				 (tiles[x4 + ((y4=((ynext+8) + TILE_SIZE - 32) / TILE_SIZE)*World.WIDTH)] instanceof ArvoreResto)
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
		Game.bulletBoto.clear();
		Game.bulletBoitata.clear();
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.mapinguari = new ArrayList<Mapinguari>();
		Game.capelobo = new ArrayList<Capelobo>();
		Game.iara = new ArrayList<Iara>();
		Game.mula = new ArrayList<Mula>();
		Game.boitata = new ArrayList<Boitata>();
		
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
		if(Game.enemies.size()!=0 || 
				Game.mapinguari.size()!=0 || 
				Game.capelobo.size()!=0 || 
				Game.mula.size()!=0 ||
				Game.boitata.size()!=0){
			if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
				tiles[passageX + (passageY*WIDTH)] = new WallTile(passX,passY, Tile.ROCHA_TILE);
			else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 || Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
				tiles[passageX + (passageY*WIDTH)] = new WallTile(passX,passY, Tile.ROCHA_COM_MUSGO2_TILE);
			else if(Game.CUR_LEVEL==18)
				tiles[passageX + (passageY*WIDTH)] = new WallTile(passX,passY, Tile.TILE_BORDA_AGUA_C);
			else if(Game.CUR_LEVEL==20)
				tiles[passageX + (passageY*WIDTH)] = new WallTile(passX,passY, Tile.CHAO_CAVERNA_CB);
		}
		else {
			if(Game.CUR_LEVEL==1 || Game.CUR_LEVEL==2 || Game.CUR_LEVEL==3 || Game.CUR_LEVEL==4 || Game.CUR_LEVEL==5 || Game.CUR_LEVEL==12 || Game.CUR_LEVEL==13 || Game.CUR_LEVEL==14 || Game.CUR_LEVEL==15 || Game.CUR_LEVEL==16 || Game.CUR_LEVEL==17 || Game.CUR_LEVEL==19)
				tiles[passageX + (passageY*WIDTH)] = new PassTile(passX,passY, Tile.TILE_PASS);
			else if(Game.CUR_LEVEL==6 || Game.CUR_LEVEL==7 || Game.CUR_LEVEL==8 || Game.CUR_LEVEL==9 || Game.CUR_LEVEL==10 || Game.CUR_LEVEL==11)
				tiles[passageX + (passageY*WIDTH)] = new PassTile(passX,passY, Tile.TILE_PASS2);
			else if(Game.CUR_LEVEL==18)
				tiles[passageX + (passageY*WIDTH)] = new PassTile(passX,passY, Tile.VITORIA_REGIA_CC_TILE);
			else if(Game.CUR_LEVEL==20)
				tiles[passageX + (passageY*WIDTH)] = new PassTile(passX,passY, Tile.CHAO_CAVERNA);
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
					Game.minimapaPixels[xx+(yy*WIDTH)]= 0x227F18;
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
