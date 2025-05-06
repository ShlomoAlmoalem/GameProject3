package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {

    Basket basket;
    ArrayList<FallingObject> objects;
    Random rand = new Random();
    boolean left, right;
    int score = 0;
    int lives = 3;
    boolean running = true;

    public GamePanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        basket = new Basket(270, 350);
        objects = new ArrayList<>();
    }

    public void startGameLoop() {
        Thread gameThread = new Thread(() -> {
            long lastDrop = System.currentTimeMillis();

            while (running) {
                // תנועה
                if (left) basket.move(-15);
                if (right) basket.move(15);

                // עדכון עצמים
                for (FallingObject o : objects)
                    o.update();

                // פגיעה בסל
                Iterator<FallingObject> it = objects.iterator();
                while (it.hasNext()) {
                    FallingObject o = it.next();
                    if (o.getBounds().intersects(basket.getBounds())) {
                        it.remove();
                        score++;
                    } else if (o.y > getHeight()) {
                        it.remove();
                        lives--;
                        if (lives == 0) {
                            running = false;
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(this, "Game Over!\nScore: " + score);
                                System.exit(0);
                            });
                        }
                    }
                }

                // יצירת עצמים חדשים כל 1 שנייה
                if (System.currentTimeMillis() - lastDrop > 1000) {
                    objects.add(new FallingObject(rand.nextInt(570), 0));
                    lastDrop = System.currentTimeMillis();
                }

                repaint();

                try {
                    Thread.sleep(16); // כ-60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        basket.draw(g);
        for (FallingObject o : objects)
            o.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
    }

    @Override public void keyTyped(KeyEvent e) {}
}

