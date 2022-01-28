package com.madplay.javaexample.threadlocal;

/**
 * 스레드 풀 & 스레드 로컬 테스트
 *
 * @author madplay
 */
public class ThreadPoolTest {
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

	public void runTest() {
		for (int threadCount = 1; threadCount <= 5; threadCount++) {
			final MadThread thread = new MadThread("thread-" + threadCount);
			thread.start();
		}
	}

	public static void main(String[] args) {
		new ThreadPoolTest().runTest();
	}
}

