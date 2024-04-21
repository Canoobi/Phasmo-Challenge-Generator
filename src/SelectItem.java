import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class SelectItem extends JFrame {
    private JLabel resultLabel;
    private JButton spinButton;
    private JPanel wheelPanel;
    private JLabel timeRemainingLabel;
    private Random random;
    private HashSet<String> selectedItems;
    private final String[][] items = SelectChallenge.getItems();
    private final int waitingTime;

    public SelectItem(long waitingTime) {
        this.waitingTime = (int) waitingTime;

        setTitle("Select Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        selectedItems = new HashSet<>();
        random = new Random();

        createComponents();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/icon.png")));
        setIconImage(icon.getImage());

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SelectChallenge.setAllButtonsSavePlay(true);
            }
        });
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Timer-Panel
        JPanel timerPanel = new JPanel();
        timerPanel.setPreferredSize(new Dimension(600, 50));
        JLabel timerLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>Zeit bis zum nächsten Item:<br></div></html>");
        timerPanel.add(timerLabel);
        timeRemainingLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>" + waitingTime + " Sekunden</div></html>");
        timerPanel.add(timeRemainingLabel);

        // Result- and Spin-Button-Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        wheelPanel = new JPanel();
        wheelPanel.setLayout(new GridLayout(1, 1));
        wheelPanel.setBackground(Color.GREEN);
        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Drücke 'Spin' um zu starten<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Spin<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setPreferredSize(new Dimension(600, 100));

        resultPanel.add(wheelPanel, BorderLayout.CENTER);
        resultPanel.add(spinButton, BorderLayout.SOUTH);

        // Main-Panel
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
            Timer timer = new Timer(1000, new ActionListener() {
                int timeLeft = waitingTime - 1;

                @Override
                public void actionPerformed(ActionEvent e) {
                    timeRemainingLabel.setText("<html><div style='font-size: 19px; text-align: center;'>" + timeLeft + " Sekunden</div></html>");
                    if (timeLeft == 0) {
                        ((Timer) e.getSource()).stop();
                        wheelPanel.setBackground(Color.GREEN);
                        spinButton.setEnabled(true);
                        timeRemainingLabel.setText("<html><div style='font-size: 19px; text-align: center;'>" + waitingTime + " Sekunden</div></html>");
                    } else {
                        timeLeft--;
                    }
                }
            });
            timer.start();
            wheelPanel.setBackground(Color.RED);
            String selectedItem = selectRandomItem();
            resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Item:<br>" + selectedItem + "</div></html>");
        }
    }

    private String selectRandomItem() {
        int index = random.nextInt(items.length);
        String selectedItem = items[index][0];
        while (selectedItems.contains(selectedItem)) {
            index = random.nextInt(items.length);
            selectedItem = items[index][0];
        }
        selectedItems.add(selectedItem);
        return selectedItem;
    }
}