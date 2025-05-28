package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoxComponent extends JComponent {
    private Color color;//保留颜色作为备用
    private int row;
    private int col;
    private boolean isSelected;
    private Image image; //新的贴图属性

    public BoxComponent(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        isSelected = false;

        //直接根据颜色选择图片加载，这样就不用改其他逻辑了
        try {
            String imageName = "";
            if (color == Color.ORANGE) imageName = "soldier.png";
            else if (color == Color.PINK) imageName = "guanYu.png";
            else if (color == Color.BLUE) imageName = "general.png";
            else if (color == Color.GREEN) imageName = "CaoCao.png";
            if (!imageName.isEmpty()) {
                this.image = new ImageIcon("resources/images/blocks1/" + imageName).getImage();
            }
        } catch (Exception e){
            e.printStackTrace();
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
        if(isSelected){
            border = BorderFactory.createLineBorder(Color.red,3);
        }else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        }
        this.setBorder(border);
    }

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
}
