package boot3.jwtAction.controller;

import boot3.jwtAction.dto.request.CreateAccessTokenRequest;
import boot3.jwtAction.dto.response.CreateAccessTokenResponse;
import boot3.jwtAction.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
            (@RequestBody CreateAccessTokenRequest request) {

        // TokenService를 사용하여 새로운 액세스 토큰 생성을 요청
        String newAccessToken
                = tokenService.createNewAccessToken(request.getRefreshToken());

        // 생성된 액세스 토큰을 ResponseEntity로 래핑하여 반환
        // CreateAccessTokenResponse 객체는 생성된 액세스 토큰을 포함하는 응답 데이터이다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
