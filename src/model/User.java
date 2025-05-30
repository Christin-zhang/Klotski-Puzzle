package model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String passwordHash;
    private String saveFilePath;
    private int lastVictorySteps = -1; // -1 表示尚未胜利


    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.saveFilePath = "resources/saves/" + username + "_save.dat";
    }

    public int getLastVictorySteps() {
        return lastVictorySteps;
    }

    public void setLastVictorySteps(int steps) {
        this.lastVictorySteps = steps;
    }


    public static User guest() {
        return new User("Guest", null); // 或者用特殊标记判断是否访客
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    @Override
    public String toString() {
        return username + "," + passwordHash + "," + lastVictorySteps;
    }

    public static User fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length >= 2) {
            User user = new User(parts[0], parts[1]);
            if (parts.length == 3) {
                try {
                    user.setLastVictorySteps(Integer.parseInt(parts[2]));
                } catch (NumberFormatException ignored) {}
            }
            return user;
        }
        return null;
    }

}
