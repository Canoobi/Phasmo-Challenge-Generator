package com.files;

import javax.swing.*;

public class ChallengeRad {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow GUI = new MainWindow();
            GUI.setVisible(true);
        });
    }
}
