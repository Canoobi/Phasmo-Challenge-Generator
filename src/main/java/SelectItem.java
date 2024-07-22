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
    private JLabel imageLabel;
    private JButton spinButton;
    private JPanel wheelPanel;
    private JLabel timeRemainingLabel;
    private Random random;
    private HashSet<Item> selectedItems;
    private final Item[] items = SelectChallenge.getItems();
    private final int waitingTime;

    public SelectItem(long waitingTime) {
        this.waitingTime = (int) waitingTime;

        setTitle("Select Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        selectedItems = new HashSet<>();
        random = new Random();

        createComponents();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icon.png")));
        setIconImage(icon.getImage());

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SelectChallenge.setAllButtonsSavePlay(true);
                if (waitingTime == 0) {
                    SelectChallenge.setItemButton(true);
                }
            }
        });
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        if (waitingTime > 0) {
            JPanel timerPanel = new JPanel();
            timerPanel.setPreferredSize(new Dimension(600, 50));
            JLabel timerLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>Zeit bis zum nächsten Item:<br></div></html>");
            timerPanel.add(timerLabel);
            timeRemainingLabel = new JLabel("<html><div style='font-size: 19px; text-align: center;'>" + waitingTime + " Sekunden</div></html>");
            timerPanel.add(timeRemainingLabel);
            mainPanel.add(timerPanel, BorderLayout.NORTH);
        }

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        wheelPanel = new JPanel(new GridLayout(2, 1));
        wheelPanel.setBackground(Color.GREEN);
        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Item' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel, BorderLayout.AFTER_LAST_LINE);
        wheelPanel.add(imageLabel, BorderLayout.AFTER_LAST_LINE);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get Item<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setPreferredSize(new Dimension(600, 100));

        resultPanel.add(wheelPanel, BorderLayout.CENTER);
        resultPanel.add(spinButton, BorderLayout.SOUTH);

        mainPanel.add(resultPanel, BorderLayout.CENTER);

        if (waitingTime <= 0) {
            wheelPanel.setBackground(Color.WHITE);
            spinButton.setBackground(Color.GREEN);
        }

        add(mainPanel);
        setLocation(1140,730);
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (waitingTime > 0) {
                spinWheel();
            } else {
                newItemWithoutDelay();
            }
        }
    }

    private void spinWheel() {
        if (selectedItems.size() == items.length) {
            wheelPanel.setBackground(Color.BLACK);
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: white;'>Alle Items verwendet! Bitte schließe das Fenster zum Fortfahren!</div></html>");
            imageLabel.setIcon(null);
            spinButton.setEnabled(false);
        } else {
            spinButton.setEnabled(false);
            Timer timer = new Timer(1000, new ActionListener() {
                int timeLeft = waitingTime - 1;

                @Override
                public void actionPerformed(ActionEvent e) {
                    timeRemainingLabel.setText("<html><div style='font-size: 19px; text-align: center;'>" + timeLeft + " Sekunden</div></html>");
                    if (timeLeft <= 0) {
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
            Item selectedItem = selectRandomItem();
            resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Item:<br>" + selectedItem.getName() + "</div></html>");
            imageLabel.setIcon(selectedItem.getImage());
        }
    }

    private Item selectRandomItem() {
        int index = random.nextInt(items.length);
        Item selectedItem = items[index];
        while (selectedItems.contains(selectedItem)) {
            index = random.nextInt(items.length);
            selectedItem = items[index];
        }
        selectedItems.add(selectedItem);
        return selectedItem;
    }

    private void newItemWithoutDelay() {
        int index = random.nextInt(items.length);
        Item selectedItem = items[index];
        resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Item:<br>" + selectedItem.getName() + "</div></html>");
        imageLabel.setIcon(selectedItem.getImage());
    }
}