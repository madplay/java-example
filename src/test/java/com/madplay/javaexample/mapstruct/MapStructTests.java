package com.madplay.javaexample.mapstruct;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author madplay
 */
@SpringBootTest
public class MapStructTests {

	@Test
	public void testMapStruct() {
		final Article mockArticle = Article.builder()
			.id(1)
			.title("What is MapStruct?")
			.type(ArticleType.TEXT)
			.writer("madplay")
			.createDate("2022-02-04")
			.build();

		final ArticleEntity articleEntity = ArticleMapper.INSTANCE.toArticleDto(mockArticle);

		assertEquals(articleEntity.getId(), mockArticle.getId());
		assertEquals(articleEntity.getTitle(), mockArticle.getTitle());
		assertEquals(articleEntity.getArticleTypeCode(), mockArticle.getType().getCode());
		assertEquals(articleEntity.getAuthor(), mockArticle.getWriter());
		assertNull(articleEntity.getCreateDate());
	}
}
