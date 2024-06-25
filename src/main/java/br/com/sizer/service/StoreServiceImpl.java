package br.com.sizer.service;

import br.com.sizer.exception.ResourceNotFoundException;
import br.com.sizer.model.Store;
import br.com.sizer.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store findOne(Long id) {
        return storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Page<Store> findAll(Pageable pageable) {
        Page<Store> resultPage = storeRepository.findAll(pageable);
        if (pageable.getPageNumber() > resultPage.getTotalPages())
            throw new ResourceNotFoundException("Store", "id", resultPage);
        return resultPage;
    }

    @Override
    public List<Store> findAll(Specification<Store> spec) {
        return storeRepository.findAll(spec);
    }

    @Override
    public Store update(Long id, Store storeDetails) {
        Store store = storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (store == null)
            throw new ResourceNotFoundException("Store", "id", id);

        store.setName(storeDetails.getName());
        store.setUrl(storeDetails.getUrl());
        store.setMaxProducts(storeDetails.getMaxProducts());
        store.setCompany(storeDetails.getCompany());
        store.setCreatedBy(storeDetails.getCreatedBy());
        return storeRepository.save(store);
    }

    @Override
    public void delete(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (store == null)
            throw new ResourceNotFoundException("Store", "id", id);

        storeRepository.delete(store);
    }

}
