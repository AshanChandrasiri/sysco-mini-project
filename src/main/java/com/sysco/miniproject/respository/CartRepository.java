package com.sysco.miniproject.respository;

import com.sysco.miniproject.data.dao.Cart;
import com.sysco.miniproject.data.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
