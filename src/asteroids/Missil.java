package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class Missil extends Elemento {

    final double vmissil = 14;
    int tempvida = 0;
    Sons s = new Sons();
    public Missil(double x, double y, double angulo) {
        super(x, y, 0, 0, 4, true);
        vx = vmissil * Math.sin(-angulo) * -1;
        vy = vmissil * Math.cos(-angulo) * -1;
    }

    @Override
    public void paint(Graphics2D g2) {
        tempvida++;
        if (tempvida >= 30) {
            Universo.getInstance().remover.add(this);
        }
        g2.setColor(Color.white);
        g2.fillOval((int) x - 2, (int) y + 2, 4, 4);
    }

    @Override
    public boolean testaColisao(Elemento other) {
        List<Elemento> lista = Universo.getInstance().listaDeElementos;
        for (Elemento show : lista) {
            if (show instanceof Asteroide) {
                double soma = Math.pow((show.x - this.x), 2) + Math.pow((show.y - this.y), 2);
                double d = Math.sqrt(soma);
                if (d <= (this.raio + show.raio)) {
                    show.vivo = false;
                    if(show.raio == 25){
                        if(!Universo.getInstance().f){
                            s.explosaoSound.play();
                        }
                        Universo.getInstance().score += 1;
                        show.raio = 10;
                        Asteroide asteroid = new Asteroide(show.x, show.y, -show.vx, -show.vy, 10, true);
                        Universo.getInstance().adicionar.add(asteroid);
                    }else{
                        if(!Universo.getInstance().f){
                            s.explosaoSound.play();
                        }
                        Universo.getInstance().score += 5;
                        Universo.getInstance().remover.add(show);
                        Universo.getInstance().criaAsteroid((int)(show.x*2.6), (int)(show.y*2.9));
                    }
                    Universo.getInstance().remover.add(this);
                    return true;
                }
            }
        }
        return false;
    }
}
