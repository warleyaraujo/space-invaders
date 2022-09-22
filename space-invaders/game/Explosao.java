package game;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Explosao {

    private BufferedImage imagem;
    private int x, y; 
    private int duracao;
    private int animacaoTotal;
    private int linha;
    private int coluna;

    public Explosao(BufferedImage imagem, int x, int y) {

        this.imagem = imagem;
        this.x = x;
        this.y = y;

        duracao = 0;
        animacaoTotal = 30;

        linha = 0;
        coluna = 0;
    }

    public void atualizar() {
        duracao +=2;

        linha = duracao / 4;
        coluna = duracao % 8;
    }

    public void pintar(Graphics2D g) {
        g.drawImage(imagem, x, y, x + 50, y + 50, 254 * coluna, 254 * linha, 254 * coluna + 254, 254 * linha + 254, null);
    }

    public boolean acabou() {
        if (duracao >= animacaoTotal)
            return true;

        return false;

    }
}
