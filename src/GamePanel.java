import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Timer.*;

public class GamePanel extends JPanel implements ActionListener {

    Timer timer;
    Random random;
    final double x[] = new double[GAME_UNITS];
    final double y[] = new double[GAME_UNITS];
    final double a[] = new double[GAME_UNITS];
    final double b[] = new double[GAME_UNITS];
    final double c[] = new double[GAME_UNITS];
    final double d[] = new double[GAME_UNITS];
    static final int DELAY = 80;
    static final int SCREEN_WIDTH = 570;
    static final int SCREEN_HEIGHT = 570;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    char direction = 'O';
    double speed = 0.3;
    int score = 0;
    int miss = 0;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(165, 225, 250));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        timer = new Timer(DELAY, this);
        timer.start();
        y[0] = SCREEN_HEIGHT-(UNIT_SIZE)*6;
        x[0] = SCREEN_WIDTH/2;
        newObstacles();
        newRock();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 180, 0));
        g.fillRect(0,SCREEN_HEIGHT-(UNIT_SIZE)*5, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.setColor(new Color(140, 90, 40));
        g.fillRect((int)x[0], (int)y[0], UNIT_SIZE, UNIT_SIZE);
        g.setColor(Color.orange);
        g.fillOval((int)a[0], (int)b[0], UNIT_SIZE, UNIT_SIZE);
        g.setColor(Color.gray);
        g.fillRect((int)c[0], (int)d[0], UNIT_SIZE, UNIT_SIZE);
        move();
        obsMove();
        if(x[0] < 0){
            x[0] = 0;
        }
        if(x[0] > SCREEN_WIDTH-UNIT_SIZE){
            x[0] = SCREEN_WIDTH-UNIT_SIZE;
        }
        if(b[0] > SCREEN_HEIGHT+UNIT_SIZE){
            newObstacles();
            miss++;
        }
        if(d[0] > SCREEN_HEIGHT+UNIT_SIZE){
            newRock();
        }
        if (x[0]+(UNIT_SIZE/2) >= a[0] && x[0]+(UNIT_SIZE/2) <= a[0] + UNIT_SIZE && y[0]+(UNIT_SIZE/2) >= b[0] && y[0]+(UNIT_SIZE/2) <= b[0] + UNIT_SIZE){
            newObstacles();
            score++;
        }
        if (x[0]+(UNIT_SIZE/2) >= c[0] && x[0]+(UNIT_SIZE/2) <= c[0] + UNIT_SIZE && y[0]+(UNIT_SIZE/2) >= d[0] && y[0]+(UNIT_SIZE/2) <= d[0] + UNIT_SIZE){
            newRock();
            score -= 5;
        }


        g.setColor(Color.black);
        g.setFont(new Font("Calibri", Font.BOLD, 32));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+score+" Miss: "+miss, (SCREEN_WIDTH - metrics.stringWidth("Score: "+score+"  Miss: "+miss))/2, g.getFont().getSize()+UNIT_SIZE/3);
        repaint();
    }

    public void move(){
        switch (direction){
            case 'R':
                x[0] = x[0] + speed;
                break;
            case 'L':
                x[0] = x[0] - speed;
            case 'O':
                x[0] = x[0];
                break;
        }
    }

    public void newObstacles(){
        a[0] = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        b[0] = -UNIT_SIZE;
    }

    public void newRock(){
        c[0] = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        d[0] = -UNIT_SIZE;
    }

    public void obsMove(){
        b[0] = b[0] + speed+speed/3;
        d[0] = d[0] + speed*2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_RIGHT:
                    direction = 'R';
                    break;
                case KeyEvent.VK_LEFT:
                    direction = 'L';
            }
        }
    }

}
