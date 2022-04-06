package hello.cos.blog.config.auth;

import hello.cos.blog.model.User;
import hello.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

     private final UserRepository userRepository;

    //스프링이 로그인 요청을 가로챌 때, username, password를 가로채는데 password는 알아서 처리해주고
    //username만 db에 있는지 확인하면 된다.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다"+username) );
        return new PrincipalDetail(principal); //시큐리티 세션에 유저정보 저장됨
    }
}
