package br.com.sizer.utill;

import br.com.sizer.dto.SearchCriteria;
import br.com.sizer.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ProductSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public ProductSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Product> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Product>> specs = new ArrayList<Specification<Product>>();
        for (SearchCriteria param : params) {
            specs.add(new ProductSpecification(param));
        }

        Specification<Product> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
