package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * It is the subclass of ListenerPanel, so that it should implement those four methods: do move left, up, down ,right.
 * The class contains a grids, which is the corresponding GUI view of the matrix variable in MapMatrix.
 */
public class GamePanel extends ListenerPanel {
    private List<BoxComponent> boxes;
    private MapModel model;
    private GameController controller;
    private JLabel stepLabel;
    private int steps;
    private final int GRID_SIZE = 120;
    private BoxComponent selectedBox;//记录哪一个箱子被选中
    private boolean hasWon = false;
    //与board有关的
    private Image boardBackground;
    private int boardOffsetX; // 棋盘水平偏移量
    private int boardOffsetY; // 棋盘垂直偏移量
    private int boardWidth;   // 棋盘绘制宽度
    private int boardHeight;  // 棋盘绘制高度

    public GamePanel(MapModel model) {
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;
        initialGame();
    }
    public int getSteps() {
        return steps;
    }

    public void initialGame() {
        this.steps = 0;
        //copy a map
        int[][] map = new int[model.getHeight()][model.getWidth()];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = model.getId(i, j);
            }
        }
        //build Component
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BoxComponent box = null;
                if (map[i][j] == 1) {
                    box = new BoxComponent(Color.ORANGE, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE);
                    map[i][j] = 0;
                } else if (map[i][j] == 2) {
                    box = new BoxComponent(Color.PINK, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE);
                    map[i][j] = 0;
                    map[i][j + 1] = 0;
                } else if (map[i][j] == 3) {
                    box = new BoxComponent(Color.BLUE, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                } else if (map[i][j] == 4) {
                    box = new BoxComponent(Color.GREEN, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    map[i][j + 1] = 0;
                    map[i + 1][j + 1] = 0;
                }
                if (box != null) {
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box);
                    this.add(box);
                }
            }
        }
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 转换为Graphics2D以获得更好效果
        Graphics2D g2d = (Graphics2D)g;

        // ---- 新增代码开始：绘制棋盘背景 ----
        if (boardBackground != null) {
            // 1. 保存当前绘图设置
            Composite oldComposite = g2d.getComposite();

            // 2. 绘制背景底色（可选，用于衬托透明棋盘）
            g2d.setColor(new Color(245, 235, 215)); // 浅米色
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // 3. 计算棋盘绘制位置（居中显示）
            int imgWidth = boardBackground.getWidth(null);
            int imgHeight = boardBackground.getHeight(null);
            float scale = Math.min(
                    getWidth() * 0.9f / imgWidth,
                    getHeight() * 0.9f / imgHeight
            );
            int drawWidth = (int)(imgWidth * scale);
            int drawHeight = (int)(imgHeight * scale);
            int x = (getWidth() - drawWidth) / 2;
            int y = (getHeight() - drawHeight) / 2;

            // 4. 启用抗锯齿
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // 5. 绘制带透明通道的棋盘
            g2d.drawImage(boardBackground,
                    x, y, drawWidth, drawHeight, null);

            // 6. 恢复原始绘图设置
            g2d.setComposite(oldComposite);

            // 保存棋盘位置供方块对齐使用
            this.boardOffsetX = x;
            this.boardOffsetY = y;
            this.boardWidth = drawWidth;
            this.boardHeight = drawHeight;
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        this.setBorder(border);
    }

    @Override
    public void doMouseClick(Point point) {
        Component component = this.getComponentAt(point);
        if (component instanceof BoxComponent clickedComponent) {
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
    }

    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.RIGHT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.LEFT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveUp() {
        System.out.println("Click VK_Up");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.UP)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.DOWN)) {
                afterMove();
            }
        }
    }

    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        if (!hasWon && model.isCaoCaoAtExit()){
            controller.showVictory();
            hasWon = true;
        }
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }


    public void setController(GameController controller) {
        this.controller = controller;
    }

    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public void resetSteps() {
        steps = 0;
        if (stepLabel != null) {
            stepLabel.setText("Steps: 0");
        }
        hasWon = false;
    }


    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public void refresh() {
        boxes.forEach(this::remove);
        boxes.clear();
        initialGame();
        revalidate();
        repaint();
    }

}
