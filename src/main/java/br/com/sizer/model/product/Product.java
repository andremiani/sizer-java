package br.com.sizer.model.product;

import br.com.sizer.model.dimensions.Dimensions;

public interface Product {
    String getType();

    Dimensions getDimensions();
}