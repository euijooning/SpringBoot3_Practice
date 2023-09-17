package boot3.jwtAction.controller;

import boot3.jwtAction.domain.Article;
import boot3.jwtAction.dto.request.AddArticleRequest;
import boot3.jwtAction.dto.request.UpdateArticleRequest;
import boot3.jwtAction.dto.response.ArticleResponse;
import boot3.jwtAction.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;


    @PostMapping("/api/articles")
    //요청 본문 값 매핑
    // HTTP POST 요청을 처리하여 블로그 글을 추가.
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());
        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        // 블로그 서비스를 통해 모든 게시물을 조회하고, 각 게시물을 ArticleResponse로 매핑
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        // 조회된 게시물 목록을 ResponseEntity로 래핑하여 반환
        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    // URL 경로에서 'id' 값을 추출하여 메서드의 매개변수로 사용
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {

        // 주어진 'id'로 블로그 서비스를 통해 게시물을 조회
        Article article = blogService.findById(id);
        // 조회된 게시물을 ArticleResponse로 매핑하고 ResponseEntity로 래핑하여 반환
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        // 주어진 'id'로 블로그 서비스를 통해 게시물을 삭제
        blogService.delete(id);
        // 삭제 성공을 나타내는 ResponseEntity를 반환
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id,
                                                 @RequestBody UpdateArticleRequest request) {
        // 주어진 'id'로 블로그 서비스를 통해 게시물을 업데이트하고 업데이트된 게시물을 받음
        Article updatedArticle = blogService.update(id, request);

        // 업데이트된 게시물을 ResponseEntity로 래핑하여 반환
        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}