package hello.cos.blog.web.controller.api;

import hello.cos.blog.service.UserService;
import hello.cos.blog.web.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseEntity<Long> save(@RequestBody CreateUserDto createUserDto) {
        Long userID = userService.save(createUserDto.getUsername(), createUserDto.getPassword(), createUserDto.getEmail());
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }


}
