package hello.cos.blog.web.controller;

import hello.cos.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/joinForm")
   public String joinForm() {
        return "user/joinForm";
   }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }









}
