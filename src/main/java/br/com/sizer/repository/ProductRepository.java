package br.com.sizer.repository;

import br.com.sizer.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository
                extends PagingAndSortingRepository<Product, Long>, ListCrudRepository<Product, Long>,
                JpaSpecificationExecutor<Product> {

        List<Product> findByCategory(String categoryId);
}
