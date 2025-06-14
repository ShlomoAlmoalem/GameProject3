package org.example;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel {

    private Sound endScreenSound;

    public EndScreen(JFrame frame, int finalScore) {
        setLayout(new BorderLayout());


        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/Images/EndBg.jpg")));
        background.setLayout(new BorderLayout());
        add(background);


        this.endScreenSound = new Sound();
        this.endScreenSound.playSound("main/resources/sounds/GOmusic.wav");


        JPanel overlayPanel = new JPanel();
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 60, 100));


        java.net.URL playButtonIconUrl = getClass().getResource("/Images/EndReB.png");
        JButton ReStartButton;
        if (playButtonIconUrl != null) {
            ImageIcon playIcon = new ImageIcon(playButtonIconUrl);
            Image scaledPlayImage = playIcon.getImage().getScaledInstance(170, 60, Image.SCALE_SMOOTH); // 200x60 פיקסלים
            playIcon = new ImageIcon(scaledPlayImage);
            ReStartButton = new JButton(playIcon);
            ReStartButton.setBorderPainted(false);
            ReStartButton.setContentAreaFilled(false);
            ReStartButton.setFocusPainted(false);
        } else {
            ReStartButton = new JButton("ReStart");
        }
        ReStartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlayPanel.add(ReStartButton);
        overlayPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JButton backButton;
        java.net.URL backButtonIconUrl = getClass().getResource("/Images/EndBTMB.png");

        if (backButtonIconUrl != null) {
            ImageIcon backIcon = new ImageIcon(backButtonIconUrl);
            Image scaledBackImage = backIcon.getImage().getScaledInstance(160, 60, Image.SCALE_SMOOTH);
            backIcon = new ImageIcon(scaledBackImage);
            backButton = new JButton(backIcon);
            backButton.setBorderPainted(false);
            backButton.setContentAreaFilled(false);
            backButton.setFocusPainted(false);
            backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            backButton.setToolTipText("Back to main menu");
        } else {
            backButton = new JButton("Back to Menu");
        }
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlayPanel.add(backButton);
        overlayPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        java.net.URL exitButtonIconUrl = getClass().getResource("/Images/EndEB.png");
        JButton exitButton;
        if (exitButtonIconUrl != null) {
            ImageIcon exitIcon = new ImageIcon(exitButtonIconUrl);
            Image scaledExitImage = exitIcon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH); // 200x60 פיקסלים
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


        ReStartButton.addActionListener(e -> {

            if (endScreenSound != null) {
                endScreenSound.stopPlay();
            }
            GamePanel gamePanel = new GamePanel();
            frame.setContentPane(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.startGameLoop();
        });

        backButton.addActionListener(e -> {

            if (endScreenSound != null) {
                endScreenSound.stopPlay();
            }

            Component[] components = frame.getContentPane().getComponents();
            for (Component component : components) {
                if (component instanceof GamePanel) {
                    ((GamePanel) component).stopGameSound();
                    break;
                }
            }
            frame.setContentPane(new MenuScreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        exitButton.addActionListener(e -> {

            if (endScreenSound != null) {
                endScreenSound.stopPlay();
            }
            System.exit(0);
        });


        new Thread(() -> {
            if (endScreenSound != null) {
                endScreenSound.startBackgroundPlay();
                endScreenSound.loopPlay();
            }
        }).start();
    }
}