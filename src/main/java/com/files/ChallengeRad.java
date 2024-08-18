package com.files;

import javax.swing.*;

public class ChallengeRad {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //SelectChallenge GUI = new SelectChallenge();
            MainWindow GUI = new MainWindow();
            GUI.setVisible(true);
        });
    }
}
