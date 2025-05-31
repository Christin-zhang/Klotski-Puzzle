package view.game;

import model.User;
import controller.UserManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class LeaderboardDialog extends JDialog {

    public LeaderboardDialog(User currentUser) {
        setTitle("排行榜");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        // 排行榜顶部显示当前用户名与排名
        List<User> allUsers = UserManager.getAllUsers().stream()
                .filter(u -> u.getLastVictorySteps() >= 0) // 只包含有胜利记录的
                .sorted(Comparator.comparingInt(User::getLastVictorySteps)) // 步数升序
                .collect(Collectors.toList());

        int rank = -1;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(currentUser.getUsername())) {
                rank = i + 1;
                break;
            }
        }

        JLabel topLabel = new JLabel(String.format("当前用户：%s 排名：%s",
                currentUser.getUsername(),
                (rank > 0 ? rank : "未上榜")), SwingConstants.CENTER);
        topLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        add(topLabel, BorderLayout.NORTH);

        // 表格显示前十名
        String[] columnNames = {"排名", "用户名", "步数"};
        Object[][] data = new Object[Math.min(10, allUsers.size())][3];

        for (int i = 0; i < data.length; i++) {
            User u = allUsers.get(i);
            data[i][0] = i + 1;
            data[i][1] = u.getUsername();
            data[i][2] = u.getLastVictorySteps();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
