package com.madplay.javaexample.reactive;

import java.util.concurrent.Flow.Subscriber;

/**
 * Publisher, Subscriber
 * 토비의 봄 TV 관련 내용
 *
 * @author madplay
 */
public class PubSubTest {
	public static void main(String[] args) {

		MyPublisher publisher = new MyPublisher();
		Subscriber subscriber = new MySubscriber();

		publisher.subscribe(subscriber);
	}
}
