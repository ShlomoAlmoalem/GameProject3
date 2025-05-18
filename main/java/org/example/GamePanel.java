package org.example;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel implements KeyListener {

    private Sound soundScene;
    private Sound rottenSound;
    private Sound goldenAppleSound; // סאונד עבור תפוח זהב
    private Sound takingObjectSound; // סאונד עבור פרי רגיל
    private boolean gameSoundStarted = false; // משתנה לבדיקה האם סאונד המשחק התחיל

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 400;
    private static final int MAX_LIVES = 3;

    private final Basket basket;
    private final CopyOnWriteArrayList<FallingObject> objects = new CopyOnWriteArrayList<>();
    private final Random rand = new Random();

    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    private int score = 0;
    private int lives = MAX_LIVES;
    int highScore = 0;
    private boolean running = true;

    private final Image fullHeart;
    private final Image emptyHeart;
    private final Image backgroundImage;
    private final JLabel scoreLabel;
    JLabel highScoreLabel;
    File highScoreFile = new File("highscore.txt");

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        setLayout(null);
        addKeyListener(this);

        this.rottenSound = new Sound();
        this.rottenSound.playSound("main/resources/sounds/rottenFruit.wav");

        this.goldenAppleSound = new Sound(); // יצירת אובייקט סאונד עבור תפוח זהב
        this.goldenAppleSound.playSound("main/resources/sounds/GoldenApplesound.wav"); // טעינת הסאונד

        this.takingObjectSound = new Sound(); // יצירת אובייקט סאונד עבור פרי רגיל
        this.takingObjectSound.playSound("main/resources/sounds/TakinkObject.wav"); // טעינת הסאונד

        basket = new Basket(200, 100);
        fullHeart = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/fullHeart.png"))).getImage();
        emptyHeart = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/emptyHeart.png"))).getImage();
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/bg.png"))).getImage();

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setBounds(10, 5, 200, 30);
        this.add(scoreLabel);

        highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        highScoreLabel.setForeground(Color.BLACK);
        highScoreLabel.setBounds(400, 5, 200, 30);
        this.add(highScoreLabel);

        loadHighScore();
        highScoreLabel.setText("High Score: " + highScore);

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    public void startGameLoop() {
        if (!gameSoundStarted) {
            this.soundScene = new Sound();
            this.soundScene.playSound("main/resources/sounds/GPmusic.wav");
            this.soundScene.startBackgroundPlay();
            this.soundScene.loopPlay();
            gameSoundStarted = true;
        }
        Thread gameThread = new Thread(() -> {
            long lastDrop = System.currentTimeMillis();

            while (running) {
                handleInput();
                basket.update();

                for (FallingObject o : objects) {
                    o.update();
                }

                checkCollisions();

                if (System.currentTimeMillis() - lastDrop > 700) {
                    spawnObject();
                    lastDrop = System.currentTimeMillis();
                }

                repaint();

                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // עצור את סאונד המשחק כשהמשחק נגמר
            stopGameSound();
        });

        gameThread.start();
    }

    public void stopGameSound() {
        if (soundScene != null && gameSoundStarted) {
            soundScene.stopPlay();
            gameSoundStarted = false;
        }
    }

    private void handleInput() {
        if (leftKeyPressed) {
            basket.currentDirection = "left";
            basket.move(-9);
        } else if (rightKeyPressed) {
            basket.currentDirection = "right";
            basket.move(9);
        } else {
            basket.currentDirection = "";
        }
    }

    private void checkCollisions() {
        List<FallingObject> toRemove = new ArrayList<>();

        for (FallingObject o : objects) {
            Rectangle basketBounds = basket.getBounds();
            Rectangle objectBounds = o.getBounds();

            if (objectBounds.intersects(basketBounds)) {
                toRemove.add(o);
                handleCaughtObject(o);

            } else if (o.y > getHeight()) {
                toRemove.add(o);
                handleMissedObject(o);
            }
        }

        objects.removeAll(toRemove);
    }

    private void handleCaughtObject(FallingObject o) {
        if (o instanceof GoldenFruit) {
            lives = Math.min(lives + 1, MAX_LIVES);
            this.goldenAppleSound.startPlay(); // הפעלת סאונד עבור תפוח זהב
        } else if (o instanceof RottenFruit) {
            lives--;
            score = Math.max(0, score - 5);
            this.rottenSound.startPlay();
            if (lives <= 0) {
                running = false;
                SwingUtilities.invokeLater(this::showEndScreen);
            }
        } else if (o instanceof FallingObject) { // אם זה פרי רגיל (לא תפוח זהב ולא רקוב)
            score++;
            this.takingObjectSound.startPlay(); // הפעלת סאונד עבור פרי רגיל
        }
    }

    private void handleMissedObject(FallingObject o) {
        if (!(o instanceof GoldenFruit) && !(o instanceof RottenFruit)) {
            loseLife();
        }
    }

    private void loseLife() {
        lives--;
        if (lives == 0) {
            running = false;
            SwingUtilities.invokeLater(this::showEndScreen);
        }
    }

    private void showEndScreen() {
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.setContentPane(new EndScreen(topFrame, score));
        topFrame.revalidate();
        topFrame.repaint();
    }

    private void spawnObject() {
        int x = rand.nextInt(PANEL_WIDTH - 30);
        int r = rand.nextInt(20);

        if (r == 0) {
            objects.add(new GoldenFruit(x, 0));
        } else if (r <= 2) {
            objects.add(new RottenFruit(x, 0));
        } else {
            objects.add(new FallingObject(x, 0));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        basket.draw(g);
        for (FallingObject o : objects) {
            o.draw(g);
        }

        for (int i = 0; i < MAX_LIVES; i++) {
            Image heart = (lives > i) ? fullHeart : emptyHeart;
            g.drawImage(heart, 40 * i, 30, 40, 40, this);
        }

        scoreLabel.setText("Score: " + score);
        highScoreLabel.setText("High Score: " + highScore);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftKeyPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightKeyPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftKeyPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightKeyPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // לא בשימוש
    }

    private void loadHighScore() {
        try {
            if (highScoreFile.exists()) {
                Scanner scanner = new Scanner(highScoreFile);
                if (scanner.hasNextInt()) {
                    highScore = scanner.nextInt();
                }
                scanner.close();
            } else {
                highScoreFile.createNewFile();
                FileWriter writer = new FileWriter(highScoreFile);
                writer.write("0");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHighScore() {
        try {
            FileWriter writer = new FileWriter(highScoreFile);
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}