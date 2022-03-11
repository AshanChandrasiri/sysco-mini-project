package com.sysco.miniproject.respository;

import com.sysco.miniproject.data.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {



}
