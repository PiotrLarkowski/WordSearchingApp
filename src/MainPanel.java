import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    Thread mainThread;
    final int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();
    public static ArrayList<String> selectedWords = new ArrayList<>();
    public static ArrayList<String> secondSelectedWords = new ArrayList<>();
    public static JTextArea resultArea;
    public static JTextField inputValue;

    public MainPanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(40, 190, 190));
        this.setLayout(null);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        inputValue = new JTextField();
        inputValue.setBounds(100, 50, 800, 50);
        inputValue.setFont(new Font("Cambria", Font.BOLD, 32));

        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) inputValue.getDocument()).setDocumentFilter(filter);

        inputValue.addKeyListener(new KeyListener() {
                                      @Override
                                      public void keyTyped(KeyEvent e) {

                                      }

                                      @Override
                                      public void keyPressed(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode == KeyEvent.VK_ENTER) {
                                              printResult();
                                          }
                                      }

                                      @Override
                                      public void keyReleased(KeyEvent e) {

                                      }
                                  });
                add(inputValue);

        resultArea = new JTextArea();
        resultArea.setBounds(100, 130, 800, Toolkit.getDefaultToolkit().getScreenSize().height-300);
        resultArea.setEnabled(true);
        add(resultArea);

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


    static void requestFocusOnPanel() {
        inputValue.requestFocus();
    }

    static void printResult() {
        availableWordsList = searchingWords();
        for (int i = 0; i < availableWordsList.size(); i++) {
            resultArea.setText(availableWordsList.get(i) + allWordsDescriptionList.get(i) + "\n");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawRect(99, 49, 801, 51);
        g2.drawRect(99, 129, 801, Toolkit.getDefaultToolkit().getScreenSize().height-299);
        finalResultWordAndDescriptions.clear();
    }

    public static ArrayList<String> downloadWordsFile() {
        ArrayList<String> allWords = new ArrayList<>();
        StringBuilder singleWordBuilder = new StringBuilder();
        StringBuilder singleDescriptionBuilder = new StringBuilder();
//        File file = new File("D:\\KRZYZOWKA\\wyrazy_2024.txt");
        File file = new File("C:\\Users\\PC\\Documents\\wyrazy_THREE.txt");
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

        String text = inputValue.getText();
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

class UppercaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
                             String text, AttributeSet attr) throws BadLocationException {
        fb.insertString(offset, text.toUpperCase(), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                        String text, AttributeSet attrs) throws BadLocationException {

        fb.replace(offset, length, text.toUpperCase(), attrs);
    }
}
