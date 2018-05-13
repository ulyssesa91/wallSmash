package wallSmash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    //private final Timer timer;
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 20;

    private int playerX = 310;

    private int ballposX = 300;
    private int ballposY = 300;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGen map;

    public Gameplay() {
        map = new MapGen(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 695, 600);

        // Drawing Map
        map.draw((Graphics2D)g);

        // Borders
        g.setColor(Color.blue);
        g.fillRect(1, 0, 3, 600);
        g.fillRect(0, 1, 692, 3);
        g.fillRect(683, 0, 3, 592);

        // Scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);

        // The Paddle
        g.setColor(Color.orange);
        g.fillRect(playerX, 550, 100, 8);

        // The Ball
        g.setColor(Color.green);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Start", 250,350);
        }

        if(ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER, Score:", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Start", 250,350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.BWidth + 80;
                        int brickY = i * map.BHeight + 50;
                        int BWidth = map.BWidth;
                        int BHeight = map.BHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, BWidth, BHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }

                ballposX += ballXdir;
                ballposY += ballYdir;
                if (ballposX < 0) {
                    ballXdir = -ballXdir;
                }
                if (ballposY < 0) {
                    ballYdir = -ballYdir;
                }
                if (ballposX > 670) {
                    ballXdir = -ballYdir;
                }
            }
            repaint();

        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGen(3, 7);

                repaint();
            }
        }
        }
        public void moveRight () {
            play = true;
            playerX += 20;
        }
        public void moveLeft () {
            play = true;
            playerX -= 20;
        }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

