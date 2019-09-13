package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;

import com.pedrokelvin.world.Camera;

public class Iara extends Entity{
	
	private int maskx = 16, masky = 16;
	
	public static int indexIara=0;
	
	private BufferedImage[] sprites;
	
	private int life = 100;
	
	private boolean isDamaged = false;
	
	public int time = 0, maxTime = (60*3);
	public int maxTimeAtack = (60*6);
	
	private int damageFrames = 5, damageCurrent = 0;
	
	public Iara(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(0,  1248, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(32, 1248, 32, 32);
		sprites[2] = Game.spritesheet.getSprite(64, 1248, 32, 32);
		sprites[3] = Game.spritesheet.getSprite(96, 1248, 32, 32);
	}
	
	public void tick() {
		maskx = 10; 
		masky = 10; 
		if(life>0) {
				double anglePosit = Math.atan2(this.getY()-16 - Game.player.getY(),this.getX()-16 -  Game.player.getX());
				if(anglePosit <=0.7853 && anglePosit>=-0.7001) {
					indexIara=2;
				}
				else if(anglePosit <-0.7001 && anglePosit>=-2.36) {
					indexIara=3;
				}
				else if(anglePosit <=2.29 && anglePosit>=0.78) {
					indexIara=0;
				}
				else{
					indexIara=1;
				}
				
				time++;
					if(time>maxTime) {
						if(time>=maxTimeAtack) {
								time=0;
								
								boolean randomFlag = true;
								int randomNumY = 0;
								int randomNumX = 0;
								
								while(randomFlag) {
									randomFlag=false;
									randomNumY = Game.rand.nextInt(420);
									randomNumX = Game.rand.nextInt(100);
									if(randomNumX>50) {
										randomNumX=50;
									}
									else {
										randomNumX=400;
									}
									if(randomNumY<60)
										randomFlag=true;
								}	
								Enemy enemy = new Enemy(randomNumX , randomNumY, 32, 32, Entity.ENEMY_EN);
								Game.entities.add(enemy);
								Game.enemies.add(enemy);
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
						
						if(life>0) {
							if(time%5==0) {
								Sound.shootEffect.play();
								BulletIara bulletIara = new BulletIara(this.getX()+px, this.getY()+py, 6, 6, null, dx, dy);								
								Game.bulletIara.add(bulletIara);
							}
						}
				}			
				if(Game.player.lancaActive || Game.player.tridenteActive)
					collidingAttack();
				else if(Game.player.zarabatanaActive)
					collidingBullet();
				else if(Game.player.fireBallActive)
					collidingFireBallShoot();
					
				if(isDamaged) {
					this.damageCurrent++;
					if(this.damageCurrent == this.damageFrames) {
						this.damageCurrent = 0;
						this.isDamaged = false;
					}
				}
			}
	}

	public void destroySelf() {
		Game.entities.remove(this);	
	}
	
	public void collidingBullet() {
		for(int i=0; i<Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					Sound.hurtEffect.play();
					life=life-12;
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
	
	public void render (Graphics g) {
		if(!isDamaged)
			g.drawImage(sprites[indexIara], this.getX() - Camera.x, this.getY()- Camera.y, null);
		else if(life<=0)
			g.drawImage(IARA_DEAD, this.getX() - Camera.x, this.getY()- Camera.y, null);
		else {
			g.drawImage(IARA_FEEDBACK, this.getX() - Camera.x, this.getY()- Camera.y, null);
		}
	}
}
