package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;

public class SelectChallenge extends JFrame {
    JLabel resultLabel;
    JButton spinButton;
    JPanel mainPanel;
    Random random;
    Challenge[] challenges;
    Item[] items;
    String startMessage1;
    String startMessage2;
    String challengeMessage;
    String[] playerColors;
    MainWindow mainWindow;
    SelectPlayerNumber selectPlayerNumber;

    public SelectChallenge(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.random = new Random();
        challenges = mainWindow.getChallenges();
        items = mainWindow.getItems();
        startMessage1 = mainWindow.getStartMessage1();
        startMessage2 = mainWindow.getStartMessage2();
        challengeMessage = mainWindow.getChallengeMessage();
        playerColors = mainWindow.getPlayerColors();
        createComponents();
    }

    private void createComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel westBlock = new JPanel();
        westBlock.setPreferredSize(new Dimension(100, 900));
        westBlock.setBackground(Color.WHITE);

        JPanel eastBlock = new JPanel();
        eastBlock.setPreferredSize(new Dimension(100, 900));
        eastBlock.setBackground(Color.WHITE);

        JPanel wheelPanel = new JPanel(new GridLayout(1, 1));
        wheelPanel.setPreferredSize(new Dimension(1000, 900));
        wheelPanel.setMinimumSize(new Dimension(1000, 900));
        wheelPanel.setMaximumSize(new Dimension(1000, 900));
        wheelPanel.setBackground(Color.WHITE);
        resultLabel = new JLabel("<html><div style='font-size: 26px; text-align: center;'>" + startMessage1 + "<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Spin<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setPreferredSize(new Dimension(1200, 100));
        spinButton.setFont(new Font("Arial", Font.BOLD, 22));
        spinButton.setBackground(Color.GREEN);
        setSpinButton(false);

        mainPanel.add(westBlock, BorderLayout.WEST);
        mainPanel.add(eastBlock, BorderLayout.EAST);
        mainPanel.add(wheelPanel, BorderLayout.CENTER);
        mainPanel.add(spinButton, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void playerNumberSet() {
        resultLabel.setText("<html><div style='font-size: 26px; text-align: center;'>" + startMessage2 + "<br></div></html>");
        setSpinButton(true);
    }

    public class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectRandomChallenge();
        }
    }

    public void setAllButtons(boolean boo) {
        setSpinButton(boo);
        mainWindow.setPlayerNumberButtons(boo);
    }

    private void setSpinButton(boolean boo) {
        spinButton.setEnabled(boo);
        if (boo) {
            spinButton.setBackground(Color.GREEN);
        } else {
            spinButton.setBackground(Color.RED);
        }
    }

    private void selectRandomChallenge() {
        setAllButtons(false);
        int spins = 20 + random.nextInt(challenges.length);
        int delay = 100;

        Timer timer = new Timer(delay, new ActionListener() {
            int count = 0;
            int currentIndex = 0;

            public void actionPerformed(ActionEvent e) {
                if (challenges[currentIndex].message().isEmpty()) {
                    resultLabel.setText("<html><div style='font-size: 18px; text-align: center;'>" + challenges[currentIndex].text() + "</div></html>");
                } else {
                    resultLabel.setText("<html><div style='font-size: 18px; text-align: center;'>" + challenges[currentIndex].message() + "</div></html>");
                }

                currentIndex = (currentIndex + 1) % challenges.length;

                count++;
                if (count >= spins && challenges[currentIndex].containsRightPlayerNum(mainWindow.getPlayerNumber())) {
                    ((Timer) e.getSource()).stop();
                    if (challenges[currentIndex].openSelectItemFrame()) {
                        setLabelText(currentIndex);
                        mainWindow.setGetItem(mainWindow.getWaitingTime());
                        setAllButtons(true);
                    } else if (challenges[currentIndex].text().contains("[$items$]")) {
                        int numberOfItems = 2 + random.nextInt(11);
                        StringBuilder selectedItemsText = new StringBuilder("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 20px; text-align: center; color: black;'>" + challenges[currentIndex].message() + "<br>");
                        HashSet<Integer> selectedIndexes = new HashSet<>();
                        while (selectedIndexes.size() < numberOfItems) {
                            int randomIndex = random.nextInt(items.length);
                            if (!selectedIndexes.contains(randomIndex)) {
                                selectedIndexes.add(randomIndex);
                                String itemName = items[randomIndex].getName();
                                String itemIcon = items[randomIndex].getImagePath();
                                selectedItemsText.append("<div style='font-size: 18px; text-align: center;'><img src='").append(SelectChallenge.class.getResource(itemIcon)).append("'width='50' height='50'/> ").append(itemName).append("</div>");
                                System.out.println(selectedItemsText);
                            }
                        }
                        selectedItemsText.append("</div></html>");
                        resultLabel.setText(selectedItemsText.toString());
                        setAllButtons(true);
                    } else if (challenges[currentIndex].text().contains("[$numb$]")) {
                        int numberOfItems = 1 + random.nextInt(8);
                        if (challenges[currentIndex].message().contains("[$numb$]")) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].message().replace("[$numb$]", String.valueOf(numberOfItems)) + "</div></html>");
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].text().replace("[$numb$]", String.valueOf(numberOfItems)) + "</div></html>");
                        }
                        setAllButtons(true);
                    } else if (challenges[currentIndex].text().contains("[$color$]")) {
                        int num = playerColors.length;
                        if (mainWindow.getPlayerNumber() < num) {
                            num = mainWindow.getPlayerNumber();
                        }
                        int rand = random.nextInt(num);
                        if (challenges[currentIndex].message().contains("[$color$]")) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].message().replace("[$color$]", String.valueOf(playerColors[rand])) + "</div></html>");
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].text().replace("[$color$]", String.valueOf(playerColors[rand])) + "</div></html>");
                        }
                        setAllButtons(true);
                    } else {
                        setLabelText(currentIndex);
                        setAllButtons(true);
                    }
                }
            }
        });
        timer.start();
    }

    private void setLabelText(int index) {
        if (challenges[index].message().isEmpty()) {
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[index].text() + "</div></html>");
        } else {
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[index].message() + "</div></html>");
        }
    }
}