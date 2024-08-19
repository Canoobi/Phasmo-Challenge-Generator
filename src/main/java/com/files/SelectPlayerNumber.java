package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectPlayerNumber {
    JPanel mainPanel;
    JPanel buttonPanel;
    String playerNumberText;
    long maxPlayerNumber;
    MainWindow mainWindow;
    SelectChallenge selectChallenge;

    public SelectPlayerNumber(MainWindow mainWindow, SelectChallenge selectChallenge) {
        this.mainWindow = mainWindow;
        this.selectChallenge = selectChallenge;
        playerNumberText = mainWindow.getPlayerNumberText();
        maxPlayerNumber = mainWindow.getMaxPlayerNumber();
        createComponents();
    }

    private void createComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(1200, 160));
        mainPanel.setMinimumSize(new Dimension(1200, 160));
        mainPanel.setMaximumSize(new Dimension(1200, 160));

        JLabel playerNumLabel = new JLabel("<html><div style='font-size: 23px; text-align: center;'>" + playerNumberText + "</div></html>");
        playerNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNumLabel.setPreferredSize(new Dimension(1200, 80));
        playerNumLabel.setMinimumSize(new Dimension(1200, 80));
        playerNumLabel.setMaximumSize(new Dimension(1200, 80));

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(1200, 80));
        buttonPanel.setMinimumSize(new Dimension(1200, 80));
        buttonPanel.setMaximumSize(new Dimension(1200, 80));

        for (int i = 0; i < maxPlayerNumber; i++) {
            JPanel buttonPanelTemp = new JPanel();
            buttonPanel.add(buttonPanelTemp, BorderLayout.CENTER);
            JButton button = new JButton("" + (i + 1));
            button.setBackground(Color.WHITE);
            button.addActionListener(new PlayerNumButtonListener());
            button.setFont(new Font("Arial", Font.BOLD, 25));
            buttonPanelTemp.add(button);
        }

        mainPanel.add(playerNumLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(buttonPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public class PlayerNumButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int playerNum = Integer.parseInt(button.getText());
            mainWindow.setPlayerNumber(playerNum);
            System.out.println("new playerNum: " + playerNum);
            selectChallenge.playerNumberSet();
            setPlayerNumberButtons(true, false);
            button.setEnabled(false);
            button.setBackground(Color.GREEN);
        }
    }


    public void setPlayerNumberButtons(boolean boo, boolean savePlayerNumbButton) {
        for (int i = 0; i < maxPlayerNumber; i++) {
            JPanel panel = (JPanel) buttonPanel.getComponent(i);
            JButton button = (JButton) panel.getComponent(0);
            if ((Integer.parseInt(button.getText()) != mainWindow.getPlayerNumber()) || !savePlayerNumbButton) {
                button.setEnabled(boo);
                button.setBackground(Color.WHITE);
            }
        }
    }
}
