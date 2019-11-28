package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class Cuca extends Entity{
	
	private int speed = 1;
	
	private int maskx = 16, masky = 16, maskw = 20, maskh = 20;
	
	private int frames=0, maxFrames=16, index=0, maxIndex=3;
	
	public static int xDead, yDead;
	
	private BufferedImage[] sprites;
	
	private int life = 100;
	
	public static boolean isDeadEnemy=false;
	private boolean isDamaged = false;
	private int damageFrames = 5, damageCurrent = 0;
	
	
	
	public Cuca(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(0, 864, 64, 64);;
		sprites[1] = Game.spritesheet.getSprite(64, 864, 64, 64);
		sprites[2] = Game.spritesheet.getSprite(128, 864, 64, 64);
		sprites[3] = Game.spritesheet.getSprite(64, 864, 64, 64);
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
			else if(Game.player.fireBallActive)
				collidingFireBallShoot();
			
			
			if(life<=0) {
				isDeadEnemy=true;
				xDead = this.x;
				yDead = this.y;
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
		Game.cuca.remove(this);
		Game.entities.remove(this);	
	}
	
	public void collidingBullet() {
		for(int i=0; i<Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					Sound.hurtEffect.play();
					life=life-10;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}
	
	public void collidingFireBallShoot() {
		for(int i=0; i<Game.fireBallShoot.size(); i++) {
			Entity e = Game.fireBallShoot.get(i);
			if(e instanceof FireBallShoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					Sound.hurtEffect.play();
					life=life-15;
					Game.fireBallShoot.remove(i);
					return;
				}
			}
		}
	}
	
	public void collidingAttack() {
		if(this.calculateDistance(this.getX()+maskx, this.getY()+masky,Game.player.getX()+maskx, Game.player.getY()+masky) < 40) {
			if(Game.player.right_dir==Game.player.dir && this.getX()>Game.player.getX() && this.getY()>Game.player.getY()-8 && this.getY()<Game.player.getY()+24) {
				if(Game.player.isAttack) {
					isDamaged = true;
					Sound.hurtEffect.play();
					if(Game.player.tridenteActive)
						life=life-3;
					if(Game.player.lancaActive)
						life=life-2;
				}
				return;
			}
			else if(Game.player.left_dir==Game.player.dir && this.getX()<Game.player.getX() && this.getY()>Game.player.getY()-8 && this.getY()<Game.player.getY()+24) {
				if(Game.player.isAttack) {
					isDamaged = true;
					Sound.hurtEffect.play();
					if(Game.player.tridenteActive)
						life=life-3;
					if(Game.player.lancaActive)
						life=life-2;
				}
				return;
			}
			else if(Game.player.down_dir==Game.player.dir && this.getY()>Game.player.getY() && this.getX()>Game.player.getX()-20 && this.getX()<Game.player.getX()+10) {
				if(Game.player.isAttack) {
					isDamaged = true;
					Sound.hurtEffect.play();
					if(Game.player.tridenteActive)
						life=life-3;
					if(Game.player.lancaActive)
						life=life-2;
				}
				return;
			}
			else if(Game.player.up_dir==Game.player.dir && this.getY()<Game.player.getY() && this.getX()>Game.player.getX()-10 && this.getX()<Game.player.getX()+20) {
				if(Game.player.isAttack) {
					isDamaged = true;
					Sound.hurtEffect.play();
					if(Game.player.tridenteActive)
						life=life-3;
					if(Game.player.lancaActive)
						life=life-2;
				}
				return;
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
		for(int i=0; i<Game.cuca.size(); i++) {
			Cuca e = Game.cuca.get(i);
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
