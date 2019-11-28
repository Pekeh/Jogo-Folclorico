package com.pedrokelvin.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.DataBufferInt;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.pedrokelvin.entities.Boitata;
import com.pedrokelvin.entities.BoitataDead;
import com.pedrokelvin.entities.Boto;
import com.pedrokelvin.entities.BulletBoitata;
import com.pedrokelvin.entities.BulletBoto;
import com.pedrokelvin.entities.BulletIara;
import com.pedrokelvin.entities.BulletShoot;
import com.pedrokelvin.entities.Capelobo;
import com.pedrokelvin.entities.Cuca;
import com.pedrokelvin.entities.Dead;
import com.pedrokelvin.entities.Enemy;
import com.pedrokelvin.entities.Entity;
import com.pedrokelvin.entities.FireBallShoot;
import com.pedrokelvin.entities.FireTrail;
import com.pedrokelvin.entities.Iara;
import com.pedrokelvin.entities.Mapinguari;
import com.pedrokelvin.entities.Mula;
import com.pedrokelvin.entities.MulaDead;
import com.pedrokelvin.entities.Npc;
import com.pedrokelvin.entities.Pergaminho;
import com.pedrokelvin.entities.Player;
import com.pedrokelvin.entities.Saci;
import com.pedrokelvin.graficos.Spritesheet;
import com.pedrokelvin.graficos.UI;
import com.pedrokelvin.world.World;

public class Game  extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;
	public static final int SCALE = 2;
	
	public static int CUR_LEVEL = 1, MAX_LEVEL = 20;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Mapinguari> mapinguari;
	public static List<Capelobo> capelobo;
	public static List<Mula> mula;
	public static List<Boitata> boitata;
	public static List<Iara> iara;
	public static List<Boto> boto;
	public static List<Cuca> cuca;
	public static List<BulletShoot> bullets;
	public static List<BulletIara> bulletIara;
	public static List<BulletBoitata> bulletBoitata;
	public static List<BulletBoto> bulletBoto;
	public static List<FireBallShoot> fireBallShoot;
	public static List<FireTrail> fireTrail;
	
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public UI ui;
	
	public int xx, yy;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("font/pixelfont2.ttf");
	public InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("font/pixelfont2.ttf");
	public static Font newfontLife;
	public static Font newfontAmmo;
	
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estado_cena = entrada;
	
	public int timeCena = 0, maxTimeCena = 60;
	
	private double time = 0;
	private double maxTimeAtack = 5;
	
	public Menu menu;
	
	public int[] pixels;
	public BufferedImage lightmap;
	public int[] lightMapPixels;
	public Pergaminho pergaminho;
	
	public Dead dead;
	public MulaDead mulaDead;
	public BoitataDead boitataDead;
	
	public static int[] minimapaPixels;
	public static BufferedImage minimapa;
	
	public boolean saveGame = false;
	
	public int mx, my;
	
	public static boolean levelup = false;
	
	public Game(){
		Sound.musicBackground.loop();
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		initFrame();
		//Inicializar OBJ
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0,0,lightmap.getWidth(),lightmap.getHeight(),lightMapPixels,0,lightmap.getWidth());
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		mapinguari = new ArrayList<Mapinguari>();
		capelobo = new ArrayList<Capelobo>();
		mula = new ArrayList<Mula>();
		boitata = new ArrayList<Boitata>();
		iara = new ArrayList<Iara>();
		cuca = new ArrayList<Cuca>();
		boto = new ArrayList<Boto>();
		bullets= new ArrayList<BulletShoot>();
		bulletIara= new ArrayList<BulletIara>();
		bulletBoitata= new ArrayList<BulletBoitata>();
		bulletBoto= new ArrayList<BulletBoto>();
		fireBallShoot = new ArrayList<FireBallShoot>();
		fireTrail =  new ArrayList<FireTrail>();
		try {
			spritesheet = new Spritesheet("/spritesheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = new Player(0,0,32,32,spritesheet.getSprite(0, 0, 32, 32));
		entities.add(player);
		world = new World("/level1.png");
		
		minimapa = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapaPixels = ((DataBufferInt) minimapa.getRaster().getDataBuffer()).getData();
		
		try {
			newfontLife = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
			newfontAmmo = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(16f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		menu = new Menu();
	}
	
	public void initFrame() {
		frame = new JFrame("Jogo Folclorico");
		frame.add(this);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		
		Image imagem = null;
		try {
			imagem = ImageIO.read(getClass().getResource("/icon.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/cursor.png"));
		
		Cursor c = toolkit.createCustomCursor(image, new Point(0,0), "img");
		
		frame.setCursor(c);
		frame.setIconImage(imagem);
		frame.setAlwaysOnTop(true);
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
		/*MOBILIZACAO DE PIXELS
			xx++;
			yy++;
		*/
			if(this.saveGame){
				if(player.lanca)
					player.loadLanca=1;
				if(player.zarabatana)
					player.loadZarabatana=1;
				if(player.tridente)
					player.loadTridente=1;
				if(player.fireBall)
					player.loadFireBall=1;
				this.saveGame = false;
				String[] opt1 = {"level", "vida", "ammo", "mana", "lanca", "zarabatana", "tridente", "fireball"};
				int[] opt2 = {Game.CUR_LEVEL,(int)player.life-1, (int)player.ammo-1,  (int)player.mana, (int)player.loadLanca, (int)player.loadZarabatana, (int)player.loadTridente, (int)player.loadFireBall};
				Menu.saveGame(opt1, opt2, 30);
				System.out.println("Jogo salvo");
			}
			this.restartGame = false;
			
			if(Game.estado_cena == Game.jogando) {
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
				for(int i=0; i<bulletIara.size(); i++) {
					bulletIara.get(i).tick();
				}
				for(int i=0; i<bulletBoitata.size(); i++) {
					bulletBoitata.get(i).tick();
				}
				for(int i=0; i<bulletBoto.size(); i++) {
					bulletBoto.get(i).tick();
				}
				for(int i=0; i<fireBallShoot.size(); i++) {
					fireBallShoot.get(i).tick();
				}
				for(int i=0; i<fireTrail.size(); i++) {
					fireTrail.get(i).tick();
				}
				if(Enemy.isDeadEnemy) {
					Dead.xDead=Enemy.xDead;
					Dead.yDead=Enemy.yDead;
					dead = new Dead(Enemy.xDead, Enemy.yDead, 8, 8, null);
					entities.add(dead);
					Enemy.isDeadEnemy=false;
					
				}
				else if(Mapinguari.isDeadMapinguari) {
					Dead.xDead=Mapinguari.xDead;
					Dead.yDead=Mapinguari.yDead;
					dead = new Dead(Mapinguari.xDead, Mapinguari.yDead, 8, 8, null);
					entities.add(dead);
					Mapinguari.isDeadMapinguari=false;
				}
				else if(Capelobo.isDeadCapelobo) {
					Dead.xDead=Capelobo.xDead;
					Dead.yDead=Capelobo.yDead;
					dead = new Dead(Capelobo.xDead, Capelobo.yDead, 8, 8, null);
					entities.add(dead);
					Capelobo.isDeadCapelobo=false;
				}
				else if(Mula.isDeadEnemy) {
					mulaDead = new MulaDead(Mula.xDead, Mula.yDead, 8, 8, null);
					entities.add(mulaDead);
					Mula.isDeadEnemy=false;

				}
				else if(Boitata.isDeadEnemy) {
					boitataDead = new BoitataDead(Boitata.xDead, Boitata.yDead, 8, 8, null);
					entities.add(boitataDead);
					Boitata.isDeadEnemy=false;

				}
			}
			else if(Game.estado_cena == Game.entrada) {
					if(player.getX()<80) {
						player.x++;
					}
					else {
						Game.estado_cena = Game.comecar;
					}
			}
			else if(Game.estado_cena == Game.comecar) {
				timeCena++;
				if(timeCena == maxTimeCena) {
					Game.estado_cena = Game.jogando;
				}
			}
			
			if(Game.player.getX() >= Game.world.passX-10
					&& Game.player.getX() <= Game.world.passX+10
					&& Game.player.getY() >= Game.world.passY-10
					&& Game.player.getY() <= Game.world.passY+10) {
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
				}
				levelup=true;
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
					Game.gameState = "NORMAL";
					CUR_LEVEL = 1;
					String newWorld = "level"+CUR_LEVEL+".png";
					World.restartGame(newWorld);
				}
			}
		}else if(gameState == "MENU") {
			menu.tick();		
		}
	}
	
	/*MOBILIZACAO DE PIXELS
	 public void drawRactangleExample(int xoff, int yoff) {
		for(int xx = 0; xx < 32 ; xx++) {
			for(int yy = 0; yy < 32 ; yy++) {
				int xOff = xx + xoff;
				System.out.println(yoff);d
				w
				
				
				int yOff = yy + yoff;
				if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
					//continue;
				pixels[xOff+(yOff*WIDTH)] = 0xff0000;
			}
		}
	}*/
	
	/*SISTEMA DE LUZ
	 public void applyLight() {
		for(int xx = 0; xx < Game.WIDTH ; xx++) {
			for(int yy = 0; yy < Game.HEIGHT ; yy++) {
				if(lightMapPixels[xx+(yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel = Pixel.getLightBlend(pixels[xx+(yy * WIDTH)], 0x008764, 0);
					pixels[xx+(yy*Game.WIDTH)] = pixel;
				}
			}
		}
	}*/
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		for(int i=0; i<entities.size(); i++) {
			
			
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i=0; i<bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		for(int i=0; i<bulletIara.size(); i++) {
			bulletIara.get(i).render(g);
		}
		for(int i=0; i<bulletBoitata.size(); i++) {
			bulletBoitata.get(i).render(g);
		}
		for(int i=0; i<bulletBoto.size(); i++) {
			bulletBoto.get(i).render(g);
		}
		for(int i=0; i<fireBallShoot.size(); i++) {
			fireBallShoot.get(i).render(g);
		}
		for(int i=0; i<fireTrail.size(); i++) {
			fireTrail.get(i).render(g);
		}
		
		//SISTEMA DE LUZ applyLight();
		ui.render(g);
		
		g.dispose();
		g=bs.getDrawGraphics();
		//MOBILIZACAO DE PIXELS drawRactangleExample(xx,yy);
		g.drawImage(image,0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height,null);
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
			g.setFont(new Font("arial", Font.BOLD,48));
			g.setColor(Color.white);
			g.drawString("Game Over", (WIDTH*SCALE)/2 + 70, (HEIGHT*SCALE)/2 + 20);
			g.setFont(new Font("arial", Font.BOLD,24));
			if(showMessageGameOver) 
				g.drawString("> Pressione Enter para reiniciar <", (WIDTH*SCALE)/2, (HEIGHT*SCALE)/2 + 60);
		}else if(gameState == "MENU") {
			menu.render(g);
		}
		
		if(Game.estado_cena == Game.comecar) {
			g.setFont(new Font("arial", Font.BOLD,48));
			g.setColor(Color.white);
			g.drawString("Jogo Começando...", (WIDTH*SCALE)/2+50, (HEIGHT*SCALE)/2);
		}
		
		/*Graphics2D g2 = (Graphics2D) g;
		double angleMouse = Math.atan2(400+16 - Game.player.getY(),400+16 -  Game.player.getX());
		g2.rotate(angleMouse, 400+16, 400+16);
		g.setColor(Color.red);
		g.fillRect(400, 400, 32, 32);*/
		
		if(gameState=="NORMAL") {
			World.renderMiniMap();
			g.drawImage(minimapa,5,5,World.WIDTH*5,World.HEIGHT*5,null);
		}
		
		bs.show();
	}

	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 /amountOfTicks;
		double delta = 0;
		int frames=0;
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
				//System.out.println("FPS: " +frames);
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}

	@Override
		public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if(gameState == "MENU") {
				menu.enter = true;
			}
			if(Npc.fraseIndex>=Npc.frases.length-1) {
				Npc.showMessage = false;
				Npc.fraseIndex=0;
			}
			else {
				Npc.fraseIndex++;
				Npc.curIndexMsg = 0;
			}
			if(Saci.fraseIndex>=Saci.frases.length-1) {
				Saci.showMessage = false;
				Saci.fraseIndex=0;
			}
			else {
				Saci.fraseIndex++;
				Saci.curIndexMsg = 0;
			}
			if(Game.player.texto) {
				Game.player.texto=false;
				for (int i = 0; i<Game.entities.size();i++) {
					Entity atual = Game.entities.get(i);
					if(atual instanceof Pergaminho) {
						Game.entities.remove(atual);
					}
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up=true;
			if(gameState == "MENU") {
				menu.up = true;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down=true;
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_J) {
			if(time>=maxTimeAtack) {
				player.shoot=true;
				time=0;
			}
			time++;
			
		}
		else {
			time=5;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			Menu.pause = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.saveGame = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_1) {
			if(Game.player.lanca) {
				Game.player.tridenteActive = false;
				Game.player.zarabatanaActive = false;
				Game.player.lancaActive = true;
				Game.player.fireBallActive = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			if(Game.player.zarabatana) {
				Game.player.lancaActive = false;
				Game.player.tridenteActive = false;
				Game.player.zarabatanaActive = true;
				Game.player.fireBallActive = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_3) {
			if(Game.player.tridente) {
				Game.player.lancaActive = false;
				Game.player.zarabatanaActive = false;
				Game.player.tridenteActive = true;
				Game.player.fireBallActive = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_4) {
			if(Game.player.fireBall) {
				Game.player.lancaActive = false;
				Game.player.zarabatanaActive = false;
				Game.player.tridenteActive = false;
				Game.player.fireBallActive = true;
			}
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
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot=true;
		player.mx = (e.getX()/2);
		player.my = (e.getY()/2);	
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();	
	}
}