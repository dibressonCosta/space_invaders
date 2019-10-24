package asteroids;

import java.awt.Graphics2D;

public abstract class Element {

    public double x, y;
    public int width;
    public int height;
    private double vx, vy;

    public Element(double x, double y, double vx, double vy, int width, int height) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.width = width;
        this.height = height;
    }

    public void move() {
        x += vx;
        y += vy;
    }


    public void paint(Graphics2D g2) {
        g2.fillRect((int) x-width-2, (int)y - height-2, width, height);
    }
    public void update(){
        move();
    }
}
