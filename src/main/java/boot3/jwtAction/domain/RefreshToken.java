package boot3.jwtAction.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;


    public RefreshToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }


    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;

        return this;
    }
    /**
     * 주어진 코드에서 update 메서드 내부에서 사용되고 있는 this 키워드는 현재 인스턴스를 나타냅니다.
     * 이 메서드는 주로 객체 내의 필드 또는 속성을 업데이트하는 데 사용됩니다.
     *
     * 여기서 update 메서드는
     * newRefreshToken 매개변수로 전달된 새로운 리프레시 토큰 값을 사용하여 refreshToken 필드를 업데이트합니다.
     * this.refreshToken = newRefreshToken;라인은
     * 현재 RefreshToken 객체의 refreshToken 필드를 newRefreshToken 값으로 설정하는 역할을 합니다.
     *
     * 그리고 마지막으로 return this; 문장은 메서드가 현재 객체 자체를 반환함을 의미합니다.
     */
}
