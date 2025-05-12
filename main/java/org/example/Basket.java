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
    int frameDelay = 5; // מספר טיקים לפני החלפת פריים
    int frameCounter = 0;
    Image currentImage;
    Image standingRightImage; // הפריים הראשון של הליכה ימינה
    Image standingLeftImage;  // הפריים הראשון של הליכה שמאלה
    String currentDirection = ""; // "left", "right", או "" (עומד)

    public Basket(int x, int y) {
        this.x = x;
        this.y = y;

        walkingRightFrames = new Image[5];
        for (int i = 0; i < walkingRightFrames.length; i++) {
            walkingRightFrames[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/Walk" + (i + 1) + ".png"))).getImage();
        }
        standingRightImage = walkingRightFrames[0]; // הפריים הראשון להסתכל ימינה

        walkingLeftFrames = new Image[5];
        for (int i = 0; i < walkingLeftFrames.length; i++) {
            walkingLeftFrames[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/walkleft" + (i + 1) + ".png"))).getImage();
        }
        standingLeftImage = walkingLeftFrames[0]; // הפריים הראשון להסתכל שמאלה

        currentImage = standingRightImage; // ברירת מחדל - הסתכל ימינה בהתחלה
    }

    public void move(int dx) {
        x += dx;
        x = Math.max(-100, Math.min(x, 700 - width)); // 700 נשאר קבוע
    }

    public void update() {
        if (currentDirection.equals("right")) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrameIndex++;
                if (currentFrameIndex >= walkingRightFrames.length) {
                    currentFrameIndex = 0; // לולאה חוזרת
                }
                currentImage = walkingRightFrames[currentFrameIndex];
            }
        } else if (currentDirection.equals("left")) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrameIndex++;
                if (currentFrameIndex >= walkingLeftFrames.length) {
                    currentFrameIndex = 0; // לולאה חוזרת
                }
                currentImage = walkingLeftFrames[currentFrameIndex];
            } else {
                // אם לא זזים, הצג את תמונת העמידה האחרונה
                if (currentDirection.equals("right") && walkingRightFrames.length > 0) {
                    currentImage = standingRightImage;
                } else if (currentDirection.equals("left") && walkingLeftFrames.length > 0) {
                    currentImage = standingLeftImage;
                }
            }
        } else { // currentDirection is "" (עומד)
            // הצג את תמונת העמידה האחרונה
            if (currentDirection.equals("right") && walkingRightFrames.length > 0) {
                currentImage = standingRightImage;
            } else if (currentDirection.equals("left") && walkingLeftFrames.length > 0) {
                currentImage = standingLeftImage;
            } else if (walkingRightFrames.length > 0) {
                currentImage = standingRightImage; // ברירת מחדל לעמוד ימינה
            }
        }
    }

    public Rectangle getBounds() {
        // הגדר גודל קטן יותר עבור ה-hitbox
        int hitboxWidth = width / 2;   // חצי מהרוחב של הדמות
        int hitboxHeight = height / 2; // חצי מהגובה של הדמות

        // מרכז את ה-hitbox בתוך מיקום הדמות
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