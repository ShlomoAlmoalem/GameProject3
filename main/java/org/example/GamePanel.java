package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel implements KeyListener {

    Basket basket;
    CopyOnWriteArrayList<FallingObject> objects;
    Random rand = new Random();
    boolean left, right;
    int score = 0;
    int lives = 3;
    boolean running = true;
    Image fullHeart;
    Image emptyHert;

    public GamePanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        basket = new Basket(270, 350);
        objects = new CopyOnWriteArrayList<>();
        fullHeart = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/fullHeart.png"))).getImage();
        emptyHert = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/emptyHeart.png"))).getImage();
    }

    public void startGameLoop() {
        Thread gameThread = new Thread(() -> {
            long lastDrop = System.currentTimeMillis();

            while (running) {
                // תנועה
                if (left) basket.move(-8);
                if (right) basket.move(8);

                // עדכון עצמים
                for (FallingObject o : objects)
                    o.update();

                // פגיעה בסל
                List<FallingObject> objectsToRemove = new ArrayList<>();
                for (FallingObject o : objects) {
                    Rectangle basketBounds = basket.getBounds();
                    Rectangle objectBounds = o.getBounds();

                    if (objectBounds.intersects(basketBounds)) {
                        objectsToRemove.add(o);
                        score++;
                    } else if (o.y > getHeight()) {
                        objectsToRemove.add(o);
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
                objects.removeAll(objectsToRemove);

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
        for (FallingObject o : objects) {
            o.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        for (int i = 0; i < basket.getMaxHp(); i++) {
            if (lives > i) {
                g.drawImage(fullHeart, 40 * i, 30, 40, 40, this);
            } else {
                g.drawImage(emptyHert, 40 * i, 30, 40, 40, this);
            }
        }
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

    @Override
    public void keyTyped(KeyEvent e) {
    }
}