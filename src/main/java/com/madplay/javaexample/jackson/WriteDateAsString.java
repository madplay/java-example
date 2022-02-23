package com.madplay.javaexample.jackson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Jackson 에서 날짜 객체를 문자열 형태로 출력하기
 *
 * @author madplay
 */
@Slf4j
public class WriteDateAsString {
	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.enable(SerializationFeature.INDENT_OUTPUT); // 콘솔에 예쁘게 출력

		/*
		 * 아래 주석을 해제 후 실행하면 문자열 형태로 출력한다.
		 */
		// mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// mapper.registerModule(new JavaTimeModule());
	}

	public void runExample() {
		final Instant now = Instant.parse("2022-01-01T00:00:00Z");

		final MultipleClock multipleClock = MultipleClock.builder()
			.date(Date.from(now))
			.localDateTime(LocalDateTime.ofInstant(now, ZoneId.systemDefault()))
			.build();

		try {
			String result = mapper.writeValueAsString(multipleClock);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			log.error("Error :", e);
		}
	}

	public static void main(String[] args) {
		new WriteDateAsString().runExample();
	}
}

@Getter
@Builder
class MultipleClock {
	private Date date;
	private LocalDateTime localDateTime;
}