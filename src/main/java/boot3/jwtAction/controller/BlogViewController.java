package boot3.jwtAction.controller;

import boot3.jwtAction.domain.Article;
import boot3.jwtAction.dto.ArticleListViewResponse;
import boot3.jwtAction.dto.ArticleViewResponse;
import boot3.jwtAction.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        // 블로그 글 목록을 조회하고, 각 글을 ArticleListViewResponse 객체로 변환하여 리스트로 저장
        List<ArticleListViewResponse> articleList
                = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();

        // 블로그 글 목록을 뷰에서 사용할 수 있도록 모델에 추가
        model.addAttribute("articles", articleList); // 블로그 글 리스트 저장

        // "articleList" 뷰를 반환
        return "articleList"; // 뷰 조회
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        // 요청된 ID에 해당하는 블로그 글을 블로그 서비스를 통해 조회
        Article article = blogService.findById(id);
        // 조회한 블로그 글을 "article"라는 이름으로 모델에 추가
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article"; // "article" 뷰를 반환
    }
}
