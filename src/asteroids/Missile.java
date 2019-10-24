package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class Missile extends Element {
    
    static int height = 10;
    static int width = 5;
    final double vmissil = 14;
    int tempvida = 0;
    
    boolean vivo = true;
    Sons s = new Sons();
    public Missile(double x, double y, double vy) {
        super(x, y, 0, vy, 0, 0);
    }

    @Override
    public void paint(Graphics2D g2) {
        tempvida++;
        if (tempvida >= 30) {
            Universe.getInstance().remover.add(this);
        }
        g2.setColor(Color.white);
        g2.fillOval((int) x - 2, (int) y + 2, 4, 4);
    }

    @Override
    public boolean collide(Element other) {
        List<Element> lista = Universe.getInstance().listaDeElementos;
        for (Element show : lista) {
            if (show instanceof Alien) {
                double soma = Math.pow((show.x - this.x), 2) + Math.pow((show.y - this.y), 2);
                double d = Math.sqrt(soma);
                if (d <= (this.raio + show.raio)) {
                    show.vivo = false;
                    if(show.raio == 25){
                        if(!Universe.getInstance().f){
                            s.explosaoSound.play();
                        }
                        Universe.getInstance().score += 1;
                        show.raio = 10;
                        Alien asteroid = new Alien(show.x, show.y, -show.vx, -show.vy, 10, true);
                        Universe.getInstance().adicionar.add(asteroid);
                    }else{
                        if(!Universe.getInstance().f){
                            s.explosaoSound.play();
                        }
                        Universe.getInstance().score += 5;
                        Universe.getInstance().remover.add(show);
                        Universe.getInstance().criaAsteroid((int)(show.x*2.6), (int)(show.y*2.9));
                    }
                    Universe.getInstance().remover.add(this);
                    return true;
                }
            }
        }
        return false;
    }
}
