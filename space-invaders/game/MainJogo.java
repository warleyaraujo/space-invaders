package game;

import javax.swing.JFrame;
import java.awt.*;

public class MainJogo {
    
    static DisplayMode monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

     public static void main(String[] args) {
        
        JFrame janela = new JFrame("Space Invaders");
        // janela.setSize(1366, 768);
        // janela.setUndecorated(true);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setLayout(null);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpaceInvaders invasaoAlienigena = new SpaceInvaders();
        invasaoAlienigena.setBounds(0, 0, monitor.getWidth(), monitor.getHeight());

        janela.add(invasaoAlienigena);

        janela.addKeyListener(invasaoAlienigena);
        janela.setVisible(true);

      
    }
}
