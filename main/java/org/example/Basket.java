package org.example;
import java.awt.*;

public class Basket {
    private int x, y, width = 60, height = 20;
    private int hp;
    private final int maxHp;
    public Basket(int x, int y) {
        this.x = x;
        this.y = y;
        maxHp=3;
        hp=5;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
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