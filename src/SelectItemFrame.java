import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Random;

public class SelectItemFrame extends JFrame {
    private JLabel resultLabel;
    private JButton spinButton;
    private JPanel wheelPanel;
    private JLabel timeRemainingLabel;
    private Timer timer;
    private Random random;
    private HashSet<String> selectedItems;
    private int waitingTime = 60;
    public static String[] items = ChallengeRad.items;

    public SelectItemFrame() {
        setTitle("Select Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        selectedItems = new HashSet<>();
        random = new Random();

        createComponents();

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ChallengeRad.setButton(true);
            }
        });
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Timer-Anzeige Panel
        JPanel timerPanel = new JPanel();
        JLabel timerLabel = new JLabel("Zeit bis zum nächsten Item:");
        timerPanel.add(timerLabel);
        timeRemainingLabel = new JLabel(Integer.toString(waitingTime));
        timerPanel.add(timeRemainingLabel);

        // Resultat und Spin-Button Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        wheelPanel = new JPanel();
        wheelPanel.setLayout(new GridLayout(1, 1));
        wheelPanel.setBackground(Color.GREEN);
        resultLabel = new JLabel("<html><div style='font-size: 18px; text-align: center;'>Drücke 'Spin' um zu starten<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        spinButton = new JButton("<html><div style='font-size: 18px; text-align: center;'>Spin<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setPreferredSize(new Dimension(200, 80)); // Set button size

        resultPanel.add(wheelPanel, BorderLayout.CENTER);
        resultPanel.add(spinButton, BorderLayout.SOUTH);

        // Haupt-Panel
        mainPanel.add(timerPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);

        add(mainPanel);
    }


    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            spinWheel();
        }
    }

    private void spinWheel() {
        if (selectedItems.size() == items.length) {
            wheelPanel.setBackground(Color.BLACK);
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: white;'>Alle Items verwendet! Bitte schließe das Fenster zum Fortfahren!</div></html>");
            spinButton.setEnabled(false);
        } else {
            spinButton.setEnabled(false);
            timer = new Timer(1000, new ActionListener() {
                int timeLeft = waitingTime - 1;

                @Override
                public void actionPerformed(ActionEvent e) {
                    timeRemainingLabel.setText(Integer.toString(timeLeft));
                    if (timeLeft == 0) {
                        ((Timer) e.getSource()).stop();
                        wheelPanel.setBackground(Color.GREEN);
                        spinButton.setEnabled(true);
                        timeRemainingLabel.setText(Integer.toString(waitingTime));
                    } else {
                        timeLeft--;
                    }
                }
            });
            timer.start();
            wheelPanel.setBackground(Color.RED);
            String selectedItem = selectRandomItem();
            resultLabel.setText("<html><div style='font-size: 18px; text-align: center; color: black;'>Item:<br>" + selectedItem + "</div></html>");
        }
    }

    private String selectRandomItem() {
        int index = random.nextInt(items.length);
        String selectedItem = items[index];
        while (selectedItems.contains(selectedItem)) {
            index = random.nextInt(items.length);
            selectedItem = items[index];
        }
        selectedItems.add(selectedItem);
        return selectedItem;
    }
}