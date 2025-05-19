package org.example;
import java.awt.*;
import javax.swing.ImageIcon;
import java.util.Objects;

public class Basket {
    int x, y, width = 280, height = 350;
    int speed = 9;
    Image[] walkingRightFrames;
    Image[] walkingLeftFrames;
    int currentFrameIndex = 0;
    int frameDelay = 5;
    int frameCounter = 0;
    Image currentImage;
    Image standingRightImage;
    Image standingLeftImage;
    String currentDirection = "";

    public Basket(int x, int y) {
        this.x = x;
        this.y = y;

        walkingRightFrames = new Image[5];
        for (int i = 0; i < walkingRightFrames.length; i++) {
            walkingRightFrames[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/Walk" + (i + 1) + ".png"))).getImage();
        }
        standingRightImage = walkingRightFrames[0];

        walkingLeftFrames = new Image[5];
        for (int i = 0; i < walkingLeftFrames.length; i++) {
            walkingLeftFrames[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/walkleft" + (i + 1) + ".png"))).getImage();
        }
        standingLeftImage = walkingLeftFrames[0];

        currentImage = standingRightImage;
    }

    public void move(int dx) {
        x += dx;
        x = Math.max(-100, Math.min(x, 700 - width));
    }

    public void update() {
        if (currentDirection.equals("right")) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrameIndex++;
                if (currentFrameIndex >= walkingRightFrames.length) {
                    currentFrameIndex = 0;
                }
                currentImage = walkingRightFrames[currentFrameIndex];
            }
        } else if (currentDirection.equals("left")) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrameIndex++;
                if (currentFrameIndex >= walkingLeftFrames.length) {
                    currentFrameIndex = 0;
                }
                currentImage = walkingLeftFrames[currentFrameIndex];
            } else {

                if (currentDirection.equals("right") && walkingRightFrames.length > 0) {
                    currentImage = standingRightImage;
                } else if (currentDirection.equals("left") && walkingLeftFrames.length > 0) {
                    currentImage = standingLeftImage;
                }
            }
        } else {

            if (currentDirection.equals("right") && walkingRightFrames.length > 0) {
                currentImage = standingRightImage;
            } else if (currentDirection.equals("left") && walkingLeftFrames.length > 0) {
                currentImage = standingLeftImage;
            } else if (walkingRightFrames.length > 0) {
                currentImage = standingRightImage;
            }
        }
    }

    public Rectangle getBounds() {

        int hitboxWidth = width / 2;
        int hitboxHeight = height / 2;


        int hitboxX = x + (hitboxWidth) -100 ;
        int hitboxY = y + (hitboxHeight) ;

        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public void draw(Graphics g) {
        g.drawImage(currentImage, x, y, width, height, null);
    }

    public int getMaxHp() {
        return 3;
    }
}