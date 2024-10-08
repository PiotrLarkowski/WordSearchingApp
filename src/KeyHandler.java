import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            System.out.println("W");
        }else if(e.getKeyCode() == KeyEvent.VK_S) {
            System.out.println("S");
        }else if(e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("A");
        }else if(e.getKeyCode() == KeyEvent.VK_D) {
            System.out.println("D");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
