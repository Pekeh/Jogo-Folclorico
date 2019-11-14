package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class Mula extends Entity{
		
		private int speed = 1;
		
		private int maskx = 16, masky = 16, maskw = 20, maskh = 20;
		
		private int frames=0, maxFrames=6, index=0, maxIndex=3;
		
		public static int xDead, yDead;
		
		private BufferedImage[] sprites;
		
		private int life = 100;
		
		public static boolean isDeadEnemy=false;
		private boolean isDamaged = false;
		private int damageFrames = 5, damageCurrent = 0;
		
		public Mula(int x, int y, int width, int height, BufferedImage sprite) {
			super(x, y, width, height, sprite);
			sprites = new BufferedImage[4];
			sprites[0] = Game.spritesheet.getSprite(0, 1184, 39, 32);
			sprites[1] = Game.spritesheet.getSprite(39, 1184, 40, 32);
			sprites[2] = Game.spritesheet.getSprite(79, 1184, 42, 32);
			sprites[3] = Game.spritesheet.getSprite(121, 1184, 41, 32);
		}
		
		public void tick() {
				maskx = 16; 
				masky = 16; 
				maskw = 28;
				maskh = 20;
					if(this.isColiddingWithPlayer()==false) {
						boolean instanceFire = false;
						if(x<Game.player.getX() && World.isFree((int)(x+speed), this.getY())
								&& !isColidding((int)(x+speed), this.getY())) {
							sprites[0] = Game.spritesheet.getSprite(0, 1184, 39, 32);
							sprites[1] = Game.spritesheet.getSprite(39, 1184, 40, 32);
							sprites[2] = Game.spritesheet.getSprite(79, 1184, 42, 32);
							sprites[3] = Game.spritesheet.getSprite(121, 1184, 41, 32);
							x+=speed;
							if(this.getX()%16==0) {
								FireTrail fireTrail = new FireTrail(this.getX()-32, this.getY()+8, 23, 27, null);
								Game.fireTrail.add(fireTrail);
							}
							instanceFire=true;
						}
						else if(x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
								&& !isColidding((int)(x-speed), this.getY())) {
							sprites[0] = Game.spritesheet.getSprite(123, 1216, 39, 32);
							sprites[1] = Game.spritesheet.getSprite(83, 1216, 40, 32);
							sprites[2] = Game.spritesheet.getSprite(41, 1216, 42, 32);
							sprites[3] = Game.spritesheet.getSprite(0, 1216, 41, 32);
							x-=speed;
							if(this.getX()%16==0) {
								FireTrail fireTrail = new FireTrail(this.getX()+32, this.getY()+8, 23, 27, null);
								Game.fireTrail.add(fireTrail);
							}
							instanceFire=true;
						}
						if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
								&& !isColidding(this.getX(), (int)(y+speed))) {
							y+=speed;
							if(this.getY()%16==0) {
								if(!instanceFire) {
									FireTrail fireTrail = new FireTrail(this.getX()+8, this.getY()-32, 23, 27, null);
									Game.fireTrail.add(fireTrail);	
								}
							}
							
						}
						else if(y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
								&& !isColidding(this.getX(), (int)(y-speed))) {
							y-=speed;
							if(this.getY()%16==0) {
								if(!instanceFire) {
									FireTrail fireTrail = new FireTrail(this.getX()+8, this.getY()+32, 23, 27, null);
									Game.fireTrail.add(fireTrail);	
								}
							}
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
			Game.mula.remove(this);
			Game.entities.remove(this);	
		}
		
		public void collidingBullet() {
			for(int i=0; i<Game.bullets.size(); i++) {
				Entity e = Game.bullets.get(i);
				if(e instanceof BulletShoot) {
					if(Entity.isColidding(this, e)) {
						isDamaged = true;
						Sound.mulaDamage.play();
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
						Sound.mulaDamage.play();
						life=life-15;
						Game.fireBallShoot.remove(i);
						return;
					}
				}
			}
		}
		
		public void collidingAttack() {
			if(this.calculateDistance(this.getX()+maskx, this.getY()+masky,Game.player.getX()+maskx, Game.player.getY()+masky) < 45) {
				if(Game.player.right_dir==Game.player.dir && this.getX()>Game.player.getX() && this.getY()>Game.player.getY()-8 && this.getY()<Game.player.getY()+24
						||Game.player.left_dir==Game.player.dir && this.getX()<Game.player.getX() && this.getY()>Game.player.getY()-8 && this.getY()<Game.player.getY()+24
						||Game.player.down_dir==Game.player.dir && this.getY()>Game.player.getY() && this.getX()>Game.player.getX()-30 && this.getX()<Game.player.getX()+10
						||Game.player.up_dir==Game.player.dir && this.getY()<Game.player.getY() && this.getX()>Game.player.getX()-20 && this.getX()<Game.player.getX()+20) {
					if(Game.player.isAttack) {
						Sound.mulaDamage.play();
						isDamaged = true;
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
			for(int i=0; i<Game.mula.size(); i++) {
				Mula e = Game.mula.get(i);
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
			else if(x <= Game.player.getX() && World.isFree((int)(x+speed), this.getY())
					&& !isColidding((int)(x+speed), this.getY())) {
				g.drawImage(MULA_FEEDBACK_LEFT, this.getX() - Camera.x, this.getY()- Camera.y, null);
			}
			else if(x >= Game.player.getX() && World.isFree((int)(x-speed), this.getY())
					&& !isColidding((int)(x-speed), this.getY())) {
				g.drawImage(MULA_FEEDBACK_RIGHT, this.getX() - Camera.x, this.getY()- Camera.y, null);
			}
		}
		
	}
