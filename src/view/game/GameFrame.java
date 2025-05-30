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
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn, loadBtn, saveBtn;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private JButton upBtn, downBtn, leftBtn, rightBtn;
    private MapModel mapModel;
    private User user;
    private int steps;
    private List<MoveRecord> history;
    private Image frameBackground;

    public GameFrame(int width, int height, MapModel mapModel, User user, int steps, List<MoveRecord> history) {
        super("Klotski 游戏界面");

        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.mapModel = mapModel;
        this.user = user;
        this.steps = steps;
        this.history = (history != null) ? history : new ArrayList<>();

        initFrameLayout();
        initGamePanel(height);
        initButtons();
        initMenuBar();
        this.setVisible(true);
    }

    private void initFrameLayout() {
        this.setLayout(null);
    }

    private void initGamePanel(int height) {
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(70, height / 2 - gamePanel.getHeight() / 2 - 10);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);
    }


    private void initButtons() {
        int buttonWidth = 100;
        int buttonHeight = 40;
        int baseX = gamePanel.getWidth() + 320;
        int baseY = 120;

        stepLabel = FrameUtil.createJLabel(this, "Step: " + steps, new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 320, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn = FrameUtil.createButton(this, "重新开始", new Point(baseX, baseY), buttonWidth, buttonHeight);
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();
        });

        JButton undoBtn = FrameUtil.createButton(this, "上一步", new Point(baseX, baseY + 50), buttonWidth, buttonHeight);
        undoBtn.addActionListener(e -> {
            //TODO:controller.undoLastMove();
            gamePanel.requestFocusInWindow();
        });

        this.loadBtn = FrameUtil.createButton(this, "加载", new Point(baseX, baseY + 100), buttonWidth, buttonHeight);
        this.loadBtn.setEnabled(user != null && !"Guest".equals(user.getUsername()));
        this.loadBtn.addActionListener(e -> controller.loadGame(user));

        JButton saveBtn = FrameUtil.createButton(this, "保存", new Point(baseX, baseY + 150), buttonWidth, buttonHeight);
        saveBtn.setEnabled(user != null && !"Guest".equals(user.getUsername()));
        saveBtn.addActionListener(e -> controller.saveGame(user));
        // 方向按钮
        upBtn = createDirectionButton("↑", 865, 400);
        downBtn = createDirectionButton("↓", 865, 520);
        leftBtn = createDirectionButton("←", 815, 460);
        rightBtn = createDirectionButton("→", 915, 460);
        bindButtonActions();
    }

    private void bindButtonActions() {
        upBtn.addActionListener(e -> {
            controller.handleMove(Direction.UP);
            gamePanel.requestFocusInWindow();
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

    private JButton createDirectionButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 50, 50);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(btn);
        return btn;
    }

    private void initMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu styleJMenu = new JMenu("风格切换");
        JMenu modeJMenu = new JMenu("模式切换");
        JMenu difficultyJMenu = new JMenu("难度切换");

        JMenuItem bgItem = new JMenuItem("背景切换");
        JMenuItem blockItem = new JMenuItem("滑块样式");
        JMenuItem unlimitedItem = new JMenuItem("无尽解题");
        JMenuItem stepsLimitedItem = new JMenuItem("步数挑战");
        JMenuItem timeLimitedItem = new JMenuItem("限时挑战");

        JMenuItem easyItem = new JMenuItem("简单");
        JMenuItem normalItem = new JMenuItem("普通");
        JMenuItem hellItem = new JMenuItem("地狱");

        styleJMenu.add(bgItem);
        styleJMenu.add(blockItem);
        modeJMenu.add(unlimitedItem);
        modeJMenu.add(stepsLimitedItem);
        modeJMenu.add(timeLimitedItem);
        difficultyJMenu.add(easyItem);
        difficultyJMenu.add(normalItem);
        difficultyJMenu.add(hellItem);

        jMenuBar.add(styleJMenu);
        jMenuBar.add(modeJMenu);
        jMenuBar.add(difficultyJMenu);
        this.setJMenuBar(jMenuBar);
    }
}
