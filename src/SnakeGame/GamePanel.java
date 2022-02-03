package SnakeGame;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final  int x[] = new int[GAME_UNITS];
    final  int y[] = new int[GAME_UNITS];
    int BodyParts = 6;
    int ApplesEaten;
    int AppleX;
    int AppleY;
    char Direction = 'R';
    boolean Running = false;
    Timer Timer;
    Random Random;


    GamePanel(){
        Random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(7,5,68));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();
    }
    public void StartGame(){
        NewApple();
        Running = true;
        Timer = new Timer(DELAY,this);
        Timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Draw(g);
        this.setForeground(Color.white);
    }
    public void Draw(Graphics g) {
        if (Running) {
            g.setColor(new Color(250,193,64));
            g.fillOval(AppleX, AppleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < BodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.green);
                    g.setColor(new Color(Random.nextInt(255),Random.nextInt(1),Random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(new Color(224,68,131));
            g.setFont(new Font("Century Gothic",Font.BOLD,20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score : "+ApplesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score :"+ApplesEaten))/2, g.getFont().getSize());
            }
        else {
            GameOver(g);
        }
    }
    public void NewApple(){
        AppleX = Random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        AppleY = Random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void Move(){
        for (int i = BodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (Direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void CheckApple(){
        if ((x[0] == AppleX) && (y[0] == AppleY)){
            BodyParts = BodyParts + 1;
            ApplesEaten++;
            NewApple();
        }

    }
    public void CheckCollisions(){
        for (int i = BodyParts; i > 0; i--){
            if ((x[0]== x[i]) && (y[0] == y[i])){
                Running = false;
            }
        }
        if (x[0] < 0){
            Running = false;
        }
        if (x[0] > SCREEN_WIDTH){
            Running = false;
        }
        if (y[0] < 0) {
            Running = false;
        }
        if (y[0] > SCREEN_WIDTH){
            Running = false;
        }
        if (!Running){
            Timer.stop();
        }
    }
    public void GameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Century Gothic",Font.BOLD,20));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score : "+ApplesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score :"+ApplesEaten))/2, g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("Century Gothic", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (Running){
            Move();
            CheckApple();
            CheckCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        protected MyKeyAdapter() {
            super();
        }

        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (Direction != 'R'){
                        Direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (Direction != 'L'){
                        Direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (Direction != 'D'){
                        Direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (Direction != 'U'){
                        Direction = 'D';
                    }
                    break;
            }

        }
    }

}
