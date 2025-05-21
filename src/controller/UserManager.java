package controller;

import model.User;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "resources/users.txt"; // 用户数据保存路径
    private static final Map<String, User> users = new HashMap<>();


    static {
        loadUsersFromFile();
    }

    private static void loadUsersFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(USER_FILE));
            for (String line : lines) {
                User user = User.fromString(line);
                if (user != null) {
                    users.put(user.getUsername(), user);
                }
            }
        } catch (IOException e) {
            // 如果文件不存在，忽略即可
        }
    }

    private static void saveUsersToFile() {
        try {
            // 确保目录存在
            File file = new File(USER_FILE);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // 写入文件
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (User user : users.values()) {
                    writer.write(user.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean register(String username, String password) {
        if (users.containsKey(username)) return false; // 用户名已存在

        String hash = hashPassword(password);
        User newUser = new User(username, hash);
        users.put(username, newUser);
        saveUsersToFile();
        return true;
    }

    public static User login(String username, String password) {
        if (!users.containsKey(username)) return null;
        String hash = hashPassword(password);
        User user = users.get(username);
        if (user.getPasswordHash().equals(hash)) {
            return user;
        }
        return null;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    public static Collection<User> getAllUsers() {
        return users.values();
    }
}
