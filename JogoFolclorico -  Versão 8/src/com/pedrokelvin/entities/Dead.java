package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class Dead extends Entity{
	
	private BufferedImage[] aniDead;
	
	public Lifepack lifePackDrop;
	public Bullet bulletDrop;
	public Mana manaDrop;
	
	private int frames=0, maxFrames=8, index=0, maxIndex=5;
	
	public int xDead, yDead;
	
	public Dead(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		aniDead = new BufferedImage[6];
		aniDead[0] = Game.spritesheet.getSprite(256, 768, 32, 32);
		aniDead[1] = Game.spritesheet.getSprite(288, 768, 32, 32);
		aniDead[2] = Game.spritesheet.getSprite(256, 736, 32, 32);
		aniDead[3] = Game.spritesheet.getSprite(288, 736, 32, 32);
		aniDead[4] = Game.spritesheet.getSprite(256, 704, 32, 32);
		aniDead[5] = Game.spritesheet.getSprite(288, 704, 32, 32);;
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames=0;
			index++;
			if(index>maxIndex) {
				index=0;
			}
		}
		if(index==5) {
			Game.entities.remove(this);
			this.xDead = Enemy.xDead;
			this.yDead = Enemy.yDead;
			int numDrop = Game.rand.nextInt(100);
			
				if(Game.player.fireBall) {
					if(numDrop>60 && numDrop<=80) {
						lifePackDrop = new Lifepack(xDead+1, yDead+1, 32, 32, Game.spritesheet.getSprite(5,295,22,19));
						Game.entities.add(lifePackDrop);
					}
					else if(numDrop>=10 && numDrop<=25) {
						bulletDrop = new Bullet(xDead+1, yDead+1, 32, 32, Game.spritesheet.getSprite(41,295,14,24));
						Game.entities.add(bulletDrop);
					}
					else if(numDrop>=35 && numDrop<=55) {
						manaDrop = new Mana(xDead+1, yDead+1, 32, 32, Game.spritesheet.getSprite(104,296,16,16));
						Game.entities.add(manaDrop);
					}
				}
				else if (Game.player.zarabatana) {
					if(numDrop>=50 && numDrop<=80) {
						lifePackDrop = new Lifepack(xDead+1, yDead+1, 32, 32, Game.spritesheet.getSprite(5,295,22,19));
						Game.entities.add(lifePackDrop);
					}
					else if(numDrop>=10 && numDrop<=40) {
						bulletDrop = new Bullet(xDead+1, yDead+1, 32, 32, Game.spritesheet.getSprite(41,295,14,24));
						Game.entities.add(bulletDrop);
					}
				}
				else {
					if(numDrop<=50)
						lifePackDrop = new Lifepack(xDead+1, yDead+1, 32, 32, Game.spritesheet.getSprite(5,295,22,19));
						Game.entities.add(lifePackDrop);
				}
		}
			
	}
	
	public void render (Graphics g) {
			g.drawImage(aniDead[index], Enemy.xDead - Camera.x, Enemy.yDead - Camera.y, null);
	}

}
