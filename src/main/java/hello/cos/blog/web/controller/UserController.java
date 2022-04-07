package hello.cos.blog.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.cos.blog.model.KakaoProfile;
import hello.cos.blog.model.LoginType;
import hello.cos.blog.model.OauthToken;
import hello.cos.blog.model.User;
import hello.cos.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Value("${cos.key}")
    private String cosKey;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
   public String joinForm() {
        return "user/joinForm";
   }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) throws JsonProcessingException {

        RestTemplate rt = new RestTemplate();

        //Http헤더 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded");
        headers.add("charset","utf-8");

        //HttpBody만들기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "5faacde146643e5ff95e4b9f85217718");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        //Http헤더와 Body를 HttpEntity에 담는다
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //Http요청 시작, ResponseEntity로 요청 결과값을 받는다
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Content-Type","application/x-www-form-urlencoded");
        headers2.add("Authorization","Bearer " + oauthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);

        String username = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();
        String password = cosKey;
        String email = kakaoProfile.getKakao_account().getEmail();


        User user = userService.findUser(username);
        if(user == null){
            userService.save(username, password, email, LoginType.KAKAO);
        }

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }









}
