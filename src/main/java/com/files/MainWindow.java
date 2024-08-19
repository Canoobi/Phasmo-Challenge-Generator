package com.files;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    PersistanceHandler persistanceHandler;
    Challenge[] challenges;
    Item[] items;
    Map[] maps;
    Penalty[] penalties;
    String startMessage1;
    String startMessage2;
    String challengeMessage;
    String playerNumberText;
    long maxPlayerNumber;
    long waitingTime;
    String[] playerColors;
    String[] keys;
    int playerNumber;
    Image iconImage;
    JPanel rightPanel;
    SelectPlayerNumber selectPlayerNumber;
    SelectChallenge selectChallenge;
    SelectItem selectItem;
    SelectMap selectMap;
    SelectPenalty selectPenalty;

    public MainWindow() {
        this.persistanceHandler = new PersistanceHandler();

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
        iconImage = persistanceHandler.getIconImage();

        selectChallenge = new SelectChallenge(this);
        selectPlayerNumber = new SelectPlayerNumber(this, selectChallenge);
        selectItem = new SelectItem(0, this);
        selectMap = new SelectMap(this);
        selectPenalty = new SelectPenalty(this);

        debugInfo();

        setTitle("Challenge-Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1905, 1060);
        setMinimumSize(new Dimension(1580, 850));

        createComponents();
        setIconImage(iconImage);
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(1200, 1060));
        leftPanel.setMinimumSize(new Dimension(1200, 1060));
        leftPanel.setMaximumSize(new Dimension(1200, 1060));
        JPanel l1Panel = selectPlayerNumber.getPanel();
        JPanel l2Panel = selectChallenge.getPanel();
        leftPanel.add(l1Panel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        leftPanel.add(l2Panel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        JPanel middlePanel = new JPanel();
        middlePanel.setPreferredSize(new Dimension(5, 1060));
        middlePanel.setMinimumSize(new Dimension(5, 1060));
        middlePanel.setMaximumSize(new Dimension(5, 1060));
        middlePanel.setBackground(Color.lightGray);

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(700, 1060));
        rightPanel.setMinimumSize(new Dimension(700, 1060));
        rightPanel.setMaximumSize(new Dimension(700, 1060));
        JPanel r1Panel = selectMap.getPanel();
        JPanel r2Panel = selectPenalty.getPanel();
        JPanel r3Panel = selectItem.getPanel();
        rightPanel.add(r1Panel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        rightPanel.add(r2Panel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        rightPanel.add(r3Panel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        mainPanel.add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(middlePanel, new GridBagConstraints(1, 0, 1, 1, 0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(rightPanel, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        add(mainPanel);
        setLocation(10, 10);
    }

    private void debugInfo() {
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
    }

    public void setPlayerNumberButtons(boolean boo) {
        selectPlayerNumber.setPlayerNumberButtons(boo, true);
    }

    public void setGetItem(long time) {
        SelectItem sel = new SelectItem(time, this);
        JPanel r3Panel = sel.getPanel();
        rightPanel.remove(2);
        rightPanel.add(r3Panel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public Challenge[] getChallenges() {
        return challenges;
    }

    public String[] getKeys() {
        return keys;
    }

    public Item[] getItems() {
        return items;
    }

    public Map[] getMaps() {
        return maps;
    }

    public Penalty[] getPenalties() {
        return penalties;
    }

    public String getStartMessage1() {
        return startMessage1;
    }

    public String getStartMessage2() {
        return startMessage2;
    }

    public String getPlayerNumberText() {
        return playerNumberText;
    }

    public long getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getChallengeMessage() {
        return challengeMessage;
    }

    public String[] getPlayerColors() {
        return playerColors;
    }

    public long getWaitingTime() {
        return waitingTime;
    }
}