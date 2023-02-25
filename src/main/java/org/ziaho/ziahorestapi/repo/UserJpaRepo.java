package org.ziaho.ziahorestapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ziaho.ziahorestapi.entity.User;

public interface UserJpaRepo extends JpaRepository<User, Long> {

}
