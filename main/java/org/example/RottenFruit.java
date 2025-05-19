package org.example;
import javax.swing.ImageIcon;
import java.util.Objects;
import java.util.Random;
public class RottenFruit extends FallingObject {

    private String type;

    public RottenFruit(int x, int y) {
        super(x, y);
        Random rand = new Random();
        if (rand.nextBoolean()) {
            type = "banana";
            loadImage("rottenBanana.png");
        } else {
            type = "apple";
            loadImage("rottenApple.png");
        }
    }

    private void loadImage(String filename) {
        image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/" + filename))).getImage();
    }

    public String getType() {
        return type;
    }
}