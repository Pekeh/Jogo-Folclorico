package com.pedrokelvin.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
	
	public boolean zarabatana = false;
	public boolean tridente = false;
	public boolean lanca = false;
	public boolean fireBall = false;

	public int loadZarabatana;
	public int loadTridente;
	public int loadLanca;
	public int loadFireBall;
	
	public boolean zarabatanaActive = false;
	public boolean tridenteActive = false;
	public boolean lancaActive = false;
	public boolean fireBallActive = false;
	
	public int moveAttack = 0;
	
	public int ammo = 0;
	public int mana = 0;
	
	public boolean isDamaged = false;
	
	private int damageFrames=0;
	
	public boolean isAttack = false;
	
	private int attackTime=0, maxTimeAttack=(10);
	
	public double life = 100, maxlife=100;
	
	public boolean shoot = false, mouseShoot = false;
	public int mx, my;
	
	public boolean texto = false;
	
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
		
		if(zarabatanaActive==true || fireBallActive==true) {
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
		else if(lancaActive==true || tridenteActive) {
			for(int i=0;i<4;i++) {
				rightPlayer[i] = Game.spritesheet.getSprite(32+(i*32),160, 32, 32);
			}
			for(int i=0;i<4;i++) {
				leftPlayer[i] = Game.spritesheet.getSprite(256-(i*32), 160, 32, 32);
			}
			for(int i=0;i<4;i++) {
				upPlayer[i] = Game.spritesheet.getSprite(192+(i*32), 192, 32, 32);
			}
			for(int i=0;i<4;i++) {
				downPlayer[i] = Game.spritesheet.getSprite(32+(i*32), 192, 32, 32);
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
		checkCollisionMana();
		checkCollisionZarabatana();
		checkCollisionTridente();
		checkCollisionLanca();
		checkCollisionFireBall();
		checkCollisionPergaminho();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 5) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(isAttack) {
			if(isAttack) {
				attackTime++;
				if(attackTime>=maxTimeAttack) {
					attackTime=0;
					isAttack=false;
					moveAttack=0;
				}
			}
		}
		
		if(shoot) {
			shoot=false;
				if(zarabatanaActive && ammo > 0) {
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
				if(fireBallActive && mana > 0) {
					Sound.shootEffect.play();
					mana--;
					int dx=0;
					int dy=0;
					int px=0;
					int py=13;
					
					if(dir == right_dir) {
						dx = 1;
						px = 28;
					}
					else if(dir == left_dir) {
						dx = -1;
						px = 0;
						py = 12;
					}
					else if(dir == down_dir) {
						dy = 1;
						px = 11;
						py = 28;
						
					}
					else if(dir == up_dir) {
						dy = -1;
						px = 18;
						py = 4;
					}
					
					FireBallShoot fireBallShoot = new FireBallShoot(this.getX()+px, this.getY()+py, 6, 6, null, dx, dy);
					Game.fireBallShoot.add(fireBallShoot);
				}
				
				if(tridenteActive || lancaActive) {
					Sound.shootEffect.play();
						moveAttack=10;
						isAttack=true;
				}
		}
		
		if(mouseShoot) {
			mouseShoot=false;
				if(zarabatanaActive && ammo > 0) {
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
				
				if(fireBallActive && mana > 0) {
					Sound.shootEffect.play();
					mana--;
					int dx=0;
					int dy=0;
					int px=0;
					int py=13;
					
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
						px = 11;
						py = 28;
						
					}
					else if(dir == up_dir) {
						dy = -1;
						px = 18;
						py = 4;
					}
					
					FireBallShoot fireBallShoot = new FireBallShoot(this.getX()+px, this.getY()+py, 6, 6, null, dx, dy);
					Game.fireBallShoot.add(fireBallShoot);
				}
				
			if(tridenteActive || lancaActive) {
				Sound.shootEffect.play();
					moveAttack=10;
					isAttack=true;
			}	
		}
		
		collidingBulletIara();
		collidingFireMula();
		collidingBulletBoto();
		collidingBulletBoitata();
		
		if(life<=0) {
			life=0;
			Game.gameState = "GAME_OVER";
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*32 - Game.HEIGHT);
	}
	//Collide para coletar Dardos
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
	//Colide para coletar Mana
	public void checkCollisionMana() {
		for (int i = 0; i<Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Mana) {
				if(Entity.isColidding(this, atual)) {
					Sound.collectEffect.play();
					mana+=20;
					if(mana>=1000) {
						mana=1000;
					}
					Game.entities.remove(atual);
				}
			}
		}
		return;
	}
	//Colide para Coletar Zarabatana
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
	//Colide para Coletar Tridente
	public void checkCollisionTridente() {
		for (int i = 0; i<Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Tridente) {
				if(Entity.isColidding(this, atual)) {
					Sound.collectEffect.play();
					tridente = true;
					Game.entities.remove(atual);
				}
			}
		}
		return;
	}
	//Colide para Coletar Lanca
	public void checkCollisionLanca() {
		for (int i = 0; i<Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lanca) {
				if(Entity.isColidding(this, atual)) {
					Sound.collectEffect.play();
					lanca = true;
					Game.entities.remove(atual);
				}
			}
		}
		return;
	}
	
	//Colide para Coletar bola de fogo
	public void checkCollisionFireBall() {
		for (int i = 0; i<Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof FireBall) {
				if(Entity.isColidding(this, atual)) {
					Sound.collectEffect.play();
					fireBall = true;
					if(ammo>=1000) {
						ammo=1000;
					}
					Game.entities.remove(atual);
				}
			}
		}
		return;
	}
	//Colide para Coletar Vida
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
	//Colide para Coletar Pergaminho
		public void checkCollisionPergaminho() {
			for (int i = 0; i<Game.entities.size();i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Pergaminho) {
					if(Entity.isColidding(this, atual)) {
						Game.player.texto=true;
					}
				}
			}
			return;
		}
	//Dano do Bullet do boss Iara
	public void collidingBulletIara() {
		for(int i=0; i<Game.bulletIara.size(); i++) {
			Entity e = Game.bulletIara.get(i);
			if(e instanceof BulletIara) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					Sound.hurtEffect.play();
					life=life-5;
					Game.bulletIara.remove(i);
					return;
				}
			}
		}
	}
	//Dano do Bullet do boss Boitata
		public void collidingBulletBoitata() {
			for(int i=0; i<Game.bulletBoitata.size(); i++) {
				Entity e = Game.bulletBoitata.get(i);
				if(e instanceof BulletBoitata) {
					if(Entity.isColidding(this, e)) {
						isDamaged = true;
						Sound.hurtEffect.play();
						life=life-5;
						Game.bulletBoitata.remove(i);
						return;
					}
				}
			}
		}
	//Dano do Bullet do Boto
		public void collidingBulletBoto() {
			for(int i=0; i<Game.bulletBoto.size(); i++) {
				Entity e = Game.bulletBoto.get(i);
				if(e instanceof BulletBoto) {
					if(Entity.isColidding(this, e)) {
						isDamaged = true;
						Sound.hurtEffect.play();
						life=life-5;
						Game.bulletBoto.remove(i);
						return;
					}
				}
			}
		}
	//Dano do Bullet do boss Mula
		public void collidingFireMula() {
			for(int i=0; i<Game.fireTrail.size(); i++) {
				Entity e = Game.fireTrail.get(i);
				if(e instanceof FireTrail) {
					if(Entity.isColidding(this, e) && frames==7) {
						isDamaged = true;
						Sound.damageEffect.play();
						life=life-1;
						return;
					}
				}
			}
		}
	//Renderizacao
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir==right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatanaActive) {
					g.drawImage(Entity.GUN_RIGHT, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
				else if(tridenteActive) {
						g.drawImage(Entity.TRIDENTE_RIGHT, this.getX() +8 + moveAttack - Camera.x, this.getY()- Camera.y, null);
				}
				else if(lancaActive) {
					g.drawImage(Entity.LANCA_RIGHT, this.getX() +8 + moveAttack - Camera.x, this.getY()- Camera.y, null);
				}
				else if(fireBallActive) {
					g.drawImage(Entity.FIRE_BALL_DOWN, this.getX()+12 - Camera.x, this.getY()-6 - Camera.y, null);
				}
				
			}
			else if(dir==left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatanaActive) {
					g.drawImage(Entity.GUN_LEFT, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
				else if(tridenteActive) {
					g.drawImage(Entity.TRIDENTE_LEFT, this.getX() -8 - moveAttack- Camera.x, this.getY()- Camera.y, null);
				}
				else if(lancaActive) {
					g.drawImage(Entity.LANCA_LEFT, this.getX() -8 - moveAttack- Camera.x, this.getY()- Camera.y, null);
				}
				else if(fireBallActive) {
					g.drawImage(Entity.FIRE_BALL_DOWN, this.getX()-9 - Camera.x, this.getY()-6 - Camera.y, null);
				}
			}
			else if(dir==up_dir) {
				if(!zarabatanaActive && !tridenteActive && !lancaActive)
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatanaActive) {
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					g.drawImage(Entity.GUN_UP, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
				else if(tridenteActive) {
					if(moveAttack!=0)
						g.drawImage(Entity.TRIDENTE_UP, this.getX()- Camera.x, this.getY() -8  - moveAttack - Camera.y, null);
					else
						g.drawImage(Entity.TRIDENTEMED_UP, this.getX()- Camera.x, this.getY() -8  - moveAttack - Camera.y, null);
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				else if(lancaActive) {
					if(moveAttack!=0)
						g.drawImage(Entity.LANCA_UP, this.getX()- Camera.x, this.getY() -8  - moveAttack - Camera.y, null);
					else
						g.drawImage(Entity.LANCAMED_UP, this.getX()- Camera.x, this.getY() -8  - moveAttack - Camera.y, null);
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				else if(fireBallActive) {
					g.drawImage(Entity.FIRE_BALL_UP, this.getX()- Camera.x, this.getY()- Camera.y, null);
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				
				
			}
			else if(dir==down_dir) {
				if(!zarabatanaActive && !tridenteActive && !lancaActive)
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(zarabatanaActive) {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					g.drawImage(Entity.GUN_DOWN, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
				else if(tridenteActive) {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(moveAttack!=0)
						g.drawImage(Entity.TRIDENTE_DOWN, this.getX()- Camera.x, this.getY() +8 + moveAttack - Camera.y, null);
					else
						g.drawImage(Entity.TRIDENTEMED_DOWN, this.getX()- Camera.x, this.getY() +8  - moveAttack - Camera.y, null);
					
				}
				else if(lancaActive) {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(moveAttack!=0)
						g.drawImage(Entity.LANCA_DOWN, this.getX()- Camera.x, this.getY() +8 + moveAttack - Camera.y, null);
					else
						g.drawImage(Entity.LANCAMED_DOWN, this.getX()- Camera.x, this.getY() +8  - moveAttack - Camera.y, null);
				}
				if(fireBallActive) {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					g.drawImage(Entity.FIRE_BALL_DOWN, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}
			}
		}
		else {
			g.drawImage(playerDamage, this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
	}
}
