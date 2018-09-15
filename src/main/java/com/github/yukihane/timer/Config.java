package com.github.yukihane.timer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.Properties;

public final class Config {

    public static final Config INSTANCE = new Config();

    public static class InitialTime {
        public final int hour;
        public final int minute;
        public final int second;

        public final long totalSecond;

        public InitialTime(final int hour, final int minute, final int second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;

            this.totalSecond = ((hour * 60L) + minute) * 60 + second;
        }

        public InitialTime(final long totalSecond) {
            this.totalSecond = totalSecond;

            this.hour = (int) (totalSecond / 60 / 60);
            this.minute = (int) ((totalSecond / 60) - (this.hour * 60));
            this.second = (int) (totalSecond % 60);
        }
    }

    private final File configFile;

    private Config() {

        try {
            final String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            final String decodedPath = URLDecoder.decode(path, "UTF-8");
            configFile = new File(decodedPath, "config.properties");

            System.out.println(configFile.getCanonicalPath());

            if (!configFile.exists()) {
                configFile.createNewFile();
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

    }

    public InitialTime load() throws IOException {

        final Properties properties = loadProperties();
        final int h = Integer.valueOf(properties.getProperty("h", "0"));
        final int m = Integer.valueOf(properties.getProperty("m", "0"));
        final int s = Integer.valueOf(properties.getProperty("s", "0"));
        final InitialTime time = new InitialTime(h, m, s);

        return time;
    }

    public void save(final InitialTime time) throws IOException {
        final Properties prop = new Properties();
        prop.setProperty("h", Integer.toString(time.hour));
        prop.setProperty("m", Integer.toString(time.minute));
        prop.setProperty("s", Integer.toString(time.second));

        try (Writer writer = new BufferedWriter(new FileWriter(configFile))) {
            prop.store(writer, "config");
        }
        System.out.println("save: " + configFile.getCanonicalPath());
    }

    private Properties loadProperties() throws IOException {

        final Properties prop = new Properties();
        try (Reader reader = new BufferedReader(new FileReader(configFile))) {
            prop.load(reader);
            return prop;
        }
    }

}
