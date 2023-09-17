package boot3.jwtAction.service;

import boot3.jwtAction.config.jwt.TokenProvider;
import boot3.jwtAction.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;


    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외를 발생시킴
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        // 주어진 리프레시 토큰으로부터 사용자 ID 가져옴
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        // 사용자 ID를 사용하여 사용자 정보를 검색
        User user = userService.findById(userId);

        // 사용자 정보를 기반으로 새로운 액세스 토큰을 생성
        // 유효기간 2시간
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
