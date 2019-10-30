package com.pedrokelvin.graficos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import com.pedrokelvin.main.Game;

public class UI {
	
	private BufferedImage lancaItem;
	private BufferedImage zarabatanaItem;
	private BufferedImage tridenteItem;
	private BufferedImage fireBallItem;
	
	public void render (Graphics g) {
		lancaItem = Game.spritesheet.getSprite(258,653, 29, 8);
		zarabatanaItem = Game.spritesheet.getSprite(300, 652, 12, 10);
		tridenteItem = Game.spritesheet.getSprite(256,681, 32, 12);
		fireBallItem = Game.spritesheet.getSprite(301,681, 11, 12);
		g.setColor(Color.red);
		g.fillRect(60,8,100,8);
		g.setColor(Color.green);
		g.fillRect(60,8,(int)((Game.player.life/Game.player.maxlife)*100),8);
		g.setColor(Color.white);
		g.setFont(Game.newfontLife);
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxlife, 95,15);
		Graphics2D g2 = (Graphics2D) g;
		if(Game.player.lanca)
			g2.drawImage(lancaItem, 170, 10, 29, 8, null);
		if(Game.player.zarabatana)
			g2.drawImage(zarabatanaItem, 210, 9, 12, 10, null);
		if(Game.player.tridente)
			g2.drawImage(tridenteItem, 230, 7, 32, 12, null);
		if(Game.player.fireBall)
			g2.drawImage(fireBallItem, 270, 8, 11, 12, null);
		g.setFont(Game.newfontAmmo);
		g.setColor(Color.white);
		g.drawString("Mana: " +Game.player.mana, 350, 15);
		g.setFont(Game.newfontAmmo);
		g.setColor(Color.white);
		g.drawString("Dardos: " +Game.player.ammo, 400, 15);
		
	}
	
}