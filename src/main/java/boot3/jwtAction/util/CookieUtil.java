package boot3.jwtAction.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {
    // 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response,
                                 String name, String value,
                                 int maxAge) {
        // 새로운 쿠키 객체를 생성함. 이름과 값을 설정.
        Cookie cookie = new Cookie(name, value);

        // 쿠키의 경로를 설정한다.
        // "/"는 웹 애플리케이션 전체에서 사용 가능한 쿠키를 의미.
        cookie.setPath("/");

        // 쿠키의 만료 기간을 설정. maxAge는 초 단위.
        cookie.setMaxAge(maxAge);

        // HttpServletResponse를 사용하여 쿠키를 응답에 추가.
        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력받아 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String name) {
        // 현재 요청에서 사용 가능한 모든 쿠키를 가져온다.
        Cookie[] cookies = request.getCookies();

        // 쿠키가 없는 경우 아무 작업도 하지 않고 함수를 종료.
        if (cookies == null) {
            return;
        }

        // 요청에서 가져온 쿠키 목록을 반복하면서
        // 입력된 이름과 일치하는 쿠키를 찾는다.
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                // 해당 쿠키의 값을 비운다.
                cookie.setValue("");

                // 쿠키의 경로를 설정.
                // 이는 추가 쿠키를 삭제하기 위한 경로와 일치해야 한다.
                cookie.setPath("/");

                // 쿠키의 만료 기간을 0으로 설정하여
                // 해당 쿠키를 즉시 만료시킨다.
                cookie.setMaxAge(0);

                // HttpServletResponse를 사용하여
                // 수정된 쿠키를 응답에 추가하여 삭제한다.
                response.addCookie(cookie);
            }
        }
    }

    // 객체를 직렬화해 쿠키의 값으로 변환
    public static String serialize(Object obj) {
        // 객체를 직렬화하고 Base64로 인코딩하여 문자열로 반환한다.
        return Base64
                .getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    // 쿠키를 역직렬화해 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> tClass) {
        // 쿠키의 값을 Base64로 디코딩하고,
        // 역직렬화하여 지정된 클래스로 변환한다.
        return tClass.cast(SerializationUtils
                .deserialize(Base64.getUrlDecoder().decode(cookie.getValue()))
        );
    }
}