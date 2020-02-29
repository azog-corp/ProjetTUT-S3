package test;

import java.io.IOException;
import java.io.PipedReader;

public class Thre extends Thread{
	
	PipedReader red = new PipedReader();
	
	public PipedReader getRed() {
		return red;
	}
	
@Override
	public void run() {
		System.out.println("Thread");
		try {
			int test = red.read();
			System.out.println((char)test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
