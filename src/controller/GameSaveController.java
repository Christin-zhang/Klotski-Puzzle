package controller;

import model.GameSave;

import java.io.*;
import java.util.List;

public class GameSaveController {

    private static final String SAVE_FOLDER = "saves";

    // 保存
    public static void save(String username, GameSave saveData) throws IOException {
        if (username == null || username.equals("Guest")) return;

        File dir = new File("resources/saves/");
        if (!dir.exists()) {
            dir.mkdirs();  // 自动创建目录
        }

        File saveFile = new File(dir, username + "_save.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            oos.writeObject(saveData);
        }
    }


    // 加载
    public static GameSave load(String username) throws IOException, ClassNotFoundException {
        if (username == null || username.equals("Guest")) {
            throw new IOException("访客无法读取存档");
        }

        File saveFile = new File(SAVE_FOLDER, username + ".sav");
        if (!saveFile.exists()) {
            throw new FileNotFoundException("存档文件不存在");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            return (GameSave) ois.readObject();
        } catch (Exception e) {
            throw new IOException("读取存档失败或格式错误", e);
        }
    }
}
