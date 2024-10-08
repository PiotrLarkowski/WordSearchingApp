import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            MainPanel.resetStringBuilder();
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            MainPanel.paintCharacter('-');
        }else if(e.getKeyCode() == KeyEvent.VK_UP){

        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){

        }else{
            MainPanel.paintCharacter((char)e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
