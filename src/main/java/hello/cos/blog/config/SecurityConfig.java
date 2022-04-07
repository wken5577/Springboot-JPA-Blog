package hello.cos.blog.config;


import hello.cos.blog.config.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근 시 권한 및 인증을 미리 체크
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //시큐리티가 로그인할 때 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야 같은 해쉬로 암호화해서
    //db에있는 해쉬랑 비교 가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf 토큰 비활성화 (테스트할때 거는게 좋다.)
                .authorizeRequests()
                    .antMatchers("/auth/**", "/js/**", "/css/**", "/image/**", "/")
                    .permitAll() //모두허용
                    .anyRequest()
                    .authenticated() //인가된 사용자만
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm") //인가되지 않은 페이지를 요철할 때에 이동할 경로
                    .loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 오는 로그인요청을 가로채고 대신 로그인한다.
                    .defaultSuccessUrl("/");
    }


}
