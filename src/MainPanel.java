import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements Runnable{
    Thread thread = new Thread();
    JComboBox lengthList;
    public MainPanel(){
        this.setPreferredSize(new Dimension(900,500));
        thread.start();
        paintComboBox();
    }

    public void paintComboBox(){
        String[] lengthStrings = {"3","4","5","7","13"};
        lengthList = new JComboBox(lengthStrings);
        lengthList.setSelectedIndex(4);

        lengthList.setBounds(650,100,50,50);
        this.add(lengthList);
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        drawSetOfRect(g2);
    }

    private void drawSetOfRect(Graphics2D g2) {
        int[] Coordinate = {100,160,220,280,340,400,460,520,580,580,640,700,760};
        int yCoordinate = 300;
        int selectedIndex = lengthList.getSelectedIndex();
        int numberOfWhiteRect = 13;

        g2.setFont(new Font("Cambria",Font.BOLD,32));
        g2.drawString("Ilu literowego slowa poszukujesz?", 100,135);

        for (int j=0;j<Coordinate.length;j++) {
            if(selectedIndex == 0){
                numberOfWhiteRect = 3;
            }else if(selectedIndex == 1){
                numberOfWhiteRect = 4;
            }else if(selectedIndex == 2){
                numberOfWhiteRect = 5;
            }else if(selectedIndex == 3){
                numberOfWhiteRect = 7;
            }
            System.out.println(numberOfWhiteRect);
            if(j>numberOfWhiteRect) {
                drawSetOfRect(g2, Coordinate[j], yCoordinate, Color.GRAY);
            }else{
                drawSetOfRect(g2, Coordinate[j], yCoordinate, Color.WHITE);
            }
        }
    }

    public void drawSetOfRect(Graphics2D g2, int x, int yCoordinate, Color color){
        g2.setColor(Color.black);
        g2.drawRect(x-1,yCoordinate-1,51,51);
        g2.setColor(color);
        g2.fillRect(x,yCoordinate,50,50);
    }

    public void update(){
        repaint();
    }
    @Override
    public void run() {

        update();

        repaint();
    }
}
