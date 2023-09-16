package boot3.jwtAction.config.oauth;

import boot3.jwtAction.domain.User;
import boot3.jwtAction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

// 사용자 정보 조회 및 업데이트 또는 생성
@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
        throws OAuth2AuthenticationException {
        // 1. 요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // OAuth2 유저 정보를 저장 또는 업데이트하고 해당 유저 정보를 반환
        saveOrUpdate(oAuth2User);
        return oAuth2User;
    }

    // 2 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User) {
        // OAuth2 유저로부터 속성(attribute) 맵을 가져온다.
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 이메일과 이름을 맵에서 추출
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // 이메일을 기반으로 사용자를 데이터베이스에서 찾거나 생성.
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name)) // 사용자가 이미 존재하는 경우, 이름을 업데이트
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .build()); // 사용자가 없는 경우, 새로운 사용자를 생성.

        // 사용자 정보를 데이터베이스에 저장하고 업데이트한 사용자 객체를 반환
        return userRepository.save(user);
    }

}
