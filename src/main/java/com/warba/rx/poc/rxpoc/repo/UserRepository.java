package com.warba.rx.poc.rxpoc.repo;

import com.warba.rx.poc.rxpoc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByCivilId(String civilId);
}
