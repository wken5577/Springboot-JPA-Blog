package hello.cos.blog.service;

import hello.cos.blog.model.User;
import hello.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

   private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public Long save(String username, String password, String email) {
        String encPassword = encoder.encode(password);

        User createdUser = new User(username, encPassword, email);
        userRepository.save(createdUser);
        return createdUser.getId();
    }

}
