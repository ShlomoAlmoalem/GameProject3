package org.example;
import java.awt.*;
import javax.swing.ImageIcon;

public class Basket {
    int x, y, width = 125, height = 80;
    int speed = 8;
    Image basketImage; // משתנה שיכיל את התמונה של הסל

    public Basket(int x, int y) {
        this.x = x;
        this.y = y;
        basketImage = new ImageIcon(getClass().getResource("/images/basket.png")).getImage(); // טוען את התמונה של הסל
    }

    public void move(int dx) {
        x += dx;
        if (x < 0) {
            x = 0;
        } else if (x > 600 - width) {
            x = 600 - width;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.drawImage(basketImage, x, y, width, height, null); // מצייר את התמונה של הסל
    }

    public int getMaxHp() {
        return 3;
    }
}