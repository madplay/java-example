package com.madplay.javaexample.mapstruct;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author madplay
 */
@RequiredArgsConstructor
@Getter
public enum ArticleType {
	TEXT(0), PHOTO(1), VIDEO(2);

	private final Integer code;

	public static ArticleType findByCode(Integer code) {
		return Arrays.stream(values())
			.filter(type -> type.getCode().equals(code))
			.findFirst().orElse(null);
	}
}
