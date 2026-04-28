package com.gympro.gymprobackend.repository;

import com.gympro.gymprobackend.entity.ERole;
import com.gympro.gymprobackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
