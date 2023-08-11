package com.system.Gulafi.repo;

import com.system.Gulafi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product order by id desc limit 6", nativeQuery = true)
    List<Product> findLatestProduct();


    @Query(value = "select * from product where LOWER(product_name) LIKE CONCAT('%', LOWER(?2), '%') order by id  offset ?1 limit 6", nativeQuery = true)
    List<Product> getSixProducts(int offSet, String partialName);

    @Query(value = "select count(*) from product where LOWER(product_name) LIKE CONCAT('%', LOWER(?1), '%')", nativeQuery = true)
    int countAllProducts(String partialName);
}
