package boot3.jwtAction.service;

import boot3.jwtAction.domain.Article;
import boot3.jwtAction.dto.request.AddArticleRequest;
import boot3.jwtAction.dto.request.UpdateArticleRequest;
import boot3.jwtAction.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        // 현재 사용자 이름을 가져와 게시물의 작성자와 비교하고, 다른 경우에 예외 던짐
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    // AddArticleRequest를 사용하여 새 게시물을 생성하고,
    // 지정된 사용자 이름으로 게시물을 저장
    public Article save(AddArticleRequest request, String username) {
        return blogRepository.save(request.toEntity(username));
    }

    // 블로그 리포지토리를 통해 모든 게시물을 조회하여 반환
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 주어진 'id'로 게시물을 조회하고, 게시물이 없으면 예외 던짐
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not fonund: " + id));
    }

    // 주어진 'id'로 게시물을 조회하고, 게시물이 없으면 예외 던짐
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        // 게시물의 작성자를 확인하고, 작성자와 현재 사용자가 다르면 예외 던짐
        authorizeArticleAuthor(article);
        // 게시물 삭제
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        // 주어진 'id'로 게시물을 조회하고, 게시물이 없으면 예외 던짐
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        // 게시물의 작성자를 확인하고, 작성자와 현재 사용자가 다르면 예외 던짐
        authorizeArticleAuthor(article);
        // 게시물의 내용을 업데이트하고 업데이트된 게시물을 반환
        article.update(request.getTitle(), request.getContent());

        return article;
    }
}