import javax.swing.*;

public class ChallengeRad {
    public static void main(String[] args) {
        //TODO [$key$] Auflösung implementieren

        SwingUtilities.invokeLater(() -> {
            SelectChallenge wheelGUI = new SelectChallenge();
            wheelGUI.setVisible(true);
        });
    }
}
