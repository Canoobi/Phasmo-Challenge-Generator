import javax.swing.*;

public class ChallengeRad {
    public static void main(String[] args) {

        //TODO add new json files to readme

        SwingUtilities.invokeLater(() -> {
            SelectChallenge wheelGUI = new SelectChallenge();
            wheelGUI.setVisible(true);
        });
    }
}
