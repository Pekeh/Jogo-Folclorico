package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;

public class Npc extends Entity{
	
	public static String[] frases = new String [8];
	
	public static boolean showMessage = false;
	public boolean show = false;
	
	public static int curIndexMsg = 0;
	public static int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 1;
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "...";
		frases[1] = "Olá, eu sou curupira, bem vindo ao jogo!";
		frases[2] = "Movimentação é dada através das teclas W,A,S,D ou das SETAS DIRECIONAIS";
		frases[3] = "Ataque os inigos com o MOUSE ou com a tecla J";
		frases[4] = "Os coletaveis no mapa representam a REGENERAÇÃO DE VIDA e MUNIÇÃO";
		frases[5] = "Você pode acessar os recursos do seu inventário através dos botões 1,2,3 e 4";
		frases[6] = "Use a BARRA DE ESPAÇO para salvar a sua fase em progresso";
		frases[7] = "Divirta-se e aproveite o conteudo";
	}
	
	public void tick() {
		
		if(Game.CUR_LEVEL==1) {
			frases[0] = "...";
			frases[1] = "Olá, eu sou curupira, bem vindo ao jogo!";
			frases[2] = "Movimentação é dada através das teclas W,A,S,D ou das SETAS DIRECIONAIS";
			frases[3] = "Ataque os inigos com o MOUSE ou com a tecla J";
			frases[4] = "Os coletaveis no mapa representam a REGENERAÇÃO DE VIDA e MUNIÇÃO";
			frases[5] = "Você pode acessar os recursos do seu inventário através dos botões 1,2,3 e 4";
			frases[6] = "Use a BARRA DE ESPAÇO para salvar a sua fase em progresso";
			frases[7] = "Divirta-se e aproveite o conteudo";
		}
		else if(Game.CUR_LEVEL==8) {
			frases[0] = "...";
			frases[1] = "Olá amigo, ainda bem que te encontrei.";
			frases[2] = "Ouvi dizer que andou falando com o Saci.";
			frases[3] = "Cuidado, ele nem sempre é alguem de confiança.";
			frases[4] = "Ele costumava ser perverso, atormentando aldeias e vilarejos.";
			frases[5] = "Siga com cuidado seu caminho, pois a grandes perigos pela frente.";
			frases[6] = "Ouvi grunhidos e gritos vindos deste lado, seja cauteloso.";
			frases[7] = "Boa sorte amigo.";
		}
		
		
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
			g.drawString("Curupira", 45, 150);
			g.drawString(frases[fraseIndex].substring(0,curIndexMsg), 45, 170);
			g.drawString(">Pressione ENTER<", 192, 190);
		}
	}
}
