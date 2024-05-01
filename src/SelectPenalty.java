import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Random;

public class SelectPenalty extends JFrame {
    private JLabel resultLabel;
    private Random random;
    private String[][] penalties = SelectChallenge.getPenalties();

    public SelectPenalty() {
        setTitle("Select Penalty");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        random = new Random();

        createComponents();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/icon.png")));
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
        wheelPanel.setLayout(new GridLayout(1, 1));
        wheelPanel.setBackground(Color.WHITE);
        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Press 'Get Penalty' to start<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        JButton spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Get Penalty<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setBackground(Color.GREEN);
        spinButton.setPreferredSize(new Dimension(600, 100));

        resultPanel.add(wheelPanel, BorderLayout.CENTER);
        resultPanel.add(spinButton, BorderLayout.SOUTH);

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
        String selectedItem = selectRandomItem();
        resultLabel.setText("<html><div style='font-size: 20px; text-align: center; color: black;'>Penalty:<br>" + selectedItem + "</div></html>");
    }

    private String selectRandomItem() {
        int index = random.nextInt(penalties.length);
        if (penalties[index][1].equals("")){
            return penalties[index][0];
        } else {
            return penalties[index][1];
        }
    }

}