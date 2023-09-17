package boot3.jwtAction.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
public class JwtFactory {

    // JWT 토큰의 "subject" 클레임(claim)에 사용될 값을 저장하는 필드.
    private String subject = "test@email.com";

    // JWT 토큰의 "issuedAt" 클레임에 사용될 값을 저장하는 필드.
    // 현재 시간으로 초기화 된다.
    private Date issuedAt = new Date();

    // JWT 토큰의 "expiration" 클레임에 사용될 값을 저장하는 필드.
    // 현재 시간에서 14일 뒤의 시간으로 초기화 된다.
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());

    // JWT 토큰의 추가 클레임을 저장하는 Map 필드.
    // 초기값은 빈 Map.
    private Map<String, Object> claims = emptyMap();


    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration,
                      Map<String, Object> claims) {
        // 생성자 매개변수로 전달된 값이 null이 아니면 해당 값으로 필드를 초기화하고,
        // null이면 기본값을 사용한다.
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    // 기본값을 가진 JwtFactory 객체를 생성하는 정적 메서드.
    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    // 주어진 JwtProperties와 함께 JWT 토큰을 생성하는 메서드.
    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject) // 토큰의 subject 클레임 설정
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // JWT 타입 설정
                .setIssuer(jwtProperties.getIssuer()) // 토큰 발급자(issuer) 설정
                .setIssuedAt(issuedAt) // 토큰 발급 시간 설정
                .setExpiration(expiration) // 토큰 만료 시간 설정
                .addClaims(claims) // 추가 클레임 설정
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 알고리즘을 HS256로 설정
                .compact(); // JWT 문자열로 변환하여 반환
    }
}