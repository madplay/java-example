package com.madplay.javaexample.reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madplay
 */
@RestController
public class SampleController {

	@GetMapping("/hello")
	public Publisher<String> hello(String name) {
		return s -> s.onSubscribe(new Subscription() {
			@Override
			public void request(long n) {
				s.onNext("Hello " + name);
				s.onComplete();
			}

			@Override
			public void cancel() {

			}
		});

	}
}
