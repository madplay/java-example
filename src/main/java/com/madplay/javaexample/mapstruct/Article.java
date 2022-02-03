package com.madplay.javaexample.mapstruct;

import lombok.Builder;
import lombok.Getter;

/**
 * @author madplay
 */
@Getter
@Builder
public class Article {
	private Integer id;
	private ArticleType type;
	private String title;
	private String writer;
	private String createDate;
}
