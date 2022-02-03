package com.madplay.javaexample.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author madplay
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper {
	ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

	@Mappings({
		@Mapping(source = "writer", target = "author"),
		@Mapping(source = "type", target = "articleTypeCode"),
		@Mapping(target = "createDate", ignore = true)
	})
	ArticleEntity toArticleDto(Article article);

	default Integer toArticleTypeCode(ArticleType type) {
		return type.getCode();
	}
}
