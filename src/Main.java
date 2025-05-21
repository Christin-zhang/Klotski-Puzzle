import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 1, 1, 1},
                    {3, 2, 2, 3},
                    {3, 4, 4, 3},
                    {3, 4, 4, 3},
                    {3, 0, 0, 3}
            });
            GameFrame gameFrame = new GameFrame(600, 450, mapModel);
            gameFrame.setVisible(false);
            new LoginFrame().setVisible(true);
        });

    }
}
