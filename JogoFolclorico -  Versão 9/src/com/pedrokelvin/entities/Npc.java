package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;

public class Npc extends Entity{
	
	public String[] frases = new String [6];
	
	public boolean showMessage = false;
	public boolean show = false;
	
	public int curIndexMsg = 0;
	public int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 1;
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "Ol�, bem vindo ao jogo!";
		frases[1] = "Movimenta��o � dada atrav�s das teclas W,A,S,D ou das SETAS DIRECIONAIS...";
		frases[2] = "Os coletaveis no mapa representam a REGENERA��O DE VIDA e MUNI��O...";
		frases[3] = "Voc� pode acessar os recursos do seu invent�rio atrav�s dos bot�es 1,2,3 e 4...";
		frases[4] = "Use a BARRA DE ESPA�O para salvar a sua fase em progresso...";
		frases[5] = "Divirta-se e aproveite o conteudo.";
	}
	
	public void tick() {
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		int xNpc = (int)x;
		int yNpc = (int)y;
		
		if(Math.abs(xPlayer-xNpc) < 32 && Math.abs(yPlayer-yNpc)<32) {
			if(show==false) {
				showMessage = true;
				show=true;
			}
		}
		
		if(showMessage) {
			
			this.time++;
			if(this.time >= this.maxTime) {
				this.time = 0;
				if(curIndexMsg < frases[fraseIndex].length() - 1) {
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
			g.drawString(frases[fraseIndex].substring(0,curIndexMsg), 45, 165);
			g.drawString(">Pressione ENTER<", 192, 190);
		}
	}
}
