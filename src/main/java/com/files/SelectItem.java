package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;

public class SelectItem {
    JPanel mainPanel;
    JPanel resultPanel;
    JLabel resultLabel;
    JLabel imageLabel;
    JButton spinButton;
    JPanel wheelPanel;
    JLabel timeRemainingLabel;
    Random random;
    HashSet<Item> selectedItems;
    Item[] items = MainWindow.getItems();
    int waitingTime;

    public SelectItem(long waitingTime) {
        this.waitingTime = (int) waitingTime;
        selectedItems = new HashSet<>();
        random = new Random();
        createComponents();
    }

    private void createComponents() {
        resultPanel = new JPanel(new BorderLayout());

        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Penalty' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wheelPanel = new JPanel(new GridLayout(2, 1));
        wheelPanel.setBackground(Color.GREEN);
        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Item' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel, BorderLayout.AFTER_LAST_LINE);
        wheelPanel.add(imageLabel, BorderLayout.AFTER_LAST_LINE);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get Item<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());

        if (waitingTime <= 0) {
            mainPanel = new JPanel(new GridLayout(2, 1));
            mainPanel.setPreferredSize(new Dimension(700, 380));
            resultPanel.setPreferredSize(new Dimension(700, 300));
            spinButton.setPreferredSize(new Dimension(700, 80));
            mainPanel.add(resultLabel);
            mainPanel.add(spinButton);
            wheelPanel.setBackground(Color.WHITE);
            spinButton.setBackground(Color.GREEN);
        } else {
            mainPanel = new JPanel(new GridLayout(3, 1));
            mainPanel.setPreferredSize(new Dimension(1200, 710));
            resultPanel.setPreferredSize(new Dimension(1200, 590));
            spinButton.setPreferredSize(new Dimension(1200, 60));

            JPanel timerPanel = new JPanel();
            timerPanel.setPreferredSize(new Dimension(1200, 60));
            JLabel timerLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>Zeit bis zum nächsten Item:<br></div></html>");
            timerPanel.add(timerLabel);
            timeRemainingLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>" + waitingTime + " Sekunden</div></html>");
            timerPanel.add(timeRemainingLabel);
        }
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (waitingTime > 0) {
                getNewItem();
            } else {
                newItemWithoutDelay();
            }
        }
    }

    private void getNewItem() {
        if (selectedItems.size() == items.length) {
            wheelPanel.setBackground(Color.BLACK);
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: white;'>Alle Items verwendet! Bitte schließe das Fenster zum Fortfahren!</div></html>");
            imageLabel.setIcon(null);
            spinButton.setEnabled(false);
        } else {
            spinButton.setEnabled(false);
            Timer timer = new Timer(1000, new ActionListener() {
                int timeLeft = waitingTime - 1;

                @Override
                public void actionPerformed(ActionEvent e) {
                    timeRemainingLabel.setText("<html><div style='font-size: 19px; text-align: center;'>" + timeLeft + " Sekunden</div></html>");
                    if (timeLeft <= 0) {
                        ((Timer) e.getSource()).stop();
                        wheelPanel.setBackground(Color.GREEN);
                        spinButton.setEnabled(true);
                        timeRemainingLabel.setText("<html><div style='font-size: 19px; text-align: center;'>" + waitingTime + " Sekunden</div></html>");
                    } else {
                        timeLeft--;
                    }
                }
            });
            timer.start();
            wheelPanel.setBackground(Color.RED);
            Item selectedItem = selectRandomItem();
            resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Item:<br>" + selectedItem.getName() + "</div></html>");
            imageLabel.setIcon(selectedItem.getImage());
        }
    }

    private Item selectRandomItem() {
        int index = random.nextInt(items.length);
        Item selectedItem = items[index];
        while (selectedItems.contains(selectedItem)) {
            index = random.nextInt(items.length);
            selectedItem = items[index];
        }
        selectedItems.add(selectedItem);
        return selectedItem;
    }

    private void newItemWithoutDelay() {
        int index = random.nextInt(items.length);
        Item selectedItem = items[index];
        resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Item:<br>" + selectedItem.getName() + "</div></html>");
        imageLabel.setIcon(selectedItem.getImage());
    }
}