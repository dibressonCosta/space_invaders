package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Alien extends Element {

    Object lock = new Object();
    
    public Alien(double x, double y, double vx, double vy, int width, int height, boolean vivo) {
        super(x, y, vx, vy, width, height);
    }

    @Override
    public void paint(Graphics2D g2) {
        g2.setColor(Color.white);
        

    }
    ArrayList remover = new ArrayList();


}
