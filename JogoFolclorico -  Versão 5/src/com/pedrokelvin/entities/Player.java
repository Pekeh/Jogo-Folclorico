package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import com.pedrokelvin.graficos.Spritesheet;
import com.pedrokelvin.main.Game;
import com.pedrokelvin.main.Sound;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class Player extends Entity{
	
	public boolean right, up, left, down;
	public int right_dir=0, left_dir=1, up_dir=2, down_dir=3;
	public int dir=right_dir;
	public double speed = 2;
	
	
	private int frames=0, maxFrames=8, index=0, maxIndex=3;
	private boolean moved=false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean zarabatana = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	
	private int damageFrames=0;
	
	public double life = 100, maxlife=100;
	
	public boolean shoot = false, mouseShoot = false;
	public int mx, my;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		playerDamage = Game.spritesheet.getSprite(288, 288, 32, 32);
		
		if(zarabatana==false) {
			for(int i=0;i<4;i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32+(i*32), 0, 32, 32);
			}
			for(int i=0;i<4;i++) {
				leftPlayer[i] = Game.spritesheet.getSprite(256-(i*32), 0, 32, 32);
			}
			for(int i=0;i<4;i++) {
				upPlayer[i] = Game.spritesheet.getSprite(192+(i*32), 32, 32, 32);
			}
			for(int i=0;i<4;i++) {
				downPlayer[i] = Game.spritesheet.getSprite(32+(i*32), 32, 32, 32);
			}
		}
	}
	
	public void tick() {
		moved=false;
		
		if(zarabatana==true) {
			for(int i=0;i<4;i++) {
				rightPlayer[i] = Game.spritesheet.getSprite(32+(i*32), 224, 32, 32);
			}
			for(int i=0;i<4;i++) {
				leftPlayer[i] = Game.spritesheet.getSprite(256-(i*32), 224, 32, 32);
			}
			for(int i=0;i<4;i++) {
				upPlayer[i] = Game.spritesheet.getSprite(192+(i*32), 256, 32, 32);
			}
			for(int i=0;i<4;i++) {
				downPlayer[i] = Game.spritesheet.getSprite(32+(i*32), 256, 32, 32);
			}
		}
		
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved=true;
			dir=right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved=true;
			dir=left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))) {
			dir=up_dir;
			moved=true;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))) {
			dir=down_dir;
			moved=true;
			y+=speed;
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames=0;
				index++;
				if(index>maxIndex) {
					index=0;
				}
			}
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionZarabatana();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 5) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			shoot=false;
			if(zarabatana && ammo > 0) {
				Sound.shootEffect.play();
				ammo--;
				int dx=0;
				int dy=0;
				int px=0;
				int py=16;
				
				if(dir == right_dir) {
					dx = 1;
					px = 28;
				}
				else if(dir == left_dir) {
					dx = -1;
					px = 0;
				}
				else if(dir == down_dir) {
					dy = 1;
					py = 26;
					px = 12;
					
				}
				else if(dir == up_dir) {
					dy = -1;
					py = 4;
					px = 18;
				}
				BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
			}
		}
		
		if(mouseShoot) {
			mouseShoot=false;
				if(zarabatana && ammo > 0) {
					Sound.shootEffect.play();
					ammo--;
					int dx=0;
					int dy=0;
					int px=0;
					int py=16;
					
					if(dir == right_dir) {
						dx = 1;
						px = 28;
					}
					else if(dir == left_dir) {
						dx = -1;
						px = 0;
					}
					else if(dir == down_dir) {
						dy = 1;
						py = 26;
						px = 12;
						
					}
					else if(dir == up_dir) {
						dy = -1;
						py = 4;
						px = 18;
					}
				BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
			}
		}
		
		if(life<=0) {
			life=0;
			Game.gameState = "GAME_OVER";
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*32 - Game.HEIGHT);
	}
	
	public void checkCollisionAmmo() {
		for (int i = 0; i<Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					Sound.collectEffect.play();
					ammo+=50;
					if(ammo>=1000) {
						ammo=1000;
					}
					Game.entities.remove(atual);
				}
			}
		}
		return;
	}
	
	public void checkCollisionZarabatana() {
		for (int i = 0; i<Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this, atual)) {
					Sound.collectEffect.play();
					zarabatana = true;
					if(ammo>=1000) {
						ammo=1000;
					}
					Game.entities.remove(atual);
				}
			}
		}
		return;
	}
	
	public void checkCollisionLifePack() {
		for (int i = 0; i<Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColidding(this, atual)) {
					Sound.collectEffect.play();
					life+=10;
					if(life>=100) {
						life=100;
					}
					Game.entities.remove(atual);
				}
			}
		}
		return;
	}
	
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir==right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatana) {
					g.drawImage(Entity.GUN_RIGHT, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
			}
			else if(dir==left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatana) {
					g.drawImage(Entity.GUN_LEFT, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
			}
			else if(dir==up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatana) {
					g.drawImage(Entity.GUN_UP, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
			}
			else if(dir==down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatana) {
					g.drawImage(Entity.GUN_DOWN, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
			}
		}
		else {
			g.drawImage(playerDamage, this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
	}
}
