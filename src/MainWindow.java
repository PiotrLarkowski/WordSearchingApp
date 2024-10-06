import javax.swing.*;
import java.awt.*;

public class MainWindow{
    static JComboBox lengthList = new JComboBox();;
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setBackground(Color.getHSBColor(130,220,210));
        window.setTitle("Wyszukiwarka slow");

        MainPanel mainPanel = new MainPanel();

        window.add(mainPanel);
        window.pack();

        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}