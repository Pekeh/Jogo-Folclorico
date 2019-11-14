package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pedrokelvin.main.Game;

public class Pergaminho extends Entity{
	
	public String[] frases = new String [7];
	public String[] titulos = new String [7];
	
	private BufferedImage pergaminho;
	
	public int curIndexMsg = 0;
	public int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 1;
	public int numeroY=90;
	
	public Pergaminho(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		frases[0] = " Depois que morre, nem Deus nem o\n "
				+ "diabo o aceitam. E até a terra o rejeita,\n "
				+ "expulsando seu corpo ressequido,\n "
				+ "condenado a vagar na noite como um\n "
				+ "ser de pele e ossos, que faz murchar\n "
				+ "tudo aquilo que toca. Ele ataca quem\n "
				+ "passa por perto, sugando o sangue\n "
				+ "como um vampiro.";
		
		titulos[0] = "CORPO SECO";
		
		try {
			pergaminho = ImageIO.read(getClass().getResourceAsStream("/pergaminho.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(Game.player.texto) {
			this.time++;
			if(this.time >= this.maxTime) {
				this.time = 0;
				if(curIndexMsg < frases[fraseIndex].length()) {
					curIndexMsg++;
				}
			}
			
		}
	}
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.render(g);
		if(Game.player.texto) {
			g.drawImage(pergaminho, 100, 20, 280, 280, null);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString(titulos[0], 210, 90);
			g.setFont(new Font("Arial", Font.BOLD, 9));
			drawString(g2,frases[0].substring(0,curIndexMsg), 150, 100);
			g.drawString(">Pressione ENTER<", 200, 230);
		}
	}
}
