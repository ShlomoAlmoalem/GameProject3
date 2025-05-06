package org.example;
import java.awt.*;

public class Basket {
    int x, y, width = 60, height = 20;

    public Basket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx) {
        x += dx;
        if (x < 0) x = 0;
        if (x + width > 600) x = 600 - width;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
