package br.com.sizer.utill;

import br.com.sizer.dto.SearchCriteria;
import br.com.sizer.model.Store;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StoreSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public StoreSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public StoreSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Store> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Store>> specs = new ArrayList<Specification<Store>>();
        for (SearchCriteria param : params) {
            specs.add(new StoreSpecification(param));
        }

        Specification<Store> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
