import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainPanel extends JPanel implements Runnable {
    static ArrayList<String> availableWordsList = new ArrayList<>();
    static ArrayList<String> finalResultWordAndDescriptions = new ArrayList<>();
    static ArrayList<String> allWordsDescriptionList = new ArrayList<>();
    public static ArrayList<String> wordsList = new ArrayList<>();
    static final int WIDTH = 1000;
    static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    JButton resetFocus;
    Thread mainThread;
    final int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();
    static StringBuilder resultData = new StringBuilder();
    static StringBuilder searchingInputData = new StringBuilder();
    public static ArrayList<String> selectedWords = new ArrayList<>();
    public static ArrayList<String> secondSelectedWords = new ArrayList<>();
    public static JTextArea resultArea;

    public MainPanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(40, 190, 190));
        this.setLayout(null);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        resultArea = new JTextArea();
        resultArea.setBounds(100, 130, 800, 800);
        resultArea.setEnabled(true);
        add(resultArea);

        resetFocus = new JButton("RESET");
        resetFocus.setBounds(0,0,10,10);
        resetFocus.addKeyListener(
                new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            requestFocusOnPanel();
                        }
                    }
        });
        add(resetFocus);
        launchProgram();

    }

    public void launchProgram() {
        mainThread = new Thread(this);
        mainThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;
        while (mainThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public static void paintCharacter(char character) {
        searchingInputData.append(character).append("  ");
    }

    public static void removeLastCharacter() {
        searchingInputData.setLength(Math.max(searchingInputData.length() - 1, 0));
    }

    public static void resetStringBuilder() {
        searchingInputData = new StringBuilder();
        resultData = new StringBuilder();
    }

    void requestFocusOnPanel(){
        requestFocusInWindow();
    }

    static void printResult() {
        availableWordsList = searchingWords();
        for (int i = 0; i < availableWordsList.size(); i++) {
            resultArea.setText(availableWordsList.get(i) + allWordsDescriptionList.get(i)+"\n");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawBlankField(g2, 50, 50);
        drawGivenString(g2, searchingInputData.toString(), 105, 90, 38);


        finalResultWordAndDescriptions.clear();
    }

    private static void drawGivenString(Graphics2D g2, String text, int x, int y, int size) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Cambria", Font.BOLD, size));
        g2.drawString(text, x, y);
    }

    private static void drawBlankField(Graphics2D g2, int y, int height) {
        g2.setColor(Color.BLACK);
        g2.drawRect(100 - 1, y - 1, 800 + 1, height + 1);
        g2.setColor(Color.WHITE);
        g2.fillRect(100, y, 800, height);
    }

    public static ArrayList<String> downloadWordsFile() {
        ArrayList<String> allWords = new ArrayList<>();
        StringBuilder singleWordBuilder = new StringBuilder();
        StringBuilder singleDescriptionBuilder = new StringBuilder();
        File file = new File("D:\\KRZYZOWKA\\wyrazy_2024.txt");
//        File file = new File("C:\\Users\\PC\\Documents\\wyrazy_2024.txt");
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) != ' ') {
                        singleWordBuilder.append(word.charAt(i));
                    } else {
                        for (int j = i; j < word.length(); j++) {
                            singleDescriptionBuilder.append(word.charAt(j));
                        }
                        allWordsDescriptionList.add(singleDescriptionBuilder.toString());
                        break;
                    }
                }
                wordsList.add(singleWordBuilder.toString());
                singleDescriptionBuilder = new StringBuilder();
                singleWordBuilder = new StringBuilder();
                System.out.println(word);
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Read File Problem");
        }
        return allWords;
    }

    private static ArrayList<String> searchingWords() {
        selectedWords.clear();
        searchingInputData = new StringBuilder(searchingInputData.toString().replaceAll("\\s", ""));

        String text = searchingInputData.toString();
        downloadWordsFile();
        boolean wordPass;
        for (String s : wordsList) {
            if (s.length() == text.length()) {
                selectedWords.add(s);
            }
        }
        for (String selectedWord : selectedWords) {
            wordPass = true;
            for (int j = 0; j < selectedWord.length(); j++) {
                if (text.charAt(j) != '-') {
                    if (selectedWord.charAt(j) != text.charAt(j)) {
                        wordPass = false;
                        break;
                    }
                }
            }
            if (wordPass) {
                secondSelectedWords.add(selectedWord);
            }
        }
        return (secondSelectedWords);
    }
}
