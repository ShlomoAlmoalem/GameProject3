package org.example;

import javax.swing.ImageIcon;
import java.util.Objects;

public class GoldenFruit extends FallingObject {

    public GoldenFruit(int x, int y) {
        super(x, y);
        loadImage();
    }

    private void loadImage() {
        image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/goldenFruit.png"))).getImage();
    }
}