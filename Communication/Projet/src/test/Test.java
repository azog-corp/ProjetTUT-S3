package test;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;

public class Test {

	static PipedWriter ecr = new PipedWriter();
	
	public PipedWriter getEcr() {
		return ecr;
	}
	
	static ArrayList<Thre> listeThread = new ArrayList<Thre>();

	public static void main(String[] args) {
		Thre t = new Thre();
		Thre t2 = new Thre();
		try {
			t.start();
			t2.start();
			listeThread.add(t);
			listeThread.add(t2);
			
			listeThread.get(0).red = new PipedReader(ecr);
			ecr.write("test",0,4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}