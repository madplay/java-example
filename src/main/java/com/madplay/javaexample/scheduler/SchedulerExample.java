package com.madplay.javaexample.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author madplay
 */
@Slf4j
public class SchedulerExample {
	public static void main(String[] args) {

		Publisher<Integer> pub = subscriber -> subscriber.onSubscribe(new Subscription() {
			@Override
			public void request(long n) {
				log.debug("request()");
				subscriber.onNext(1);
				subscriber.onNext(2);
				subscriber.onNext(3);
				subscriber.onNext(4);
				subscriber.onNext(5);
				subscriber.onComplete();
			}

			@Override
			public void cancel() {

			}
		});

		// publishOn
		// publisher는 빠르고, consumer(subscriber)가 느릴 때
		Publisher<Integer> pubOnPub = sub -> {
			pub.subscribe(new Subscriber<>() {
				// thread pool은 하나여야 한다.
				ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory("pubOn-"));

				@Override
				public void onSubscribe(Subscription s) {
					sub.onSubscribe(s);
				}

				@Override
				public void onNext(Integer integer) {
					es.execute(() -> sub.onNext(integer));
				}

				@Override
				public void onError(Throwable t) {
					es.execute(() -> sub.onError(t));
					es.shutdown();
				}

				@Override
				public void onComplete() {
					es.execute(() -> sub.onComplete());
					es.shutdown();
				}
			});
		};

		pubOnPub.subscribe(new Subscriber<>() {
			@Override
			public void onSubscribe(Subscription s) {
				log.debug("onSubscribe");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer integer) {
				log.debug("onNext: {}", integer);

			}

			@Override
			public void onError(Throwable t) {
				log.debug("onError: {}", t);
			}

			@Override
			public void onComplete() {
				log.debug("onComplete");
			}
		});
		log.debug("Exit");
	}
}
