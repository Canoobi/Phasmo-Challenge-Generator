package com.files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class SelectPenalty extends JFrame {
    private JLabel resultLabel;
    private JButton getKeyButton;
    private JButton spinButton;
    private Random random;
    private String selectedPenalty;
    private HashSet<String> selectedKeys;
    private final String[] keys = SelectChallenge.getKeys();
    private final String[][] penalties = SelectChallenge.getPenalties();

    public SelectPenalty() {
        setTitle("Select Penalty");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        random = new Random();

        createComponents();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png")));
        setIconImage(icon.getImage());

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SelectChallenge.setPenaltyButton(true);
            }
        });
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        JPanel wheelPanel = new JPanel();
        wheelPanel.setLayout(new BorderLayout());
        wheelPanel.setBackground(Color.WHITE);
        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Penalty' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);
        getKeyButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get New Key<br></div></html>");
        getKeyButton.addActionListener(new SpinKeyButtonListener());
        getKeyButton.setBackground(Color.WHITE);
        getKeyButton.setPreferredSize(new Dimension(600, 70));
        getKeyButton.setVisible(false);
        wheelPanel.add(getKeyButton, BorderLayout.SOUTH);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get Penalty<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setBackground(Color.GREEN);
        spinButton.setPreferredSize(new Dimension(600, 100));

        resultPanel.add(wheelPanel, BorderLayout.CENTER);
        resultPanel.add(spinButton, BorderLayout.SOUTH);

        mainPanel.add(resultPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocation(1140,320);
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            spinWheel();
        }
    }

    private class SpinKeyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectKey(selectedPenalty);
        }
    }

    private void selectKey(String selectedPenalty) {
        getKeyButton.setVisible(true);
        getKeyButton.setEnabled(true);
        int num = keys.length;
        int rand = random.nextInt(num);
        while (selectedKeys.contains(keys[rand])) {
            rand = random.nextInt(num);
        }
        selectedKeys.add(keys[rand]);
        if (selectedKeys.size() == num) {
            getKeyButton.setEnabled(false);
        }
        String replString = selectedPenalty.replace("[$key$]", "<div style='font-size: 20px; text-align: center; color: red;'>'" + keys[rand] + "'</div><div style='font-size: 20px; text-align: center; color: black;'>");
        resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Penalty:<br>" + replString + "</div></html>");
    }

    private void spinWheel() {
        selectedPenalty = selectRandomItem();
        if (selectedPenalty.contains("[$key$]")) {
            selectedKeys = new HashSet<>();
            spinButton.setText("<html><div style='font-size: 22px; text-align: center;'>Get New Penalty<br></div></html>");
            selectKey(selectedPenalty);
        } else {
            getKeyButton.setVisible(false);
            spinButton.setText("<html><div style='font-size: 22px; text-align: center;'>Get Penalty<br></div></html>");
            resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Penalty:<br>" + selectedPenalty + "</div></html>");
        }
    }

    private String selectRandomItem() {
        int index = random.nextInt(penalties.length);
        if (penalties[index][1].isEmpty()) {
            return penalties[index][0];
        } else {
            return penalties[index][1];
        }
    }
}