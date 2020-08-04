package com.madplay.javaexample.varargs;

/**
 * @author madplay
 */
public class VarargsExample {

	public static void main(String[] args) {

		Tester tester = new Tester();
		tester.sum(1, 2, 3, 4);
	}
}

class Tester {

	public void sum(int var1, int var2, int var3) {

	}

	public void sum(int... var) {

	}

	// Ambiguous method call.
	// 가변인자를 사용한 메서드를 오버로딩하면 컴파일러가 구분하기 어렵다.
//	public void sum(int var1, int... var) {
//
//	}
}