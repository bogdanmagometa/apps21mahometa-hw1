package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    interface TempFilter {
        boolean filter(double temp);
    }

    private static final double MIN_TEMP = -273;
    private double[] temperatureSeries;
    private int size;

    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[0];
        size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        checkNewTemps(temperatureSeries);
        this.temperatureSeries = Arrays.copyOf(temperatureSeries,
                temperatureSeries.length);
        size = temperatureSeries.length;
    }

    private void checkTemperatureSeries() {
        if (size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
    }

    private void checkNewTemps(double... temps) {
        for (double temp: temps) {
            if (temp < MIN_TEMP) {
                throw new InputMismatchException(
                        "Some value is below minimum " + MIN_TEMP);
            }
        }
    }

    public double average() {
        checkTemperatureSeries();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            sum += temp;
        }
        return sum / size;
    }

    public double deviation() {
        checkTemperatureSeries();
        double avg = average();
        double var = 0;
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            var += (temp - avg) * (temp - avg);
        }
        var /= temperatureSeries.length-1;
        return Math.sqrt(var);
    }

    public double min() {
        return findTempClosestToValue(MIN_TEMP);
    }

    public double max() {
        checkTemperatureSeries();
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            max = Math.max(max, temp);
        }
        return max;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        checkTemperatureSeries();
        double minDiff = Double.POSITIVE_INFINITY;
        double closestVal = 0;
        for (int i = 0; i < size; i++) {
            double curVal = temperatureSeries[i];
            double curDiff = Math.abs(curVal - tempValue);
            if (curDiff < minDiff) {
                closestVal = curVal;
                minDiff = curDiff;
            } else if (Math.abs(curVal - tempValue) == minDiff &&
                    curVal > closestVal) {
                closestVal = curVal;
            }
        }
        return closestVal;
    }

    private double[] getFilteredTemps(TempFilter tempFilter) {
        checkTemperatureSeries();
        int numFilteredTemps = 0;
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            if (tempFilter.filter(temp)) {
                numFilteredTemps++;
            }
        }
        double[] filteredTemps = new double[numFilteredTemps];
        int filteredTempIdx = 0;
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            if (tempFilter.filter(temp)) {
                filteredTemps[filteredTempIdx++] = temp;
            }
        }
        return filteredTemps;
    }

    public double[] findTempsLessThan(double tempValue) {
        checkTemperatureSeries();
        return getFilteredTemps((temp) -> temp < tempValue);
    }

    public double[] findTempsGreaterThan(double tempValue) {
        checkTemperatureSeries();
        return getFilteredTemps((temp) -> temp > tempValue);
    }

    public TempSummaryStatistics summaryStatistics() {
        checkTemperatureSeries();
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    private void addTemp(double temp) {
        if (size < temperatureSeries.length) {
            temperatureSeries[size++] = temp;
        } else {
            int newLength;
            if (temperatureSeries.length == 0) {
                newLength = 1;
            } else {
                newLength = 2 * temperatureSeries.length;
            }
            double[] newTemperatureSeries = new double[newLength];
            System.arraycopy(temperatureSeries,
                    0,
                    newTemperatureSeries,
                    0,
                    size);
            newTemperatureSeries[size++] = temp;
            temperatureSeries = newTemperatureSeries;
        }
    }

    public int addTemps(double... temps) {
        checkNewTemps(temps);
        for (double temp: temps) {
            addTemp(temp);
        }
        return size;
    }
}
