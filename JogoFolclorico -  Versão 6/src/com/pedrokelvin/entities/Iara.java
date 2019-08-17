package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class Iara extends Entity{
	
	private int speed = 1;
	
	private int maskx = 16, masky = 16, maskw = 20, maskh = 20;
	
	private int frames=0, maxFrames=16, index=0, maxIndex=3;
	
	private BufferedImage[] sprites;
	
	private int life = 100;
	
	private boolean isDamaged = false;
	
	public int time = 0, maxTime = (60*3);
	public int timeAtack = 0, maxTimeAtack = (20);
	
	private int damageFrames = 5, damageCurrent = 0;
	
	public Iara(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(0,  416, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(32, 416, 32, 32);
		sprites[2] = Game.spritesheet.getSprite(64, 416, 32, 32);
		sprites[3] = Game.spritesheet.getSprite(96, 416, 32, 32);
	}
	
	public void tick() {
		maskx = 10; 
		masky = 10; 
		maskw = 13;
		maskh = 20;
			
			double anglePosit = Math.atan2(this.getY()+16 - Game.player.getY(),this.getX()+16 -  Game.player.getX());
			
			if(anglePosit <=0.78 && anglePosit>=-0.71) {
				index=2;
			}
			else if(anglePosit <=-0.70 && anglePosit>=-2.36) {
				index=3;
			}
			else if(anglePosit <=2.29 && anglePosit>=0.78) {
				index=0;
			}
			else{
				index=1;
			}
			
			time++;
			if(time>maxTime) {
					time=0;
					int dx=0;
					int dy=0;
					int px=0;
					int py=16;
					
					if(anglePosit <=0.78 && anglePosit>=-0.71) {
						dx = -1;
						px = 0;
					}
					else if(anglePosit <=-0.70 && anglePosit>=-2.36) {
						dy = 1;
						py = 26;
						px = 12;
						
					}
					else if(anglePosit <=2.29 && anglePosit>=0.78) {
						dy = -1;
						py = 4;
						px = 18;
					}
					else {
						dx = 1;
						px = 28;
					}
		
						Sound.shootEffect.play();
						BulletIara bulletIara = new BulletIara(this.getX()+px, this.getY()+py, 6, 6, null, dx, dy);
						Game.bulletIara.add(bulletIara);
			}
			
			/*else {
				if(Game.rand.nextInt(100)<10) {
					Sound.damageEffect.play();
					Game.player.life --;
					if(Game.player.life<0)
						Game.player.life=0;
					Game.player.isDamaged = true;
				}
			}
		
		frames++;
		if(frames == maxFrames) {
			frames=0;
			index++;
			if(index>maxIndex) {
				index=0;
			}
		}
		
		if(Game.player.lancaActive || Game.player.tridenteActive)
			collidingAttack();
		else if(Game.player.zarabatanaActive)
			collidingBullet();
		
		
		
		if(life<=0) {
			destroySelf();
		}
		
		if(isDamaged) {
			this.damageCurrent++;
			if(this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;
			}
		}*/
	}
	
	public void render (Graphics g) {
		if(!isDamaged)
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
		else {
			g.drawImage(ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY()- Camera.y, null);
		}
	}
}
