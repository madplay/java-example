package com.madplay.javaexample.mapstruct;

import lombok.Builder;
import lombok.Getter;

/**
 * @author madplay
 */
@Getter
@Builder
public class ArticleEntity {
	private Integer id;
	private Integer articleTypeCode;
	private String title;
	private String author;
	private String createDate;
}
