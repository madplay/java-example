package com.madplay.javaexample.opeartor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

/**
 * reactivestreams
 *
 * @author madplay
 */
@Slf4j // lombok logging. dependency 추가 필요
public class PubSub {
	public static void main(String[] args) {
		Publisher<Integer> publisher = getPublisher(Stream.iterate(1, value -> value + 1)
			.limit(10)
			.collect(Collectors.toList()));

		Publisher<String> mapPublisher = mapPublisher(publisher, s -> "[" + s + "]");
		mapPublisher.subscribe(getLoggingSubscriber());
	}

	private static <T, R> Publisher<R> mapPublisher(Publisher<T> publisher, Function<T, R> function) {
		return subscriber -> publisher.subscribe(new DelegateSubscriber<T, R>(subscriber) {
			@Override
			public void onNext(T i) {
				subscriber.onNext(function.apply(i));
			}
		});
	}

	private static <T> Subscriber<T> getLoggingSubscriber() {
		return new Subscriber<T>() {
			@Override
			public void onSubscribe(Subscription subscription) {
				log.debug("onSubscribe");
				subscription.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(T integer) {
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
		};
	}

	private static Publisher<Integer> getPublisher(List<Integer> iter) {
		return subscriber -> {
			Iterable<Integer> iterable = iter;

			subscriber.onSubscribe(new Subscription() {
				@Override
				public void request(long n) {
					try {
						iterable.forEach(sub -> subscriber.onNext(sub));
						subscriber.onComplete();
					} catch (Throwable t) {
						subscriber.onError(t);
					}
				}

				@Override
				public void cancel() {

				}
			});
		};
	}
}

/**
 * @author madplay
 */
class DelegateSubscriber<T, R> implements Subscriber<T> {

	private Subscriber subscriber;

	public DelegateSubscriber(Subscriber<? super R> subscriber) {
		this.subscriber = subscriber;
	}

	@Override
	public void onSubscribe(Subscription s) {
		subscriber.onSubscribe(s);

	}

	public void onNext(T i) {
		subscriber.onNext(i);

	}

	@Override
	public void onError(Throwable t) {
		subscriber.onError(t);

	}

	@Override
	public void onComplete() {
		subscriber.onComplete();
	}
}

