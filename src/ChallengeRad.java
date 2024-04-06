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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChallengeRad extends JFrame {
    private String[][] elements;
    private Random random;
    private JLabel resultLabel;
    private static JButton spinButton;
    public static String[][] items = readElementsFromJSON("/items.json", "itemtype");

    public ChallengeRad(String[][] elements) {
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
        //TODO set color to white and button to green/red
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
                resultLabel.setText("<html><div style='font-size: 18px; text-align: center;'>" + elements[currentIndex][0] + "</div></html>");
                currentIndex = (currentIndex + 1) % elements.length;

                count++;
                if (count >= spins) {
                    ((Timer) e.getSource()).stop();
                    if (elements[currentIndex][1].equals("true")) { // this block opens the SelectItemFrame-Window and sets the custom message
                        setLabelText(currentIndex);
                        new SelectItemFrame();
                    } else if (elements[currentIndex][0].contains("[items]")) { // 2-12 random items
                        int numberOfItems = 2 + random.nextInt(11);
                        StringBuilder selectedItemsText = new StringBuilder("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 20px; text-align: center; color: black;'>" + elements[currentIndex][2] + "<br>");
                        HashSet<Integer> selectedIndexes = new HashSet<>();
                        while (selectedIndexes.size() < numberOfItems) {
                            int randomIndex = random.nextInt(items.length);
                            if (!selectedIndexes.contains(randomIndex)) {
                                selectedIndexes.add(randomIndex);
                                selectedItemsText.append("<div style='font-size: 18px; text-align: center;'>").append(items[randomIndex][0]).append("</div>");
                            }
                        }
                        selectedItemsText.append("</div></html>");
                        resultLabel.setText(selectedItemsText.toString());
                        setButton(true);
                    } else if (elements[currentIndex][0].contains("[numb]")) { // random number between 1 and 8
                        int numberOfItems = 1 + random.nextInt(8);
                        if (elements[currentIndex][2].isEmpty()) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>" + elements[currentIndex][0].replace("[numb]", String.valueOf(numberOfItems)) + "</div></html>");
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>" + elements[currentIndex][2].replace("[numb]", String.valueOf(numberOfItems)) + "</div></html>");
                        }
                        setButton(true);
                    } else {
                        setLabelText(currentIndex);
                        setButton(true);
                    }
                }
            }
        });
        timer.start();
    }

    private void setLabelText(int index) {
        if (elements[index][2].isEmpty()) {
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>" + elements[index][0] + "</div></html>");
        } else {
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>Die Challenge für diese Runde lautet:<br><div style='font-size: 18px; text-align: center; color: black;'>" + elements[index][2] + "</div></html>");
        }
    }

    public static String[][] readElementsFromJSON(String filename, String varname) {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = ChallengeRad.class.getResourceAsStream("/resources" + filename)) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            String[][] elements = new String[jsonArray.size()][3];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                //JSONArray item = (JSONArray) jsonArray.get(i);
                System.out.println(item.toString());
                elements[i][0] = (String) item.get("text"); // Text
                elements[i][1] = (String) item.get(varname); // Variable
                elements[i][2] = (String) item.get("message"); // additional Text
            }
            return elements;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }

    public static void main(String[] args) {
        String[][] wheelElements = readElementsFromJSON("/tasks.json", "openSelectItemFrame");
        //String[] wheelElements = readElementsFromJSON("A:/Dokumente/Privat/PhasmoChalGenerator/PhasmoChallengeGenerator/out/production/PhasmoChallengeGenerator/resources/tasks.json");

        for (int i = 0; i < wheelElements.length; i++) {
            System.out.println(i + ": " + wheelElements[i][0] + "; " + wheelElements[i][1]);
        }
        System.out.println();
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ": " + items[i][0] + "; " + items[i][1]);
        }
        SwingUtilities.invokeLater(() -> {
            ChallengeRad wheelGUI = new ChallengeRad(wheelElements);
            wheelGUI.setVisible(true);
        });
    }
}