package hello.cos.blog.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends  BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private  String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    public User(String username, String password, String email, LoginType loginType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.loginType = loginType;
        this.role = RoleType.USER;
    }

    public void update(String encPassword, String email) {
        this.password = encPassword;
        this.email = email;
    }

    public void update(String email) {
        this.email = email;
    }

}
