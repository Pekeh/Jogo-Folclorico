package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.world.Camera;

public class MulaDead extends Entity{
	
	private BufferedImage[] aniDead;
	
	public Weapon zarabatana;
	
	private int frames=0, maxFrames=8, index=0, maxIndex=5;
	
	public int xDead, yDead;
	
	public MulaDead(int x, int y, int width, int height, BufferedImage sprite) {
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
			this.xDead = Mula.xDead;
			this.yDead = Mula.yDead;
			
			zarabatana = new Weapon(xDead+1, yDead+1, 32, 32, Game.spritesheet.getSprite(181,142,10,5));
			Game.entities.add(zarabatana);
		}
	}
	
	public void render (Graphics g) {
			g.drawImage(aniDead[index], Mula.xDead - Camera.x, Mula.yDead - Camera.y, null);
	}

}
