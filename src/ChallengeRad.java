import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChallengeRad extends JFrame {
    private JLabel resultLabel;
    private static JPanel buttonPanel;
    private static JButton spinButton;

    private static String startMessage1;
    private static String startMessage2;
    private static String challengeMessage;
    private static String playerNumberText;
    private static long maxPlayerNumber;
    private Random random;
    private static int playerNum;
    private static long waitingTime;
    private static String[] playerColors;
    private static final WheelElement[] wheelElements = readElementsFromJSON("/tasks.json");
    public static String[][] items;

    public ChallengeRad() {
        this.random = new Random();

        setTitle("Challenge-Wheel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);

        createComponents();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/icon.png")));
        setIconImage(icon.getImage());
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel playerNumPanel = new JPanel(new GridLayout(2, 1));

        JLabel playerNumLabel = new JLabel("<html><div style='font-size: 23px; text-align: center;'>" + playerNumberText + "</div></html>");
        playerNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNumPanel.setFont(new Font("Arial", Font.BOLD, 35));
        playerNumPanel.add(playerNumLabel, BorderLayout.AFTER_LAST_LINE);

        buttonPanel = new JPanel();

        for (int i = 0; i < maxPlayerNumber; i++) {
            JPanel buttonPanelTemp = new JPanel();
            buttonPanel.add(buttonPanelTemp, BorderLayout.CENTER);
            JButton button = new JButton("" + (i + 1));
            button.setBackground(Color.WHITE);
            button.addActionListener(new PlayerNumButtonListener());
            button.setFont(new Font("Arial", Font.BOLD, 25));
            buttonPanelTemp.add(button);
        }
        playerNumPanel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

        JPanel westBlock = new JPanel();
        westBlock.setPreferredSize(new Dimension(100, 800));
        westBlock.setBackground(Color.WHITE);

        JPanel eastBlock = new JPanel();
        eastBlock.setPreferredSize(new Dimension(100, 800));
        eastBlock.setBackground(Color.WHITE);

        JPanel wheelPanel = new JPanel();
        wheelPanel.setLayout(new GridLayout(1, 1));
        wheelPanel.setBackground(Color.WHITE);
        resultLabel = new JLabel("<html><div style='font-size: 26px; text-align: center;'>" + startMessage1 + "<br></div></html>");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wheelPanel.add(resultLabel);

        spinButton = new JButton("<html><div style='font-size: 22px; text-align: center;'>Spin<br></div></html>");
        spinButton.addActionListener(new SpinButtonListener());
        spinButton.setPreferredSize(new Dimension(1200, 100));
        spinButton.setFont(new Font("Arial", Font.BOLD, 22));
        spinButton.setBackground(Color.GREEN);
        setButton(false);

        mainPanel.add(playerNumPanel, BorderLayout.NORTH);
        mainPanel.add(westBlock, BorderLayout.WEST);
        mainPanel.add(eastBlock, BorderLayout.EAST);
        mainPanel.add(wheelPanel, BorderLayout.CENTER);
        mainPanel.add(spinButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public class PlayerNumButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            playerNum = Integer.parseInt(button.getText());
            System.out.println("new playerNum: " + playerNum);
            setAllButtons(true);
            button.setEnabled(false);
            button.setBackground(Color.GREEN);
            resultLabel.setText("<html><div style='font-size: 26px; text-align: center;'>" + startMessage2 + "<br></div></html>");
        }
    }

    public class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            spinWheel();
        }
    }

    public static void setAllButtons(boolean boo) {
        setPlayerNumberButtons(boo, false);
        setButton(boo);
    }

    public static void setAllButtonsSavePlay(boolean boo) {
        setPlayerNumberButtons(boo, true);
        setButton(boo);
    }

    private static void setPlayerNumberButtons(boolean boo, boolean savePlayerNumbButton) {
        for (int i = 0; i < maxPlayerNumber; i++) {
            JPanel panel = (JPanel) buttonPanel.getComponent(i);
            JButton button = (JButton) panel.getComponent(0);
            if ((Integer.parseInt(button.getText()) != playerNum) || !savePlayerNumbButton) {
                button.setEnabled(boo);
                button.setBackground(Color.WHITE);
            }
        }
    }

    private static void setButton(boolean boo) {
        spinButton.setEnabled(boo);
        if (boo) {
            spinButton.setBackground(Color.GREEN);
        } else {
            spinButton.setBackground(Color.RED);
        }
    }

    private void spinWheel() {
        setAllButtonsSavePlay(false);
        int spins = 20 + random.nextInt(wheelElements.length);
        int delay = 100;

        Timer timer = new Timer(delay, new ActionListener() {
            int count = 0;
            int currentIndex = 0;

            public void actionPerformed(ActionEvent e) {
                if (wheelElements[currentIndex].message().isEmpty()) {
                    resultLabel.setText("<html><div style='font-size: 18px; text-align: center;'>" + wheelElements[currentIndex].text() + "</div></html>");
                } else {
                    resultLabel.setText("<html><div style='font-size: 18px; text-align: center;'>" + wheelElements[currentIndex].message() + "</div></html>");
                }

                currentIndex = (currentIndex + 1) % wheelElements.length;

                count++;
                if (count >= spins && wheelElements[currentIndex].containsRightPlayerNum(playerNum)) {
                    ((Timer) e.getSource()).stop();
                    if (wheelElements[currentIndex].openSelectItemFrame()) { // this block opens the SelectItemFrame-Window and sets the custom message
                        setLabelText(currentIndex);
                        new SelectItemFrame(waitingTime);
                    } else if (wheelElements[currentIndex].text().contains("[$items$]")) { // 2-12 random items
                        int numberOfItems = 2 + random.nextInt(11);
                        StringBuilder selectedItemsText = new StringBuilder("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 20px; text-align: center; color: black;'>" + wheelElements[currentIndex].message() + "<br>");
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
                        setAllButtonsSavePlay(true);
                    } else if (wheelElements[currentIndex].text().contains("[$numb$]")) { // random number between 1 and 8
                        int numberOfItems = 1 + random.nextInt(8);
                        if (wheelElements[currentIndex].message().contains("[$numb$]")) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + wheelElements[currentIndex].message().replace("[$numb$]", String.valueOf(numberOfItems)) + "</div></html>");
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + wheelElements[currentIndex].text().replace("[$numb$]", String.valueOf(numberOfItems)) + "</div></html>");
                        }
                        setAllButtonsSavePlay(true);
                    } else if (wheelElements[currentIndex].text().contains("[$color$]")) {
                        int num = playerColors.length;
                        if (playerNum < num) {
                            num = playerNum;
                        }
                        int rand = random.nextInt(num); //TODO edit this when player number is implemented
                        //System.out.println("playNum: " + playerNum + " colors: " + playerColors.length + " num: " + num + " rand: " + rand);
                        if (wheelElements[currentIndex].message().contains("[$color$]")) {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + wheelElements[currentIndex].message().replace("[$color$]", String.valueOf(playerColors[rand])) + "</div></html>");
                        } else {
                            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + wheelElements[currentIndex].text().replace("[$color$]", String.valueOf(playerColors[rand])) + "</div></html>");
                        }
                        setAllButtonsSavePlay(true);
                    } else {
                        setLabelText(currentIndex);
                        setAllButtonsSavePlay(true);
                    }
                }
            }
        });
        timer.start();
    }

    private void setLabelText(int index) {
        if (wheelElements[index].message().isEmpty()) {
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + wheelElements[index].text() + "</div></html>");
        } else {
            resultLabel.setText("<html><div style='font-size: 22px; text-align: center; color: red;'>" + challengeMessage + "<br><div style='font-size: 18px; text-align: center; color: black;'>" + wheelElements[index].message() + "</div></html>");
        }
    }

    public static void getItemsFromJSON() {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = ChallengeRad.class.getResourceAsStream("/resources/items.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            items = new String[jsonArray.size()][3];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                items[i][0] = (String) item.get("text"); // Text
                items[i][1] = (String) item.get("itemType"); // Variable
                items[i][2] = (String) item.get("message"); // additional Text
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static WheelElement[] readElementsFromJSON(String filename) {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = ChallengeRad.class.getResourceAsStream("/resources" + filename)) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            WheelElement[] elements = new WheelElement[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                JSONArray array = (JSONArray) item.get("reqPlayers");
                int[] reqPlayers = new int[array.size()];
                for (int j = 0; j < array.size(); j++) {
                    reqPlayers[j] = Integer.parseInt(array.get(j).toString());
                }
                elements[i] = new WheelElement((String) item.get("text"), (Boolean) item.get("openSelectItemFrame"), (String) item.get("message"), reqPlayers);
            }
            return elements;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new WheelElement[0];
        }
    }

    private static void initSettingsFromJSON() {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = ChallengeRad.class.getResourceAsStream("/resources/settings.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));

            JSONObject jsonObject = (JSONObject) obj;

            startMessage1 = jsonObject.get("start-message-1").toString();
            startMessage2 = jsonObject.get("start-message-2").toString();
            challengeMessage = jsonObject.get("challenge-message").toString();
            playerNumberText = jsonObject.get("player-number").toString();
            maxPlayerNumber = (long) jsonObject.get("max-player-number");
            waitingTime = (long) jsonObject.get("waiting-time-for-new-item");

            JSONArray jsonArray = (JSONArray) jsonObject.get("player-colors");
            playerColors = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                playerColors[i] = jsonArray.get(i).toString();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initSettingsFromJSON();
        getItemsFromJSON();

        System.out.println(wheelElements.length);

        //TODO update README.md
        //TODO new ui design

        System.out.println("Start-message: " + startMessage1);
        System.out.println("challenge-message: " + challengeMessage);
        System.out.println("waiting-time: " + waitingTime);
        System.out.println();
        for (int i = 0; i < wheelElements.length; i++) {
            System.out.println(i + ": " + wheelElements[i].text() + "; " + wheelElements[i].openSelectItemFrame());
        }
        System.out.println();
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ": " + items[i][0] + "; " + items[i][1]);
        }
        SwingUtilities.invokeLater(() -> {
            ChallengeRad wheelGUI = new ChallengeRad();
            wheelGUI.setVisible(true);
        });
    }
}