package controller;

import model.GameSave;

import java.io.*;
import java.util.List;

public class GameSaveController {

    private static final String SAVE_FOLDER = "saves";

    // 保存
    public static void save(String username, GameSave saveData) {
        if (username == null || username.equals("Guest")) return;

        File dir = new File(SAVE_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File saveFile = new File(dir, username + ".sav");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            oos.writeObject(saveData);
        } catch (IOException e) {
            e.printStackTrace();
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
