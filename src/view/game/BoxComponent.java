package view.game;

import view.game.GamePanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.util.TimerTask;

public class BoxComponent extends JComponent {
    private Color color;//保留颜色作为备用
    private int row;
    private int col;
    private boolean isSelected;
    private Image image; //新的贴图属性
    /*private int logicalRow;  // 逻辑位置行
    private int logicalCol;  // 逻辑位置列
    private Point visualPosition; // 视觉位置
    private Timer animationTimer;
    private float animationProgress;
    private int GRID_SIZE = 120;

     */

    public BoxComponent(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        isSelected = false;
        /*
        this.logicalRow = row;
        this.logicalCol = col;
        this.visualPosition = new Point(col * 120, row * 120);
        setLocation(visualPosition);

         */

        //直接根据颜色选择图片加载，这样就不用改其他逻辑了
        try {
            String imageName = "";
            if (color == Color.ORANGE) imageName = "soldier.png";
            else if (color == Color.PINK) imageName = "GuanYu.png";
            else if (color == Color.BLUE) imageName = "general.png";
            else if (color == Color.GREEN) imageName = "CaoCao.png";
            if (!imageName.isEmpty()) {
                this.image = new ImageIcon("resources/images/blocks1/" + imageName).getImage();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("图片加载失败");
            this.image = null; //图片加载失败就算了
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //先尝试用贴图
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
        //贴图万一失败了就用原来的颜色
        else {
            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Border border ;
        if(isSelected) {
            Border outer1 = BorderFactory.createRaisedBevelBorder();
            Border inner1 = BorderFactory.createRaisedBevelBorder();
            Border inner2 = BorderFactory.createCompoundBorder(outer1, inner1);
            Border outer2 = BorderFactory.createLineBorder(new Color(156, 108, 60), 5);
            border = BorderFactory.createCompoundBorder(outer2, inner2);
        }
        else {
            Border outer = BorderFactory.createLoweredBevelBorder();
            Border inner = BorderFactory.createLoweredBevelBorder();
            border = BorderFactory.createCompoundBorder(outer, inner);
        }
        this.setBorder(border);
    }
/*
    //动画部分代码
    // 开始移动动画
    public void startMoveAnimation(int targetRow, int targetCol, Runnable onComplete) {
        Point targetPos = new Point(targetCol * GRID_SIZE, targetRow * GRID_SIZE);

        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        animationProgress = 0f;
        animationTimer = new Timer(16, e -> { // ~60fps
            animationProgress += 0.1f; // 调整这个值改变动画速度
            if (animationProgress >= 1f) {
                animationProgress = 1f;
                animationTimer.stop();
                onComplete.run(); // 动画完成回调
            }

            // 使用缓动函数计算插值
            float eased = easeOutCubic(animationProgress);

            // 计算中间位置
            int newX = (int)(visualPosition.x + (targetPos.x - visualPosition.x) * eased);
            int newY = (int)(visualPosition.y + (targetPos.y - visualPosition.y) * eased);

            visualPosition.setLocation(newX, newY);
            setLocation(visualPosition);
            repaint();
        });
        animationTimer.start();
    }

    // 立方缓出函数 - 更平滑的动画效果
    private float easeOutCubic(float t) {
        return (float)(1 - Math.pow(1 - t, 3));
    }

    // 立即更新逻辑位置（无动画）
    public void setLogicalPosition(int row, int col) {
        this.logicalRow = row;
        this.logicalCol = col;
        this.visualPosition.setLocation(col * GRID_SIZE, row * GRID_SIZE);
        setLocation(visualPosition);
    }

 */

    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
/*
    // 获取逻辑位置
    public int getLogicalRow() { return logicalRow; }
    public int getLogicalCol() { return logicalCol; }
*/
}
