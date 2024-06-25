package br.com.sizer.model.dimensions;

import java.util.HashMap;
import java.util.Map;

public class DressDimensions implements Dimensions {

    private double bust;
    private double waist;
    private double hip;
    private double length;

    public DressDimensions(double bust, double waist, double hip, double length) {
        this.bust = bust;
        this.waist = waist;
        this.hip = hip;
        this.length = length;
    }

    @Override
    public Map<String, Double> getDimensions() {
        Map<String, Double> dimensions = new HashMap<>();
        dimensions.put("bust", bust);
        dimensions.put("waist", waist);
        dimensions.put("hip", hip);
        dimensions.put("length", length);
        return dimensions;
    }

}
