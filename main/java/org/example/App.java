package org.example;
import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Falling Objects");
        GamePanel panel = new GamePanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.startGameLoop(); // הפעלת הלולאה
    }
}
