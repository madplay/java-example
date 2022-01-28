package com.madplay.javaexample.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 스레드 로컬 테스트
 *
 * @author madplay
 */
public class ThreadLocalTest {
	static class MadThread extends Thread {
		private static final ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "defaultName");
		private final String name;

		public MadThread(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			System.out.printf("%s Started,  ThreadLocal: %s%n", name, threadLocal.get());
			threadLocal.set(name);
			System.out.printf("%s Finished, ThreadLocal: %s%n", name, threadLocal.get());
			threadLocal.remove(); // `remove` 메서드를 호출해준다.
		}
	}

	private final ExecutorService executorService = Executors.newFixedThreadPool(3);

	public void runTest() {
		for (int threadCount = 1; threadCount <= 5; threadCount++) {
			final String name = "thread-" + threadCount;
			final MadThread thread = new MadThread(name);
			executorService.execute(thread);
		}

		executorService.shutdown();

		while (true) {
			try {
				if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
					break;
				}
			} catch (InterruptedException e) {
				System.err.println("Error: " + e);
				executorService.shutdownNow();
			}
		}
		System.out.println("All threads are finished");
	}

	public static void main(String[] args) {
		new ThreadLocalTest().runTest();
	}
}

