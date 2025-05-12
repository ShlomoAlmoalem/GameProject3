package org.example;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel {
    public EndScreen(JFrame frame, int finalScore) {
        setLayout(new BorderLayout());

        // רקע
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/Images/EndBg.jpg")));
        background.setLayout(new BorderLayout());
        add(background);

        // לוח כפתורים
        JPanel overlayPanel = new JPanel();
        overlayPanel.setOpaque(false);
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        // טקסט Game Over
//        JLabel gameOver = new JLabel("GAME OVER", SwingConstants.CENTER);
//        gameOver.setFont(new Font("Impact", Font.BOLD, 50));
//        gameOver.setForeground(Color.RED);
//        gameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
//        overlayPanel.add(gameOver);
//
//        // ניקוד סופי
//        JLabel scoreLabel = new JLabel("Your Score: " + finalScore, SwingConstants.CENTER);
//        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 28));
//        scoreLabel.setForeground(Color.WHITE);
//        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        overlayPanel.add(Box.createRigidArea(new Dimension(0, 20)));
//        overlayPanel.add(scoreLabel);
//        overlayPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // כפתור שחק שוב
        java.net.URL playButtonIconUrl = getClass().getResource("/Images/EndReB.png");
        JButton ReStartButton;
        if (playButtonIconUrl != null) {
            ImageIcon playIcon = new ImageIcon(playButtonIconUrl);
            Image scaledPlayImage = playIcon.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH); // 200x60 פיקסלים
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

        // כפתור חזור לתפריט
        JButton backButton;
        java.net.URL backButtonIconUrl = getClass().getResource("/Images/EndBTMB.png");

        if (backButtonIconUrl != null) {
            ImageIcon backIcon = new ImageIcon(backButtonIconUrl);
            Image scaledBackImage = backIcon.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
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
            Image scaledExitImage = exitIcon.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH); // 200x60 פיקסלים
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

        // פעולות כפתורים
        ReStartButton.addActionListener(e -> {
            GamePanel gamePanel = new GamePanel();
            frame.setContentPane(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.startGameLoop();
        });

        backButton.addActionListener(e -> {
            frame.setContentPane(new MenuScreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        exitButton.addActionListener(e -> System.exit(0));
    }
}