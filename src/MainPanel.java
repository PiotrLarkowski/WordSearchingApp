import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements Runnable{
    static final int WIDTH = 1000;
    static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    Thread mainThread;
    final int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();

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
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawRect(99, 49, 501, 51);
        g2.setColor(Color.WHITE);
        g2.fillRect(100, 50, 500, 50);
    }
}
