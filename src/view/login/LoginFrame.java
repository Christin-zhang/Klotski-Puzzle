package view.login;

import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;

import controller.UserManager;
import model.User;

public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private JButton guestBtn;
    private JButton registerBtn;
    private GameFrame gameFrame;
    private UserManager userManager;

    public LoginFrame(int width, int height) {
        this.userManager = new UserManager();
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);

        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);
        guestBtn = FrameUtil.createButton(this, "Guest", new Point(280, 140), 100, 40);
        registerBtn = FrameUtil.createButton(this, "Register", new Point(40, 190), 100, 40);

        submitBtn.addActionListener(e -> {
            User user = userManager.login(username.getText(), password.getText());
            if (user != null) {
                if (this.gameFrame != null) {
                    this.gameFrame.setCurrentUser(user);
                    this.gameFrame.setVisible(true);
                    this.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        });

        guestBtn.addActionListener(e -> {
            if (this.gameFrame != null) {
                this.gameFrame.setCurrentUser(null);
                this.gameFrame.setVisible(true);
                this.setVisible(false);
            }
        });

        registerBtn.addActionListener(e -> {
            JFrame registerFrame = new JFrame("Register");
            registerFrame.setSize(300, 200);
            registerFrame.setLayout(new GridLayout(3, 2));

            JTextField newUser = new JTextField();
            JPasswordField newPass = new JPasswordField();
            JPasswordField confirmPass = new JPasswordField();

            registerFrame.add(new JLabel("Username:"));
            registerFrame.add(newUser);
            registerFrame.add(new JLabel("Password:"));
            registerFrame.add(newPass);
            registerFrame.add(new JLabel("Confirm:"));
            registerFrame.add(confirmPass);

            JButton regConfirm = new JButton("Register");
            regConfirm.addActionListener(ev -> {
                if (new String(newPass.getPassword()).equals(new String(confirmPass.getPassword()))) {
                    if (userManager.register(newUser.getText(), new String(newPass.getPassword()))) {
                        registerFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(registerFrame, "Username exists");
                    }
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "Password mismatch");
                }
            });
            registerFrame.add(regConfirm);
            registerFrame.setVisible(true);
        });

        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
}
