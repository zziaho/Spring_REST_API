package org.ziaho.ziahorestapi.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable() // rest api이므로 기본설정 사용안함. 기본설정은 비인증 시 로그인폼 화면으로 리다이렉트 된다.
//                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable 처리.
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT token으로 인증하므로 세션은 필요없기에 생성하지 않는다.
//                .and()
//                .authorizeHttpRequests() // 다음 리퀘스트에 대한 사용권한 체크
//                .requestMatchers("/*/signin", "/*/signup").permitAll() // 가입 및 인증 주소는 누구나 접근 가능
//                .requestMatchers(HttpMethod.GET, "helloworld/**").permitAll() // hellowrold로 시작하는 GET 요청 리소스는 누구나 접근 가능
//                .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT token 필터를 id/password 인증 필터 전에 넣는다.
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api이므로 기본설정 사용안함. 기본설정은 비인증 시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable 처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT token으로 인증하므로 세션은 필요없기에 생성하지 않는다.
                .and()
                    .authorizeHttpRequests() // 다음 리퀘스트에 대한 사용권한 체크
                        .requestMatchers("/*/signin", "/*/signup").permitAll() // 가입 및 인증 주소는 누구나 접근 가능
                        .requestMatchers(HttpMethod.GET, "/exception/**", "helloworld/**").permitAll()
                    .requestMatchers("/*/users").hasRole("ADMIN")
                    .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT token 필터를 id/password 인증 필터 전에 넣는다.

                return http.build();
    }

}
