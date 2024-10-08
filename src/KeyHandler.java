import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            MainPanel.printResult();
        }else if(e.getKeyCode() == KeyEvent.VK_TAB){
        }else if(e.getKeyCode() == KeyEvent.VK_CAPS_LOCK){
        }else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            MainPanel.removeLastCharacter();
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            MainPanel.paintCharacter('-');
        }else if(e.getKeyCode() == KeyEvent.VK_UP){

        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){

        }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            MainPanel.resetStringBuilder();
        }else{
            MainPanel.paintCharacter((char)e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
