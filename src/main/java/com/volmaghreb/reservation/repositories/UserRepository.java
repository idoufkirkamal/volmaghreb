package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findByRole(Role role, Pageable pageable);
}
