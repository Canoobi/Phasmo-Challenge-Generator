import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChallengeRad extends JFrame {
    private String[] elements;
    private Random random;
    private JLabel resultLabel;
    private static JButton spinButton;
    public static String[] items = readElementsFromJSON("/items.json");

    public ChallengeRad(String[] elements) {
        this.elements = elements;
        this.random = new Random();

        setTitle("Challenge-Wheel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        createComponents();

        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/icon.png"));
        setIconImage(icon.getImage());
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel wheelPanel = new JPanel();
        wheelPanel.setLayout(new GridLayout(1, 1));
        wheelPanel.setBackground(Color.GREEN);
        resultLabel = new JLabel("<html><div style='font-size: 22px; text-align: center;'>Drücke 'Spin' um zu starten<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Spin<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setPreferredSize(new Dimension(200, 80)); //button size

        mainPanel.add(wheelPanel, BorderLayout.CENTER);
        mainPanel.add(spinButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            spinWheel();
        }
    }

    public static void setButton(boolean boo) {
        spinButton.setEnabled(boo);
    }

    private void spinWheel() {
        setButton(false);
        int spins = 20 + random.nextInt(50); // Number of spins
        int delay = 100; // Delay between each spin

        Timer timer = new Timer(delay, new ActionListener() {
            int count = 0;
            int currentIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                resultLabel.setText("<html><div style='font-size: 18px; text-align: center;'>" + elements[currentIndex] + "</div></html>");
                currentIndex = (currentIndex + 1) % elements.length;

                count++;
                if (count >= spins) {
                    ((Timer) e.getSource()).stop();
                    if (currentIndex == 23) {
                        resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>Jede Minute darf ein Item gezogen werden. Siehe extra Fenster.</div></html>");
                        new SelectItemFrame();
                    } else if (currentIndex == 24) {
                        int numberOfItems = 2 + random.nextInt(11);
                        StringBuilder selectedItemsText = new StringBuilder("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 20px; text-align: center; color: black;'>Die folgenden Items dürfen diese Runde nicht verwendet werden:<br>");
                        HashSet<Integer> selectedIndexes = new HashSet<>();
                        while (selectedIndexes.size() < numberOfItems) {
                            int randomIndex = random.nextInt(items.length);
                            if (!selectedIndexes.contains(randomIndex)) {
                                selectedIndexes.add(randomIndex);
                                selectedItemsText.append("<div style='font-size: 18px; text-align: center;'>").append(items[randomIndex]).append("</div>");
                            }
                        }
                        selectedItemsText.append("</div></html>");
                        resultLabel.setText(selectedItemsText.toString());
                        setButton(true);
                    } else if (currentIndex == 25) {
                        int numberOfItems = random.nextInt(6);
                        if (numberOfItems == 1) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>Es darf 1 Item mit ins Haus genommen werden, dann ist man dort eingesperrt</div></html>");
                            setButton(true);
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>Es dürfen <b>" + numberOfItems + "</b> Items mit ins Haus genommen werden, dann ist man dort eingesperrt</div></html>");
                            setButton(true);
                        }
                    } else {
                        resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>" + elements[currentIndex] + "</div></html>");
                        setButton(true);
                    }
                }
            }
        });
        timer.start();
    }

    public static String[] readElementsFromJSON(String filename) {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = ChallengeRad.class.getResourceAsStream("/resources" + filename)) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            String[] elements = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                elements[i] = (String) jsonArray.get(i);
            }
            return elements;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public static void main(String[] args) {
        String[] wheelElements = readElementsFromJSON("/tasks.json");
        //String[] wheelElements = readElementsFromJSON("A:/Dokumente/Privat/PhasmoChalGenerator/PhasmoChallengeGenerator/out/production/PhasmoChallengeGenerator/resources/tasks.json");

        for (int i = 0; i < wheelElements.length; i++) {
            System.out.println(i + ": " + wheelElements[i]);
        }
        System.out.println();
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ": " + items[i]);
        }
        SwingUtilities.invokeLater(() -> {
            ChallengeRad wheelGUI = new ChallengeRad(wheelElements);
            wheelGUI.setVisible(true);
        });
    }
}