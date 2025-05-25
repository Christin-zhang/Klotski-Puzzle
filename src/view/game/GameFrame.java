package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;
    private JButton upBtn, downBtn, leftBtn, rightBtn;

    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("Klotski Puzzle");
        this.setLayout(null);//我们采用的是绝对布局，按照像素点，在指定位置进行渲染
        this.setSize(width, height);//通过构造方法传递进来的尺寸
        //置顶游戏界面
        //this.setAlwaysOnTop(true);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            gamePanel.requestFocusInWindow();//enable key listener
        });
        //todo: add other button here
        // 1. 创建方向按钮
        upBtn = createDirectionButton("↑", 465, 100);
        downBtn = createDirectionButton("↓", 465, 220);
        leftBtn = createDirectionButton("←", 415, 160);
        rightBtn = createDirectionButton("→", 515, 160);

        // 2. 绑定按钮事件
        bindButtonActions(controller);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    
        private model.User currentUser;
        private JButton saveBtn;

        public GameFrame(int width, int height, model.User user) {
            this.currentUser = user;
            saveBtn = FrameUtil.createButton(this, "保存", new Point(gamePanel.getWidth() + 80, 300), 80, 50);
            saveBtn.setEnabled(user != null);
            saveBtn.addActionListener(e -> {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(this, "访客无法保存游戏", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            });
            upBtn = createDirectionButton("↑", 100, 200);
            downBtn = createDirectionButton("↓", 100, 260);
            leftBtn = createDirectionButton("←", 415, 260);
            rightBtn = createDirectionButton("→", 515, 260);

            // 2. 绑定按钮事件
            bindButtonActions(controller);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }//这里没有写button control，需要询问之后补上

    private JButton createDirectionButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 50, 50);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(btn);
        return btn;
    }
    private void bindButtonActions(GameController controller) {
        upBtn.addActionListener(e -> {
            controller.handleMove(Direction.UP);
            gamePanel.requestFocusInWindow();  // 保持焦点在游戏面板
        });
        downBtn.addActionListener(e -> {
            controller.handleMove(Direction.DOWN);
            gamePanel.requestFocusInWindow();
        });
        leftBtn.addActionListener(e -> {
            controller.handleMove(Direction.LEFT);
            gamePanel.requestFocusInWindow();
        });
        rightBtn.addActionListener(e -> {
            controller.handleMove(Direction.RIGHT);
            gamePanel.requestFocusInWindow();
        });
    }

}


