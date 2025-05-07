package demo.controller;

import demo.model.User;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private static final String USER_DATA_FILE = "users.txt";

    public UserManager() {
        loadUsers();
    }

    private void loadUsers() {
        users = new ArrayList<>();
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    User user = new User(parts[0], parts[1]);
                    if (parts.length >= 3) user.setSaveData(parts[2]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("加载用户数据失败: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, StandardCharsets.UTF_8))) {
            for (User user : users) {
                String line = user.getUsername() + ","
                        + user.getPassword() + ","
                        + (user.getSaveData() != null ? user.getSaveData() : "");
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("保存用户数据失败: " + e.getMessage());
        }
    }

    public boolean register(String username, String password) {
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            return false;
        }
        users.add(new User(username, password));
        saveUsers();
        return true;
    }

    public User login(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}