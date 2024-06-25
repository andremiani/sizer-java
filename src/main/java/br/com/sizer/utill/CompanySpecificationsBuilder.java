package br.com.sizer.utill;

import br.com.sizer.dto.SearchCriteria;
import br.com.sizer.model.Company;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CompanySpecificationsBuilder {
    private final List<SearchCriteria> params;

    public CompanySpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public CompanySpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Company> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Company>> specs = new ArrayList<Specification<Company>>();
        for (SearchCriteria param : params) {
            specs.add(new CompanySpecification(param));
        }

        Specification<Company> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
