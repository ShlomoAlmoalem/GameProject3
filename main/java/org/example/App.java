package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class App {
    public static void main(String[] args) {

        JFrame frame = new JFrame("FruitBasket");
        GamePanel panel = new GamePanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);

        Image gameIcon = new ImageIcon(Objects.requireNonNull(App.class.getResource("/images/game_banner.png"))).getImage();

        frame.setIconImage(gameIcon);
        frame.pack();
        frame.setLocationRelativeTo(null);
        MenuScreen menu = new MenuScreen(frame);
        frame.setContentPane(menu);
        frame.setVisible(true);
    }
}