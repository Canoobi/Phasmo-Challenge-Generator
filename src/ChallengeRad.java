import javax.swing.*;

public class ChallengeRad {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SelectChallenge wheelGUI = new SelectChallenge();
            wheelGUI.setVisible(true);
        });
    }
}
