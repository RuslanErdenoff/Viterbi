package Viterby;

import java.nio.charset.Charset;


public class StartClass {
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private UI ui;
	public static void main(String[] args){
		StartClass mainClass = new StartClass();
		mainClass.go();
	}
	public void go(){
		ui = new UI();
	}
}

