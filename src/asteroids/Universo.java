package asteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class Universo extends JPanel implements Runnable, KeyListener {

    boolean keyEnterPressed;
    int score = 0;
    private static Universo singleton;
    private Nave nave;
    ArrayList<Elemento> remover = new ArrayList<>();
    ArrayList<Elemento> adicionar = new ArrayList<>();

    public static synchronized Universo getInstance() {
        if (singleton == null) {
            singleton = new Universo();
        }
        return singleton;
    }

    private Universo() {
        preencheUniverso();
    }
    
    public void preencheUniverso() {
        this.nave = new Nave(0, 3, 350, 250, 0, 0, 4, true);
        listaDeElementos = new ArrayList<>();
        this.setFocusable(true);
        this.addKeyListener(nave);
        listaDeElementos.add(nave);
        int w = 700;
        int h = 500;
        Random r = new Random(0);
        for (int i = 0; i < 10; i++) {
            double x = r.nextInt(w);
            double y = r.nextInt(h);
            double vx = r.nextDouble() * 2;
            double vy = r.nextDouble() * 2;
            double raio = 25;
            boolean vivo = true;
            addElemento(new Asteroide(x, y, vx, vy, raio, vivo));
        }
    }
    List<Elemento> listaDeElementos;
    boolean gameOver = false;
    long t = 0;

    public void addElemento(Elemento e) {
        listaDeElementos.add(e);
    }

    Object lock = new Object();

    Random random = new Random();

    public Color getRandomColor() {
        int red = random.nextInt(256);
        int blue = random.nextInt(256);
        int green = random.nextInt(256);
        return new Color(red, green, blue);
    }

    boolean f;

    public Font getFonte(int tamanho) {
        Font font = null;
        try {
            File file = Paths.get(getClass().getResource("/digital.ttf").toURI()).toFile();
            FileInputStream fis = new FileInputStream(file);
            font = Font.createFont(Font.TRUETYPE_FONT, fis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        font = font.deriveFont(Font.PLAIN, tamanho);
        return font;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        String cu = "SCORE: " + score;
        String life = "LIFE: " + nave.vida;
        int w = this.getWidth();
        int h = this.getHeight();
        if (f) {
            g2.setColor(getRandomColor());
            g2.fillRect(0, 0, w, h);
        } else {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, w, h);
        }
        Font incrivel = getFonte(30);

        g2.setFont(incrivel);
        g2.setColor(Color.white);
        if (nave.vida == 0) {
            g2.drawString("GAME OVER", 250, 250);
        }
        g2.drawString(cu, 5, 25);
        g2.drawString(life, 600, 25);

        synchronized (lock) {
            for (Elemento e : listaDeElementos) {
                e.paint(g2);
            }
        }
    }

    private void update() {
        t = t + 1;
        nave.timeLapse();
        for (Elemento e : listaDeElementos) {
            e.atualizaPosicao(this.getWidth(), this.getHeight());
            e.x += e.vx;
            e.y += e.vy;
            if (e instanceof Missil) {
                e.testaColisao(e);
            }
            if (e instanceof Asteroide) {
                if (e.testaColisao(e)) {
                    e.vx = -e.vx;
                    e.vy = -e.vy;
                }
            }
            if (t > 30) {
                if (e != nave) {
                    if (nave.testaColisao(e)) {
                        nave.x = 700 / 2;
                        nave.y = 500 / 2;
                        nave.vx = 0;
                        nave.vy = 0;
                        nave.vida -= 1;
                        t = 0;
                        nave.c = 0;
                        if (nave.vida == 0) {
                            gameOver = true;
                            remover.addAll(listaDeElementos);
                            if(keyEnterPressed){
                                System.out.println("top");
                                preencheUniverso();
                            }
                        }
                    }
                }
            }
            
        }
        synchronized (lock) {
            listaDeElementos.removeAll(remover);
            listaDeElementos.addAll(adicionar);
            adicionar.clear();
            remover.clear();

        }
        repaint();
    }
    
    @Override
    public void run() {
        while (!gameOver) {
            update();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void criaAsteroid(int xis, int ipeslon) {
        if (xis > 0 && ipeslon > 0) {
            Random r = new Random(0);
            double x = r.nextInt(xis);
            double y = r.nextInt(ipeslon);
            double vx = r.nextDouble() * 2;
            double vy = r.nextDouble() * 2;
            double raio = 25;
            boolean vivo = true;
            adicionar.add(new Asteroide(x, y, vx, vy, raio, vivo));
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {         
            case KeyEvent.VK_ENTER:
                System.out.println("enter");
                keyEnterPressed = true;
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }
}
