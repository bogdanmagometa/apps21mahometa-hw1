package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, 0.00001);        
    }

    @Test
    public void testFindClosestToZero() {
        // setup input data and expected result
        double[] temperatureSeries = {-0.2, 0.2, -0.2};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 0.2;
        // call tested method
        double actualResult = seriesAnalysis.findTempClosestToZero();
        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testDeviation() {
        double[] temperatureSeries = {1, 2, 3, 4, 5};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = Math.sqrt(2.5);
        double actualResult = seriesAnalysis.deviation();
        assertEquals(0, Double.compare(expResult, actualResult));
    }

    @Test
    public void testMin() {
        double[] temperatureSeries = {-1, 2, -3, 4, 5};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        assertEquals(0, Double.compare(-3, seriesAnalysis.min()));
        seriesAnalysis.addTemps(10);
        assertEquals(0, Double.compare(-3, seriesAnalysis.min()));
        seriesAnalysis.addTemps(-5);
        assertEquals(0, Double.compare(-5, seriesAnalysis.min()));
        seriesAnalysis.addTemps(-5);
        assertEquals(0, Double.compare(-5, seriesAnalysis.min()));
        seriesAnalysis.addTemps(-200);
        assertEquals(0, Double.compare(-200, seriesAnalysis.min()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMax() {
        double[] temperatureSeries = {-1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        assertEquals(0, Double.compare(-1, seriesAnalysis.max()));
        seriesAnalysis.addTemps(10);
        assertEquals(0, Double.compare(10, seriesAnalysis.max()));
        seriesAnalysis.addTemps(-5);
        assertEquals(0, Double.compare(10, seriesAnalysis.max()));
        seriesAnalysis.addTemps(10);
        assertEquals(0, Double.compare(10, seriesAnalysis.max()));
        seriesAnalysis.addTemps(20_000);
        assertEquals(0, Double.compare(20_000, seriesAnalysis.max()));

        seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.max();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToValue() {
        double[] temperatureSeries = {-1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        assertEquals(0, Double.compare(-1, seriesAnalysis.findTempClosestToValue(0)));
        seriesAnalysis.addTemps(2);
        assertEquals(0, Double.compare(2, seriesAnalysis.findTempClosestToValue(2)));
        seriesAnalysis.addTemps(-31);
        assertEquals(0, Double.compare(-31, seriesAnalysis.findTempClosestToValue(-31)));
        seriesAnalysis.addTemps(0);
        assertEquals(0, Double.compare(0, seriesAnalysis.findTempClosestToValue(0.5)));
        seriesAnalysis.addTemps(20_000);
        assertEquals(0, Double.compare(2, seriesAnalysis.findTempClosestToValue(21)));
        seriesAnalysis.addTemps(-3);
        assertEquals(0, Double.compare(-1, seriesAnalysis.findTempClosestToValue(-2)));

        seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.findTempClosestToValue(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsLessThan() {
        double[] temperatureSeries = {-1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        assertArrayEquals(new double[]{-1}, seriesAnalysis.findTempsLessThan(1), 0.001);
        seriesAnalysis.addTemps(2);
        assertArrayEquals(new double[]{-1}, seriesAnalysis.findTempsLessThan(2), 0.001);
        seriesAnalysis.addTemps(-31);
        assertArrayEquals(new double[]{}, seriesAnalysis.findTempsLessThan(-31), 0.001);
        seriesAnalysis.addTemps(0);
        assertArrayEquals(new double[]{-1, -31, 0}, seriesAnalysis.findTempsLessThan(0.5), 0.001);
        seriesAnalysis.addTemps(20_000);
        assertArrayEquals(new double[]{-1, 2, -31, 0}, seriesAnalysis.findTempsLessThan(21), 0.001);
        seriesAnalysis.addTemps(-3);
        assertArrayEquals(new double[]{-31, -3}, seriesAnalysis.findTempsLessThan(-2), 0.001);

        seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.findTempsLessThan(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsGreaterThan() {
        double[] temperatureSeries = {-1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        assertArrayEquals(new double[]{}, seriesAnalysis.findTempsGreaterThan(1), 0.001);
        seriesAnalysis.addTemps(2);
        assertArrayEquals(new double[]{}, seriesAnalysis.findTempsGreaterThan(2), 0.001);
        seriesAnalysis.addTemps(-31);
        assertArrayEquals(new double[]{-1, 2}, seriesAnalysis.findTempsGreaterThan(-31), 0.001);
        seriesAnalysis.addTemps(0);
        assertArrayEquals(new double[]{2}, seriesAnalysis.findTempsGreaterThan(0.5), 0.001);
        seriesAnalysis.addTemps(20_000);
        assertArrayEquals(new double[]{20_000}, seriesAnalysis.findTempsGreaterThan(21), 0.001);
        seriesAnalysis.addTemps(-3);
        assertArrayEquals(new double[]{-1, 2, 0, 20_000}, seriesAnalysis.findTempsGreaterThan(-2), 0.001);

        seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.findTempsGreaterThan(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatistics() {
        double[] temperatureSeries = {1, 2, 3, 4, 5};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        TempSummaryStatistics tempSummaryStatistics = seriesAnalysis.summaryStatistics();
        assertEquals(3, tempSummaryStatistics.getAvgTemp(), 0.001);
        assertEquals(Math.sqrt(2.5), tempSummaryStatistics.getDevTemp(), 0.001);
        assertEquals(1, tempSummaryStatistics.getMinTemp(), 0.001);
        assertEquals(5, tempSummaryStatistics.getMaxTemp(), 0.001);

        seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.summaryStatistics();
    }

    @Test(expected = InputMismatchException.class)
    public void testAddTemps() {
        double[] temperatureSeries = {1, 2, 3, 4, 5};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        seriesAnalysis.addTemps(-10000);
    }
}
