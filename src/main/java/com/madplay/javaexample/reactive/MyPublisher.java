package com.madplay.javaexample.reactive;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author madplay
 */
public class MyPublisher implements Publisher {
	@Override
	public void subscribe(Subscriber subscriber) {
		Iterator<Integer> iterator = IntStream.rangeClosed(1, 5).boxed().iterator();
		ExecutorService es = Executors.newSingleThreadExecutor();

		subscriber.onSubscribe(new Subscription() {
			@Override
			public void request(long n) {

				es.execute(() -> {
					int i = 0;
					try {
						// 람다식 밖의 변수(n) 접근 불가
						while (i++ < n) {
							if (iterator.hasNext()) {
								subscriber.onNext(iterator.next());
							} else {
								// 소비할 데이터 없음
								subscriber.onComplete();
								break;
							}
						}
					} catch (Exception e) {
						subscriber.onError(e);
					}
				});
			}

			@Override
			public void cancel() {

			}
		});
		try {
			es.awaitTermination(10, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			System.err.println(e);
		} finally {
			es.shutdown();
		}
	}
}
