package hello.cos.blog.service;

import hello.cos.blog.model.LoginType;
import hello.cos.blog.model.User;
import hello.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


    public Long save(String username, String password, String email, LoginType loginType) {
        String encPassword = encoder.encode(password);

        User createdUser = new User(username, encPassword, email, loginType);
        userRepository.save(createdUser);
        return createdUser.getId();
    }

    public Long update(Long userId, String password, String email) {
        User target = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 수정 실패 : 회원정보가 없습니다"));
        String encPassword = encoder.encode(password);
        if(target.getLoginType() == LoginType.NORMAL){
            target.update(encPassword, email);
        }else{
            target.update(email);
        }

        return target.getId();
    }

    public User findUser(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        return user;
    }


}
