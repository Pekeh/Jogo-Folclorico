package com.pedrokelvin.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.pedrokelvin.entities.Player;
import com.pedrokelvin.main.Game;

public class UI {

	public void render (Graphics g) {
		g.setColor(Color.red);
		g.fillRect(16,8,100,8);
		g.setColor(Color.green);
		g.fillRect(16,8,(int)((Game.player.life/Game.player.maxlife)*100),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN,10));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxlife, 50,15);
	}
	
}
