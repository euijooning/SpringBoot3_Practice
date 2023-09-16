package boot3.jwtAction.dto.request;

import boot3.jwtAction.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity(String author) {
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
    /*
    이 메서드는 주로 DTO(Data Transfer Object)를
    엔티티로 변환하기 위한 유틸리티 메서드로 사용됩니다.
    주로 데이터를 전송하거나 저장하기 위한 데이터 객체와
    데이터베이스나 비즈니스 로직에서 사용되는 엔티티 객체 간의 변환을 단순화하는 데 사용됩니다.
    여기서 toEntity() 메서드는 아마도 어떤 데이터 전송 객체(DTO)의 메서드
     */
}