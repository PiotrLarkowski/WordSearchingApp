import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainPanel extends JPanel implements Runnable{
    public static ArrayList<String> wordsList = new ArrayList<>();
    static final int WIDTH = 1000;
    static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    Thread mainThread;
    final int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();
    static StringBuilder resultData = new StringBuilder();
    static StringBuilder searchingInputData = new StringBuilder();
    public static ArrayList<String> selectedWords = new ArrayList<>();
    public static ArrayList<String> secondSelectedWords = new ArrayList<>();
    public MainPanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(40,190,190));
        this.setLayout(null);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);

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
                update();
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
    public void update() {

    }

    public static void paintCharacter(char character){
        searchingInputData.append(character).append("  ");
    }
    public static void resetStringBuilder(){
        searchingInputData = new StringBuilder();
        resultData = new StringBuilder();
    }
    static void printResult(){
        ArrayList<String> list = searchingWords();
        for (String s : list) {
            resultData.append(s);
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawBlankField(g2, 50, 50);
        drawGivenString(g2, searchingInputData.toString(),105, 90);

        drawBlankField(g2, 130, 800);
//        drawGivenString(g2, "30 znakow i enter",110,170);
        drawGivenString(g2, resultData.toString(),110,170);
    }
    private static void drawGivenString(Graphics2D g2, String text, int x, int y){
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Cambria", Font.BOLD, 38));
        g2.drawString(text,x, y);
    }
    private static void drawBlankField(Graphics2D g2, int y, int height) {
        g2.setColor(Color.BLACK);
        g2.drawRect(100 -1, y-1, 800 +1, height+1);
        g2.setColor(Color.WHITE);
        g2.fillRect(100, y, 800, height);
    }
    public static ArrayList<String> downloadWordsFile() {
        ArrayList<String> allWords = new ArrayList<>();
//        File file = new File("D:\\KRZYZOWKA\\wyrazy_2024.txt");
        File file = new File("C:\\Users\\PC\\Documents\\wyrazy_2024.txt");
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                wordsList.add(myReader.nextLine());
                System.out.println(myReader.nextLine());
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
        return(secondSelectedWords);
    }
}
