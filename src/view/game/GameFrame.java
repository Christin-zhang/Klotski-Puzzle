package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;
import view.FrameUtil;
import model.User;
import model.GameSave;
import model.MoveRecord;
import controller.GameSaveController;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;
    private JButton upBtn, downBtn, leftBtn, rightBtn;

    private MapModel mapModel;
    private User user;
    private int steps;
    private List<MoveRecord> history;
    private Image frameBackground;
    private JLayeredPane layeredPane;

    public GameFrame(int width, int height, MapModel mapModel, User user, int steps) {
        //初始化界面
        initJFrame(width, height);
        //初始化gamePanel
        initGamePanel(height, mapModel);
        //初始化按钮
        initButton();
        //初始化菜单
        initJMenuBar();
        //显示界面，写在最后
        this.setVisible(true);
    }

    private void initGamePanel(int height, MapModel mapModel) {
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(100, height / 2 - gamePanel.getHeight() / 2 - 25);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);
    }

    private void initButton() {
        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 320, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 320, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 320, 70), 180, 50);
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
        upBtn = createDirectionButton("↑", 865, 400);
        downBtn = createDirectionButton("↓", 865, 520);
        leftBtn = createDirectionButton("←", 815, 460);
        rightBtn = createDirectionButton("→", 915, 460);
        // 2. 绑定按钮事件
        bindButtonActions(controller);
    }

    private void initJMenuBar() {
        //初始化游戏界面内的菜单
        //创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        //创建菜单上面的几个选项的对象：风格切换/模式切换/难度切换
        JMenu styleJMenu = new JMenu("风格切换");
        JMenu modeJMenu = new JMenu("模式切换");
        JMenu difficultyJMenu = new JMenu("难度切换");
        //创建选项下面的条目对象
        JMenuItem bgItem = new JMenuItem("背景切换");
        //TODO:add backgrounds choices;
        JMenuItem blockItem = new JMenuItem("滑块样式");
        //TODO:add block styles choices;
        JMenuItem unlimitedItem = new JMenuItem("无尽解题");
        JMenuItem stepsLimitedItem = new JMenuItem("步数挑战");
        JMenuItem timeLimitedItem = new JMenuItem("限时挑战");

        JMenuItem easyItem = new JMenuItem("简单");
        JMenuItem normalItem = new JMenuItem("普通");
        JMenuItem hellItem = new JMenuItem("地狱");
        //将每一个条目添加到选项当中
        styleJMenu.add(bgItem);
        styleJMenu.add(blockItem);

        modeJMenu.add(unlimitedItem);
        modeJMenu.add(stepsLimitedItem);
        modeJMenu.add(timeLimitedItem);

        difficultyJMenu.add(easyItem);
        difficultyJMenu.add(normalItem);
        difficultyJMenu.add(hellItem);
        //将菜单中三个选项添加到菜单中
        jMenuBar.add(styleJMenu);
        jMenuBar.add(modeJMenu);
        jMenuBar.add(difficultyJMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame(int width, int height) {
        this.setTitle("Klotski Puzzle");
        this.setLayout(null);
        this.setSize(width, height);
        //置顶游戏界面
        //this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private model.User currentUser;
        private JButton saveBtn;

        public GameFrame(int width, int height, model.User user) {
            this.currentUser = user;
            saveBtn = FrameUtil.createButton(this, "保存", new Point(gamePanel.getWidth() + 80, 300), 80, 50);
            saveBtn.setEnabled(user != null && !"Guest".equals(user.getUsername()));
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

            loadBtn = FrameUtil.createButton(this, "加载", new Point(gamePanel.getWidth() + 80, 360), 80, 50);
            loadBtn.setEnabled(user != null && !"Guest".equals(user.getUsername()));
            loadBtn.addActionListener(e -> controller.loadGame(currentUser));
        }//这里没有写button control，需要询问之后补上

    public GameFrame(int width, int height, MapModel mapModel, User user, int steps, List<MoveRecord> history) {
        super("Klotski 游戏界面");

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 保存字段（你要在 GameFrame 中声明它们）
        this.mapModel = mapModel;
        this.user = user;
        this.steps = steps;
        this.history = history;

        // 初始化 UI
        initComponents();
    }


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

    private void initComponents() {
        // 清空内容面板
        getContentPane().removeAll();
        setLayout(null);

        // 初始化游戏棋盘视图
        GamePanel boardPanel = new GamePanel(mapModel);
        boardPanel.setBounds(20, 20, mapModel.getWidth() * 70, mapModel.getHeight() * 70);
        add(boardPanel);

        // 显示步数信息
        JLabel stepsLabel = new JLabel("步数: " + steps);
        stepsLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        stepsLabel.setBounds(500, 20, 100, 30);
        add(stepsLabel);

        // 添加“保存游戏”按钮
        JButton saveButton = new JButton("保存游戏");
        saveButton.setBounds(500, 70, 100, 30);
        add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                GameSave saveData = new GameSave(mapModel.getCurrentMatrixCopy(), steps, history); // 构造 GameSave 对象
                GameSaveController.save(user.getUsername(), saveData); // 调用已有 save 方法
                JOptionPane.showMessageDialog(this, "保存成功！");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "保存失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        repaint();
        revalidate();
    }
}


