package com.sysco.miniproject.respository;

import com.sysco.miniproject.data.dao.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByCartIdAndCartUserIdAndProductId(Long cartId, Long userId, Long productId);

    void deleteByCartIdAndProductId(Long cartId, Long productId);

}
