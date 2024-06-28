package br.com.sizer.payload.requests;

import java.util.List;
import java.util.Map;

// Classe para representar a requisição JSON
public class RecommendationRequest {
    private Map<String, Double> measurements;
    private List<String> category;
    private double tolerance;

    public Map<String, Double> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Map<String, Double> measurements) {
        this.measurements = measurements;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public double getTolerance() {
        return tolerance;
    }

    public double setTolerance(double tolerance) {
        return this.tolerance = tolerance;
    }

}