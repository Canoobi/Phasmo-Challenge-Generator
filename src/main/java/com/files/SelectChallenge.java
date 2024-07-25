package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SelectChallenge extends JFrame {
    private JLabel resultLabel;
    private static JPanel buttonPanel;
    private static JButton spinButton;
    private static JButton mapButton;
    private static JButton penaltyButton;
    private static JButton itemButton;
    private Random random;
    private static int playerNum;
    private static Challenge[] challenges;
    private static Item[] items;
    private static Map[] maps;
    private static Penalty[] penalties;
    private static String startMessage1;
    private static String startMessage2;
    private static String challengeMessage;
    private static String playerNumberText;
    private static long maxPlayerNumber;
    private static long waitingTime;
    private static String[] playerColors;
    private static String[] keys;
    private PersistanceHandler persistanceHandler;

    public SelectChallenge() {
        this.persistanceHandler = new PersistanceHandler();
        this.random = new Random();
        challenges = persistanceHandler.getChallengesFromJSON();
        items = persistanceHandler.getItemsFromJSON();
        maps = persistanceHandler.getMapsFromJSON();
        penalties = persistanceHandler.getPenaltiesFromJSON();
        startMessage1 = persistanceHandler.getTextFromSettingsJSON("start-message-1");
        startMessage2 = persistanceHandler.getTextFromSettingsJSON("start-message-2");
        challengeMessage = persistanceHandler.getTextFromSettingsJSON("challenge-message");
        playerNumberText = persistanceHandler.getTextFromSettingsJSON("player-number");
        maxPlayerNumber = persistanceHandler.getNumFromSettingsJSON("max-player-number");
        waitingTime = persistanceHandler.getNumFromSettingsJSON("waiting-time-for-new-item");
        playerColors = persistanceHandler.getArrayFromSettingsJSON("player-colors");
        keys = persistanceHandler.getArrayFromSettingsJSON("keys");

        System.out.println(challenges.length);
        System.out.println("start-message: " + startMessage1 + "\nchallenge-message: " + challengeMessage + "\nwaiting-time: " + waitingTime + "\n");
        for (int i = 0; i < challenges.length; i++) {
            System.out.println(i + ": " + challenges[i].text() + "; " + challenges[i].openSelectItemFrame());
        }
        System.out.println();
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ": " + items[i].getName() + "; " + items[i].getItemType());
        }
        System.out.println();
        for (int i = 0; i < maps.length; i++) {
            System.out.println(i + ": " + maps[i].getName() + "; " + maps[i].getSize() + "; " + maps[i].getMessage());
        }
        System.out.println();
        for (int i = 0; i < penalties.length; i++) {
            System.out.println(i + ": " + penalties[i].getName() + "; " + penalties[i].getMessage());
        }

        setTitle("Challenge-Wheel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 1120);

        createComponents();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png")));
        setIconImage(icon.getImage());
    }

    public static String[] getKeys() {
        return keys;
    }

    public static Item[] getItems() {
        return items;
    }

    public static Map[] getMaps() {
        return maps;
    }

    public static Penalty[] getPenalties() {
        return penalties;
    }

    public static String getStartMessage2() {
        return startMessage2;
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel playerNumPanel = new JPanel(new GridLayout(2, 1));

        JLabel playerNumLabel = new JLabel("<html><div style='font-size: 23px; text-align: center;'>" + playerNumberText + "</div></html>");
        playerNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNumPanel.setPreferredSize(new Dimension(1120, 150));
        playerNumPanel.setFont(new Font("Arial", Font.BOLD, 35));
        playerNumPanel.add(playerNumLabel, BorderLayout.AFTER_LAST_LINE);

        buttonPanel = new JPanel();

        for (int i = 0; i < maxPlayerNumber; i++) {
            JPanel buttonPanelTemp = new JPanel();
            buttonPanel.add(buttonPanelTemp, BorderLayout.CENTER);
            JButton button = new JButton("" + (i + 1));
            button.setBackground(Color.WHITE);
            button.addActionListener(new PlayerNumButtonListener());
            button.setFont(new Font("Arial", Font.BOLD, 25));
            buttonPanelTemp.add(button);
        }
        playerNumPanel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

        JPanel westBlock = new JPanel();
        westBlock.setPreferredSize(new Dimension(100, 800));
        westBlock.setBackground(Color.WHITE);

        JPanel eastBlock = new JPanel();
        eastBlock.setPreferredSize(new Dimension(100, 800));
        eastBlock.setBackground(Color.WHITE);

        JPanel wheelPanel = new JPanel();
        wheelPanel.setLayout(new GridLayout(1, 1));
        wheelPanel.setBackground(Color.WHITE);
        resultLabel = new JLabel("<html><div style='font-size: 26px; text-align: center;'>" + startMessage1 + "<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        mapButton = new JButton("<html><div style='font-size: 18px; text-align: center;'>Open Map-Selector<br></div></html>");
        mapButton.addActionListener(new MapButtonListener());
        mapButton.setPreferredSize(new Dimension(373, 50));
        mapButton.setFont(new Font("Arial", Font.BOLD, 18));
        mapButton.setBackground(Color.WHITE);

        penaltyButton = new JButton("<html><div style='font-size: 18px; text-align: center;'>Open Penalty-Selector<br></div></html>");
        penaltyButton.addActionListener(new PenaltyButtonListener());
        penaltyButton.setPreferredSize(new Dimension(374, 50));
        penaltyButton.setFont(new Font("Arial", Font.BOLD, 18));
        penaltyButton.setBackground(Color.WHITE);

        itemButton = new JButton("<html><div style='font-size: 18px; text-align: center;'>Open Item-Selector<br></div></html>");
        itemButton.addActionListener(new ItemButtonListener());
        itemButton.setPreferredSize(new Dimension(373, 50));
        itemButton.setFont(new Font("Arial", Font.BOLD, 18));
        itemButton.setBackground(Color.WHITE);

        JPanel openPanel = new JPanel(new GridLayout(1, 3));
        openPanel.setPreferredSize(new Dimension(1120, 50));
        openPanel.add(mapButton, BorderLayout.WEST);
        openPanel.add(penaltyButton, BorderLayout.CENTER);
        openPanel.add(itemButton, BorderLayout.EAST);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Spin<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setPreferredSize(new Dimension(1120, 100));
        spinButton.setFont(new Font("Arial", Font.BOLD, 22));
        spinButton.setBackground(Color.GREEN);
        setSpinButton(false);

        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(1120, 210));
        northPanel.add(playerNumPanel, BorderLayout.NORTH);
        northPanel.add(openPanel, BorderLayout.SOUTH);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(westBlock, BorderLayout.WEST);
        mainPanel.add(eastBlock, BorderLayout.EAST);
        mainPanel.add(wheelPanel, BorderLayout.CENTER);
        mainPanel.add(spinButton, BorderLayout.SOUTH);

        add(mainPanel);
        setLocation(10,10);
    }

    public class PlayerNumButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            playerNum = Integer.parseInt(button.getText());
            System.out.println("new playerNum: " + playerNum);
            setAllButtons(true);
            button.setEnabled(false);
            button.setBackground(Color.GREEN);
            resultLabel.setText("<html><div style='font-size: 26px; text-align: center;'>" + startMessage2 + "<br></div></html>");
        }
    }

    public class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            spinWheel();
        }
    }

    public static class MapButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SelectMap();
            setMapButton(false);
        }
    }

    public static class PenaltyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SelectPenalty();
            setPenaltyButton(false);
        }
    }

    public static class ItemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SelectItem(0);
            setItemButton(false);
        }
    }

    public static void setAllButtons(boolean boo) {
        setPlayerNumberButtons(boo, false);
        setSpinButton(boo);
    }

    public static void setAllButtonsSavePlay(boolean boo) {
        setPlayerNumberButtons(boo, true);
        setSpinButton(boo);
    }

    private static void setPlayerNumberButtons(boolean boo, boolean savePlayerNumbButton) {
        for (int i = 0; i < maxPlayerNumber; i++) {
            JPanel panel = (JPanel) buttonPanel.getComponent(i);
            JButton button = (JButton) panel.getComponent(0);
            if ((Integer.parseInt(button.getText()) != playerNum) || !savePlayerNumbButton) {
                button.setEnabled(boo);
                button.setBackground(Color.WHITE);
            }
        }
    }

    private static void setSpinButton(boolean boo) {
        spinButton.setEnabled(boo);
        if (boo) {
            spinButton.setBackground(Color.GREEN);
        } else {
            spinButton.setBackground(Color.RED);
        }
    }

    public static void setMapButton(boolean boo) {
        mapButton.setEnabled(boo);
        if (boo) {
            mapButton.setBackground(Color.WHITE);
        } else {
            mapButton.setBackground(Color.RED);
        }
    }

    public static void setPenaltyButton(boolean boo) {
        penaltyButton.setEnabled(boo);
        if (boo) {
            penaltyButton.setBackground(Color.WHITE);
        } else {
            penaltyButton.setBackground(Color.RED);
        }
    }

    public static void setItemButton(boolean boo) {
        itemButton.setEnabled(boo);
        if (boo) {
            itemButton.setBackground(Color.WHITE);
        } else {
            itemButton.setBackground(Color.RED);
        }
    }

    private void spinWheel() {
        setAllButtonsSavePlay(false);
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
                if (count >= spins && challenges[currentIndex].containsRightPlayerNum(playerNum)) {
                    ((Timer) e.getSource()).stop();
                    if (challenges[currentIndex].openSelectItemFrame()) {
                        setLabelText(currentIndex);
                        new SelectItem(waitingTime);
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
                        setAllButtonsSavePlay(true);
                    } else if (challenges[currentIndex].text().contains("[$numb$]")) {
                        int numberOfItems = 1 + random.nextInt(8);
                        if (challenges[currentIndex].message().contains("[$numb$]")) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].message().replace("[$numb$]", String.valueOf(numberOfItems)) + "</div></html>");
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].text().replace("[$numb$]", String.valueOf(numberOfItems)) + "</div></html>");
                        }
                        setAllButtonsSavePlay(true);
                    } else if (challenges[currentIndex].text().contains("[$color$]")) {
                        int num = playerColors.length;
                        if (playerNum < num) {
                            num = playerNum;
                        }
                        int rand = random.nextInt(num);
                        if (challenges[currentIndex].message().contains("[$color$]")) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].message().replace("[$color$]", String.valueOf(playerColors[rand])) + "</div></html>");
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + challenges[currentIndex].text().replace("[$color$]", String.valueOf(playerColors[rand])) + "</div></html>");
                        }
                        setAllButtonsSavePlay(true);
                    } else {
                        setLabelText(currentIndex);
                        setAllButtonsSavePlay(true);
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