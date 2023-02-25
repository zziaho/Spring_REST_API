package org.ziaho.ziahorestapi.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ziaho.ziahorestapi.entity.User;
import org.ziaho.ziahorestapi.repo.UserJpaRepo;

import java.util.List;

@RequiredArgsConstructor // class상단에 선언하면 class내부에 final로 선언된 객체에 대해서 Constructor Injection(의존성 주입)을 수행합니다.
                        // 해당 어노테이션을 사용하지 않고 선언된 객체에 @Autowired를 사용해도 됩니다.
@RestController // 결과 데이터를 JSON형식으로 내보냅니다.
@RequestMapping(value = "/v1") // api resource를 버전별로 관리하기 위해 /v1 을 모든 리소스 주소에 적용되도록 합니다.
public class UserController {

    private final UserJpaRepo userJpaRepo;

    @GetMapping(value = "/user")
    public List<User> findAllUser() { // user테이블에 있는 데이터를 모두 읽어옵니다. 데이터가 한개 이상 일 수 있으므로 리턴타입은 List<User>로 선언합니다.
        // Jpa를 사용하면 기본적으로 CRUD에 대해서는 별다른 설정없이 쿼리를 질의할 수 있도록 메소드를 지원합니다.
        // select * from user와 같음
        return userJpaRepo.findAll();
    }

    @PostMapping(value = "/user")
    public User save() {
        // user 테이블에 데이터 1건을 입력합니다.
        // Jpa를 사용하면 기본적으로 CRUD에 대해서는 별다른 설정없이 쿼리를 질의할 수 있도록 메소드를 지원합니다.
        // insert into user(msrl, name, uid) values(null, ?, ?)와 같음
        User user = User.builder()
                .uid("test1@test.com")
                .name("테스트1")
                .build();
        return userJpaRepo.save(user);
    }

}
