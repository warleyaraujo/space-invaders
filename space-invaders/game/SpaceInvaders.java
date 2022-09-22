package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.scene.text.Font;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpaceInvaders extends JPanel implements Runnable, KeyListener {
    
    // private Font minhaFonte = new Font("Consolas", Font.BOLD, 20);

    private Nave nave;
    private int direcao = 0;
    private ArrayList<Tiro> tiros;
    private ArrayList<Inimigo> inimigos;
    private ArrayList<Explosao> explosoes;
    private PlanoDeFundo fundo;
    private boolean ganhou;
    private boolean perdeu;
    private float fechandoEm = 3;
    private BufferedImage imagemExplosao;

    //CONSTRUTOR - é chamado quando fazemos o new SpaceInvaders();
    public SpaceInvaders() {

        nave = new Nave();
        tiros = new ArrayList<Tiro>();
        inimigos = new ArrayList<Inimigo>();
        explosoes = new ArrayList<Explosao>();
        fundo = new PlanoDeFundo();
        ganhou = false;
        perdeu = false;
        BufferedImage imagemInimigo = null;
        try {
            imagemInimigo = ImageIO.read(new File("imagens/inimigo.png"));
            imagemExplosao = ImageIO.read(new File("imagens/explosao.png"));
        } catch (IOException e) {
            System.out.println("Não foi possível carregar a imagem do inimigo");
            e.printStackTrace();
        }

        for (int i = 0; i< 60; i++) {
            inimigos.add(new Inimigo(imagemInimigo ,50 + i%20 * 75, 50 + i/20 * 75, 1));
        }

        Thread lacoDoJogo = new Thread(this);
        lacoDoJogo.start();
    }

    @Override
    public void run() {

        while (true) {
            long tempoInicial = System.currentTimeMillis();
            update();
            repaint();
            long tempoFinal = System.currentTimeMillis();

            long diferenca = 16 - (tempoFinal - tempoInicial);
            if (diferenca > 0) {
                dorme(diferenca);
            }

        }
    }

    private void update() {

        if(inimigos.size() == 0) {
            ganhou = true;
        }

        nave.movimenta(direcao);

        for (int i = 0; i< inimigos.size(); i++) {
            inimigos.get(i).atualizar();

            if(inimigos.get(i).getY() >= MainJogo.monitor.getHeight() - 150)
            perdeu = true;
        }
        
        for (int i = 0; i < tiros.size(); i++) {
            tiros.get(i).atualiza();

            if (tiros.get(i).destroy()) {
                tiros.remove(i);
                i--;
            }
            else {
            for (int j = 0; j < inimigos.size(); j++) {
                if (tiros.get(i).colideCom(inimigos.get(j))) {
                    
                    explosoes.add(new Explosao(imagemExplosao, inimigos.get(j).getX(), inimigos.get(j).getY()));

                    inimigos.remove(j);
                    j--;
    
                    tiros.remove(i);
                    break;
                }
            }
            }
        }

        for (int i = 0; i< inimigos.size(); i++) {
            if (inimigos.get(i).getX() <= 0 || inimigos.get(i).getX() >= MainJogo.monitor.getWidth() - 50) {
                
                for (int j = 0; j < inimigos.size(); j++) {
                    inimigos.get(j).trocaDirecao();
                }
                break;
            }
        }

        for (int i = 0; i< explosoes.size(); i++) {
            explosoes.get(i).atualizar();

            if (explosoes.get(i).acabou()) {
                explosoes.remove(i);
            }
        }


    }

    int x = 0;
    public void paintComponent(Graphics g2) {
        super.paintComponent(g2); //limpar a tela
        
        //copia e cola da internet - para ligar o anti aliasing
        Graphics2D g = (Graphics2D) g2.create(); 
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


            fundo.pinta(g);

            for (int i = 0; i< explosoes.size(); i++) {
                explosoes.get(i).pintar(g);
            }

    //    g.setColor(Color.RED);
    //    g.fillRect( 600, 400, 3, 15);

    for (int i = 0; i< inimigos.size(); i++) {
        inimigos.get(i).pintar(g);
    }
        nave.pintar(g);

        for (int i = 0; i < tiros.size(); i++) {
            tiros.get(i).pintar(g);
        }

        if (ganhou) {
            g.setColor(Color.white);
            // g.setFont(minhaFonte);
            g.drawString("VOCÊ TERMINOU O JOGO!!\n\nFechando em " + fechandoEm + " segundos", MainJogo.monitor.getWidth()/2 - 200, MainJogo.monitor.getHeight()/2);
            
            fechandoEm -= 0.01666f;
            if (fechandoEm <=0) {
                System.exit(0);
            }
        }

        if (perdeu) {
            g.setColor(Color.white);
            // g.setFont(minhaFonte);
            g.drawString("VOCÊ PERDEU O JOGO!!!\n\nFechando em " + fechandoEm + " segundos", MainJogo.monitor.getWidth()/2 - 200, MainJogo.monitor.getHeight()/2);
            
            fechandoEm -= 0.01666f;
            if (fechandoEm <=0) {
                System.exit(0);
            }
        }
    }


    private void dorme(long duracao) {
        try {
            Thread.sleep(duracao);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
       
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            direcao = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            direcao = -1;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && nave.podeAtirar) {
            tiros.add(nave.atirar());
        }
        
        if (e.getKeyCode() == KeyEvent.VK_V) {
            ganhou = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            perdeu = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            direcao = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            direcao = 0;
        }
       

    }
}
