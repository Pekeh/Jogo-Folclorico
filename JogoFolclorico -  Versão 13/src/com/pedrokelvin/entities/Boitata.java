package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class Boitata extends Entity{
		
		private int speed = 1;
		
		private int maskx = 16, masky = 16, maskw = 20, maskh = 20;
		
		private int frames=0, maxFrames=6, index=0, maxIndex=3;
		
		private int timeSound=0, maxTimeSound=7;
		
		private int time=0, maxTime=(60*4);
		public int maxTimeAtack = (60*8);
		
		public static int xDead, yDead;
		
		public static int indexBoitata=0;
		
		private BufferedImage[] sprites;
		
		private int life = 100;
		
		private boolean poison = false;
		private int timePoison=0, maxTimePoison=(60*8);
		
		public static boolean isDeadEnemy=false;
		private boolean isDamaged = false;
		private int damageFrames = 5, damageCurrent = 0;
		
		public Boitata(int x, int y, int width, int height, BufferedImage sprite) {
			super(x, y, width, height, sprite);
			sprites = new BufferedImage[4];
			sprites[0] = Game.spritesheet.getSprite(32, 768, 32, 32);
			sprites[1] = Game.spritesheet.getSprite(64, 768, 32, 32);
			sprites[2] = Game.spritesheet.getSprite(96, 768, 32, 32);
			sprites[3] = Game.spritesheet.getSprite(128, 768, 32, 32);
		}
		
		public void tick() {
				maskx = 16; 
				masky = 16; 
				maskw = 28;
				maskh = 20;
				if(this.isColiddingWithPlayer()==false) {
					if(x<Game.player.getX() && World.isFree((int)(x+speed), this.getY())
							&& !isColidding((int)(x+speed), this.getY())) {
						sprites[0] = Game.spritesheet.getSprite(32, 704, 32, 32);
						sprites[1] = Game.spritesheet.getSprite(64, 704, 32, 32);
						sprites[2] = Game.spritesheet.getSprite(96, 704, 32, 32);
						sprites[3] = Game.spritesheet.getSprite(128, 704, 32, 32);
						indexBoitata=1;
						x+=speed;
					}
					else if(x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
							&& !isColidding((int)(x-speed), this.getY())) {
						sprites[0] = Game.spritesheet.getSprite(32, 736, 32, 32);
						sprites[1] = Game.spritesheet.getSprite(64, 736, 32, 32);
						sprites[2] = Game.spritesheet.getSprite(96, 736, 32, 32);
						sprites[3] = Game.spritesheet.getSprite(128, 736, 32, 32);
						indexBoitata=2;
						x-=speed;
					}
					if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
								&& !isColidding(this.getX(), (int)(y+speed))) {
						sprites[0] = Game.spritesheet.getSprite(32, 768, 32, 32);
						sprites[1] = Game.spritesheet.getSprite(64, 768, 32, 32);
						sprites[2] = Game.spritesheet.getSprite(32, 768, 32, 32);
						sprites[3] = Game.spritesheet.getSprite(64, 768, 32, 32);
						indexBoitata=3;
						y+=speed;
					}
					else if(y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
							&& !isColidding(this.getX(), (int)(y-speed))) {
						y-=speed;
						sprites[0] = Game.spritesheet.getSprite(32, 672, 32, 32);
						sprites[1] = Game.spritesheet.getSprite(64, 672, 32, 32);
						sprites[2] = Game.spritesheet.getSprite(96, 672, 32, 32);
						sprites[3] = Game.spritesheet.getSprite(128, 672, 32, 32);
						indexBoitata=0;
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
					poison=true;
				}	
				
				if(poison) {
					timePoison++;
					if(time%30==0)
					Game.player.life-=1;
					if(timePoison>=maxTimePoison) {
						timePoison=0;
						poison=false;
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
					
				double anglePosit = Math.atan2(this.getY()-16 - Game.player.getY(),this.getX()-16 -  Game.player.getX());
				time++;
				if(time>maxTime) {
					speed=0;
					if(time>=maxTimeAtack) {
						speed=1;
						time=0;
					}
					double dx=0;
					double dy=0;
					int px=0;
					int py=16;
						
					if(anglePosit <0.7853 && anglePosit>=-0.7001) {
						dx = Math.cos(anglePosit)*-1;
						dy = Math.sin(anglePosit)*-1;
						px = 0;
					}
						else if(anglePosit <-0.7001 && anglePosit>=-2.3670) {
						dx = Math.cos(anglePosit)*-1;
						dy = Math.sin(anglePosit)*-1;
						py = 26;
						px = 12;
					}
					else if(anglePosit <2.2817 && anglePosit>=0.7853) {
						dx = Math.cos(anglePosit)*-1;
						dy = Math.sin(anglePosit)*-1;
						py = 4;
						px = 18;
					}
					else {
						dx = Math.cos(anglePosit)*-1;
						dy = Math.sin(anglePosit)*-1;
						px = 28;
					}
						
					if(time%5==0) {
						Sound.waterballEffect.play();
						BulletBoitata bulletBoitata = new BulletBoitata(this.getX()+px, this.getY()+py, 6, 6, null, dx, dy);								
						Game.bulletBoitata.add(bulletBoitata);
					}
				}
				
		}
		
		public void destroySelf() {
			Game.boitata.remove(this);
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
			if(this.calculateDistance(this.getX()+maskx, this.getY()+masky,Game.player.getX()+maskx, Game.player.getY()+masky) < 45) {
				if(Game.player.right_dir==Game.player.dir && this.getX()>Game.player.getX() && this.getY()>Game.player.getY()-8 && this.getY()<Game.player.getY()+24
						||Game.player.left_dir==Game.player.dir && this.getX()<Game.player.getX() && this.getY()>Game.player.getY()-8 && this.getY()<Game.player.getY()+24
						||Game.player.down_dir==Game.player.dir && this.getY()>Game.player.getY() && this.getX()>Game.player.getX()-30 && this.getX()<Game.player.getX()+10
						||Game.player.up_dir==Game.player.dir && this.getY()<Game.player.getY() && this.getX()>Game.player.getX()-20 && this.getX()<Game.player.getX()+20) {
					if(Game.player.isAttack) {
						timeSound++;
						if(timeSound>maxTimeSound) {
							timeSound=0;
							Sound.hurtEffect.play();
						}
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
			for(int i=0; i<Game.boitata.size(); i++) {
				Boitata e = Game.boitata.get(i);
				if(e == this)
					continue;
				Rectangle targetEnemy = new Rectangle(e.getX()+maskx, e.getY()+masky, maskw, maskh);
				if(enemyCurrent.intersects(targetEnemy))
					return true;
			}
			return false;
		}
		
		public void render (Graphics g) {
			if(!isDamaged && speed==1)
				g.drawImage(sprites[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
			else if(!isDamaged && speed==0) {
				g.drawImage(sprites[0] = Game.spritesheet.getSprite(0, 768, 32, 32), this.getX() - Camera.x, this.getY()- Camera.y, null);
			}
			else{
				g.drawImage(BOITATA_FEEDBACK, this.getX() - Camera.x, this.getY()- Camera.y, null);
			}
		}
		
	}
