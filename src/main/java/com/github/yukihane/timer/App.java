package com.github.yukihane.timer;

import com.github.yukihane.timer.Config.InitialTime;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class App {

    private final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(final String[] args) throws IOException {
        new App().exec();
    }

    private void exec() throws IOException {
        logger.fine("app start");
        final InitialTime time = Config.INSTANCE.load();
        SwingUtilities.invokeLater(() -> {
            startGui(time);
        });
    }

    private static void startGui(final InitialTime time) {

        final AppFrame frame = new AppFrame();
        frame.setOffset(time);
        frame.setVisible(true);
    }
}
