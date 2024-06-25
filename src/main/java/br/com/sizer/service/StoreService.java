package br.com.sizer.service;

import br.com.sizer.model.Store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface StoreService {

    public Store findOne(Long id);

    public Page<Store> findAll(Pageable pageable);

    public List<Store> findAll(Specification<Store> spec);

    public Store create(Store company);

    public Store update(Long id, Store companyDetails);

    public void delete(Long id);

}
