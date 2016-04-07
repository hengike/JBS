package hu.elte;

import java.io.Console;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleSignal extends Signal<String>{

	public ConsoleSignal(){
		this.value = "";
	}
	
	@Override
	public void run() {
		while (true) {
			this.value = new Scanner(System.in).nextLine();
			Consumer<String> c = (x) -> System.out.print( this.value);
			c.accept("");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
