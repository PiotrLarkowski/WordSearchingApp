import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MainWindow extends JFrame implements ActionListener {
    public static ArrayList<String> wordsList = new ArrayList<>();
    public static ArrayList<String> selectedWords = new ArrayList<>();
    public static ArrayList<String> secondSelectedWords = new ArrayList<>();
    public static JButton searchWordsWithGivenLength;
    public static JTextArea exampleWords;
    public static JTextField informationOfSearchingWord;
    public static JTextArea descriptionOfExampleWord;

    public MainWindow() {
        createJButton();
        createSearchingBar();
        createAreaTextFields();
    }

    private void createSearchingBar() {
        informationOfSearchingWord = new JTextField("");
        informationOfSearchingWord.setFocusable(true);
        informationOfSearchingWord.setFont(new Font("Cambria", Font.BOLD, 32));
        informationOfSearchingWord.setBounds(100, 50, 500, 50);
        add(informationOfSearchingWord);
    }

    private void createAreaTextFields() {
        exampleWords = new JTextArea();
        exampleWords.setEnabled(false);
        exampleWords.setWrapStyleWord(true);
        exampleWords.setLineWrap(true);
        exampleWords.setEditable(false);
        exampleWords.setBounds(100, 200, 1300, 250);
        exampleWords.setFont(new Font("Cambria", Font.PLAIN, 32));
        add(exampleWords);

        descriptionOfExampleWord = new JTextArea();
        descriptionOfExampleWord.setEnabled(false);
        descriptionOfExampleWord.setWrapStyleWord(true);
        descriptionOfExampleWord.setLineWrap(true);
        descriptionOfExampleWord.setEditable(false);
        descriptionOfExampleWord.setBounds(100, 700, 600, 400);
        descriptionOfExampleWord.setFont(new Font("Cambria", Font.PLAIN, 20));
        add(descriptionOfExampleWord);
    }

    private void createJButton() {
        searchWordsWithGivenLength = new JButton("Szukaj");
        searchWordsWithGivenLength.setBounds(650, 50, 100, 50);
        searchWordsWithGivenLength.addActionListener(this);
        add(searchWordsWithGivenLength);
    }

    public static void main(String[] args) {
        createMainWindow();
    }

    public static void createMainWindow() {
        MainWindow window = new MainWindow();

        window.setSize(1000,Toolkit.getDefaultToolkit().getScreenSize().height);
        window.setResizable(false);
        window.setTitle("Wyszukiwarka slow");

        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

    public static ArrayList<String> downloadWordsFile() {
        ArrayList<String> allWords = new ArrayList<>();
        File file = new File("D:\\KRZYZOWKA\\wyrazy_2024.txt");
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

    private static void searchingWords() {
        selectedWords.clear();
        String text = informationOfSearchingWord.getText().toUpperCase();
        boolean wordPass;
        for (String s : wordsList) {
            if (s.length() == text.length()) {
                selectedWords.add(s);
            }
        }
        for (String selectedWord : selectedWords) {
            wordPass = true;
            for (int j = 0; j < selectedWord.length(); j++) {
                if(text.charAt(j) != '-'){
                    if (selectedWord.charAt(j) != text.charAt(j)) {
                        wordPass = false;
                        break;
                    }
                }
            }
            if(wordPass){
                secondSelectedWords.add(selectedWord);
            }
        }
        for (int i = 0; i < secondSelectedWords.size(); i++) {
            searchingWordsDescription();
        }
    }

    public static void printFindWords() {
        exampleWords.setText("");
        for (int i = 0; i < secondSelectedWords.size(); i++) {
            exampleWords.setText(exampleWords.getText() + ", " + secondSelectedWords.get(i));
        }
    }

    public static void searchingWordsDescription() {
        //TODO
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == searchWordsWithGivenLength) {
            downloadWordsFile();
            searchingWords();
            printFindWords();
        }
    }
}