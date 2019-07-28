package com.pedrokelvin.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.pedrokelvin.entities.BulletShoot;
import com.pedrokelvin.entities.Enemy;
import com.pedrokelvin.entities.Entity;
import com.pedrokelvin.entities.Player;
import com.pedrokelvin.graficos.Spritesheet;
import com.pedrokelvin.graficos.UI;
import com.pedrokelvin.world.Camera;
import com.pedrokelvin.world.World;

public class Game  extends Canvas implements Runnable, KeyListener, MouseListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;
	public static final int SCALE = 2;
	
	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<BulletShoot> bullets;
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public UI ui;
	
	public static String gameState = "NORMAL";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public Game(){
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//Inicializar OBJ
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets= new ArrayList<BulletShoot>();
		try {
			spritesheet = new Spritesheet("/spritesheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = new Player(0,0,32,32,spritesheet.getSprite(0, 0, 32, 32));
		entities.add(player);
		world = new World("/level1.png");
		
		
	}
	
	public void initFrame() {
		frame = new JFrame("Jogo Folclorico");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning=true;
		thread.start();
	}
	public synchronized void stop() {
		thread = new Thread(this);
		isRunning=false;
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(gameState=="NORMAL") {
		this.restartGame = false;
		for(int i=0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			if(e instanceof Player) {
				//Tick no Player
			}
			e.tick();
		}
		
		for(int i=0; i<bullets.size(); i++) {
			bullets.get(i).tick();
		}
		if(enemies.size()==0) {
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL) {
				CUR_LEVEL = 1;
			}
			String newWorld = "level"+CUR_LEVEL+".png";
			World.restartGame(newWorld);
		}
		}else if(gameState == "GAME_OVER") {
			this.framesGameOver++;
			if(this.framesGameOver == 60) {
				this.framesGameOver = 0;
				if(this.showMessageGameOver)
					this.showMessageGameOver=false;
				else {
					this.showMessageGameOver = true;
				}
				
				if(restartGame) {
					this.restartGame = false;
					this.gameState = "NORMAL";
					CUR_LEVEL = 1;
					String newWorld = "level"+CUR_LEVEL+".png";
					World.restartGame(newWorld);
				}
			}
		}
			
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		for(int i=0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i=0; i<bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		ui.render(g);
		/***/
		g.dispose();
		g=bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g.setFont(new Font("arial", Font.BOLD,17));
		g.setColor(Color.white);
		g.drawString("Municao: " +player.ammo, 820,32);
		if(gameState == "GAME_OVER") {
			Graphics g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD,48));
			g.setColor(Color.white);
			g.drawString("Game Over", (WIDTH*SCALE)/2 - 130, (HEIGHT*SCALE)/2 + 20);
			g.setFont(new Font("arial", Font.BOLD,24));
			if(showMessageGameOver) {
				g.drawString("> Pressione Enter para reiniciar <", (WIDTH*SCALE)/2 - 190, (HEIGHT*SCALE)/2 + 50);
			}
		}
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 /amountOfTicks;
		double delta = 0;
		int frames =0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			if(delta>=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " +frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up=true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up=false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down=false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot=true;
		player.mx = (e.getX()/2);
		player.my = (e.getY()/2);
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
