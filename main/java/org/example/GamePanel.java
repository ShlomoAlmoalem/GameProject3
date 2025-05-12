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
    Image backgroundImage;
    JLabel scoreLabel;


    public GamePanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        basket = new Basket(270, 350);
        objects = new CopyOnWriteArrayList<>();
        fullHeart = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/fullHeart.png"))).getImage();
        emptyHert = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/emptyHeart.png"))).getImage();
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/bg.png"))).getImage(); // טעינת תמונת הרקע
        scoreLabel=new JLabel("Score: " +score);
        scoreLabel.setFont(new Font("Impact",Font.PLAIN,24));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setBounds(0,50,200,30);
        this.add(scoreLabel);

        SwingUtilities.invokeLater(this::requestFocusInWindow);

    }
    public void startGameLoop() {
        Thread gameThread = new Thread(() -> {
            long lastDrop = System.currentTimeMillis();

            while (running) {
                // תנועה
                if (left) basket.move(-9);
                if (right) basket.move(9);

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
                        if (o instanceof GoldenFruit) {
                            lives = Math.min(lives + 1, 3);
                        } else if (o instanceof RottenFruit) {
                            score = Math.max(0, score - 5); // מוריד 5 נקודות, מינימום 0
                            lives--;
                            if (lives == 0) {
                                running = false;
                                SwingUtilities.invokeLater(() -> {
                                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                                    topFrame.setContentPane(new EndScreen(topFrame, score));
                                    topFrame.revalidate();
                                    topFrame.repaint();
                                });
                            }
                        } else {
                            score++;
                        }
                    } else if (o.y > getHeight()) {
                        objectsToRemove.add(o);
                        // בודקים אם זה לא פרי זהב או רקוב ואז מורידים חיים
                        if (!(o instanceof GoldenFruit) && !(o instanceof RottenFruit)) {
                            lives--;
                            if (lives == 0) {
                                running = false;
                                SwingUtilities.invokeLater(() -> {
                                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                                    topFrame.setContentPane(new EndScreen(topFrame, score));
                                    topFrame.revalidate();
                                    topFrame.repaint();
                                    });
                            }
                        }
                    }
                }
                objects.removeAll(objectsToRemove);
// יצירת עצמים חדשים כל 1 שנייה
                if (System.currentTimeMillis() - lastDrop > 700) {
                    int randomValue = rand.nextInt(12); // הגדלתי את טווח האקראיות
                    if (randomValue == 0) { // סיכוי של 1 ל-20 ליצור פרי זהב
                        objects.add(new GoldenFruit(rand.nextInt(570), 0));
                    } else if (randomValue >= 1 && randomValue <= 2) { // סיכוי של 2 ל-20 (1 ל-10) ליצור פרי רקוב
                        objects.add(new RottenFruit(rand.nextInt(570), 0));
                    } else {
                        objects.add(new FallingObject(rand.nextInt(570), 0));
                    }
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
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // ציור תמונת הרקע
        basket.draw(g);
        for (FallingObject o : objects) {
            o.draw(g);
        }
        g.setColor(Color.CYAN);
        for (int i = 0; i < basket.getMaxHp(); i++) {
            if (lives > i) {
                g.drawImage(fullHeart, 40 * i, 30, 40, 40, this);
            } else {
                g.drawImage(emptyHert, 40 * i, 30, 40, 40, this);
            }
        }
        scoreLabel.setText("Score: "+score);
        this.repaint();
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