import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
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
    public static ArrayList<String> selectedWords = new ArrayList<>();
    public static ArrayList<String> secondSelectedWords = new ArrayList<>();
    public static JTextArea resultArea;
    public static JTextField inputValue;

    public MainPanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(40, 190, 190));
        this.setLayout(null);

//        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        inputValue = new JTextField() {

            public void addNotify() {
                super.addNotify();
                requestFocus();
            }
        };
        inputValue.setBounds(100, 50, 800, 50);
        inputValue.setFont(new Font("Cambria", Font.BOLD, 32));

        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) inputValue.getDocument()).setDocumentFilter(filter);

        inputValue.addKeyListener(new KeyListener() {
            boolean keyPressed = false;

                                      @Override
                                      public void keyTyped(KeyEvent e) {

                                      }

                                      @Override
                                      public void keyPressed(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if(!keyPressed) {
                                              keyPressed = true;
                                              if (keyCode == KeyEvent.VK_ENTER) {
                                                  printResult();
                                              } else if (keyCode == KeyEvent.VK_SPACE) {
                                                  inputValue.setText(inputValue.getText() + " - ");
                                                  if (inputValue.getText() != null) {
                                                      inputValue.setText(inputValue.getText().substring(0, inputValue.getText().length() - 1));
                                                  }
                                              }else if(keyCode == KeyEvent.VK_BACK_SPACE){
                                                  if (inputValue.getText() != null && inputValue.getText().length()>2) {
                                                      inputValue.setText(inputValue.getText().substring(0, inputValue.getText().length() - 1));
                                                  }
                                              }else {
                                                  inputValue.setText(inputValue.getText() + " " + (char) e.getKeyCode());
                                                  if (inputValue.getText() != null) {
                                                      inputValue.setText(inputValue.getText().substring(0, inputValue.getText().length() - 1));
                                                  }
                                              }
                                          }
                                          keyPressed = false;
                                      }
                                      @Override
                                      public void keyReleased(KeyEvent e) {

                                      }
                                  });
                add(inputValue);
        inputValue.requestFocus();

        resultArea = new JTextArea();
        resultArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(20, 20, 0, 0 ),
                        resultArea.getBorder()
                ));
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



    static void printResult() {
        resultArea.setText("");
        availableWordsList = searchingWords();
        for (int i = 0; i < availableWordsList.size()-1; i++) {
            if(!availableWordsList.get(i).isEmpty()){
                resultArea.setText(resultArea.getText() + availableWordsList.get(i) + allWordsDescriptionList.get(i) + "\n");
            }
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
      File file = new File("D:\\KRZYZOWKA\\wyrazy_2024.txt");
//        File file = new File("C:\\Users\\PC\\Documents\\wyrazy_2024.txt");
        try {
            int numberOfWord = 0;
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
                        allWordsDescriptionList.add(numberOfWord, singleDescriptionBuilder.toString());
                        break;
                    }
                }
                allWords.add(numberOfWord, singleWordBuilder.toString());
                singleDescriptionBuilder = new StringBuilder();
                singleWordBuilder = new StringBuilder();
                numberOfWord++;
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
        ArrayList<Integer> wordPosition = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputValue.getText().length(); i++) {
            if(inputValue.getText().charAt(i)!=' '){
                stringBuilder.append(inputValue.getText().charAt(i));
            }
        }
        String text = stringBuilder.toString();
        wordsList = downloadWordsFile();
        boolean wordPass;
        for (int i = 0; i < wordsList.size(); i++) {
            selectedWords.add(i,"");
            secondSelectedWords.add(i,"");
            wordPosition.add(0);
        }
        for (int i = 0; i < wordsList.size(); i++) {
            if (wordsList.get(i).length() == text.length()) {
                selectedWords.add(i, wordsList.get(i));
                wordPosition.add(i, i);
            }
        }
        for (int i = 1; i < selectedWords.size(); i++) {
            wordPass = true;
            for (int j = 0; j < selectedWords.get(i).length(); j++) {
                if (text.charAt(j) != '-') {
                    if (selectedWords.get(i).charAt(j) != text.charAt(j)) {
                        wordPass = false;
                        break;
                    }
                }
            }
            if (wordPass) {
                secondSelectedWords.set(wordPosition.get(i), selectedWords.get(i));
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
