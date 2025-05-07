package demo.model;

public class User {
    private String username;
    private String password;
    private String saveData;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSaveData() { return saveData; }
    public void setSaveData(String saveData) { this.saveData = saveData; }
}
