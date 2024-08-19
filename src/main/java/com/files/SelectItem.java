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
    Item[] items;
    int waitingTime;
    MainWindow mainWindow;

    public SelectItem(long waitingTime, MainWindow mainWindow) {
        this.waitingTime = (int) waitingTime;
        this.mainWindow = mainWindow;
        selectedItems = new HashSet<>();
        random = new Random();
        items = mainWindow.getItems();
        createComponents();
    }

    private void createComponents() {
        resultPanel = new JPanel(new BorderLayout());
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

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(700, 380));
        mainPanel.setMinimumSize(new Dimension(700, 380));
        mainPanel.setMaximumSize(new Dimension(700, 380));

        spinButton.setPreferredSize(new Dimension(700, 80));
        spinButton.setMinimumSize(new Dimension(700, 80));
        spinButton.setMaximumSize(new Dimension(700, 80));

        resultPanel.add(wheelPanel, BorderLayout.CENTER);

        if (waitingTime <= 0) {
            resultPanel.setPreferredSize(new Dimension(700, 300));
            resultPanel.setMinimumSize(new Dimension(700, 300));
            resultPanel.setMaximumSize(new Dimension(700, 300));

            mainPanel.add(resultPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            mainPanel.add(spinButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            wheelPanel.setBackground(Color.WHITE);
            spinButton.setBackground(Color.GREEN);
        } else {
            JButton closeButton = new JButton("X");
            closeButton.addActionListener(new CloseButtonListener());
            closeButton.setBackground(Color.RED);
            closeButton.setPreferredSize(new Dimension(50, 50));
            closeButton.setMinimumSize(new Dimension(50, 50));
            closeButton.setMaximumSize(new Dimension(50, 50));
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setPreferredSize(new Dimension(700, 50));
            topPanel.setMinimumSize(new Dimension(700, 50));
            topPanel.setMaximumSize(new Dimension(700, 50));
            JPanel timerPanel = new JPanel();
            timerPanel.setPreferredSize(new Dimension(650, 50));
            timerPanel.setMinimumSize(new Dimension(650, 50));
            timerPanel.setMaximumSize(new Dimension(650, 50));
            JLabel timerLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>Zeit bis zum nächsten Item:<br></div></html>");
            timeRemainingLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>" + waitingTime + " Sekunden</div></html>");
            timerPanel.add(timerLabel);
            timerPanel.add(timeRemainingLabel);

            topPanel.add(closeButton, BorderLayout.WEST);
            topPanel.add(timerPanel, BorderLayout.CENTER);

            resultPanel.setPreferredSize(new Dimension(700, 250));
            resultPanel.setMinimumSize(new Dimension(700, 250));
            resultPanel.setMaximumSize(new Dimension(700, 250));

            wheelPanel.setBackground(Color.GREEN);
            spinButton.setBackground(Color.WHITE);

            mainPanel.add(topPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.3, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            mainPanel.add(resultPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            mainPanel.add(spinButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
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

    private class CloseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainWindow.setGetItem(0);
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

    private void newItemWithoutDelay() {
        int index = random.nextInt(items.length);
        Item selectedItem = items[index];
        resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Item:<br>" + selectedItem.getName() + "</div></html>");
        imageLabel.setIcon(selectedItem.getImage());
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
}