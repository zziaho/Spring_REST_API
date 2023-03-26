package org.ziaho.ziahorestapi.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ziaho.ziahorestapi.advice.exception.CEmailSigninFailedException;
import org.ziaho.ziahorestapi.config.security.JwtTokenProvider;
import org.ziaho.ziahorestapi.entity.User;
import org.ziaho.ziahorestapi.model.response.CommonResult;
import org.ziaho.ziahorestapi.model.response.SingleResult;
import org.ziaho.ziahorestapi.repo.UserJpaRepo;
import org.ziaho.ziahorestapi.service.ResponseService;

import java.util.Collections;

@Tag(name = "1. Sign", description = "SignController")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo; // jpa 쿼리 활용
    private final ResponseService responseService; // JWT 토큰 생성
    private final JwtTokenProvider jwtTokenProvider; // API 요청 결과에 대한 code, message
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    @Operation(summary = "로그인", description = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(
            @Parameter(name = "회원ID : 이메일", required = true) @RequestParam String id,
            @Parameter(name = "비밀번호", required = true) @RequestParam String password) {
        User user = userJpaRepo.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
        if(!passwordEncoder.matches(password, user.getPassword())) { // matches : 평문, 암호문 패스워드 비교하여 boolean 결과 돌려줌
            System.out.println("에러");
            throw new CEmailSigninFailedException();
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @Operation(summary = "가입", description = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult singup(
            @Parameter(name = "회원ID : 이메일", required = true) @RequestParam String id,
            @Parameter(name = "비밀번호", required = true) @RequestParam String password,
            @Parameter(name = "이름", required = true) @RequestParam String name) {
        userJpaRepo.save(User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }

}
