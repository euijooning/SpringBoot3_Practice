package boot3.jwtAction.repository;

import boot3.jwtAction.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
