package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpaceInvaders extends JPanel implements Runnable, KeyListener {
    
    private Nave nave;
    private int direcao = 0;
    private ArrayList<Tiro> tiros;
    private ArrayList<Inimigo> inimigos;
    private PlanoDeFundo fundo;

    //CONSTRUTOR - é chamado quando fazemos o new SpaceInvaders();
    public SpaceInvaders() {

        nave = new Nave();
        tiros = new ArrayList<Tiro>();
        inimigos = new ArrayList<Inimigo>();
        fundo = new PlanoDeFundo();

        BufferedImage imagemInimigo = null;
        try {
            imagemInimigo = ImageIO.read(new File("imagens/inimigo.png"));
        } catch (IOException e) {
            System.out.println("Não foi possível carregar a imagem do inimigo");
            e.printStackTrace();
        }

        for (int i = 0; i< 60; i++) {
            inimigos.add(new Inimigo(imagemInimigo ,50 + i%20 * 50, 50 + i/20 * 50, 1));
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

        nave.movimenta(direcao);

        for (int i = 0; i< inimigos.size(); i++) {
        inimigos.get(i).atualizar();
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

    //    g.setColor(Color.RED);
    //    g.fillRect( 600, 400, 3, 15);

    for (int i = 0; i< inimigos.size(); i++) {
        inimigos.get(i).pintar(g);
    }
        nave.pintar(g);

        for (int i = 0; i < tiros.size(); i++) {
            tiros.get(i).pintar(g);
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
