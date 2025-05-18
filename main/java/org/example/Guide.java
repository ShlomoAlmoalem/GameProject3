package org.example;

import javax.swing.*;
import java.awt.*;

public class Guide extends JPanel {

    private Image backgroundImage;

    public Guide(JFrame frame) {
        setLayout(new BorderLayout());

        java.net.URL imageUrl = getClass().getResource("/Images/bg1.jpg");
        if (imageUrl != null) {
            backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Background image not found! Check path: /Images/bg1.jpg");
        }

        // אזור ההוראות
        JTextArea instructions = new JTextArea();
        instructions.setText("""
                FruitBasket - GUIDE

                Game objective: Collect as many fruits as possible with the basket

                Fruits: Each fruit increases a point

                Rotten fruits: Each rotten fruit decreases a heart and 5 points

                Golden apple: Each golden apple restores a heart

                Keys: Right arrow and left arrow

                You are a person who walked past the competition

                Game plot: Shay finished the semester and passed all the exams on time

                So he went with his children to pick fruits and he
                participates in a parent-child fruit picking competition
                Help Shay collect as many fruits as possible
                """);
        instructions.setFont(new Font("Impact", Font.BOLD, 22));
        instructions.setEditable(false);
        instructions.setBackground(new Color(240, 240, 240, 200));
        instructions.setMargin(new Insets(20, 20, 20, 20));
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        JScrollPane scrollPane = new JScrollPane(instructions);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JButton backButton;
        java.net.URL backButtonIconUrl = getClass().getResource("/Images/btmB.png");

        if (backButtonIconUrl != null) {
            ImageIcon backIcon = new ImageIcon(backButtonIconUrl);
            Image scaledBackImage = backIcon.getImage().getScaledInstance(150, 45, Image.SCALE_SMOOTH);
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

        backButton.addActionListener(e -> {
            frame.setContentPane(new MenuScreen(frame));
            frame.revalidate();
            frame.repaint();

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}