package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;

import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.CenarioTile;
import com.pedrokelvin.world.FloorTile;
import com.pedrokelvin.world.World;

public class Boto extends Entity{
	
	private int maskx = 16, masky = 16;
	
	public static int indexBoto=0;
	
	private BufferedImage[] sprites;
	
	public Tridente tridente;
	
	private int life = 100;
	
	private boolean isDamaged = false;
	
	public int timeAnima = 0, maxTimeAnima = (20);
	public int time = 0, maxTime = (60*8);
	public int maxTimeAtack = (60*3);
	
	private int damageFrames = 5, damageCurrent = 0;
	
	public Boto(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(0,  832, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(32, 832, 32, 32);
		sprites[2] = Game.spritesheet.getSprite(64, 832, 32, 32);
		sprites[3] = Game.spritesheet.getSprite(96, 832, 32, 32);
	}
	
	public void tick() {
		maskx = 10; 
		masky = 10; 
		if(life>0) {
			double anglePosit = Math.atan2(this.getY()-16 - Game.player.getY(),this.getX()-16 -  Game.player.getX());
			if(anglePosit <=1.66 && anglePosit>=-1.6) {
				timeAnima++;
				if(timeAnima<=30)
					indexBoto=2;
				else if(timeAnima<=60) {
					indexBoto=3;
				}
				else {
					timeAnima=0;
				}
			}
			else {
				timeAnima++;
				if(timeAnima<=30)
					indexBoto=1;
				else if(timeAnima<=60) {
					indexBoto=0;
				}
				else {
					timeAnima=0;
				}
			}
			time++;
				if(time>maxTime) {
					if(time>=maxTimeAtack) {
						time=0;
						if(Game.CUR_LEVEL==17) {
							boolean randomFlag = true;
							int randomNumY = 0;
							int randomNumX = 0;
							int randomNumZ = 0;
							do {
								randomFlag=true;
								randomNumY = Game.rand.nextInt(20);
								randomNumX = Game.rand.nextInt(20);
								randomNumZ = Game.rand.nextInt(40);
								System.out.println(randomNumZ);
								if(World.tiles [randomNumX + (randomNumY*World.WIDTH)] instanceof FloorTile) {
									if(randomNumZ<=25) {
										Mana mana = new Mana(randomNumX*32, randomNumY*32, 32, 32, Game.spritesheet.getSprite(104,296,16,16));
										Game.entities.add(mana);
									}
									else if(randomNumZ<=15) {
										Bullet bullet= new Bullet(randomNumX*32, randomNumY*32, 32, 32, Game.spritesheet.getSprite(41,295,14,24));
										Game.entities.add(bullet);
									}
									else {
										Lifepack lifepack = new Lifepack(randomNumX*32, randomNumY*32, 32, 32, Game.spritesheet.getSprite(5,295,22,19));
										Game.entities.add(lifepack);
									}
									randomFlag=false;
								}
							}while(randomFlag);	
						}
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
						
					if(time%15==0) {
						Sound.shootEffect.play();
						BulletBoto bulletBoto = new BulletBoto(this.getX()+px, this.getY()+py, 6, 6, null, dx, dy);								
						Game.bulletBoto.add(bulletBoto);
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
		else if(life<=0) {
			
		}
	}

	public void destroySelf() {
		System.out.println(Game.boto.size());
		Game.boto.remove(this);
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
		if(life<=0) {
			g.drawImage(DOLPHIN_WATER, this.getX() - Camera.x, this.getY()- Camera.y, null);
		}
		else if(!isDamaged)
			g.drawImage(sprites[indexBoto], this.getX() - Camera.x, this.getY()- Camera.y, null);
		else{
			if(indexBoto==2 || indexBoto==3)
				g.drawImage(DOLPHIN_FEEDBACK_RIGHT, this.getX() - Camera.x, this.getY()- Camera.y, null);
			else {
				g.drawImage(DOLPHIN_FEEDBACK_LEFT, this.getX() - Camera.x, this.getY()- Camera.y, null);
			}
		}
		
	}
}
