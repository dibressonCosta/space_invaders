package asteroids;

import java.awt.Dimension;
import java.util.Random;
import javax.swing.JFrame;

public class TesteUniverso {

    static public void criaUniverso() {
        int w = 700;
        int h = 500;
        JFrame jf = new JFrame();
        jf.setSize(new Dimension(w, h));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Universo u = Universo.getInstance();

        jf.getContentPane().add(u);

        jf.setVisible(true);
        Thread t = new Thread(u);
        t.start();
    }
    

    public static void main(String[] listargs) {
        criaUniverso();

    }
}
