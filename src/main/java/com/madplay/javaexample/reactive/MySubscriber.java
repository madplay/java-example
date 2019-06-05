package com.madplay.javaexample.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * @author madplay
 */
public class MySubscriber implements Subscriber {

	Subscription subscription;

	@Override
	public void onSubscribe(Flow.Subscription subscription) {
		System.out.println("onSubscribe(" + Thread.currentThread().getName() + ")");
		this.subscription = subscription;

		// 개수 만큼 요청
		this.subscription.request(1);
	}

	@Override
	public void onNext(Object item) {
		System.out.println("onNext(" + Thread.currentThread().getName() + "): " + item);
		this.subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		System.out.println("onError: " + throwable.getMessage());
	}

	@Override
	public void onComplete() {
		System.out.println("onComplete(" + Thread.currentThread().getName() + ")");
	}
}
