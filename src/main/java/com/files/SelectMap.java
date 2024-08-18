package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SelectMap {
    JPanel mainPanel;
    JLabel resultLabel;
    Random random;
    Map[] maps = MainWindow.getMaps();

    public SelectMap() {
        random = new Random();
        createComponents();
    }

    private void createComponents() {
        mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.setPreferredSize(new Dimension(700, 300));

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setPreferredSize(new Dimension(700, 220));

        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Map' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        resultPanel.add(resultLabel);

        JButton spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get Map<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setBackground(Color.GREEN);
        spinButton.setPreferredSize(new Dimension(700, 80));

        mainPanel.add(resultPanel);
        mainPanel.add(spinButton);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getNewMap();
        }
    }

    private void getNewMap() {
        String selectedMap = selectRandomMap();
        resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Map:<br>" + selectedMap + "</div></html>");
    }

    private String selectRandomMap() {
        int index = random.nextInt(maps.length);
        return maps[index].getName();
    }
}