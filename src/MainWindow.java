import javax.swing.*;

public class MainWindow extends JFrame {

    public static void main(String[] args) {
        createMainWindow();
    }

    public static void createMainWindow() {
        JFrame window = new JFrame("Wyszukiwarka slow");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        MainPanel gp = new MainPanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}