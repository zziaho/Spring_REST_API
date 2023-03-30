package org.ziaho.ziahorestapi.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ziaho.ziahorestapi.entity.User;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByUid_thenReturnUser() {
        String uid = "test@test.com";
        String name = "test1";

        // given
        userJpaRepo.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode("1234"))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        // when
        Optional<User> user = userJpaRepo.findByUid(uid);
        // then
        assertNotNull(user); // user객체가 null이 아닌지 체크
        assertTrue(user.isPresent()); // user객체의 존재여부 true/false 체크
        assertEquals(user.get().getName(), name); // user 객체의 name과 name변수 값이 같은지 체크
    }

}