package asteroids;

import java.awt.Graphics2D;

public abstract class Elemento {

    double x, y, vx, vy, raio;
    boolean vivo;

    public Elemento(double x, double y, double vx, double vy, double raio, boolean vivo) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.raio = raio;
        this.vivo = vivo;

    }

    public void atualizaPosicao(int w, int h) {
        if (this.x >= w) {
            x -= w;
        } else if (this.x < 0) {
            x += w;
        }
        if (this.y >= h) {
            y -= h;
        } else if (this.y < 0) {
            y += h;
        }
    }

    public boolean testaColisao(Elemento other) {
        
        return false;
    }
        
    public void paint(Graphics2D g2) {

    }
}
