package view.login;

import controller.GameSaveController;
import model.MapModel;
import model.User;
import view.game.GameFrame;
import model.GameSave;


import javax.swing.*;
import java.awt.*;

public class GameStartUI extends JFrame {
    private User user;
    private JButton newGameButton, loadGameButton, logoutButton, exitButton;

    public GameStartUI(User user) {
        this.user = user;

        String username = (user == null || user.getUsername() == null) ? "Guest" : user.getUsername();
        setTitle("Klotski 主菜单 - 用户：" + username);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        String username = (user == null || user.getUsername() == null) ? "Guest" : user.getUsername();
        JLabel welcomeLabel = new JLabel("欢迎，" + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panel.add(welcomeLabel);

        newGameButton = new JButton("开始新游戏");
        loadGameButton = new JButton("回到上次游戏");
        logoutButton = new JButton("退出登录");
        exitButton = new JButton("退出游戏");

        panel.add(newGameButton);
        panel.add(loadGameButton);
        panel.add(logoutButton);
        panel.add(exitButton);

        add(panel);

        newGameButton.addActionListener(e -> handleNewGame());
        loadGameButton.addActionListener(e -> handleLoadGame());
        logoutButton.addActionListener(e -> handleLogout());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void handleNewGame() {
        // 创建默认初始地图
        MapModel mapModel = new MapModel(new int[][]{
                {2, 2, 1, 1},
                {3, 0, 0, 3},
                {3, 4, 4, 3},
                {3, 4, 4, 3},
                {3, 1, 1, 3}
        });

        // 启动游戏主界面
        GameFrame gameFrame = new GameFrame(1100, 825, mapModel, user, 0, null); // 步数为0
        gameFrame.setVisible(true);
        this.dispose();
    }

    private void handleLoadGame() {
        if (user == null || "Guest".equals(user.getUsername())) {
            JOptionPane.showMessageDialog(this, "访客无法加载存档", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            // 使用 GameSaveController 读取 GameSave 对象
            GameSave saveData = GameSaveController.load(user.getUsername());

            // 创建 MapModel
            MapModel loadedModel = new MapModel(saveData.getMatrix());

            // 创建 GameFrame 并传入用户、已保存步数、历史记录等
            GameFrame gameFrame = new GameFrame(600, 450, loadedModel, user, saveData.getSteps(), saveData.getHistory());
            gameFrame.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "无法加载存档：文件不存在或格式损坏", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void handleLogout() {
        int result = JOptionPane.showConfirmDialog(this, "确定要注销并返回登录界面？", "确认注销",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }
}
