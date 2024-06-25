package br.com.sizer.service;

import br.com.sizer.exception.ResourceNotFoundException;
import br.com.sizer.model.Company;
import br.com.sizer.repository.CompanyRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {

        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public Company create(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company findOne(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return company;
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        Page<Company> resultPage = companyRepository.findAll(pageable);
        if (pageable.getPageNumber() > resultPage.getTotalPages())
            throw new ResourceNotFoundException("Company", "id", resultPage);
        return resultPage;
    }

    @Override
    public List<Company> findAll(Specification<Company> spec) {
        return companyRepository.findAll(spec);
    }

    @Override
    public Company update(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (company == null)
            throw new ResourceNotFoundException("Company", "id", id);

        company.setName(companyDetails.getName());
        company.setMaxStores(companyDetails.getMaxStores());
        company.setCreatedBy(companyDetails.getCreatedBy());
        return companyRepository.save(company);
    }

    @Override
    public void delete(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (company == null)
            throw new ResourceNotFoundException("Company", "id", id);

        companyRepository.delete(company);
    }

}
