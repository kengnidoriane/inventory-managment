package com.inventory.inventory_api.repository;


import com.inventory.inventory_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByQuantityInStockLessThan(Integer threshold);
}

