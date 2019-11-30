package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;

public class Saci extends Entity{
	
	public static String[] frases = new String [8];
	
	public static boolean showMessage = false;
	public boolean show = false;
	
	public static int curIndexMsg = 0;
	public static int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 1;
	
	public Saci(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "...";
		frases[1] = "Olá, eu sou saci.";
		frases[2] = "Sua vila foi atacada e sua irmã foi raptada.";
		frases[3] = "Acho que a Cuca tem envolvimento nisso.";
		frases[4] = "Você deveria checar a caverna dela.";
		frases[5] = "Não fica muito longe daqui.";
		frases[6] = "Só precisa seguir esta trilha.";
		frases[7] = "Boa sorte amigo.";
		
		
	}
	
	public void tick() {
		
		if(Game.CUR_LEVEL == 19) {
			frases[0] = "...";
			frases[1] = "Você conseguiu!!!";
			frases[2] = "Não pensei que passaria por todos os desafios.";
			frases[3] = "Enfrente está a entrada da caverna da Cuca.";
			frases[4] = "Você esta pronto?";
			frases[5] = "Ela não é um oponente muito fácil.";
			frases[6] = "Tome cuidado e salve a sua irmã.";
			frases[7] = "Boa sorte amigo.";
		}
		
		
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		int xSaci = (int)x;
		int ySaci = (int)y;
		
		if(Math.abs(xPlayer-xSaci) < 32 && Math.abs(yPlayer-ySaci)<32) {
			if(show==false) {
				showMessage = true;
				show=true;
			}
		}
		
		if(showMessage) {
			this.time++;
			if(this.time >= this.maxTime) {
				this.time = 0;
				if(curIndexMsg < frases[fraseIndex].length()) {
					curIndexMsg++;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(showMessage) {
			g.setColor(Color.white);
			g.fillRect(40-2, 140-2, Game.WIDTH - 80 + 4, Game.HEIGHT/4+4);
			g.setColor(Color.blue);
			g.fillRect(40, 140, Game.WIDTH - 80, Game.HEIGHT/4);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.setColor(Color.white);
			g.drawString("Saci", 45, 150);
			g.drawString(frases[fraseIndex].substring(0,curIndexMsg), 45, 170);
			g.drawString(">Pressione ENTER<", 192, 190);
		}
	}
}
