package com.madplay.javaexample.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

/**
 * @author madplay
 */
@Slf4j
public class IntervalExample {
	public static void main(String[] args) {
		Publisher<Integer> pub = sub -> sub.onSubscribe(new Subscription() {
			int number = 0;
			boolean cancelled = false;

			@Override
			public void request(long n) {
				ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
				exec.scheduleAtFixedRate(() -> {
					if (cancelled) {
						exec.shutdown();
						return;
					}
					sub.onNext(number++);
				}, 0, 300, TimeUnit.MILLISECONDS);

			}

			@Override
			public void cancel() {
				cancelled = true;
			}
		});

		Publisher<Integer> takePub = sub -> pub.subscribe(new Subscriber<>() {
			int count = 0;
			Subscription subsc;

			@Override
			public void onSubscribe(Subscription s) {
				subsc = s;
				sub.onSubscribe(s);
			}

			@Override
			public void onNext(Integer integer) {
				sub.onNext(integer);
				if (++count > 9) {
					subsc.cancel();
				}
			}

			@Override
			public void onError(Throwable t) {
				sub.onError(t);
			}

			@Override
			public void onComplete() {
				sub.onComplete();
			}
		});



		takePub.subscribe(new Subscriber<>() {
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
	}
}
