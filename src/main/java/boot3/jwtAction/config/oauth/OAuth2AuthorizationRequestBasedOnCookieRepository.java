package boot3.jwtAction.config.oauth;

import boot3.jwtAction.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

public class OAuth2AuthorizationRequestBasedOnCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    // OAuth2 Authorization Request를 저장할 쿠키의 이름
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";

    // 쿠키 만료 시간 (초)
    private final static int COOKIE_EXPIRE_SECONDS = 18000;

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        // Authorization Request를 삭제하고 반환
        return this.loadAuthorizationRequest(request);
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        // 저장된 쿠키에서 Authorization Request를 불러옴
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            // Authorization Request가 없으면 쿠키 삭제
            removeAuthorizationRequestCookies(request, response);
            return;
        }

        // Authorization Request를 쿠키에 저장
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        // 저장된 Authorization Request 쿠키 삭제
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}