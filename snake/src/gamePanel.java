import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.security.cert.TrustAnchor;
import java.util.Random;

import javax.swing.JPanel;

public class gamePanel extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;

    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    final int x[] = new int[NUMBER_OF_UNITS];
    final int y[] = new int[NUMBER_OF_UNITS];

    int length = 5;
    int foodEaten;
    int foodX;
    int foodY;
    char direction = 'D';
    boolean running = false;
    Random random;
    Timer timer;

    gamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        play();
    }

    private void play() {
        addFood();
        running = true;

        timer = new Timer(80, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void move(){
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (direction == 'L'){
            x[0] = x[0] - UNIT_SIZE;
        }else if (direction == 'R'){
            x[0] = x[0] + UNIT_SIZE;
        }else if (direction == 'U'){
            y[0] = y[0] - UNIT_SIZE;
        }else
            y[0] = y[0] + UNIT_SIZE;
    }

    public void chcekFood(){
        if(x[0] == foodX && y[0] == foodY){
            length++;
            foodEaten++;
            addFood();
        }
    }

    public void draw(Graphics graphics){
        if(running){
            graphics.setColor(Color.orange);
            graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            graphics.setColor(Color.red);
            graphics.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

            for (int i = 1; i < length; i++) {
                graphics.setColor(new Color(40, 200, 150));
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            graphics.setColor(Color.lightGray);
            graphics.setFont(new Font("Snas serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth(("Score: " + foodEaten))/2, graphics.getFont().getSize());
        }else{
            gameOver(graphics);
        }
    }

    private void addFood() {
        foodX = random.nextInt((int)(WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int)(HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }

    public void chcekHit(){
        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]){
                running = false;
            }
        }

        if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
        }
    }


    private void gameOver(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER", (WIDTH - metrics.stringWidth("GAME OVER")) / 2, HEIGHT / 2);

        graphics.setColor(Color.red);
        graphics
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter {
    }
}