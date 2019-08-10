package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class Enemy extends Entity{
	
	private int speed = 1;
	
	private int maskx = 16, masky = 16, maskw = 20, maskh = 20;
	
	private int frames=0, maxFrames=16, index=0, maxIndex=3;
	
	private BufferedImage[] sprites;
	
	private int life = 10;
	
	private boolean isDamaged = false;
	private int damageFrames = 5, damageCurrent = 0;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(0, 192, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(32, 192, 32, 32);
		sprites[2] = Game.spritesheet.getSprite(64, 192, 32, 32);
		sprites[3] = Game.spritesheet.getSprite(96, 192, 32, 32);
	}
	
	public void tick() {
			maskx = 10; 
			masky = 10; 
			maskw = 13;
			maskh = 20;
			if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 300) {
				if(this.isColiddingWithPlayer()==false) {
				if(x<Game.player.getX() && World.isFree((int)(x+speed), this.getY())
						&& !isColidding((int)(x+speed), this.getY())) {
					x+=speed;
				}
				else if(x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
						&& !isColidding((int)(x-speed), this.getY())) {
					x-=speed;
				}
				if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
						&& !isColidding(this.getX(), (int)(y+speed))) {
					y+=speed;
				}
				else if(y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
						&& !isColidding(this.getX(), (int)(y-speed))) {
					y-=speed;
				}
				}
				else {
					if(Game.rand.nextInt(100)<10) {
						Sound.damageEffect.play();
						Game.player.life --;
						if(Game.player.life<0)
							Game.player.life=0;
						Game.player.isDamaged = true;
					}
				}
			}else {
				
			}
			
			frames++;
			if(frames == maxFrames) {
				frames=0;
				index++;
				if(index>maxIndex) {
					index=0;
				}
			}
			
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
			}
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);	
	}
	
	public void collidingBullet() {
		for(int i=0; i<Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					Sound.hurtEffect.play();
					life--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX()+maskx, this.getY()+masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX()+maskx, Game.player.getY()+masky, 16, 16);
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext+maskx, ynext+masky, maskw, maskh);
		for(int i=0; i<Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+maskx, e.getY()+masky, maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy))
				return true;
		}
		return false;
	}
	
	public void render (Graphics g) {
	if(!isDamaged)
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
	else {
		g.drawImage(ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY()- Camera.y, null);
	}
	}
	
}
