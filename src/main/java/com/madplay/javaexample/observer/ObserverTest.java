package com.madplay.javaexample.observer;

import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Observable
 * 토비의 봄 TV 관련 내용
 *
 * @author madplay
 */
public class ObserverTest {

	public static void main(String[] args) {
		Observer ob = (o, arg) -> System.out.println(Thread.currentThread().getName() + " " + arg);

		IntObservable io = new IntObservable();
		io.addObserver(ob);

		// 별도 스레드에서 수행되게 한다.
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(io);

		System.out.println(Thread.currentThread().getName() + " EXIT");
		es.shutdown();
	}
}

