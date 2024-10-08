package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;

public class SelectPenalty {
    JPanel mainPanel;
    JPanel resultPanel;
    JPanel keyPanel;
    JLabel keyLabel;
    JLabel resultLabel;
    JButton keyButton;
    JButton spinButton;
    Random random;
    String selectedPenalty;
    HashSet<String> selectedKeys;
    Penalty[] penalties;
    String[] keys;

    public SelectPenalty(MainWindow mainWindow) {
        random = new Random();
        penalties = mainWindow.getPenalties();
        keys = mainWindow.getKeys();
        createComponents();
    }

    private void createComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(700, 380));
        mainPanel.setMinimumSize(new Dimension(700, 380));
        mainPanel.setMaximumSize(new Dimension(700, 380));

        resultPanel = new JPanel(new BorderLayout());
        resultPanel.setPreferredSize(new Dimension(700, 300));
        resultPanel.setMinimumSize(new Dimension(700, 300));
        resultPanel.setMaximumSize(new Dimension(700, 300));
        resultPanel.setBackground(Color.WHITE);

        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Penalty' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        keyPanel = new JPanel(new GridBagLayout());
        keyPanel.setPreferredSize(new Dimension(700, 300));
        keyPanel.setMinimumSize(new Dimension(700, 300));
        keyPanel.setMaximumSize(new Dimension(700, 300));

        keyLabel = new JLabel("");
        keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        keyLabel.setPreferredSize(new Dimension(700, 230));
        keyLabel.setMinimumSize(new Dimension(700, 230));
        keyLabel.setMaximumSize(new Dimension(700, 230));
        keyButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get New Key<br></div></html>");
        keyButton.addActionListener(new SpinKeyButtonListener());
        keyButton.setBackground(Color.WHITE);
        keyButton.setPreferredSize(new Dimension(700, 70));
        keyButton.setMinimumSize(new Dimension(700, 70));
        keyButton.setMaximumSize(new Dimension(700, 70));

        keyPanel.add(keyLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        keyPanel.add(keyButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        resultPanel.add(resultLabel);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get Penalty<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setBackground(Color.GREEN);
        spinButton.setPreferredSize(new Dimension(700, 80));
        spinButton.setMinimumSize(new Dimension(700, 80));
        spinButton.setMaximumSize(new Dimension(700, 80));

        mainPanel.add(resultPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(spinButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getNewPenalty();
        }
    }

    private class SpinKeyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectKey(selectedPenalty);
        }
    }

    private void getNewPenalty() {
        selectedPenalty = selectRandomPenalty();
        if (selectedPenalty.contains("[$key$]")) {
            selectedKeys = new HashSet<>();
            spinButton.setText("<html><div style='font-size: 22px; text-align: center;'>Get New Penalty<br></div></html>");
            resultPanel.remove(resultLabel);
            resultPanel.add(keyPanel);
            keyButton.setEnabled(true);
            selectKey(selectedPenalty);
        } else {
            keyButton.setEnabled(false);
            resultPanel.remove(keyPanel);
            resultPanel.add(resultLabel);
            spinButton.setText("<html><div style='font-size: 22px; text-align: center;'>Get Penalty<br></div></html>");
            resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Penalty:<br>" + selectedPenalty + "</div></html>");
        }
    }

    private void selectKey(String selectedPenalty) {
        int num = keys.length;
        int rand = random.nextInt(num);
        while (selectedKeys.contains(keys[rand])) {
            rand = random.nextInt(num);
        }

        selectedKeys.add(keys[rand]);

        if (selectedKeys.size() == num) {
            keyButton.setEnabled(false);
        }

        String replString = selectedPenalty.replace("[$key$]", "<div style='font-size: 20px; text-align: center; color: red;'>'" + keys[rand] + "'</div><div style='font-size: 20px; text-align: center; color: black;'>");
        keyLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Penalty:<br>" + replString + "</div></html>");
    }

    private String selectRandomPenalty() {
        int index = random.nextInt(penalties.length);
        if (penalties[index].getMessage().isEmpty()) {
            return penalties[index].getName();
        } else {
            return penalties[index].getMessage();
        }
    }
}