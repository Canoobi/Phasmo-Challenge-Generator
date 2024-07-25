package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Random;

public class SelectMap extends JFrame {
    private JLabel resultLabel;
    private Random random;
    private final Map[] maps = SelectChallenge.getMaps();

    public SelectMap() {
        setTitle("Select Map");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        random = new Random();

        createComponents();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png")));
        setIconImage(icon.getImage());

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SelectChallenge.setMapButton(true);
            }
        });
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        JPanel wheelPanel = new JPanel();
        wheelPanel.setLayout(new GridLayout(1, 1));
        wheelPanel.setBackground(Color.WHITE);
        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Map' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        JButton spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get Map<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setBackground(Color.GREEN);
        spinButton.setPreferredSize(new Dimension(600, 100));

        resultPanel.add(wheelPanel, BorderLayout.CENTER);
        resultPanel.add(spinButton, BorderLayout.SOUTH);

        mainPanel.add(resultPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocation(1140,10);
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            spinWheel();
        }
    }

    private void spinWheel() {
        String selectedMap = selectRandomMap();
        resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Map:<br>" + selectedMap + "</div></html>");
    }

    private String selectRandomMap() {
        int index = random.nextInt(maps.length);
        return maps[index].getName();
    }
}