package com.github.yukihane.timer;

import static org.junit.Assert.*;

import com.github.yukihane.timer.Config.InitialTime;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void test() {
        final InitialTime time = new Config.InitialTime(61L);
        assertEquals(1, time.minute);
        assertEquals(1, time.second);

        final InitialTime time2 = new Config.InitialTime(121L);
        assertEquals(0, time2.hour);
        assertEquals(2, time2.minute);
        assertEquals(1, time2.second);

        final InitialTime time3 = new Config.InitialTime((2 * 60 + 13) * 60 + 33);
        assertEquals(2, time3.hour);
        assertEquals(13, time3.minute);
        assertEquals(33, time3.second);

    }

}
