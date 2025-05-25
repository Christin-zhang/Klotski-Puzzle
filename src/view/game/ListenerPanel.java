package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * This class is only to enable key events.完成的就是事件触发的功能
 */
public abstract class ListenerPanel extends JPanel {
    public ListenerPanel() {
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setFocusable(true);//对于键盘点击的事件一定要设置可聚焦
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> doMoveRight();
                case KeyEvent.VK_LEFT -> doMoveLeft();
                case KeyEvent.VK_UP -> doMoveUp();
                case KeyEvent.VK_DOWN -> doMoveDown();
            }
        }
    }
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            doMouseClick(e.getPoint());
        }
    }
    //以下抽象方法都需要下推到子类实现
    public abstract void doMouseClick(Point point);

    public abstract void doMoveRight();

    public abstract void doMoveLeft();

    public abstract void doMoveUp();

    public abstract void doMoveDown();

}
