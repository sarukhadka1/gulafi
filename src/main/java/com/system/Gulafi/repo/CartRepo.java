package com.system.Gulafi.repo;

import com.system.Gulafi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {
    @Query(value = "SELECT * FROM cart WHERE user_id = ?1", nativeQuery = true)
    List<Cart> findByUser(Integer id);
}
