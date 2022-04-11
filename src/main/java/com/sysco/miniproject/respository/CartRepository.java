package com.sysco.miniproject.respository;

import com.sysco.miniproject.data.dao.Cart;
import com.sysco.miniproject.respository.models.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByIdAndUserId(Long id, Long userId);

    @Query(value = "select *\n" +
            "from ( \n" +
            "select c.id, c.name, count(cp.id) as totalItems, IFNULL(sum(cp.quantity * p.price),0) as totalPrice, max(cp.modified_date) as lastUpdateDate\n" +
            "from cart c left join cart_product cp on c.id = cp.cart_id left join product p on cp.product_id = p.id\n" +
            "where c.user_id =  :userId\n" +
            "group by c.id\n" +
            ") as T\n" +
            "order by T.lastUpdateDate desc", nativeQuery = true)
    List<CartDetails> getUsersAllCarts(@Param("userId") Long userId);

    void deleteByIdAndUserId(Long cartId, Long userId);
}
