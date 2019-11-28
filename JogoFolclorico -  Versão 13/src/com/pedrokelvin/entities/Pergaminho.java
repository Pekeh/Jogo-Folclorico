package com.pedrokelvin.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pedrokelvin.main.Game;

public class Pergaminho extends Entity{
	
	public String[] frases = new String [10];
	public String[] titulos = new String [10];
	
	private BufferedImage pergaminho;
	
	public int curIndexMsg = 0;
	public int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 1;
	public int numeroY=90;
	
	public Pergaminho(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		titulos[0] = "CORPO SECO";
		frases[0] = " Depois que morre, nem Deus nem o\n "
				+ "diabo o aceitam. E at� a terra o rejeita,\n "
				+ "expulsando seu corpo ressequido,\n "
				+ "condenado a vagar na noite como um\n "
				+ "ser de pele e ossos, que faz murchar\n "
				+ "tudo aquilo que toca. Ele ataca quem\n "
				+ "passa por perto, sugando o sangue\n "
				+ "como um vampiro.";
		
		titulos[1]= "MULA";
		frases[1] = " A lenda conta que quando uma mulher\n"
				+ " namorasse um padre ela seria\n"
				+ " transformada em um monstro, com\n"
				+ " corpo de mula e sem cabe�a onde no,\n"
				+ " lugar labaredas flamejantes assustam\n"
				+ " as pessoas que a vissem. Ela possui\n"
				+ " em seus cascos ferraduras de prata\n"
				+ " ou a�o e somente se fossem retirados\n"
				+ " de suas patas que voltaria a uma\n"
				+ " mulher arrependida de seus pecados";
		
		titulos[2] = "MAPINGUARI";
		frases[2] = " A criatura � selvagem e capaz de dilatar\n"
				+ " o a�o quando sopra no cano de uma\n"
				+ " espingarda. Quando anda pela mata,\n"
				+ " deixa um rastro de destrui��o. Alguns\n"
				+ " �ndios acreditam que ao atingirem uma\n"
				+ " idade mais avan�ada transformariam-se\n"
				+ " em Mapinguaris e passariam a habitar\n"
				+ " o interior das florestas sozinhos. Ele\n"
				+ " logo vai at� o individuo, que acaba\n"
				+ " perdendo a vida.";
		
		titulos[3] = "BOITAT�";
		frases[3] = " Uma serpente de fogo que protege os\n"
				+ "  animais e as matas das pessoas que \n"
				+ "  lhe fazem mal e principalmente,\n"
				+ "  que realizam queimadas nas florestas.\n"
				+ "  Acredita-se que apessoa que olhar o\n"
				+ "  Boitat� torna-se cegae louca.";
				
		titulos[4]= "BOTO ROSA";
		frases[4] = " O boto cor-de-rosa sai dos rios nas\n"
				+ "  noites de festa e transforma-se em um\n"
				+ "  lindo jovem vestido roupas brancas.\n "
				+ " Nas festas, com seu jeito galanteador\n"
				+ "  e falante, o boto  aproxima-se das\n"
				+ "  jovens solteiras, seduzindo-as. Logo\n"
				+ "  ap�s, consegue convencer as mulheres\n"
				+ "  para um passeio no fundo do rio";
		
		titulos[5]= "IARA";
		frases[5]= "A Iara � um ser, metade peixe metade\n"
				+ " mulher, que vive nos rios. A Iara, al�m\n"
				+ " de possuir um belo canto, tamb�m\n"
				+ " contava com a sua beleza, podendo ao\n"
				+ " sair da �gua assumir a forma humana\n"
				+ " de uma mulher. Atra�a os pescadores\n"
				+ " da regi�o com seu canto m�gico e sua\n"
				+ " beleza levando-os at� o fundo dos rios\n"
				+ " onde havia seu reino";
		
		titulos[6] = "CAPELOBO";
		frases[6] = "Possui cabe�a de tamandu�-bandeira, \n"
				+ " corpo humano forte, patas redondas. \n"
				+ "	 casas e acampamentos que ficam \n"
				+ "	 dentro das florestas, costuma apanhar \n"
				+ "	 c�es e gatos rec�m nascidos, mas \n"
				+ "	 quando captura um animal maior ou \n"
				+ "  um homem, ele quebra o cr�nio e \n"
				+ "  come o c�rebro  ou bebe o sangue. \n"
				+ " S� � morto com um golpe no umbigo.";
		
		titulos[7]= "CUCA";
		frases[7]= "Uma bruxa com apar�ncia assustadora\n"
				+ " que possui cabe�a de jacar� e unhas\n"
				+ " imensas. Tem uma voz horripilante,\n"
				+ " e rapta crian�as desobedientes. A Cuca\n"
				+ " dorme  a cada sete anos. Por isso,\n"
				+ " os pais tentam convencer as crian�as\n"
				+ " a dormirem nas horas corretas, pois,\n"
				+ " do contr�rio, ser�o levadas por ela\n";
		
		titulos[8]="SACI";
		frases[8]="";
				
		titulos[9]= "CURUPIRA";
		frases[9]= " De acordo com a lenda, o Curupira �\n"
				+ " um protetor da fauna e flora.Uma lan�a,\n"
				+ " arco e flechas e possui os p�s voltados\n"
				+ " para tr�s. Para ressuscitar os animais\n"
				+ " mortos sem seu consentimento utiliza \n"
				+ " sua lan�a, seu arco, ordem verbal e\n"
				+ " atrav�s do contato do nariz do Caititu\n"
				+ " seu animal de estima��o.";
		
		
		
		
		try {
			pergaminho = ImageIO.read(getClass().getResourceAsStream("/pergaminho.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(Game.CUR_LEVEL==2) {
			fraseIndex=0;
		}else if(Game.CUR_LEVEL==4) {
			fraseIndex=1;
		}else if(Game.CUR_LEVEL==6) {
			fraseIndex=2;
		}else if(Game.CUR_LEVEL==10) {
			fraseIndex=3;
		}
		else if(Game.CUR_LEVEL==13) {
			fraseIndex=7;
		}
		
		if(Game.player.texto) {
			this.time++;
			if(this.time >= this.maxTime) {
				this.time = 0;
				if(curIndexMsg < frases[fraseIndex].length()) {
					curIndexMsg++;
				}
			}
			
		}
		
		if(Game.CUR_LEVEL==2)
			fraseIndex=0;
		else if(Game.CUR_LEVEL==3) {
			fraseIndex=2;
		}
	}
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.render(g);
		if(Game.player.texto) {
			g.drawImage(pergaminho, 100, 20, 280, 280, null);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString(titulos[fraseIndex], 210, 90);
			g.setFont(new Font("Arial", Font.BOLD, 9));
			drawString(g2,frases[fraseIndex].substring(0,curIndexMsg), 150, 95);
			g.drawString(">Pressione ENTER<", 200, 230);
		}
	}
}
