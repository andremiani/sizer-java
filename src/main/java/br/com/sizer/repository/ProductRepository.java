package br.com.sizer.repository;

import br.com.sizer.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository
                extends PagingAndSortingRepository<Product, Long>, ListCrudRepository<Product, Long>,
                JpaSpecificationExecutor<Product> {
        Page<Product> findAllByCategoryIn(List<String> categories, Pageable pageable);

        Page<Product> findAll(Pageable pageable);
}
