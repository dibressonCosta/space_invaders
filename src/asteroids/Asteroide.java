package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Asteroide extends Elemento {

    Object lock = new Object();
    
    public Asteroide(double x, double y, double vx, double vy, double raio, boolean vivo) {
        super(x, y, vx, vy, raio, vivo);

    }

    @Override
    public void paint(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.drawOval((int) x, (int) y, (int) raio, (int) raio);

    }
    ArrayList remover = new ArrayList();


}
