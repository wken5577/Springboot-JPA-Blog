package hello.cos.blog.web.controller.api;

import hello.cos.blog.config.auth.PrincipalDetail;
import hello.cos.blog.model.LoginType;
import hello.cos.blog.service.UserService;
import hello.cos.blog.web.dto.CreateUserDto;
import hello.cos.blog.web.dto.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseEntity<Long> save(@RequestBody CreateUserDto createUserDto) {
        Long userID = userService.save(createUserDto.getUsername(), createUserDto.getPassword(), createUserDto.getEmail(), LoginType.NORMAL);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<Long> update(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                       @RequestBody UpdateUserDto updateUserDto, HttpSession session){
        Long id = userService.update(principalDetail.getUser().getId(),
                updateUserDto.getPassword(), updateUserDto.getEmail());

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(principalDetail.getUser().getUsername(), updateUserDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
