package model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String passwordHash;
    private String saveFilePath;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.saveFilePath = "resources/saves/" + username + "_save.dat";
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
        return username + "," + passwordHash;
    }

    public static User fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 2) {
            return new User(parts[0], parts[1]);
        }
        return null;
    }
}
