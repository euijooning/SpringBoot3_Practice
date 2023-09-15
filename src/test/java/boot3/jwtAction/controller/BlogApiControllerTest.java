package boot3.jwtAction.controller;

import boot3.jwtAction.domain.Article;
import boot3.jwtAction.dto.request.AddArticleRequest;
import boot3.jwtAction.dto.request.UpdateArticleRequest;
import boot3.jwtAction.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("블로그 글 추가")
    @Test
    public void addArticle() throws Exception {
        // given
        String url = "/api/articles";
        String title = "title";
        String content = "content";
        AddArticleRequest request = new AddArticleRequest(title, content);

        String requestBody = objectMapper.writeValueAsString(request);

        // when
        // 설정한 내용을 바탕으로 요청을 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지를 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("블로그 글 목록 조회")
    @Test
    public void findAllArticles() throws Exception {
        // given
        String url = "/api/articles";
        String title = "title";
        String content = "content";

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("블로그 글 조회")
    @Test
    public void findArticle() throws Exception {
        // given
        String url = "/api/articles/{id}";
        String title = "title";
        String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("블로그 글 삭제")
    @Test
    public void deleteArticle() throws Exception {
        // given
        String url = "/api/articles/{id}";
        String title = "title";
        String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("블로그 글 수정")
    @Test
    public void updateArticle() throws Exception {
        // given
        String url = "/api/articles/{id}";
        String title = "title";
        String content = "content";

        Article savedArticle = blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        String newTitle = "new title";
        String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

}