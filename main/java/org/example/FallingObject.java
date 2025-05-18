package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FallingObject {
    int x, y, size = 42;
    int speed = 7;
    Image images[];
    Image image;

    private Sound fallSound;


    public FallingObject(int x, int y) {
//        this.fallSound = new Sound();
//        this.fallSound.playSound("נתיב שורק");

        Random rand = new Random();
        this.x = x;
        this.y = y;
        images = new Image[4];
        imagesMeathel();
        image = images[rand.nextInt(4)];
    }

    public void update() {
        y += speed;
//        this.fallSound.startPlay();
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y,size,size, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
    public void imagesMeathel(){
        for(int i = 1; i < images.length+1; i++){
            images[i-1] = new ImageIcon(getClass().getResource("/images/Fruit"+i+".png")).getImage();
        }
    }

}

