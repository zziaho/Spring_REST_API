package org.ziaho.ziahorestapi.controller.v1;

//import io.swagger.annotations.ApiParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ziaho.ziahorestapi.entity.User;
import org.ziaho.ziahorestapi.model.response.CommonResult;
import org.ziaho.ziahorestapi.model.response.ListResult;
import org.ziaho.ziahorestapi.model.response.SingleResult;
import org.ziaho.ziahorestapi.repo.UserJpaRepo;
import org.ziaho.ziahorestapi.service.ResponseService;

@Tag(name = "1. User", description = "UserController") // UserController를 대표하는 최상단 타이틀 영역에 표시될 값을 세팅합니다.
@RequiredArgsConstructor // class상단에 선언하면 class내부에 final로 선언된 객체에 대해서 Constructor Injection(의존성 주입)을 수행합니다.
                        // 해당 어노테이션을 사용하지 않고 선언된 객체에 @Autowired를 사용해도 됩니다.
@RestController // 결과 데이터를 JSON형식으로 내보냅니다.
@RequestMapping(value = "/v1") // api resource를 버전별로 관리하기 위해 /v1 을 모든 리소스 주소에 적용되도록 합니다.
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService; // 결과를 처리할 Service

    @Operation(summary = "회원 리스트 조회", description = "모든 회원을 조회한다.") // 각각의 리소스에 제목과 설명을 표시하기 위해 세팅합니다.
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() { // user테이블에 있는 데이터를 모두 읽어옵니다. 데이터가 한개 이상 일 수 있으므로 리턴타입은 List<User>로 선언합니다.
        // Jpa를 사용하면 기본적으로 CRUD에 대해서는 별다른 설정없이 쿼리를 질의할 수 있도록 메소드를 지원합니다.
        // select * from user와 같음

        // 결과 데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @Operation(summary = "회원 단건 조회", description = "msrl로 회원을 조회한다.")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(@Parameter(name = "회원번호", required = true) @PathVariable long msrl) throws Exception {
        // 결과 데이터가 단일건인 경우 getSingleResult를 이용하여 결과를 출력
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(Exception::new));
    }

    @Operation(summary = "회원 입력", description = "회원을 입력한다.") // 각각의 리소스에 제목과 설명을 표시하기 위해 세팅합니다.
    @PostMapping(value = "/user")
    public SingleResult<User> save(@Parameter(name = "회원아이디", required = true) @RequestParam(value = "uid") String uid,
                                  @Parameter(name = "이름", required = true) @RequestParam(value = "name") String name) { // 파라미터에 대한 설명을 보여주기 위해 세팅합니다.
        // user 테이블에 데이터 1건을 입력합니다.
        // Jpa를 사용하면 기본적으로 CRUD에 대해서는 별다른 설정없이 쿼리를 질의할 수 있도록 메소드를 지원합니다.
        // insert into user(msrl, name, uid) values(null, ?, ?)와 같음
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        // 결과 데이터가 단일건일경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @Operation(summary = "회원 수정", description = "회원정보를 수정한다.")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @Parameter(name = "회원번호", required = true) @RequestParam long msrl,
            @Parameter(name = "회원 아이디", required = true) @RequestParam String uid,
            @Parameter(name = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();

        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @Operation(summary = "회원 삭제", description = "msrl로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @Parameter(name = "회원번호", required = true) @PathVariable long msrl) {
        userJpaRepo.deleteById(msrl);
        // 성공 결과 정보만 필요한 경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }

}
