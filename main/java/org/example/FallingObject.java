package org.example;
import java.awt.*;

public class FallingObject {
    int x, y, size = 20;
    int speed = 3;

    public FallingObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}

