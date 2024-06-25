package br.com.sizer.service;

import br.com.sizer.model.Company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CompanyService {

    public Company findOne(Long id);

    public List<Company> findAll(Specification<Company> spec);

    public Page<Company> findAll(Pageable pageable);

    public Company create(Company company);

    public Company update(Long id, Company companyDetails);

    public void delete(Long id);

}
