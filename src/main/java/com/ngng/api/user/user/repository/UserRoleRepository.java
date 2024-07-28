package com.ngng.api.user.user.repository;

import com.ngng.api.user.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(String roleType);
}
