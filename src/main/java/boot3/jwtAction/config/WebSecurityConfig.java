package boot3.jwtAction.config;

import boot3.jwtAction.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

// 시큐리티 설정하기
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userService;


    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .requestMatchers("/login", "/signup", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/articles")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
/**
 * @Bean public WebSecurityCustomizer configure() {...}
 * : Spring Security의 웹 보안 구성을 정의하는 메서드입니다.
 * H2 데이터베이스 콘솔 접근과 정적 자원(/static/**)에 대한 보안 설정을 구성합니다.
 *
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) {...}
 * : Spring Security의 보안 필터 체인을 정의하는 메서드입니다. 주요 보안 규칙과 권한 설정을 구성합니다.
 *
 * .authorizeRequests()...: 요청에 대한 권한을 설정합니다.
 * "/login", "/signup", "/user"와 같은 경로는 모든 사용자에게 허용되며, 그 외의 모든 요청은 인증된 사용자에게만 허용됩니다.
 *
 * .formLogin()...: 로그인 관련 설정을 구성합니다.
 * 로그인 페이지 경로(/login)와 로그인 성공 후 이동할 경로(/articles)를 지정합니다.
 *
 * .logout()...: 로그아웃 관련 설정을 구성합니다.
 * 로그아웃 시 세션을 무효화하고 로그아웃 성공 후 이동할 경로(/login)를 지정합니다.
 *
 * .csrf().disable(): CSRF(Cross-Site Request Forgery) 보호를 비활성화합니다.
 * CSRF 공격을 방지하기 위한 설정입니다.
 *
 * @Bean public AuthenticationManager authenticationManager
 * (HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) {...}
 * : 인증 관련 설정을 구성하는 메서드입니다.
 *   사용자 인증과 관련된 AuthenticationManager 빈을 정의하고,
 *   사용자 정보를 제공하는 userDetailService와 암호화를 위한 bCryptPasswordEncoder를 설정합니다.
 *
 * @Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {...}
 * : BCrypt 암호화를 위한 BCryptPasswordEncoder 빈을 정의합니다.
 */