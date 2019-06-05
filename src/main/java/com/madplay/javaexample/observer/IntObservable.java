package com.madplay.javaexample.observer;

import java.util.Observable;
import java.util.stream.IntStream;

/**
 * @author madplay
 */
class IntObservable extends Observable implements Runnable {

	@Override
	public void run() {
		IntStream.range(0, 10).forEach(i -> {
			setChanged();
			notifyObservers(i);
		});
	}
}
