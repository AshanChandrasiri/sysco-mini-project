package com.sysco.miniproject.respository;

import com.sysco.miniproject.data.dao.Role;
import com.sysco.miniproject.data.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String role);

}
