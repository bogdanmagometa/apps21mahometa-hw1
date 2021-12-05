package ua.edu.ucu.tempseries;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TempSummaryStatisticsTest {
    TempSummaryStatistics sum1, sum2, sum3;
    double delta = 0.001;
    @Before
    public void setUp() {
        sum1 = new TempSummaryStatistics(1, 2, 3, 4);
        sum2 = new TempSummaryStatistics(5, 6, 7, 8);
        sum3 = new TempSummaryStatistics(5, 6, 7, 8);
    }

    @Test
    public void getAvgTemp() {
        assertEquals(1, sum1.getAvgTemp(), delta);
    }

    @Test
    public void getDevTemp() {
        assertEquals(2, sum1.getDevTemp(), delta);
    }

    @Test
    public void getMinTemp() {
        assertEquals(3, sum1.getMinTemp(), delta);
    }

    @Test
    public void getMaxTemp() {
        assertEquals(4, sum1.getMaxTemp(), delta);
    }

    @Test
    public void testToString() {
        assertEquals("TempSummaryStatistics{avgTemp=1.0, devTemp=2.0, minTemp=3.0, maxTemp=4.0}", sum1.toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals(sum1, sum2);
        assertEquals(sum2, sum3);
        assertFalse(sum1.equals(new TemperatureSeriesAnalysis()));
        assertFalse(sum1.equals(null));
        assertEquals(sum1, sum1);
    }

    @Test
    public void testHashCode() {
        assertEquals(sum2.hashCode(), sum3.hashCode());
    }
}