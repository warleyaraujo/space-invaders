package game;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Nave {

    private BufferedImage desenho; 

    private int x;
    private int velocidade;
    boolean podeAtirar;
    private int tempo;


    public Nave() {
        try {
            desenho = ImageIO.read(new File("imagens/nave.png"));
        } catch (IOException e) {
            System.out.println("Não foi possível carregar a imagem da nave");
            e.printStackTrace();
        }

        x = 683;
        velocidade = 3;
        podeAtirar = true;
        tempo = 0;
    }

    public void pintar(Graphics2D g) {
       
        g.drawImage(desenho,
                    x, MainJogo.monitor.getHeight() - 150,
                    x + 100, MainJogo.monitor.getHeight() - 150 + 100,
                    0, 0,
                    desenho.getWidth(), desenho.getHeight(),
                    null);
      
    }

    public Tiro atirar() {

        podeAtirar = false;
        tempo = 0;

        Tiro novoTiro = new Tiro(x + 49, MainJogo.monitor.getHeight() - 150);
        return novoTiro;
    }

    public void movimenta(int valor) {
        
        if(valor == 1) {
            x += velocidade;
        } else if (valor == -1) {
            x -= velocidade;
        }

        if (tempo >= 10) {
            podeAtirar = true;
            tempo = 0;
        }
        tempo++;
    }

    public boolean podeAtirar() {
        return podeAtirar;
    }

}
