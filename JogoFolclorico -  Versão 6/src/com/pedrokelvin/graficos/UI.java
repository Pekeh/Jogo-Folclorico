package com.pedrokelvin.graficos;

import java.awt.Color;
import java.awt.Graphics;
import com.pedrokelvin.main.Game;

public class UI {
	
	public void render (Graphics g) {
		g.setColor(Color.red);
		g.fillRect(60,8,100,8);
		g.setColor(Color.green);
		g.fillRect(60,8,(int)((Game.player.life/Game.player.maxlife)*100),8);
		g.setColor(Color.white);
		g.setFont(Game.newfontLife);
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxlife, 95,15);
		g.setFont(Game.newfontAmmo);
		g.setColor(Color.white);
		g.drawString("Dardos: " +Game.player.ammo, 400, 15);
	}
	
}
