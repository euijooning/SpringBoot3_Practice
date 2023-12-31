package boot3.jwtAction.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password") // OAuth 쓰면서 nullable = false 속성 삭제
    private String password;


    // 사용자 이름
    @Column(name = "nickname", unique = true)
    private String nickname;


    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname; // 추가
    }


    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override // 고유한 값 - 사용자의 id를 반환
    public String getUsername() {
        return email;
    }

    @Override  // 사용자의 패스워드를 반환
    public String getPassword() {
        return password;
    }

    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {  // 만료되었는지 확인하는 로직
        return true; // true -> 유효한 상태임
    }

    @Override // 계정 잠금 여부 반환
    public boolean isAccountNonLocked() { //계정이 잠겼는지 확인하는 로직
        return true; // 잠기지 않았음
    }

    @Override // 패스워드의 만료 여부 반환
    public boolean isCredentialsNonExpired() {  // 패스워드가 만료되었는지 확인하는 로직
        return true; // 만료되지 않았음(true)
    }

    @Override // 계정 사용 가능 여부 반환
    public boolean isEnabled() { // 계정이 사용 가능한지 확인하는 로직
        return true; // 사용 가능(true)
    }


    // 사용자 이름 변경
    public User update(String nickname) {
        this.nickname = nickname;

        return this;
    }
}
