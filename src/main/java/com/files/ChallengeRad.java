package com.files;

import javax.swing.*;

public class ChallengeRad {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Hello World!");
            SelectChallenge wheelGUI = new SelectChallenge();
            wheelGUI.setVisible(true);
        });
    }
}
