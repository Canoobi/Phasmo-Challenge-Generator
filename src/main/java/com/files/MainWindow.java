package com.files;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    PersistanceHandler persistanceHandler;

    Challenge[] challenges;
    static Item[] items;
    static Map[] maps;
    static Penalty[] penalties;
    String startMessage1;
    static String startMessage2;
    String challengeMessage;
    String playerNumberText;
    long maxPlayerNumber;
    long waitingTime;
    String[] playerColors;
    static String[] keys;
    Image iconImage;

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

        selectChallenge = new SelectChallenge();
        selectItem = new SelectItem(0);
        selectMap = new SelectMap();
        selectPenalty = new SelectPenalty();

        debugInfo();

        setTitle("Challenge-Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1900, 1060);

        createComponents();
        setIconImage(iconImage);
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        //JPanel leftPanel = selectChallenge.getPanel();
        SelectItem sel = new SelectItem(60);
        JPanel leftPanel = sel.getPanel();
        leftPanel.setPreferredSize(new Dimension(1200, 1060));

        JPanel rightPanel = new JPanel(new GridLayout(3, 1));
        rightPanel.setPreferredSize(new Dimension(700, 1060));
        JPanel r1Panel = selectMap.getPanel();
        JPanel r2Panel = selectPenalty.getPanel();
        JPanel r3Panel = selectItem.getPanel();
        rightPanel.add(r1Panel);
        rightPanel.add(r2Panel);
        rightPanel.add(r3Panel);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

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
}