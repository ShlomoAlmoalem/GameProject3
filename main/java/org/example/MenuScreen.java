package org.example;

import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JPanel {

    private Sound soundScene;


    public MenuScreen(JFrame frame) {
        setLayout(new BorderLayout());

        this.soundScene = new Sound();
        this.soundScene.playSound("main/resources/sounds/GMmusic.wav"); // שינוי שם הקובץ

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/Images/MBg.jpg")));
        background.setLayout(new BorderLayout());
        add(background);

        JPanel overlayPanel = new JPanel();
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("FruitBasket", SwingConstants.CENTER);
        title.setFont(new Font("Impact", Font.BOLD, 50));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlayPanel.add(title);
        overlayPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        java.net.URL playButtonIconUrl = getClass().getResource("/Images/PlayB.png");
        JButton startButton;
        if (playButtonIconUrl != null) {
            ImageIcon playIcon = new ImageIcon(playButtonIconUrl);
            Image scaledPlayImage = playIcon.getImage().getScaledInstance(150, 45, Image.SCALE_SMOOTH); // 200x60 פיקסלים
            playIcon = new ImageIcon(scaledPlayImage);
            startButton = new JButton(playIcon);
            startButton.setBorderPainted(false);
            startButton.setContentAreaFilled(false);
            startButton.setFocusPainted(false);
        } else {
            startButton = new JButton("Play");
        }
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlayPanel.add(startButton);
        overlayPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        java.net.URL guideButtonIconUrl = getClass().getResource("/Images/GuideB.png");
        JButton GuiButton;
        if (guideButtonIconUrl != null) {
            ImageIcon guideIcon = new ImageIcon(guideButtonIconUrl);
            Image scaledGuideImage = guideIcon.getImage().getScaledInstance(150, 45, Image.SCALE_SMOOTH); // 200x60 פיקסלים
            guideIcon = new ImageIcon(scaledGuideImage);
            GuiButton = new JButton(guideIcon);
            GuiButton.setBorderPainted(false);
            GuiButton.setContentAreaFilled(false);
            GuiButton.setFocusPainted(false);
        } else {
            GuiButton = new JButton("Guide");
        }
        GuiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlayPanel.add(GuiButton);
        overlayPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        java.net.URL exitButtonIconUrl = getClass().getResource("/Images/ExitB.png");
        JButton exitButton;
        if (exitButtonIconUrl != null) {
            ImageIcon exitIcon = new ImageIcon(exitButtonIconUrl);
            Image scaledExitImage = exitIcon.getImage().getScaledInstance(150, 45, Image.SCALE_SMOOTH); // 200x60 פיקסלים
            exitIcon = new ImageIcon(scaledExitImage);
            exitButton = new JButton(exitIcon);
            exitButton.setBorderPainted(false);
            exitButton.setContentAreaFilled(false);
            exitButton.setFocusPainted(false);
        } else {
            exitButton = new JButton("Exit");
        }
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlayPanel.add(exitButton);

        background.add(overlayPanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            GamePanel gamePanel = new GamePanel();
            frame.setContentPane(gamePanel);
            frame.revalidate();
            frame.repaint();
            soundScene.stopPlay(); // עצירת מוזיקת התפריט
            gamePanel.startGameLoop();
        });

        GuiButton.addActionListener(e -> {
            frame.setContentPane(new Guide(frame));
            frame.revalidate();
            frame.repaint();
            soundScene.stopPlay();
        });

        exitButton.addActionListener(e -> {
            soundScene.stopPlay(); // עצירת מוזיקת התפריט (למרות שהתוכנית נסגרת)
            System.exit(0);
        });

        sceneGame();
    }

    public void sceneGame() {

        new Thread(()-> {

            this.soundScene.startBackgroundPlay();
            this.soundScene.loopPlay(); // הפעלת לולאה למוזיקת התפריט

        }).start();

    }
}