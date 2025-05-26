package view.login;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, guestButton;

    public LoginFrame() {
        setTitle("Klotski 登录界面");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("登录");
        registerButton = new JButton("注册");
        guestButton = new JButton("以访客身份进入");

        panel.add(createLabeledField("用户名：", usernameField));
        panel.add(createLabeledField("密码：", passwordField));
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(guestButton);

        add(panel);

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        guestButton.addActionListener(e -> handleGuest());
    }

    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户名和密码", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = UserController.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "登录成功：" + user.getUsername());
            // TODO: 进入游戏主界面
            this.dispose();
            new GameStartUI(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户名和密码", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (UserController.register(username, password)) {
            JOptionPane.showMessageDialog(this, "注册成功，请登录！");
        } else {
            JOptionPane.showMessageDialog(this, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleGuest() {
        JOptionPane.showMessageDialog(this, "以访客身份进入游戏");
        // TODO: 创建 Guest 用户对象并跳转游戏
        new GameStartUI(null).setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}


