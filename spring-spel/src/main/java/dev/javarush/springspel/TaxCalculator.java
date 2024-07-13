package dev.javarush.springspel;

public class TaxCalculator {
    private String defaultLocale;
    private double initialRate;

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public void setInitialRate(double initialRate) {
        this.initialRate = initialRate;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public double getInitialRate() {
        return initialRate;
    }
}
