package game;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
// import javax.imageio.ImageIO;

public class Inimigo {
    
    private BufferedImage desenho;
    private int x;
    private int y;
    private float velocidade;
    private int direcao;

    public Inimigo(BufferedImage imagem, int inicioX, int inicioY, int direcao) {

        this.desenho = imagem;
        
        this.x = inicioX;
        this.y = inicioY;
        this.direcao = direcao;

        this.velocidade = 2;

    }

    public void atualizar() {
        x += velocidade * direcao;

    }
    
    public void trocaDirecao() {
        direcao = direcao * -1;
        y += 25;
        velocidade += 0.25f;
   
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTam() {
        return 50;
    }
    

    public void pintar(Graphics2D g) {
        g.drawImage(desenho, x, y, x + 50, y + 50, 0, 0, desenho.getWidth(), desenho.getHeight(), null);
    }



}
