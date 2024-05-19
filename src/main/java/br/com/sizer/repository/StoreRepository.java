package br.com.sizer.repository;

import br.com.sizer.model.Store;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>, JpaSpecificationExecutor<Store> {
    Store findById(Long id);
}
