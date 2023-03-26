package org.ziaho.ziahorestapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ziaho.ziahorestapi.entity.User;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

//    Optional<User> findById(String email);

    Optional<User> findByUid(String email);

}
