package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Nave extends Elemento implements KeyListener {

    boolean keyRightPressed;
    boolean keyLeftPressed;
    boolean keyUpPressed;
    boolean keyDownPressed;
    boolean keySpacePressed;
    double angulo;
    int vertices = 0;
    int vida;
    double xti, yti;
    static int tempoMinimoEntreTiros = 7;
    boolean f = false;
    Sons s = new Sons();

    public Nave(double angulo, int vida,
            double x, double y, double vx, double vy, double raio, boolean vivo) {
        super(x, y, vx, vy, raio, vivo);
        this.angulo = angulo;
        this.vida = vida;
        this.raio = 13;
    }

    public void timeLapse() {
        if (keyRightPressed) {
            angulo += 0.1;
        }
        if (keyLeftPressed) {
            angulo -= 0.1;
        }
        if (keyUpPressed) {
            velocidade(0.2);
        }
        if (keyDownPressed) {
            velocidade(-0.2);
        }
        if (keySpacePressed) {
            if (tempoMinimoEntreTiros >= 7) {
                shoot();
                tempoMinimoEntreTiros = 0;
            }
        }
        if (f) {
            angulo += 0.2;
            keySpacePressed = true;
            tempoMinimoEntreTiros = 6;
        }
    }

    public void shoot() {
        xti = x + (26 * Math.sin(angulo));
        yti = y - (26 * Math.cos(angulo));
        if (!f) {
            s.laserSound.play();
        }
        Missil tiro = new Missil(this.xti, (this.yti - 13), angulo);
        Universo.getInstance().adicionar.add(tiro);
    }

    public void velocidade(double v) {
        vx += v * Math.sin(angulo);
        vy -= v * Math.cos(angulo);
        int vmax = 7;
        vx = Math.min(vx, vmax);
        vy = Math.min(vy, vmax);
        vx = Math.max(vx, -vmax);
        vy = Math.max(vy, -vmax);
    }

    public void fshow() {
        if (f == false) {
            f = true;
            Universo.getInstance().f = true;
            s.music(true);
        } else if (f == true) {
            f = false;
            keySpacePressed = false;
            Universo.getInstance().f = false;
            s.music(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                keyRightPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                keyLeftPressed = true;
                break;
            case KeyEvent.VK_UP:
                keyUpPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                keyDownPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                keySpacePressed = true;
                break;
            case KeyEvent.VK_F:
                fshow();
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                keyRightPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                keyLeftPressed = false;
                break;
            case KeyEvent.VK_UP:
                keyUpPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                keyDownPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                keySpacePressed = false;
                break;
        }
    }

    Random random = new Random();

    public Color getRandomColor() {
        int red = random.nextInt(256);
        int blue = random.nextInt(256);
        int green = random.nextInt(256);
        return new Color(red, green, blue);
    }
    int c = 0;

    @Override
    public void paint(Graphics2D g2) {
        tempoMinimoEntreTiros++;
        if (c < 60) {
            c = c + 1;
            g2.setColor(getRandomColor());

        } else {
            g2.setColor(Color.white);
        }
        g2.rotate(angulo, x, y);
        g2.drawPolygon(new int[]{(int) x - 10, (int) x, (int) x + 10},
                new int[]{(int) y + 13, (int) y - 13, (int) y + 13},
                3);
        g2.rotate(-angulo, x, y);
    }

    @Override
    public boolean testaColisao(Elemento other) {
        double soma = Math.pow((other.x - this.x), 2) + Math.pow((other.y - this.y), 2);
        double d = Math.sqrt(soma);
        if (f) {
            return false;
        }
        if (other instanceof Missil) {
            return false;
        }
        if (d <= (this.raio + other.raio)) {
            double reta1 = -2.6 * ((other.x) - (this.x - 10)) + (this.y + 13);
            double reta2 = 2.6 * ((other.x) - (this.x + 10)) - (this.y + 13);
            double reta3 = 1 * (other.x - this.x) + (this.y + 13);
            if (other.y > (int) reta1) {
                if (other.y < (int) -reta2) {
                    if (other.y < (int) reta3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
