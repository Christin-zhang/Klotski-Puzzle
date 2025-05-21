package view.login;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameStartUI extends JFrame {
    private User user;
    private JButton newGameButton, loadGameButton, logoutButton, exitButton;

    public GameStartUI(User user) {
        this.user = user;

        setTitle("Klotski 主菜单 - 用户：" + user.getUsername());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel welcomeLabel = new JLabel("欢迎，" + user.getUsername(), SwingConstants.CENTER);
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
        JOptionPane.showMessageDialog(this, "进入新游戏（待实现）");
        // TODO: 启动游戏主逻辑界面并传递 user 对象
    }

    private void handleLoadGame() {
        if (user.getUsername().equals("Guest")) {
            JOptionPane.showMessageDialog(this, "访客无法加载存档", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // TODO: 加载存档功能
        JOptionPane.showMessageDialog(this, "加载存档（待实现）");
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

