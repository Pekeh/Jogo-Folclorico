package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class FireTrail extends Entity{
	
	private BufferedImage fireTrail;
	
	private int life = 200, curLife = 0;
	
	
	public FireTrail(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		fireTrail= Game.spritesheet.getSprite(293, 804, 23, 27);
	}
	
	public void tick() {
	
		curLife++;
		if(curLife == life) {
			Game.fireTrail.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		Graphics2D g2 = (Graphics2D) g;
		if(Game.player.dir == Game.player.left_dir) {
			g2.drawImage(fireTrail, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Game.player.dir == Game.player.right_dir) {
			g2.drawImage(fireTrail, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Game.player.dir == Game.player.up_dir) {
			g2.drawImage(fireTrail, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(Game.player.dir == Game.player.down_dir) {
			g2.drawImage(fireTrail, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
