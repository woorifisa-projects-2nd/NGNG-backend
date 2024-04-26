package com.ngng.api.user.repository;

import com.ngng.api.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(String roleType);
}
