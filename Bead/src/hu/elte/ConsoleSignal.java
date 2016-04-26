package hu.elte;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleSignal<T> extends Signal<T> implements Runnable {

	Consumer<ConsoleSignal> c;

	public static ConsoleSignal<String> lastInput() throws IOException {
		ConsoleSignal<String> result = new ConsoleSignal<String>();
		result.type = "Console";
		result.c = (x) -> x.setValue(consoleRead(result));
		new Thread(result).start();
		return result;

	}

	private static String consoleRead(ConsoleSignal<String> signal) {
		String result = null;
		try {
			InputStreamReader fileInputStream = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(fileInputStream);
			result = bufferedReader.readLine();
		} catch (Exception e) {

		}
		if(result.equals(signal.getValue())){
			signal.giveSignal();
		}		
		return result;
	}

	@Override
	public void run() {
		while(true){
			c.accept(this);
		}
		
	}

}
