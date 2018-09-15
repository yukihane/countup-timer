package com.github.yukihane.timer;

import com.github.yukihane.timer.Config.InitialTime;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class AppFrame extends JFrame {

    private static final long serialVersionUID = -6814522355949473354L;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    final JLabel label = new DisplayLabel("Hello World");

    private final Date start;

    private InitialTime offset;

    private InitialTime displayData;

    public AppFrame() {
        super("Count Up");

        start = new Date();

        offset = new Config.InitialTime(0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                try {
                    saveProperties();
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        getContentPane().add(label);

        // Display the window.
        pack();

        final Runnable command = () -> {
            SwingUtilities.invokeLater(() -> {
                update();
            });
        };

        scheduler.scheduleAtFixedRate(command, 0L, 16, TimeUnit.MILLISECONDS);
    }

    public synchronized void setOffset(final InitialTime offset) {
        this.offset = offset;
    }

    private synchronized InitialTime getOffset() {
        return offset;
    }

    private void update() {
        final Date now = new Date();
        final long passSec = (now.getTime() - start.getTime()) / 1000;
        final long currentSec = passSec + getOffset().totalSecond;
        displayData = new Config.InitialTime(currentSec);

        display(displayData);
    }

    private void display(final InitialTime time) {
        label.setText(String.format("%02d:%02d:%02d", time.hour, time.minute, time.second));
    }

    private void saveProperties() throws IOException {
        Config.INSTANCE.save(displayData);
    }

    private static class DisplayLabel extends JLabel {

        private static final long serialVersionUID = -7300587638124524324L;

        private DisplayLabel(final String text) {
            super(text);

            final Font f = getFont();
            final int newSize = f.getSize() * 5;

            setFont(new Font(f.getName(), f.getStyle(), newSize));
        }
    }
}
